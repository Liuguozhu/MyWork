package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.blood.BloodAward;
import iyunu.NewTLOL.model.blood.BloodMonster;
import iyunu.NewTLOL.model.blood.BloodRes1;
import iyunu.NewTLOL.model.blood.BloodRes2;
import iyunu.NewTLOL.model.blood.BloodWord;
import iyunu.NewTLOL.model.blood.KillWord;
import iyunu.NewTLOL.model.blood.ReBornRes;
import iyunu.NewTLOL.util.json.JsonImporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;

public final class BloodJson {

	/**
	 * 私有构造方法
	 */
	private BloodJson() {

	}

	private static BloodJson instance = new BloodJson();

	private static final String BLOOD1 = "json/" + ServerManager.SERVER_RES + "/blood1.json.txt";
	private static final String BLOOD2 = "json/" + ServerManager.SERVER_RES + "/blood2.json.txt";
	private static final String REBORN = "json/" + ServerManager.SERVER_RES + "/reBorn.json.txt";
	private static final String BLOODMONSTER = "json/" + ServerManager.SERVER_RES + "/bloodMonster.json.txt";
	private static final String BLOODWORD = "json/" + ServerManager.SERVER_RES + "/bloodWord.json.txt";
	private static final String KILLWORD = "json/" + ServerManager.SERVER_RES + "/killWord.json.txt";
	private static final String BLOODAWARD = "json/" + ServerManager.SERVER_RES + "/bloodAward.json.txt";

	List<BloodRes1> blood1 = new ArrayList<BloodRes1>();
	List<BloodRes2> blood2 = new ArrayList<BloodRes2>();
	Map<Integer, ReBornRes> reBorn = new HashMap<Integer, ReBornRes>();
	Map<Integer, BloodMonster> bloodMonster = new HashMap<Integer, BloodMonster>();
	List<String> bloodWord = new ArrayList<String>();
	List<KillWord> killWord = new ArrayList<>();
	Map<Integer, BloodAward> bloodAward = new HashMap<Integer, BloodAward>();

	/**
	 * 获取单例对象
	 * 
	 * @return 单例对象
	 */
	public static BloodJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		blood1.clear();
		blood2.clear();
		reBorn.clear();
		bloodMonster.clear();
		bloodWord.clear();
		killWord.clear();
		bloodAward.clear();
	}

	/**
	 * 初始化
	 */
	public void init() {
		long start = System.currentTimeMillis();
		clear();

		blood1 = JsonImporter.fileImporter(BLOOD1, new TypeReference<List<BloodRes1>>() {
		});
		blood2 = JsonImporter.fileImporter(BLOOD2, new TypeReference<List<BloodRes2>>() {
		});
		List<ReBornRes> list1 = JsonImporter.fileImporter(REBORN, new TypeReference<List<ReBornRes>>() {
		});

		for (ReBornRes reBornRes : list1) {
			reBorn.put(reBornRes.getFlag(), reBornRes);
		}
		List<BloodMonster> list2 = JsonImporter.fileImporter(BLOODMONSTER, new TypeReference<List<BloodMonster>>() {
		});

		for (BloodMonster monster : list2) {
			bloodMonster.put(monster.getId(), monster);
		}

		List<BloodWord> list3 = JsonImporter.fileImporter(BLOODWORD, new TypeReference<List<BloodWord>>() {
		});
		for (BloodWord word : list3) {
			bloodWord.add(word.getWord());
		}
		killWord = JsonImporter.fileImporter(KILLWORD, new TypeReference<List<KillWord>>() {
		});
		List<BloodAward> list4 = JsonImporter.fileImporter(BLOODAWARD, new TypeReference<List<BloodAward>>() {
		});
		for (BloodAward award : list4) {
			bloodAward.put(award.getId(), award);
		}

		long end = System.currentTimeMillis();
		System.out.println("血战脚本加载耗时：" + (end - start));
	}

	/**
	 * @return the blood
	 */
	public List<BloodRes1> getBlood1() {
		return blood1;
	}

	/**
	 * @param blood
	 *            the blood to set
	 */
	public void setBlood1(List<BloodRes1> blood1) {
		this.blood1 = blood1;
	}

	/**
	 * @return the blood
	 */
	public List<BloodRes2> getBlood2() {
		return blood2;
	}

	/**
	 * @param blood
	 *            the blood to set
	 */
	public void setBlood2(List<BloodRes2> blood2) {
		this.blood2 = blood2;
	}

	/**
	 * @return the reBorn
	 */
	public Map<Integer, ReBornRes> getReBorn() {
		return reBorn;
	}

	/**
	 * @param reBorn
	 *            the reBorn to set
	 */
	public void setReBorn(Map<Integer, ReBornRes> reBorn) {
		this.reBorn = reBorn;
	}

	/**
	 * @return the bloodMonster
	 */
	public Map<Integer, BloodMonster> getBloodMonster() {
		return bloodMonster;
	}

	/**
	 * @param bloodMonster
	 *            the bloodMonster to set
	 */
	public void setBloodMonster(Map<Integer, BloodMonster> bloodMonster) {
		this.bloodMonster = bloodMonster;
	}

	/**
	 * @return the bloodWord
	 */
	public List<String> getBloodWord() {
		return bloodWord;
	}

	/**
	 * @param bloodWord
	 *            the bloodWord to set
	 */
	public void setBloodWord(List<String> bloodWord) {
		this.bloodWord = bloodWord;
	}

	/**
	 * @return the killWord
	 */
	public List<KillWord> getKillWord() {
		return killWord;
	}

	/**
	 * @param killWord
	 *            the killWord to set
	 */
	public void setKillWord(List<KillWord> killWord) {
		this.killWord = killWord;
	}

	/**
	 * @return the bloodAward
	 */
	public Map<Integer, BloodAward> getBloodAward() {
		return bloodAward;
	}

	/**
	 * @param bloodAward
	 *            the bloodAward to set
	 */
	public void setBloodAward(Map<Integer, BloodAward> bloodAward) {
		this.bloodAward = bloodAward;
	}

}
