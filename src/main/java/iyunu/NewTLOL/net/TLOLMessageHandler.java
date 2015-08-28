package iyunu.NewTLOL.net;

import iyunu.NewTLOL.base.net.socket.ProtocolHandler;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.redis.Redis;
import iyunu.NewTLOL.service.iface.auction.AuctionService;
import iyunu.NewTLOL.service.iface.bulletin.BulletinService;
import iyunu.NewTLOL.service.iface.gang.GangService;
import iyunu.NewTLOL.service.iface.mail.MailService;
import iyunu.NewTLOL.service.iface.payActivity.PayBackService;
import iyunu.NewTLOL.service.iface.role.RoleServiceIfce;
import iyunu.NewTLOL.service.iface.user.UserService;

import org.jboss.netty.channel.Channel;

public abstract class TLOLMessageHandler extends Redis implements ProtocolHandler {

	protected Role online;
	protected Channel channel;
	protected RoleServiceIfce roleService;
	protected AuctionService auctionService;
	protected BulletinService bulletinService;
	protected MailService mailService;
	protected GangService gangService;
	protected UserService userService;
	protected PayBackService payBackService;

	@Override
	public void preHandle(Channel channel) {
		this.channel = channel;
		online = ServerManager.instance().getOnlinePlayer(channel);
		if (online != null) {
			// 暂时无操作
		}
	}

	public RoleServiceIfce getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleServiceIfce roleService) {
		this.roleService = roleService;
	}

	/**
	 * @return the auctionService
	 */
	public AuctionService getAuctionService() {
		return auctionService;
	}

	/**
	 * @param auctionService
	 *            the auctionService to set
	 */
	public void setAuctionService(AuctionService auctionService) {
		this.auctionService = auctionService;
	}

	public BulletinService getBulletinService() {
		return bulletinService;
	}

	public void setBulletinService(BulletinService bulletinService) {
		this.bulletinService = bulletinService;
	}

	/**
	 * @return the mailService
	 */
	public MailService getMailService() {
		return mailService;
	}

	/**
	 * @param mailService
	 *            the mailService to set
	 */
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	/**
	 * @return the gangService
	 */
	public GangService getGangService() {
		return gangService;
	}

	/**
	 * @param gangService
	 *            the gangService to set
	 */
	public void setGangService(GangService gangService) {
		this.gangService = gangService;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the payBackService
	 */
	public PayBackService getPayBackService() {
		return payBackService;
	}

	/**
	 * @param payBackService
	 *            the payBackService to set
	 */
	public void setPayBackService(PayBackService payBackService) {
		this.payBackService = payBackService;
	}

}
