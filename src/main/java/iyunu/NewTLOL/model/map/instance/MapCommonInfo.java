package iyunu.NewTLOL.model.map.instance;

import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.DistributionHidden;

import java.util.ArrayList;
import java.util.List;

/**
 * 普通地图信息
 * 
 * @author SunHonglei
 * 
 */
public class MapCommonInfo extends BaseMap {

	// private int gridWidth = 20; // 每行格子数
	// private int gridHeight = 11; // 每列格子数
	// private static int GRID_SIZE = 6; // 每个单元格长度
	// private static int GRID_LENGTH = 5; // 显示周围玩家半径
	private List<DistributionHidden> distributions = new ArrayList<DistributionHidden>(); // 怪物分布
	private List<DistributionHidden> eliteDistributions = new ArrayList<DistributionHidden>(); // 怪物分布

	// @Override
	// public void init() {
	// rolesAtGrids.add(new HashSet<Long>());
	// gridWidth = width / GRID_SIZE;
	// gridHeight = (height + 1) / GRID_SIZE;
	// for (int i = 0; i < gridWidth * gridHeight; i++) {
	// rolesAtGrids.add(new HashSet<Long>());
	// }
	// }

	// @Override
	// public int calcGrid(final Role role) {
	// return 0;
	// // return role.getMapInfo().getX() / GRID_SIZE + role.getMapInfo().getY()
	// / GRID_SIZE * gridWidth;
	// }

	// @Override
	// public List<Integer> countGrid(Role role) {
	// List<Integer> list = new ArrayList<Integer>();
	// list.add(calcGrid(role));
	// return list;
	//
	// // int curgid = calcGrid(role);
	// // List<Integer> list = new ArrayList<Integer>();
	// // for (int j = 0 - GRID_LENGTH; j < 0 + GRID_LENGTH; j++) {
	// // int base = curgid - (gridWidth * j);
	// // for (int i = base - GRID_LENGTH; i <= base + GRID_LENGTH; i++) {
	// // if (i >= 0 && i < rolesAtGrids.size()) {
	// // list.add(i);
	// // }
	// // }
	// // }
	// // return list;
	// }

	/**
	 * @return the distributions
	 */
	public List<DistributionHidden> getDistributions() {
		return distributions;
	}

	@Override
	public MapCommonInfo copy() {
		return this;
	}

	/**
	 * @return the eliteDistributions
	 */
	public List<DistributionHidden> getEliteDistributions() {
		return eliteDistributions;
	}

}
