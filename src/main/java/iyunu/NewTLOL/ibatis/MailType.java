package iyunu.NewTLOL.ibatis;

import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.util.Translate;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.ibatis.sqlmap.engine.type.TypeHandler;

public class MailType implements TypeHandler {

	@Override
	public Object getResult(ResultSet rs, String parameter) throws SQLException {
		String v = rs.getString(parameter);

		HashMap<Item, Integer> map = new HashMap<Item, Integer>();
		if (v != null && !"".equals(v)) {

			if (v.contains("&")) {
				String[] strings = v.split("&");
				for (String string : strings) {
					String[] strs = string.split("#");
					map.put(Item.decode(strs[0]), Translate.stringToInt(strs[1]));
				}
			} else {
				String[] strings = v.split(";");
				for (String string : strings) {

					String[] strs = string.split(":");

					Item item = ItemJson.instance().getItem(Translate.stringToInt(strs[0]));
					if (item instanceof Equip) {
						Equip equip = (Equip) item;
						equip.setStar(Translate.stringToInt(strs[1]));
					}
					if (strs.length > 3) {
						item.setIsDeal(Translate.stringToInt(strs[3]));
						item.setTimeOut(Translate.stringToLong(strs[4]));
					}
					map.put(item, Translate.stringToInt(strs[2]));
				}
			}
		}
		return map;
	}

	@Override
	public Object getResult(ResultSet rs, int parameter) throws SQLException {
		String v = rs.getString(parameter);

		HashMap<Item, Integer> map = new HashMap<Item, Integer>();

		if (v != null && !"".equals(v)) {

			if (v.contains("&")) {
				String[] strings = v.split("&");
				for (String string : strings) {
					String[] strs = string.split("#");
					map.put(Item.decode(strs[0]), Translate.stringToInt(strs[1]));
				}
			} else {
				String[] strings = v.split(";");
				for (String string : strings) {

					String[] strs = string.split(":");

					Item item = ItemJson.instance().getItem(Translate.stringToInt(strs[0]));
					if (item instanceof Equip) {
						Equip equip = (Equip) item;
						equip.setStar(Translate.stringToInt(strs[1]));
					}
					if (strs.length > 3) {
						item.setIsDeal(Translate.stringToInt(strs[3]));
						item.setTimeOut(Translate.stringToLong(strs[4]));
					}
					map.put(item, Translate.stringToInt(strs[2]));
				}
			}
		}

		return map;
	}

	@Override
	public void setParameter(PreparedStatement prmt, int index, Object parameter, String jdbcType) throws SQLException {

		@SuppressWarnings("unchecked")
		HashMap<Item, Integer> map = (HashMap<Item, Integer>) parameter;

		String string = "";
		Iterator<Entry<Item, Integer>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Item, Integer> entry = it.next();

			string += entry.getKey().encode() + "#" + entry.getValue() + "&";
			// if (entry.getKey() instanceof Equip) {
			// Equip equip = (Equip) entry.getKey();
			// string += equip.getId() + ":" + equip.getStar() + ":" +
			// entry.getValue() + ":" + equip.getIsDeal() + ":" +
			// equip.getTimeOut() + ";";
			// } else {
			// Item item = entry.getKey();
			// string += item.getId() + ":0" + ":" + entry.getValue() + ":" +
			// item.getIsDeal() + ":" + item.getTimeOut() + ";";
			// }
		}

		prmt.setString(index, string);
	}

	@Override
	public Object valueOf(String arg0) {
		return null;
	}

	@Override
	public boolean equals(Object arg0, String arg1) {
		return false;
	}

	@Override
	public Object getResult(CallableStatement arg0, int arg1) throws SQLException {
		return null;
	}
}
