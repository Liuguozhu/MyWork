package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.map.res.CollectedInfoRes;
import iyunu.NewTLOL.model.map.res.MapInfoRes;
import iyunu.NewTLOL.model.map.res.NpcInfoRes;
import iyunu.NewTLOL.model.map.res.TransferInfoRes;
import iyunu.NewTLOL.util.json.JsonExproter;

public class MapConverter {

	/**
	 * 地图资源转换器
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" + serverRes + "/地图.xlsx";

		String mapInfo = "src/main/resources/json/" + serverRes + "/MapInfo.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, mapInfo, MapInfoRes.class, "地图");

		String mapNpc = "src/main/resources/json/" + serverRes + "/MapNpc.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, mapNpc, NpcInfoRes.class, "NPC");

		String mapTransfer = "src/main/resources/json/" + serverRes + "/MapTransfer.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, mapTransfer, TransferInfoRes.class, "传送点");

		String mapCollected = "src/main/resources/json/" + serverRes + "/MapCollected.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, mapCollected, CollectedInfoRes.class, "采集物品");
	}

}