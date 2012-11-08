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

import com.nexse.serial.server.exchange.EventMarkSenseExchange;
import com.nexse.serial.server.exchange.EventStringExchange;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * A HTTP server which serves Web Socket requests at:
 * 
 * http://localhost:8080/websocket
 * 
 * Open your browser at http://localhost:8080/, then the demo page will be loaded and a Web Socket connection will be
 * made automatically.
 * 
 * This server illustrates support for the different web socket specification versions and will work with:
 * 
 * <ul>
 * <li>Safari 5+ (draft-ietf-hybi-thewebsocketprotocol-00)
 * <li>Chrome 6-13 (draft-ietf-hybi-thewebsocketprotocol-00)
 * <li>Chrome 14+ (draft-ietf-hybi-thewebsocketprotocol-10)
 * <li>Chrome 16+ (RFC 6455 aka draft-ietf-hybi-thewebsocketprotocol-17)
 * <li>Firefox 7+ (draft-ietf-hybi-thewebsocketprotocol-10)
 * <li>Firefox 11+ (RFC 6455 aka draft-ietf-hybi-thewebsocketprotocol-17)
 * </ul>
 */
public class ScannerWebSocketServer {
    static final Logger logger = LoggerFactory.getLogger(ScannerWebSocketServer.class);
    
    private final int port;
    private EventMarkSenseExchange dataReaded;
    private EventStringExchange dataToPrint;

    public ScannerWebSocketServer(int port, EventMarkSenseExchange dataReaded, EventStringExchange dataToPrint) {
        this.dataReaded = dataReaded;
        this.port = port;
        this.dataToPrint = dataToPrint;
    }
    
    public void run() {
        // Configure the server.
        ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

        // Set up the event pipeline factory.
        bootstrap.setPipelineFactory(new WebSocketServerPipelineFactory(dataReaded,dataToPrint));

        // Bind and start to accept incoming connections.
        bootstrap.bind(new InetSocketAddress(port));

        //logger.debug("Web socket server started at port {} .", port);
        //logger.debug("Open your browser and navigate to http://localhost: {}/ ", port);
    }


}
