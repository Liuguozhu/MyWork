package iyunu.NewTLOL.net;

import iyunu.NewTLOL.base.net.socket.LlpChannelHandler;
import iyunu.NewTLOL.manager.ServerManager;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TLOLChannelHandler extends LlpChannelHandler {

	private Logger logger = LoggerFactory.getLogger(TLOLChannelHandler.class);

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		ServerManager.instance().offline(ctx.getChannel(), "玩家下线，断开连接");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {

		try {
			ServerManager.instance().offline(ctx.getChannel(), "异常断开连接TLOLChannelHandler");
		} catch (Exception e2) {
			logger.debug("关闭连接异常");
		}
	}

}
