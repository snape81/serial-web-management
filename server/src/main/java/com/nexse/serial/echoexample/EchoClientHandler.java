package com.nexse.serial.echoexample;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

  /**
   * Handler implementation for the echo client.  It initiates the ping-pong
   * traffic between the echo client and server by sending the first message to
   * the server.
   */
  public class EchoClientHandler extends SimpleChannelUpstreamHandler {

      private static final Logger logger = Logger.getLogger(
              EchoClientHandler.class.getName());

      private final ChannelBuffer firstMessage;
      private final AtomicLong transferredBytes = new AtomicLong();

      /**
       * Creates a client-side handler.
       */
      public EchoClientHandler(int firstMessageSize) {
          if (firstMessageSize <= 0) {
              throw new IllegalArgumentException(
                      "firstMessageSize: " + firstMessageSize);
          }
          firstMessage = ChannelBuffers.buffer(firstMessageSize);
          for (int i = 0; i < firstMessage.capacity(); i ++) {
              firstMessage.writeByte((byte) i);
          }
      }

      public long getTransferredBytes() {
          return transferredBytes.get();
      }

      @Override
      public void channelConnected(
              ChannelHandlerContext ctx, ChannelStateEvent e) {
          // Send the first message.  Server will not send anything here
          // because the firstMessage's capacity is 0.
          e.getChannel().write(firstMessage);
      }

      @Override
      public void messageReceived(
              ChannelHandlerContext ctx, MessageEvent e) {
          // Send back the received message to the remote peer.
          transferredBytes.addAndGet(((ChannelBuffer) e.getMessage()).readableBytes());
          System.out.println("messaggio: " + e.getMessage());
          e.getChannel().write(e.getMessage());
      }

      @Override
      public void exceptionCaught(
              ChannelHandlerContext ctx, ExceptionEvent e) {
          // Close the connection when an exception is raised.
          logger.log(
                  Level.WARNING,
                  "Unexpected exception from downstream.",
                  e.getCause());
          e.getChannel().close();
      }
  }