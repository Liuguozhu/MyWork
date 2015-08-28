package iyunu.NewTLOL.model.auction;

import iyunu.NewTLOL.model.partner.instance.Partner;

public class AuctionPartner extends Auction {

	private long partnerId;
	private Partner partner;

	/**
	 * @return the partnerId
	 */
	public long getPartnerId() {
		return partnerId;
	}

	/**
	 * @param partnerId
	 *            the partnerId to set
	 */
	public void setPartnerId(long partnerId) {
		this.partnerId = partnerId;
	}

	/**
	 * @return the partner
	 */
	public Partner getPartner() {
		return partner;
	}

	/**
	 * @param partner
	 *            the partner to set
	 */
	public void setPartner(Partner partner) {
		this.partner = partner;
	}

}
