package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.buffRole.instance.BuffRole;
import iyunu.NewTLOL.model.buffRole.res.BuffRoleRes;
import iyunu.NewTLOL.model.role.instance.RoleSign;
import iyunu.NewTLOL.model.role.instance.RoleSignItem;
import iyunu.NewTLOL.model.role.res.ExpMaxRes;
import iyunu.NewTLOL.model.role.res.FigureRes;
import iyunu.NewTLOL.model.role.res.RoleSignItemRes;
import iyunu.NewTLOL.model.role.res.RoleSignRes;
import iyunu.NewTLOL.model.role.title.instance.Title;
import iyunu.NewTLOL.model.role.title.res.TitleRes;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;

public final class RoleJson {

	/**
	 * 私有构造方法
	 */
	private RoleJson() {

	}

	private static RoleJson instance = new RoleJson();
	private static final String ROLE_EXP = "json/" + ServerManager.SERVER_RES + "/RoleExp.json.txt";
	private static final String ROLE_FIGURE = "json/" + ServerManager.SERVER_RES + "/RoleFigure.json.txt";
	private static final String ROLE_SIGN = "json/" + ServerManager.SERVER_RES + "/roleSign.json.txt";
	private static final String BUFF_ROLE = "json/" + ServerManager.SERVER_RES + "/BuffRole.json.txt";
	private static final String ROLE_TITLE = "json/" + ServerManager.SERVER_RES + "/RoleTitle.json.txt";
	private static final String SIGN_ITEM = "json/" + ServerManager.SERVER_RES + "/roleSignItem.json.txt";

	private Map<Integer, Integer> roleExpMap = new HashMap<Integer, Integer>();
	private Map<Long, FigureRes> figures = new HashMap<Long, FigureRes>();
	private Map<Integer, RoleSign> roleSign = new HashMap<Integer, RoleSign>();
	private Map<Integer, BuffRoleRes> buffRoles = new HashMap<Integer, BuffRoleRes>(); // <角色BUFF编号，角色BUFF对象>
	private Map<Integer, Title> titles = new HashMap<Integer, Title>(); // <称号编号，称号对象>
	private Map<Integer, RoleSignItem> signItem = new HashMap<>();// 签到物品

	/**
	 * 获取实例对象
	 * 
	 * @return 实例对象
	 */
	public static RoleJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		roleExpMap.clear();
		figures.clear();
		roleSign.clear();
		buffRoles.clear();
		titles.clear();
		signItem.clear();
	}

	/**
	 * 初始化物品模板
	 */
	public void init() {
		long start = System.currentTimeMillis();
		clear();

		List<ExpMaxRes> list = JsonImporter.fileImporter(ROLE_EXP, new TypeReference<List<ExpMaxRes>>() {
		});

		for (ExpMaxRes expMaxRes : list) {
			roleExpMap.put(expMaxRes.getLevel(), expMaxRes.getExp());
		}

		List<FigureRes> figureResList = JsonImporter.fileImporter(ROLE_FIGURE, new TypeReference<List<FigureRes>>() {
		});
		for (FigureRes figureRes : figureResList) {
			figures.put(figureRes.getId(), figureRes);
		}

		List<RoleSignRes> list3 = JsonImporter.fileImporter(ROLE_SIGN, new TypeReference<List<RoleSignRes>>() {
		});
		for (RoleSignRes roleSignRes : list3) {
			roleSign.put(roleSignRes.getId(), roleSignRes.toRoleSign());
		}

		List<BuffRoleRes> buffRoleResList = JsonImporter.fileImporter(BUFF_ROLE, new TypeReference<List<BuffRoleRes>>() {
		});
		for (BuffRoleRes buffRoleRes : buffRoleResList) {
			buffRoles.put(buffRoleRes.getId(), buffRoleRes);
		}

		List<TitleRes> titleResList = JsonImporter.fileImporter(ROLE_TITLE, new TypeReference<List<TitleRes>>() {
		});
		for (TitleRes titleRes : titleResList) {
			titles.put(titleRes.getIndex(), titleRes.toTitle());
		}

		List<RoleSignItemRes> rList = JsonImporter.fileImporter(SIGN_ITEM, new TypeReference<List<RoleSignItemRes>>() {
		});
		for (RoleSignItemRes r : rList) {
			signItem.put(r.getDay(), r.toRoleSignItem());
		}

		long end = System.currentTimeMillis();
		System.out.println("角色信息脚本加载耗时：" + (end - start));
	}

	/**
	 * 获取经验
	 * 
	 * @param level
	 *            等级
	 * @return 经验
	 */
	public int getExpMax(int level) {
		if (roleExpMap.containsKey(level)) {
			return roleExpMap.get(level);
		}
		return level * 100;
	}

	/**
	 * 获取角色形象
	 * 
	 * @param figureId
	 *            形象编号
	 * @return 形象对象
	 */
	public FigureRes getFigure(long figureId) {
		if (figures.containsKey(figureId)) {
			return figures.get(figureId);
		}
		return null;
	}

	/**
	 * @return the roleSign
	 */
	public Map<Integer, RoleSign> getRoleSign() {
		return roleSign;
	}

	/**
	 * 根据编号获取角色BUFF对象
	 * 
	 * @param id
	 *            BUFF编号
	 * @return 角色BUFF对象
	 */
	public BuffRole getbuffRoleResById(int id) {
		if (buffRoles.containsKey(id)) {
			return buffRoles.get(id).toBuffRole();
		}
		return null;
	}

	/**
	 * @return the signItem
	 */
	public Map<Integer, RoleSignItem> getSignItem() {
		return signItem;
	}

	/**
	 * @param signItem
	 *            the signItem to set
	 */
	public void setSignItem(Map<Integer, RoleSignItem> signItem) {
		this.signItem = signItem;
	}

}
