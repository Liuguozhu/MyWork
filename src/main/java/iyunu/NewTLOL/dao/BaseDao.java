package iyunu.NewTLOL.dao;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.dao.iface.AuctionDao;
import iyunu.NewTLOL.dao.iface.BulletinDao;
import iyunu.NewTLOL.dao.iface.CdKeyDao;
import iyunu.NewTLOL.dao.iface.CompensateDao;
import iyunu.NewTLOL.dao.iface.GangDao;
import iyunu.NewTLOL.dao.iface.MailDao;
import iyunu.NewTLOL.dao.iface.MultipleDao;
import iyunu.NewTLOL.dao.iface.PayActivityDao;
import iyunu.NewTLOL.dao.iface.PayBackDao;
import iyunu.NewTLOL.dao.iface.RoleDao;
import iyunu.NewTLOL.dao.iface.UserDao;

/**
 * 获取Dao层实例的类
 * 
 * @author SunHonglei
 * 
 */
public class BaseDao {

	/**
	 * 私有构造方法
	 */
	private BaseDao() {

	}

	private static BaseDao instance = new BaseDao();

	public static BaseDao instance() {
		return instance;
	}

	public RoleDao getRoleDao() {
		return Spring.instance().getBean("roleDao", RoleDao.class);
	}

	public AuctionDao getAuctionDao() {
		return Spring.instance().getBean("auctionDao", AuctionDao.class);
	}

	public MailDao getMailDao() {
		return Spring.instance().getBean("mailDao", MailDao.class);
	}

	public GangDao getGangDao() {
		return Spring.instance().getBean("gangDao", GangDao.class);
	}

	public BulletinDao getBulletinDao() {
		return Spring.instance().getBean("bulletinDao", BulletinDao.class);
	}

	public UserDao getUserDao() {
		return Spring.instance().getBean("userDao", UserDao.class);
	}

	public MultipleDao getMultipleDao() {
		return Spring.instance().getBean("multipleDao", MultipleDao.class);
	}

	public CdKeyDao getCdKeyDao() {
		return Spring.instance().getBean("cdKeyDao", CdKeyDao.class);
	}

	public CompensateDao getCompensateDao() {
		return Spring.instance().getBean("compensateDao", CompensateDao.class);
	}

	public PayActivityDao getPayActivityDao() {
		return Spring.instance().getBean("payActivityDao", PayActivityDao.class);
	}

	public PayBackDao getPayBackDao() {
		return Spring.instance().getBean("payBackDao", PayBackDao.class);
	}

}
