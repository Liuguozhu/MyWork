package iyunu.NewTLOL.net;

import iyunu.NewTLOL.base.net.socket.LlpDecoder;
import iyunu.NewTLOL.base.net.socket.LlpSender;
import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.manager.ServerManager;

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.timeout.ReadTimeoutHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

public class TLOLChannelPipelineFactory implements ChannelPipelineFactory {

	private final Timer timer;
	private final ChannelHandler timeoutHandler;
	private static final int TIME_OUT = 600;

	/**
	 * 事件链 添加read timeout处理
	 */
	public TLOLChannelPipelineFactory() {
		timer = new HashedWheelTimer();
		// timer must be shared.
		timeoutHandler = new ReadTimeoutHandler(timer, TIME_OUT) {

			@Override
			protected void readTimedOut(ChannelHandlerContext ctx) throws Exception {
				ServerManager.instance().offline(ctx.getChannel(),"超时下线");
			}

		};

	}

	@Override
	public ChannelPipeline getPipeline() throws Exception {

		ChannelHandler msgHandler = Spring.instance().getBean("msgHandler", ChannelHandler.class);
		LlpSender llpSender = Spring.instance().getBean("llpSender", LlpSender.class);
		return Channels.pipeline(llpSender, timeoutHandler, new LlpDecoder(), msgHandler);
	}

}
