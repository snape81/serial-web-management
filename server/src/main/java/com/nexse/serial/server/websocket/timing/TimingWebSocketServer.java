package com.nexse.serial.server.websocket.timing;

import com.nexse.serial.server.exchange.EventMarkSenseExchange;
import com.nexse.serial.server.exchange.EventStringExchange;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class TimingWebSocketServer {
    static final Logger logger = LoggerFactory.getLogger(TimingWebSocketServer.class);

       private final int port;
       private EventMarkSenseExchange dataReaded;
       private EventStringExchange dataToPrint;

       public TimingWebSocketServer(int port) {
           this.port = port;
       }

       public void run() {
           // Configure the server.
           ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

           // Set up the event pipeline factory.
           bootstrap.setPipelineFactory(new TimingSocketServerPipelineFactory());

           // Bind and start to accept incoming connections.
           bootstrap.bind(new InetSocketAddress(port));

           //logger.debug("Web socket server started at port {} .", port);
           //logger.debug("Open your browser and navigate to http://localhost: {}/ ", port);
       }
}
