package com.geekbrains.netty.example.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;


import java.net.InetSocketAddress;

public class NettyNetwork {
    private static NettyNetwork ourInstance = new NettyNetwork();


    public static NettyNetwork getInstance() { return ourInstance;}
    
    private NettyNetwork() {        
    }
    
    private Channel currentChannel;

    public Channel getCurrentChannel() { return currentChannel; }
    
    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap clientBootstrap = new Bootstrap();
            clientBootstrap.group(group);
            clientBootstrap.channel(NioSocketChannel.class);
            clientBootstrap.remoteAddress(new InetSocketAddress("localhost", 8189));
            clientBootstrap.handler((ChannelInitializer) (socketChannel) -> {
                socketChannel.pipeline().addLast();
                currentChannel = socketChannel;
            });
            ChannelFuture channelFuture = clientBootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                group.shutdownGracefully().sync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void sendData() {
            ByteBufAllocator allocator = new PooledByteBufAllocator();
            ByteBuf buf = allocator.buffer(1024);

            buf.writeByte(15);

            buf.writeLong(8L);

            for(byte b : "test.txt".getBytes()) {
                buf.writeByte(b);
            }
            currentChannel.writeAndFlush(buf);
        }
        public boolean isConnectionOpened() {
            return currentChannel != null && currentChannel.isActive();
        }
        public void closeConnection() {
            currentChannel.close();
        }
}
