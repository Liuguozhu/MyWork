package iyunu.NewTLOL.model.mining;

import java.util.ArrayList;
import java.util.List;

public class MiningPage {

	private List<Mining> MiningList = new ArrayList<>();
	private int page;
	private int totalPage;

	/**
	 * @return the miningList
	 */
	public List<Mining> getMiningList() {
		return MiningList;
	}

	/**
	 * @param miningList
	 *            the miningList to set
	 */
	public void setMiningList(List<Mining> miningList) {
		MiningList = miningList;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * @param totalPage
	 *            the totalPage to set
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

}
