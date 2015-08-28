package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.qiancengta.instance.QiancengtaInfo;
import iyunu.NewTLOL.model.qiancengta.res.QiancengtaInfoRes;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;

public final class QiancengtaJson {

	/**
	 * 私有构造方法
	 */
	private QiancengtaJson() {

	}

	private static QiancengtaJson instance = new QiancengtaJson();
	private static final String QIANCENGTA = "json/" + ServerManager.SERVER_RES + "/Qiancengta.json.txt";
	
	private Map<Integer, QiancengtaInfo> qiancengtaMap = new HashMap<Integer, QiancengtaInfo>();
	private List<QiancengtaInfo> qiancengtas = new ArrayList<QiancengtaInfo>();

	/**
	 * 获取MonsterJson对象
	 * 
	 * @return MonsterJson对象
	 */
	public static QiancengtaJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		qiancengtaMap.clear();
		qiancengtas.clear();
	}

	/**
	 * 初始化物品模板
	 */
	public void init() {
		long start = System.currentTimeMillis();
		clear();

		List<QiancengtaInfoRes> qiancengtaInfoResList = JsonImporter.fileImporter(QIANCENGTA, new TypeReference<List<QiancengtaInfoRes>>() {
		});
		for (QiancengtaInfoRes qiancengtaInfoRes : qiancengtaInfoResList) {
			qiancengtaMap.put(qiancengtaInfoRes.getId(), qiancengtaInfoRes.toQiancengtaInfo());
			qiancengtas.add(qiancengtaInfoRes.toQiancengtaInfo());
		}

		long end = System.currentTimeMillis();
		System.out.println("千层塔加载耗时：" + (end - start));
	}

	public Map<Integer, QiancengtaInfo> getQiancengtaMap() {
		return qiancengtaMap;
	}

	public List<QiancengtaInfo> getQiancengtas() {
		return qiancengtas;
	}

}
