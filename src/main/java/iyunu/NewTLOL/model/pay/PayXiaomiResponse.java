package iyunu.NewTLOL.model.pay;

public class PayXiaomiResponse {
	private String errcode; // 状态码1506 cpOrderId 错误1515 appId 错误1516 uid 错误1525 signature 错
	private String errMsg; // 错误信息
	private String appId; // 游戏ID
	private String cpOrderId; // 开发商订单ID
	private String cpUserInfo; // 开发商透传信息
	private String uid; // 用户ID
	private String orderId; // 游戏平台订单ID
	private String orderStatus; // 订单状态，TRADE_SUCCESS 代表成功
	private String payFee; // 支付金额,单位为分,即0.01 米币。
	private String productCode; // 商品代码
	private String productName; // 商品名称
	private String productCount; // 商品数量
	private String payTime; // 支付时间,格式 yyyy-MM-dd HH:mm:ss
	private String orderConsumeType; // 订单类型：10：普通订单11：直充直消订单
	private String signature; // 签名,签名方法见后面说明

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getCpOrderId() {
		return cpOrderId;
	}

	public void setCpOrderId(String cpOrderId) {
		this.cpOrderId = cpOrderId;
	}

	public String getCpUserInfo() {
		return cpUserInfo;
	}

	public void setCpUserInfo(String cpUserInfo) {
		this.cpUserInfo = cpUserInfo;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayFee() {
		return payFee;
	}

	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCount() {
		return productCount;
	}

	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getOrderConsumeType() {
		return orderConsumeType;
	}

	public void setOrderConsumeType(String orderConsumeType) {
		this.orderConsumeType = orderConsumeType;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
