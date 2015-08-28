package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.json.PayJson;
import iyunu.NewTLOL.model.pay.PayRatio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public final class PayManager {

	/**
	 * 私有构造方法
	 */
	private PayManager() {
	}

	private static PayManager instance = new PayManager();

	// ==================================所有充值共用=====================================
	/** 游戏 **/
	private String game;
	/** 游戏回调URL **/
	private String url;

	// ==================================支付宝=====================================
	/** 接口名称 **/
	private String service;
	/** 合作者身份ID **/
	private String partner;
	/** 秘钥 **/
	private String privateKey;
	/** 参数编码字符集 **/
	private String charset;
	/** 签名方式 **/
	private String signType;
	/** 商品名称 **/
	private String subject;
	/** 支付类型 **/
	private String paymentType;
	/** 卖家支付宝账号 **/
	private String sellerId;
	/** 服务器异步通知路径（支付宝） **/
	private String alipayUrl;

	// ==================================银联=====================================
	/** 游戏服务器往充值服务器发送http请求url（银联） **/
	private String upmpReqUrl;
	/** 服务器异步通知路径 （银联） **/
	private String upmpUrl;

	// ==================================易宝=====================================
	/** 游戏服务器往充值服务器发送http请求url（易宝） **/
	private String yeeBaoReqUrl;
	/** 服务器异步通知路径 （易宝） **/
	private String yeeBaoUrl;

	// ==================================斯凯=====================================
	/** 商户编号 **/
	private String merchantId;
	/** 商户秘钥 **/
	private String merchantPasswd;
	/** 应用编号 **/
	private String appId;
	/** 服务器异步通知路径 （斯凯） **/
	private String sikaiPayUrl;
	// ==================================百度=====================================
	/** 应用编号 **/
	private String baiduAppId;
	/** 应用key **/
	private String productKey;
	/** 应用秘钥 **/
	private String productSecret;
	/** 百度充值查询接口地址 **/
	private String baiduQueryUrl;
	// ==================================小米=====================================
	/** 应用编号 **/
	private String xiaomiAppId;
	/** 应用key **/
	private String xiaomiAppKey;
	/** 应用秘钥 **/
	private String xiaomiAppSecret;
	/** 小米充值查询接口地址 **/
	private String xiaomiQueryUrl;
	// ==================================安智=====================================
	/** 应用编号 **/
	private String anzhiAppId;
	/** 应用秘钥 **/
	private String anzhiAppSecret;
	/** 安智充值查询接口地址 **/
	private String anzhiQueryUrl;
	// ==================================联想=====================================
	/** 应用编号 **/
	private String lenovoAppId;
	/** 应用秘钥 **/
	private String lenovoAppKey;
	// ==================================奇虎360=====================================
	/** 应用编号 **/
	private String qihuAppId;
	/** 应用key **/
	private String qihuAppKey;
	/** 应用密钥 **/
	private String qihuAppSecret;
	/** 奇虎充值回调地址 **/
	private String qihuUrl;
	// ==================================应用汇=====================================
	/** 应用编号 **/
	private String yingyonghuiAppId;
	/** 应用key **/
	private String yingyonghuiAppKey;
	/** 商品编码 **/
	private String yingyonghuiWaresId;
	/** 回调地址 **/
	private String yingyonghuiUrl;
	// ==================================OPPO=====================================
	/** 应用编号 **/
	private String oppoAppId;
	/** 应用key **/
	private String oppoAppKey;
	/** 应用密钥 **/
	private String oppoAppSecret;
	/** OPPO充值回调地址 **/
	private String oppoUrl;
	// ==================================华为=====================================
	/** 私钥 **/
	private String huaweiPrivateKey;
	// ==================================海马IOS=====================================
	/** 应用编号 **/
	private String haimaIosAppId;
	/** 应用key **/
	private String haimaIosAppKey;
	/** 海马充值回调地址 **/
	private String haimaIosUrl;
	// ==================================百度IOS=====================================
	/** 应用编号 **/
	private String baiduIosAppId;
	/** 应用key **/
	private String baiduIosAppKey;
	/** 百度IOS充值回调地址 **/
	private String baiduIosUrl;
	// ==================================快用IOS=====================================
	/** 应用编号 **/
	private String kuaiyongIosAppId;
	/** 应用key **/
	private String kuaiyongIosAppKey;
	/** MD5key **/
	private String kuaiyongIosMD5Key;
	// ==================================PPIOS=====================================
	/** 应用编号 **/
	private String ppIosAppId;
	/** 应用key **/
	private String ppIosAppKey;
	// ==================================PPIOS=====================================
	/** 应用编号 **/
	private String tongbutuiIosAppId;
	/** 应用key **/
	private String tongbutuiIosAppkey;
	// ==================================AISIIOS=====================================
	/** 应用编号 **/
	private String aisiIosAppId;
	/** 应用key **/
	private String aisiIosAppKey;

	/**
	 * 初始化
	 */
	public void init() {
		try {
			InputStream in = new FileInputStream("./conf/pay.properties");
			Properties p = new Properties();
			p.load(in);
			game = p.getProperty("game");
			url = p.getProperty("url");

			service = p.getProperty("service");
			partner = p.getProperty("partner");
			privateKey = p.getProperty("privateKey");
			charset = p.getProperty("charset");
			signType = p.getProperty("signType");
			subject = p.getProperty("subject");
			paymentType = p.getProperty("paymentType");
			sellerId = p.getProperty("sellerId");
			alipayUrl = p.getProperty("alipayUrl");

			upmpReqUrl = p.getProperty("upmpReqUrl");
			upmpUrl = p.getProperty("upmpUrl");

			yeeBaoReqUrl = p.getProperty("yeeBaoReqUrl");
			yeeBaoUrl = p.getProperty("yeeBaoUrl");

			merchantId = p.getProperty("merchantId");
			merchantPasswd = p.getProperty("merchantPasswd");
			appId = p.getProperty("appId");
			sikaiPayUrl = p.getProperty("sikaiPayUrl");

			baiduAppId = p.getProperty("baiduAppId");
			productKey = p.getProperty("productKey");
			productSecret = p.getProperty("productSecret");
			baiduQueryUrl = p.getProperty("baiduQueryUrl");

			xiaomiAppId = p.getProperty("xiaomiAppId");
			xiaomiAppKey = p.getProperty("xiaomiAppKey");
			xiaomiAppSecret = p.getProperty("xiaomiAppSecret");
			xiaomiQueryUrl = p.getProperty("xiaomiQueryUrl");

			anzhiAppId = p.getProperty("anzhiAppId");
			anzhiAppSecret = p.getProperty("anzhiAppSecret");
			anzhiQueryUrl = p.getProperty("anzhiQueryUrl");

			lenovoAppId = p.getProperty("lenovoAppId");
			lenovoAppKey = p.getProperty("lenovoAppKey");

			qihuAppId = p.getProperty("qihuAppId");
			qihuAppKey = p.getProperty("qihuAppKey");
			qihuAppSecret = p.getProperty("qihuAppSecret");
			qihuUrl = p.getProperty("qihuUrl");

			yingyonghuiAppId = p.getProperty("yingyonghuiAppId");
			yingyonghuiAppKey = p.getProperty("yingyonghuiAppKey");
			yingyonghuiWaresId = p.getProperty("yingyonghuiWaresId");
			yingyonghuiUrl = p.getProperty("yingyonghuiUrl");

			oppoAppId = p.getProperty("oppoAppId");
			oppoAppKey = p.getProperty("oppoAppKey");
			oppoAppSecret = p.getProperty("oppoAppSecret");
			oppoUrl = p.getProperty("oppoUrl");

			huaweiPrivateKey = p.getProperty("huaweiPrivateKey");

			haimaIosAppId = p.getProperty("haimaIosAppId");
			haimaIosAppKey = p.getProperty("haimaIosAppKey");
			haimaIosUrl = p.getProperty("haimaIosUrl");

			baiduIosAppId = p.getProperty("baiduIosAppId");
			baiduIosAppKey = p.getProperty("baiduIosAppKey");
			baiduIosUrl = p.getProperty("baiduIosUrl");

			kuaiyongIosAppId = p.getProperty("kuaiyongIosAppId");
			kuaiyongIosAppKey = p.getProperty("kuaiyongIosAppKey");
			kuaiyongIosMD5Key = p.getProperty("kuaiyongIosMD5Key");

			ppIosAppId = p.getProperty("ppIosAppId");
			ppIosAppKey = p.getProperty("ppIosAppKey");

			tongbutuiIosAppId = p.getProperty("tongbutuiIosAppId");
			tongbutuiIosAppkey = p.getProperty("tongbutuiIosAppkey");

			aisiIosAppId = p.getProperty("aisiIosAppId");
			aisiIosAppKey = p.getProperty("aisiIosAppKey");
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @function 根据兑换比率系数计算兑换元宝
	 * @author LuoSR
	 * @param rmb
	 * @return
	 * @date 2014年5月9日
	 */
	public int countMoney(int rmb) {
		int money = 0;
		List<PayRatio> list = PayJson.instance().getPayRatios();
		for (PayRatio payRatio : list) {
			if (rmb < payRatio.getRMB()) {
				money = (int) (rmb * payRatio.getRatio() / 10f);
				break;
			}
		}
		return money;
	}

	/**
	 * 获取PayManager对象
	 * 
	 * @return PayManager对象
	 */
	public static PayManager instance() {
		return instance;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getAlipayUrl() {
		return alipayUrl;
	}

	public void setAlipayUrl(String alipayUrl) {
		this.alipayUrl = alipayUrl;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public String getUpmpReqUrl() {
		return upmpReqUrl;
	}

	public void setUpmpReqUrl(String upmpReqUrl) {
		this.upmpReqUrl = upmpReqUrl;
	}

	public String getUpmpUrl() {
		return upmpUrl;
	}

	public void setUpmpUrl(String upmpUrl) {
		this.upmpUrl = upmpUrl;
	}

	public String getYeeBaoReqUrl() {
		return yeeBaoReqUrl;
	}

	public void setYeeBaoReqUrl(String yeeBaoReqUrl) {
		this.yeeBaoReqUrl = yeeBaoReqUrl;
	}

	public String getYeeBaoUrl() {
		return yeeBaoUrl;
	}

	public void setYeeBaoUrl(String yeeBaoUrl) {
		this.yeeBaoUrl = yeeBaoUrl;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantPasswd() {
		return merchantPasswd;
	}

	public void setMerchantPasswd(String merchantPasswd) {
		this.merchantPasswd = merchantPasswd;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSikaiPayUrl() {
		return sikaiPayUrl;
	}

	public void setSikaiPayUrl(String sikaiPayUrl) {
		this.sikaiPayUrl = sikaiPayUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBaiduAppId() {
		return baiduAppId;
	}

	public void setBaiduAppId(String baiduAppId) {
		this.baiduAppId = baiduAppId;
	}

	public String getProductKey() {
		return productKey;
	}

	public void setProductKey(String productKey) {
		this.productKey = productKey;
	}

	public String getProductSecret() {
		return productSecret;
	}

	public void setProductSecret(String productSecret) {
		this.productSecret = productSecret;
	}

	public String getBaiduQueryUrl() {
		return baiduQueryUrl;
	}

	public void setBaiduQueryUrl(String baiduQueryUrl) {
		this.baiduQueryUrl = baiduQueryUrl;
	}

	public String getXiaomiAppId() {
		return xiaomiAppId;
	}

	public void setXiaomiAppId(String xiaomiAppId) {
		this.xiaomiAppId = xiaomiAppId;
	}

	public String getXiaomiAppKey() {
		return xiaomiAppKey;
	}

	public void setXiaomiAppKey(String xiaomiAppKey) {
		this.xiaomiAppKey = xiaomiAppKey;
	}

	public String getXiaomiAppSecret() {
		return xiaomiAppSecret;
	}

	public void setXiaomiAppSecret(String xiaomiAppSecret) {
		this.xiaomiAppSecret = xiaomiAppSecret;
	}

	public String getXiaomiQueryUrl() {
		return xiaomiQueryUrl;
	}

	public void setXiaomiQueryUrl(String xiaomiQueryUrl) {
		this.xiaomiQueryUrl = xiaomiQueryUrl;
	}

	public String getAnzhiAppId() {
		return anzhiAppId;
	}

	public void setAnzhiAppId(String anzhiAppId) {
		this.anzhiAppId = anzhiAppId;
	}

	public String getAnzhiAppSecret() {
		return anzhiAppSecret;
	}

	public void setAnzhiAppSecret(String anzhiAppSecret) {
		this.anzhiAppSecret = anzhiAppSecret;
	}

	public String getAnzhiQueryUrl() {
		return anzhiQueryUrl;
	}

	public void setAnzhiQueryUrl(String anzhiQueryUrl) {
		this.anzhiQueryUrl = anzhiQueryUrl;
	}

	public String getLenovoAppId() {
		return lenovoAppId;
	}

	public void setLenovoAppId(String lenovoAppId) {
		this.lenovoAppId = lenovoAppId;
	}

	public String getLenovoAppKey() {
		return lenovoAppKey;
	}

	public void setLenovoAppKey(String lenovoAppKey) {
		this.lenovoAppKey = lenovoAppKey;
	}

	public String getQihuAppId() {
		return qihuAppId;
	}

	public void setQihuAppId(String qihuAppId) {
		this.qihuAppId = qihuAppId;
	}

	public String getQihuAppKey() {
		return qihuAppKey;
	}

	public void setQihuAppKey(String qihuAppKey) {
		this.qihuAppKey = qihuAppKey;
	}

	public String getQihuAppSecret() {
		return qihuAppSecret;
	}

	public void setQihuAppSecret(String qihuAppSecret) {
		this.qihuAppSecret = qihuAppSecret;
	}

	public String getQihuUrl() {
		return qihuUrl;
	}

	public void setQihuUrl(String qihuUrl) {
		this.qihuUrl = qihuUrl;
	}

	public String getYingyonghuiAppId() {
		return yingyonghuiAppId;
	}

	public void setYingyonghuiAppId(String yingyonghuiAppId) {
		this.yingyonghuiAppId = yingyonghuiAppId;
	}

	public String getYingyonghuiAppKey() {
		return yingyonghuiAppKey;
	}

	public void setYingyonghuiAppKey(String yingyonghuiAppKey) {
		this.yingyonghuiAppKey = yingyonghuiAppKey;
	}

	public String getYingyonghuiWaresId() {
		return yingyonghuiWaresId;
	}

	public void setYingyonghuiWaresId(String yingyonghuiWaresId) {
		this.yingyonghuiWaresId = yingyonghuiWaresId;
	}

	public String getOppoAppId() {
		return oppoAppId;
	}

	public void setOppoAppId(String oppoAppId) {
		this.oppoAppId = oppoAppId;
	}

	public String getOppoAppKey() {
		return oppoAppKey;
	}

	public void setOppoAppKey(String oppoAppKey) {
		this.oppoAppKey = oppoAppKey;
	}

	public String getOppoAppSecret() {
		return oppoAppSecret;
	}

	public void setOppoAppSecret(String oppoAppSecret) {
		this.oppoAppSecret = oppoAppSecret;
	}

	public String getOppoUrl() {
		return oppoUrl;
	}

	public void setOppoUrl(String oppoUrl) {
		this.oppoUrl = oppoUrl;
	}

	public String getYingyonghuiUrl() {
		return yingyonghuiUrl;
	}

	public void setYingyonghuiUrl(String yingyonghuiUrl) {
		this.yingyonghuiUrl = yingyonghuiUrl;
	}

	public String getHuaweiPrivateKey() {
		return huaweiPrivateKey;
	}

	public void setHuaweiPrivateKey(String huaweiPrivateKey) {
		this.huaweiPrivateKey = huaweiPrivateKey;
	}

	public String getHaimaIosAppId() {
		return haimaIosAppId;
	}

	public void setHaimaIosAppId(String haimaIosAppId) {
		this.haimaIosAppId = haimaIosAppId;
	}

	public String getHaimaIosAppKey() {
		return haimaIosAppKey;
	}

	public void setHaimaIosAppKey(String haimaIosAppKey) {
		this.haimaIosAppKey = haimaIosAppKey;
	}

	public String getHaimaIosUrl() {
		return haimaIosUrl;
	}

	public void setHaimaIosUrl(String haimaIosUrl) {
		this.haimaIosUrl = haimaIosUrl;
	}

	public String getBaiduIosAppId() {
		return baiduIosAppId;
	}

	public void setBaiduIosAppId(String baiduIosAppId) {
		this.baiduIosAppId = baiduIosAppId;
	}

	public String getBaiduIosAppKey() {
		return baiduIosAppKey;
	}

	public void setBaiduIosAppKey(String baiduIosAppKey) {
		this.baiduIosAppKey = baiduIosAppKey;
	}

	public String getBaiduIosUrl() {
		return baiduIosUrl;
	}

	public void setBaiduIosUrl(String baiduIosUrl) {
		this.baiduIosUrl = baiduIosUrl;
	}

	public String getKuaiyongIosAppId() {
		return kuaiyongIosAppId;
	}

	public void setKuaiyongIosAppId(String kuaiyongIosAppId) {
		this.kuaiyongIosAppId = kuaiyongIosAppId;
	}

	public String getKuaiyongIosAppKey() {
		return kuaiyongIosAppKey;
	}

	public void setKuaiyongIosAppKey(String kuaiyongIosAppKey) {
		this.kuaiyongIosAppKey = kuaiyongIosAppKey;
	}

	public String getKuaiyongIosMD5Key() {
		return kuaiyongIosMD5Key;
	}

	public void setKuaiyongIosMD5Key(String kuaiyongIosMD5Key) {
		this.kuaiyongIosMD5Key = kuaiyongIosMD5Key;
	}

	public String getPpIosAppId() {
		return ppIosAppId;
	}

	public void setPpIosAppId(String ppIosAppId) {
		this.ppIosAppId = ppIosAppId;
	}

	public String getPpIosAppKey() {
		return ppIosAppKey;
	}

	public void setPpIosAppKey(String ppIosAppKey) {
		this.ppIosAppKey = ppIosAppKey;
	}

	public String getTongbutuiIosAppId() {
		return tongbutuiIosAppId;
	}

	public void setTongbutuiIosAppId(String tongbutuiIosAppId) {
		this.tongbutuiIosAppId = tongbutuiIosAppId;
	}

	public String getTongbutuiIosAppkey() {
		return tongbutuiIosAppkey;
	}

	public void setTongbutuiIosAppkey(String tongbutuiIosAppkey) {
		this.tongbutuiIosAppkey = tongbutuiIosAppkey;
	}

	public String getAisiIosAppId() {
		return aisiIosAppId;
	}

	public void setAisiIosAppId(String aisiIosAppId) {
		this.aisiIosAppId = aisiIosAppId;
	}

	public String getAisiIosAppKey() {
		return aisiIosAppKey;
	}

	public void setAisiIosAppKey(String aisiIosAppKey) {
		this.aisiIosAppKey = aisiIosAppKey;
	}

}
