package net.deechael.invokationtcg.network;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import net.deechael.dutil.gson.JOBuilder;
import net.deechael.dutil.gson.JOReader;
import net.deechael.invokationtcg.network.packet.ClosePacket;
import net.deechael.invokationtcg.network.packet.Packet;
import net.deechael.invokationtcg.network.packet.PacketRegistry;

public class Connection extends SimpleChannelInboundHandler<Object> {

    private final static Gson GSON = new Gson();

    private final NetworkManager networkManager;
    private final Channel channel;
    private final PacketListener listener;
    private WebSocketServerHandshaker handshaker;

    public Connection(NetworkManager networkManager, Channel channel, PacketListener listener) {
        this.networkManager = networkManager;
        this.channel = channel;
        this.listener = listener;
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public boolean isConnected() {
        return this.channel.isOpen();
    }

    public void disconnect() {
        JOBuilder<?> builder = new JOBuilder<>(null);
        builder.string("packetId", "close");
        builder.string("message", "Remote server closed your connection");
        this.channel.writeAndFlush(new TextWebSocketFrame(GSON.toJson(builder.build())));
        this.channel.writeAndFlush(new CloseWebSocketFrame());
        try {
            this.channel.closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendPacket(Packet packet) {
        if (packet instanceof ClosePacket) {
            disconnect();
        } else {
            this.channel.writeAndFlush(new TextWebSocketFrame(GSON.toJson(packet.serialize())));
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            handleWebsocketRequest(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest httpRequest) {
        if (!httpRequest.decoderResult().isSuccess() || (!"websocket".equals(httpRequest.headers().get("Upgrade")))) {
            ctx.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory websocketFactory = new WebSocketServerHandshakerFactory("ws://localhost:65322/websocket", null, false);
        this.handshaker = websocketFactory.newHandshaker(httpRequest);
        if (this.handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), httpRequest);
        }
    }

    private void handleWebsocketRequest(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof CloseWebSocketFrame) {
            ctx.close();
        } else if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame());
        } else if (frame instanceof TextWebSocketFrame) {
            JOReader rawPacket = new JOReader(JsonParser.parseString(((TextWebSocketFrame) frame).text()).getAsJsonObject());
            if (!PacketRegistry.hasType(rawPacket.string("packetId")))
                throw new RuntimeException("Unknown packet type");
            else {
                Packet packet = PacketRegistry.createPacket(rawPacket.original(),
                        PacketRegistry.getType(rawPacket.string("packetId")));

            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
