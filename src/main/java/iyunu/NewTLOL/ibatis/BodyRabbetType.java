package iyunu.NewTLOL.ibatis;

import iyunu.NewTLOL.model.intensify.instance.Rabbet;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.util.Translate;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.ibatis.sqlmap.engine.type.TypeHandler;

public class BodyRabbetType implements TypeHandler {

	@Override
	public Object getResult(ResultSet rs, String parameter) throws SQLException {
		String v = rs.getString(parameter);

		HashMap<EEquip, HashMap<Integer, Rabbet>> newMap = new HashMap<EEquip, HashMap<Integer, Rabbet>>();
		if(v!= null && !"".equals(v)){
			String[] strings = v.split("=");
			for (int i = 0; i < strings.length; i++) {
				String[] strs = strings[i].split("!");
				EEquip part  =	EEquip.values()[Translate.stringToInt(strs[0])];
//				EEquip part = EEquip.getEEquipByIndex(Translate.stringToInt(strs[0]));
				if (part != null) {
					HashMap<Integer, Rabbet> rabbetMap = new HashMap<Integer, Rabbet>();
					for (int j = 1; j < strs.length; j++) {
						rabbetMap.put(j - 1, Rabbet.decode(strs[j]));
					}
					newMap.put(part, rabbetMap);
				}
			}
		}
		
		return newMap;
	}

	@Override
	public Object getResult(ResultSet rs, int parameter) throws SQLException {
		String v = rs.getString(parameter);

		HashMap<EEquip, HashMap<Integer, Rabbet>> newMap = new HashMap<EEquip, HashMap<Integer, Rabbet>>();
		if(v!= null && !"".equals(v)){
			String[] strings = v.split("=");
			for (int i = 0; i < strings.length; i++) {
				String[] strs = strings[i].split("!");
				EEquip part  =	EEquip.values()[Translate.stringToInt(strs[0])];
//				EEquip part = EEquip.getEEquipByIndex(Translate.stringToInt(strs[0]));
				if (part != null) {
					HashMap<Integer, Rabbet> rabbetMap = new HashMap<Integer, Rabbet>();
					for (int j = 1; j < strs.length; j++) {
						rabbetMap.put(j - 1, Rabbet.decode(strs[j]));
					}
					newMap.put(part, rabbetMap);
				}
			}
		}
		return newMap;
	}

	@Override
	public void setParameter(PreparedStatement prmt, int index, Object parameter, String jdbcType) throws SQLException {

		@SuppressWarnings("unchecked")
		HashMap<EEquip, HashMap<Integer, Rabbet>> map = (HashMap<EEquip, HashMap<Integer, Rabbet>>) parameter;
		String result = "";
		Iterator<Entry<EEquip, HashMap<Integer, Rabbet>>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<EEquip, HashMap<Integer, Rabbet>> entry = it.next();
			result += entry.getKey().ordinal();
			for (Rabbet rabbet : entry.getValue().values()) {
				result += "!" + rabbet.encode();
			}
			result += "=";
		}
		prmt.setString(index, result);
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
