package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.jingMai.instance.JingMai;
import iyunu.NewTLOL.model.jingMai.res.JingMaiRes;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;

public final class JingMaiJson {

	/**
	 * 私有构造方法
	 */
	private JingMaiJson() {

	}

	private static JingMaiJson instance = new JingMaiJson();
	private static final String JINGMAI = "json/" + ServerManager.SERVER_RES + "/JingMai.json.txt";
	
	Map<Integer, JingMai> jingMaiMap = new HashMap<Integer, JingMai>();

	/**
	 * 获取SkillBookJson对象
	 * 
	 * @return SkillBookJson对象
	 */
	public static JingMaiJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		jingMaiMap.clear();
	}

	/**
	 * 初始化物品模板
	 */
	public void init() {
		long start = System.currentTimeMillis();
		clear();

		List<JingMaiRes> jingMaiResList = JsonImporter.fileImporter(JINGMAI, new TypeReference<List<JingMaiRes>>() {
		});

		for (JingMaiRes jingMaiRes : jingMaiResList) {
			jingMaiMap.put(jingMaiRes.getId(), jingMaiRes.toJingMai());
		}
		long end = System.currentTimeMillis();
		System.out.println("经脉脚本加载耗时：" + (end - start));
	}

	public Map<Integer, JingMai> getJingMaiMap() {
		return jingMaiMap;
	}

	public JingMai getJingMai(int note) {
		if (jingMaiMap.containsKey(note)) {
			return jingMaiMap.get(note).copy();
		}
		return null;
	}

}
