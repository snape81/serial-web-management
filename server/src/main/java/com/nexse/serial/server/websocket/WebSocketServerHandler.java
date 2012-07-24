/*
 * Copyright 2011 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.nexse.serial.server.websocket;

import com.nexse.serial.server.bean.MarkSenseCard;
import com.nexse.serial.server.exchange.EventMarkSenseExchange;
import com.nexse.serial.server.exchange.EventStringExchange;
import com.nexse.serial.server.printer.SportWetteMarkSenseCardsPrinter;
import flexjson.JSONSerializer;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.websocketx.*;
import org.jboss.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static org.jboss.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static org.jboss.netty.handler.codec.http.HttpHeaders.setContentLength;
import static org.jboss.netty.handler.codec.http.HttpMethod.GET;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.*;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Handles handshakes and messages
 */
public class WebSocketServerHandler extends SimpleChannelUpstreamHandler {
    static final Logger logger = LoggerFactory.getLogger(WebSocketServerHandler.class);
    private EventMarkSenseExchange dataRead;
    private EventStringExchange dataToPrint;

    private static final String WEBSOCKET_PATH = "/websocket";

    private WebSocketServerHandshaker handshaker;

    private Thread writingThread;

    public WebSocketServerHandler(EventMarkSenseExchange dataRead, EventStringExchange dataToPrint) {
        this.dataRead = dataRead;
        this.dataToPrint = dataToPrint;

    }


    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {

        Object msg = e.getMessage();
        if (msg instanceof HttpRequest) {
            handleHttpRequest(ctx, (HttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, HttpRequest req) throws Exception {
        // Allow only GET methods.
        if (req.getMethod() != GET) {
            sendHttpResponse(ctx, req, new DefaultHttpResponse(HTTP_1_1, FORBIDDEN));
            return;
        }

        // Send the demo page and favicon.ico
        if (req.getUri().equals("/")) {
            HttpResponse res = new DefaultHttpResponse(HTTP_1_1, OK);

            ChannelBuffer content = WebSocketServerIndexPage.getContent(getWebSocketLocation(req));

            res.setHeader(CONTENT_TYPE, "text/html; charset=UTF-8");
            setContentLength(res, content.readableBytes());

            res.setContent(content);
            sendHttpResponse(ctx, req, res);
            return;
        } else if (req.getUri().equals("/favicon.ico")) {
            HttpResponse res = new DefaultHttpResponse(HTTP_1_1, NOT_FOUND);
            sendHttpResponse(ctx, req, res);
            return;
        }

        // Handshake
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                this.getWebSocketLocation(req), null, false);
        this.handshaker = wsFactory.newHandshaker(req);
        if (this.handshaker == null) {
            wsFactory.sendUnsupportedWebSocketVersionResponse(ctx.getChannel());
        } else {
            this.handshaker.handshake(ctx.getChannel(), req).addListener(HANDSHAKE_SCANNER_LISTENER);
        }
    }

    private final  ChannelFutureListener HANDSHAKE_SCANNER_LISTENER = new MyChannelFutureListener(this);

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {

        // Check for closing frame
        if (frame instanceof CloseWebSocketFrame) {
            this.handshaker.close(ctx.getChannel(), (CloseWebSocketFrame) frame);
            return;
        } else if (frame instanceof PingWebSocketFrame) {
            ctx.getChannel().write(new PongWebSocketFrame(frame.getBinaryData()));
            return;
        } else if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
        }

        // Send the uppercase string back.
        String request = ((TextWebSocketFrame) frame).getText();
        logger.debug("Channel {} received {}", ctx.getChannel().getId(), request);

        String result = "Printing Result: ";
        if (request.contains("SPORTWETTE")) {
            try {
                result += SportWetteMarkSenseCardsPrinter.getInstance().printMarkSenseCard();
            } catch (Exception e) {
                result += "Mark Sense Card Printing Error: " + e.getMessage();
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else {
            result += "unknown card's printing map. Now Printing only raw string ";
            dataToPrint.put(request);
        }


        //ctx.getChannel().write(new TextWebSocketFrame("PRINTED --> " + request));
        ctx.getChannel().write(new TextWebSocketFrame(result));
    }


    private void sendHttpResponse(ChannelHandlerContext ctx, HttpRequest req, HttpResponse res) {
        // Generate an error page if response status code is not OK (200).
        if (res.getStatus().getCode() != 200) {
            res.setContent(ChannelBuffers.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8));
            setContentLength(res, res.getContent().readableBytes());
        }

        // Send the response and close the connection if necessary.
        ChannelFuture f = ctx.getChannel().write(res);
        if (!isKeepAlive(req) || res.getStatus().getCode() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }

    private String getWebSocketLocation(HttpRequest req) {
        return "ws://" + req.getHeader(HttpHeaders.Names.HOST) + WEBSOCKET_PATH;
    }


    private String getJsonString(MarkSenseCard msc) {
        JSONSerializer serializer = new JSONSerializer();
        try {

                return  serializer.deepSerialize(msc);

        } catch (Exception e) {
            logger.error("Eccezione nella serializzazione JSON ",e);
            return "error";
        }

    }


    private  class WaitAndWriteScannerString implements Runnable {
        final Logger logger = LoggerFactory.getLogger(WaitAndWriteScannerString.class);

        private Channel chl;


        WaitAndWriteScannerString(Channel chl) {
            this.chl = chl;
        }


        @Override
        public void run() {
                while (true) {
                    try {

                        // ciclo infinito di attesa di ricezione dati


                        MarkSenseCard mess = dataRead.get();

                        logger.debug(" Il server websocket ha letto la mark sense card {}", mess);
                        String jsonSerialization = getJsonString(mess);
                        logger.debug(" JSON: {}",jsonSerialization);
                        if (chl != null && chl.isOpen()) {
                                chl.write(new TextWebSocketFrame(jsonSerialization));
                        } else {
                            logger.error(" Context e' null il messaggio " + mess + " e' stato scartato");
                        }
                    } catch (InterruptedException e) {
                        logger.debug("INTERRUPTED!!!! ");
                        return;
                    }
                }

        }


    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        logger.debug("DISCONNECTED {} ",this.toString());
        this.writingThread.interrupt();

        logger.debug("Thread to kill {}",this.writingThread );
        super.channelDisconnected(ctx, e);    //To change body of overridden methods use File | Settings | File Templates.
    }



    private class MyChannelFutureListener implements ChannelFutureListener {

        private WebSocketServerHandler handler;

        private MyChannelFutureListener(WebSocketServerHandler handler) {
            this.handler = handler;
        }



        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
                        if (!future.isSuccess()) {
                            Channels.fireExceptionCaught(future.getChannel(), future.getCause());
                        } else {
                            logger.debug("CLASS {} STARTING WRITING THREAD ON CHANNEL {}",this.toString(),future.getChannel());
                             Thread myNewWritingThread = new Thread(new WaitAndWriteScannerString(future.getChannel()));
                            handler.setWritingThread(myNewWritingThread);
                            myNewWritingThread.start();
                        }
                    }
    }

    public void setWritingThread(Thread writingThread) {
        this.writingThread = writingThread;
    }
}
