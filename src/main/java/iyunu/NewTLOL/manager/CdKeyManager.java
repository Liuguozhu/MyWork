package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.model.base.CdKeyInfo;
import iyunu.NewTLOL.service.iface.cdKey.CdKeyService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CdKeyManager {

	/**
	 * 私有构造方法
	 */
	private CdKeyManager() {
	}

	private static CdKeyManager instance = new CdKeyManager();

	/**
	 * 获取CdKeyManager对象
	 * 
	 * @return CdKeyManager对象
	 */
	public static CdKeyManager instance() {
		return instance;
	}

	private List<String> cdKeyList = new ArrayList<String>();
	private Map<String, CdKeyInfo> cdKeys = new HashMap<String, CdKeyInfo>();

	private void clear() {
		cdKeyList.clear();
		cdKeys.clear();
	}

	/**
	 * 初始化
	 */
	public void init() {
		clear();

		CdKeyService cdKeyService = Spring.instance().getBean("cdKeyService", CdKeyService.class);
		List<CdKeyInfo> cdKeyInfos = cdKeyService.query();
		for (CdKeyInfo cdKeyInfo : cdKeyInfos) {
			cdKeyList.add(cdKeyInfo.getCdKey());
			cdKeys.put(cdKeyInfo.getCdKey(), cdKeyInfo);
		}
	}

	public synchronized boolean check(String cdKey) {
		if (cdKeyList.contains(cdKey)) {
			cdKeyList.remove(cdKey);
			return true;
		}
		return false;
	}

	public CdKeyInfo getCdKeyInfo(String cdKey) {
		return cdKeys.get(cdKey);
	}

	public void delete(String cdKey) {
		cdKeys.remove(cdKey);
	}
}
