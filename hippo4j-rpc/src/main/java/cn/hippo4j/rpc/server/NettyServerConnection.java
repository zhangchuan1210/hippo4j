/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.hippo4j.rpc.server;

import cn.hippo4j.common.toolkit.Assert;
import cn.hippo4j.rpc.coder.NettyDecoder;
import cn.hippo4j.rpc.coder.NettyEncoder;
import cn.hippo4j.rpc.discovery.ServerPort;
import cn.hippo4j.rpc.exception.ConnectionException;
import cn.hippo4j.rpc.handler.AbstractNettyHandlerManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * adapter to the netty server
 */
@Slf4j
public class NettyServerConnection extends AbstractNettyHandlerManager implements ServerConnection {

    ServerPort port;
    EventLoopGroup leader;
    EventLoopGroup worker;
    Class<? extends ServerChannel> socketChannelCls = NioServerSocketChannel.class;
    ChannelFuture future;
    Channel channel;

    public NettyServerConnection(EventLoopGroup leader, EventLoopGroup worker, List<ChannelHandler> handlers) {
        super(handlers);
        Assert.notNull(leader);
        Assert.notNull(worker);
        this.leader = leader;
        this.worker = worker;
    }

    public NettyServerConnection(EventLoopGroup leader, EventLoopGroup worker, ChannelHandler... handlers) {
        this(leader, worker, (handlers != null ? Arrays.asList(handlers) : Collections.emptyList()));
    }

    public NettyServerConnection(ChannelHandler... handlers) {
        this(handlers != null ? Arrays.asList(handlers) : Collections.emptyList());
    }

    public NettyServerConnection(List<ChannelHandler> handlers) {
        this(new NioEventLoopGroup(), new NioEventLoopGroup(), handlers);
    }

    @Override
    public void bind(ServerPort port) {
        ServerBootstrap server = new ServerBootstrap();
        server.group(leader, worker)
                .channel(socketChannelCls)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new NettyEncoder());
                        pipeline.addLast(new NettyDecoder(ClassResolvers.cacheDisabled(null)));
                        handlerEntities.stream()
                                .sorted()
                                .forEach(h -> {
                                    if (h.getName() == null) {
                                        pipeline.addLast(h.getHandler());
                                    } else {
                                        pipeline.addLast(h.getName(), h.getHandler());
                                    }
                                });
                    }
                });
        try {
            this.future = server.bind(port.getPort()).sync();
            this.channel = this.future.channel();
            log.info("The server is started and can receive requests. The listening port is {}", port.getPort());
            this.port = port;
            this.future.channel().closeFuture().sync();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new ConnectionException("Listening port failed, Please check whether the port is occupied", ex);
        }
    }

    @Override
    public synchronized void close() {
        if (port == null) {
            return;
        }
        this.leader.shutdownGracefully();
        this.worker.shutdownGracefully();
        this.channel.close();
        this.future.channel().close();
        log.info("The server is shut down and no more requests are received. The release port is {}", port.getPort());
    }

    @Override
    public boolean isActive() {
        if (channel == null) {
            return false;
        }
        return channel.isActive();
    }

    @Override
    public NettyServerConnection addLast(String name, ChannelHandler handler) {
        super.addLast(name, handler);
        return this;
    }

    @Override
    public NettyServerConnection addFirst(String name, ChannelHandler handler) {
        super.addFirst(name, handler);
        return this;
    }

    @Override
    public NettyServerConnection addLast(ChannelHandler handler) {
        super.addLast(handler);
        return this;
    }

    @Override
    public NettyServerConnection addFirst(ChannelHandler handler) {
        super.addFirst(handler);
        return this;
    }

}
