package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.kuai3.Kuai3Enum;
import iyunu.NewTLOL.model.kuai3.res.Kuai3HeZhiRes;
import iyunu.NewTLOL.model.kuai3.res.Kuai3Res;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;

public final class Kuai3Json {

	/**
	 * 私有构造方法
	 */
	private Kuai3Json() {

	}

	private static Kuai3Json instance = new Kuai3Json();
	private static final String KUAI3NUM = "json/" + ServerManager.SERVER_RES + "/kuai3.json.txt";
	private static final String KUAI3HEZHI = "json/" + ServerManager.SERVER_RES + "/kuai3HeZhi.json.txt";
	
	Map<String, Kuai3Res> kuai3Num = new HashMap<String, Kuai3Res>();
	Map<Integer, Kuai3HeZhiRes> kuai3HeZhi = new HashMap<Integer, Kuai3HeZhiRes>();

	/**
	 * 获取单例对象
	 * 
	 * @return 单例对象
	 */
	public static Kuai3Json instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		kuai3Num.clear();
		kuai3HeZhi.clear();
	}

	/**
	 * 初始化
	 */
	public void init() {
		long start = System.currentTimeMillis();
		clear();

		List<Kuai3Res> list = JsonImporter.fileImporter(KUAI3NUM, new TypeReference<List<Kuai3Res>>() {
		});

		for (Kuai3Res kuai3Res : list) {
			kuai3Num.put(kuai3Res.getName(), kuai3Res);
		}
		List<Kuai3HeZhiRes> list1 = JsonImporter.fileImporter(KUAI3HEZHI, new TypeReference<List<Kuai3HeZhiRes>>() {
		});

		for (Kuai3HeZhiRes kuai3HeZhiRes : list1) {
			kuai3HeZhi.put(kuai3HeZhiRes.getHezhiResult(), kuai3HeZhiRes);
		}
		long end = System.currentTimeMillis();
		System.out.println("猜猜看脚本加载耗时：" + (end - start));
	}

	/**
	 * @return the kuai3Num
	 */
	public Map<String, Kuai3Res> getKuai3Num() {
		return kuai3Num;
	}

	/**
	 * @return the kuai3HeZhi
	 */
	public Map<Integer, Kuai3HeZhiRes> getKuai3HeZhi() {
		return kuai3HeZhi;
	}

	public int getAward(Kuai3Enum type, int heZhi) {
		if (type.name().equals("和值")) {
			return kuai3HeZhi.get(heZhi).getResult();
		} else {
			return kuai3Num.get(type.name()).getResult();
		}
	}

}
