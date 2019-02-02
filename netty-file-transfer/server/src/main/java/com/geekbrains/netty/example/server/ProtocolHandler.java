package com.geekbrains.netty.example.server;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class ProtocolHandler extends ChannelInboundHandlerAdapter {
    private int state = -1;
    private HashMap<String, Object> map = new HashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;

        if (state == -1) {
            if (buf.readableBytes() >= 1) {
                map.put("type", buf.readByte());
                state = 0;
            }
        }
        if (state == 0) {
            if (buf.readableBytes() >= 8) {
                state = 1;
                map.put("fileNameLength", buf.readLong());
            }
        }
        if (state == 1) {
            if (buf.readableBytes() >= (Long) map.get("FileNameLength")) {
                byte[] fnb = new byte[((Long) map.get("FileNameLength")).intValue()];
                buf.readBytes(fnb);
                System.out.println(new String(fnb));
            }
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}