package iyunu.NewTLOL.net.protocol.pay;

import iyunu.NewTLOL.manager.PayManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 斯凯充值
 * @author LuoSR
 * @date 2014年7月21日
 */
public class SikaiPay extends TLOLMessageHandler {

	private String orderInfo;

	@Override
	public void handleReceived(LlpMessage msg) {

		int appVersion = msg.readInt("appVersion");// 应用版本，不能含有特殊符号的整数。
		int price = msg.readInt("price"); // 计费价格，单位：分
		String url = PayManager.instance().getUrl() + ":" + ServerManager.MX4J_PORT;
		String game = PayManager.instance().getGame();

		// 步骤一：初始化订单参数
		String merchantId = PayManager.instance().getMerchantId(); // 斯凯分配的商户号
		String merchantPasswd = PayManager.instance().getMerchantPasswd(); // 斯凯分配的商户密码
		int appId = Integer.valueOf(PayManager.instance().getAppId()); // 斯凯分配的应用编号
		String orderId = String.valueOf(System.currentTimeMillis()); // 订单号，应保证唯一以便日后核对数据
		String notifyAddress = null;
		try {
			notifyAddress = URLEncoder.encode(PayManager.instance().getSikaiPayUrl(), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} // 后台回调地址
		String payMethod = "3rd"; // 设置使用运营商计费还是第三方计费；第三方：3rd 运营商：sms
		String gameType = "1"; // 游戏类型，0：单机 1：联网 2：弱联网
		int systemId = 300021; // 系统号，300024：支付中心合作接入 ； 300021：冒泡市场合作接入
		int payType = 3; // 支付类型，3：充值
		String productName = PayManager.instance().getSubject(); // 商品名称，不需要编码，建议使用中文
		/*
		 * .渠道号：若应用在斯凯渠道推广，则选择填写下列预定义值 a. 冒泡市场：1_zhiyifu_ b. 冒泡游戏：9_zhiyifu_
		 * 
		 * 如果在斯凯以外的渠道推广，以daiji_打头，后面自定义（自定义内容不能含有‘zhiyifu’）
		 */
		String channelId = "18_zhiyifu_"; // 渠道号，方便分渠道统计收入

		// 三个保留字段提供给CP使用，后台通知时将源数据返回，可以为null
		String reserved1 = String.valueOf(online.getId());
		String reserved2 = url;
		String reserved3 = online.getUserId() + "_" + ServerManager.instance().getSrvId() + "_" + game + "_" + online.getPlatform();

		// 步骤二：生成签名，
		/*
		 * 注：1、签名参数有序不能调换!!2、没有出现在订单里的参数，可以不参与签名，如:3个保留字段3、以后新增加的字段，不再需要参与签名
		 */
		StringBuffer sb = new StringBuffer();

		sb.append("merchantId=" + merchantId);
		sb.append("&appId=" + appId);
		sb.append("&notifyAddress=" + notifyAddress);
		sb.append("&appName=" + game);
		sb.append("&appVersion=" + appVersion);
		sb.append("&payType=" + payType);
		sb.append("&price=" + price);
		sb.append("&orderId=" + orderId);
		if (reserved1 != null && !reserved1.equals("")) {
			sb.append("&reserved1=" + reserved1);
		}
		if (reserved2 != null && !reserved2.equals("")) {
			sb.append("&reserved2=" + reserved2);
		}
		if (reserved3 != null && !reserved3.equals("")) {
			sb.append("&reserved3=" + reserved3);
		}
		sb.append("&key=" + merchantPasswd);// 最后添加密钥

		String sigSrc = sb.toString();
		System.out.println("签名数据：" + sigSrc);

		String mySign = null;
		try {
			// 通过MD5计算出签名
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] b = messageDigest.digest(sigSrc.getBytes("utf-8"));

			String HEX_CHARS = "0123456789abcdef";
			sb = new StringBuffer();
			for (byte aB : b) {
				sb.append(HEX_CHARS.charAt(aB >>> 4 & 0x0F));
				sb.append(HEX_CHARS.charAt(aB & 0x0F));
			}
			mySign = sb.toString();

			// 注意：最后需要转化成大写
			mySign = mySign.toUpperCase();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		// 步骤三：组装订单，订单里的参数无序可以随意调换
		orderInfo = "notifyAddress=" + notifyAddress + "&merchantId=" + merchantId + "&appId=" + appId + "&orderId=" + orderId + "&appName=" + game + "&appVersion=" + appVersion + "&price=" + price + "&payMethod=" + payMethod + "&gameType=" + gameType + "&systemId=" + systemId + "&payType="
				+ payType + "&productName=" + productName + "&channelId=" + channelId

				+ "&reserved1=" + reserved1 + "&reserved2=" + reserved2 + "&reserved3=" + reserved3 + "&merchantSign=" + mySign;

		System.out.println("订单数据：" + orderInfo);
		orderInfo = orderInfo.replace("%", "：");
		System.out.println("转换之后订单数据：" + orderInfo);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_sikaiPay");
			message.write("orderInfo", orderInfo);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

	}

}
