package iyunu.NewTLOL.model.pay;

public class PayHelp {

	/** 卡类型 **/
	private String type;
	/** 支持卡种 **/
	private String cardDes;
	/** 支付面额 **/
	private String cardAmount;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCardDes() {
		return cardDes;
	}

	public void setCardDes(String cardDes) {
		this.cardDes = cardDes;
	}

	public String getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(String cardAmount) {
		this.cardAmount = cardAmount;
	}

}
