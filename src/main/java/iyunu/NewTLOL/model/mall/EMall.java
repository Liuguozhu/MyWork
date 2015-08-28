package iyunu.NewTLOL.model.mall;

import iyunu.NewTLOL.model.mall.instance.Mall;

import java.util.ArrayList;

public enum EMall {

	none(new ArrayList<Mall>()), // （暂无）
	shop(new ArrayList<Mall>()), // 商店
	gang(new ArrayList<Mall>()), // 帮派商店
	drug(new ArrayList<Mall>()), // 药品
	cailiao(new ArrayList<Mall>()), // 材料
	shizhuang(new ArrayList<Mall>()), // 时装
	stone(new ArrayList<Mall>()), // 宝石
	other(new ArrayList<Mall>()), // 其他
	vip(new ArrayList<Mall>()), // Vip专属
	coin(new ArrayList<Mall>()), // 银两
	;

	private ArrayList<Mall> malls;

	EMall(ArrayList<Mall> malls) {
		this.malls = malls;
	}

	/**
	 * @return the malls
	 */
	public ArrayList<Mall> getMalls() {
		return malls;
	}

}
