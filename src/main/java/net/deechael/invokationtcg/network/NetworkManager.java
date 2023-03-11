package net.deechael.invokationtcg.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import net.deechael.invokationtcg.server.GeniusInvokationTCGServer;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

public class NetworkManager {

    private final GeniusInvokationTCGServer server;
    private final OkHttpClient httpClient;
    private Channel channel;

    public NetworkManager(GeniusInvokationTCGServer server) {
        this.server = server;
        this.httpClient = new OkHttpClient.Builder()
                .build();
    }

    public GeniusInvokationTCGServer getServer() {
        return server;
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(@NotNull Channel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                pipeline.addLast("codec-http", new HttpServerCodec());
                pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
                pipeline.addLast("handler", new Connection(NetworkManager.this, channel, new PacketListener()));
            }
        });

        try {
            this.channel = bootstrap.bind(65322).sync().channel();
            this.channel.closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void close() {
        this.channel.close();
    }

}
