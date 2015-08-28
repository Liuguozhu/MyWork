package iyunu.NewTLOL.model.skillBook.res;

import iyunu.NewTLOL.model.skillBook.instance.HuntskillCoord;

public class HuntskillCoordRes {
	int mapId;
	int x;
	int y;

	public HuntskillCoord toHuntskillCoord() {
		HuntskillCoord huntskillCoord = new HuntskillCoord();
		huntskillCoord.setMapId(mapId);
		huntskillCoord.setX(x);
		huntskillCoord.setY(y);
		return huntskillCoord;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
