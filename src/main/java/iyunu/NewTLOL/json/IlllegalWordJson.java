package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.IlllegalWordManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.base.IlllegalWordInfo;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.List;

import com.alibaba.fastjson.TypeReference;

public final class IlllegalWordJson {

	/**
	 * 私有构造方法
	 */
	private IlllegalWordJson() {

	}

	private static IlllegalWordJson instance = new IlllegalWordJson();
	private static final String ILLLEGAL_WORD = "json/" + ServerManager.SERVER_RES + "/IlllegalWord.json.txt";

	/**
	 * 获取实例对象
	 * 
	 * @return 实例对象
	 */
	public static IlllegalWordJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		IlllegalWordManager.instance().clear();
	}

	/**
	 * 初始化物品模板
	 */
	public void init() throws Exception {
		long start = System.currentTimeMillis();
		clear();

		List<IlllegalWordInfo> illlegalWordInfoList = JsonImporter.fileImporter(ILLLEGAL_WORD, new TypeReference<List<IlllegalWordInfo>>() {
		});
		for (IlllegalWordInfo illlegalWordInfo : illlegalWordInfoList) {
			IlllegalWordManager.instance().writeToList(illlegalWordInfo.getIlllegalWord());

		}

		long end = System.currentTimeMillis();
		System.out.println("非法文字脚本加载耗时：" + (end - start));
	}

}
