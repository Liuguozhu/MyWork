package iyunu.NewTLOL.model.pay;

public class PayAnzhiResponse {
	private String sc; // 状态码
	private String st; // 状态描述
	private String total; // 订单记录总计
	private String time; // 响应时间
	private String msg; // 订单数据加密串，采用Base64.decode();解密即可得到json

	public String getSc() {
		return sc;
	}

	public void setSc(String sc) {
		this.sc = sc;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
