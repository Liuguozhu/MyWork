package iyunu.NewTLOL.ibatis;

import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.ShenbingJson;
import iyunu.NewTLOL.model.item.AddProperty;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.model.shenbing.res.ShenbingUpStarRes;
import iyunu.NewTLOL.util.json.JsonTool;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.alibaba.fastjson.TypeReference;
import com.ibatis.sqlmap.engine.type.TypeHandler;

public class EquipmentsType implements TypeHandler {

	@Override
	public Object getResult(ResultSet rs, String parameter) throws SQLException {
		String v = rs.getString(parameter);
		HashMap<EEquip, Equip> map = JsonTool.decode(v, new TypeReference<HashMap<EEquip, Equip>>() {
		});

		if (map == null) {
			map = new HashMap<EEquip, Equip>();
		}
		Iterator<Entry<EEquip, Equip>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<EEquip, Equip> entry = it.next();
			if (entry.getValue() != null) {
				if (entry.getValue().getPart().equals(EEquip.shizhuang) && entry.getValue().getTimeOut() < System.currentTimeMillis()) {
					map.put(entry.getKey(), null);
				} else {
					Equip newEquip = (Equip) ItemJson.instance().getItem(entry.getValue().getId(), entry.getValue().getUid());
					newEquip.setSteps(entry.getValue().getSteps());
					newEquip.setStar(entry.getValue().getStar());
					newEquip.setLuck(entry.getValue().getLuck());
					ShenbingUpStarRes newShenbingUpStarRes = ShenbingJson.instance().getShenbingUpStarResMap().get(newEquip.getStar());
					newEquip.setLuckLimit(newShenbingUpStarRes.getLuckLimit());
					newEquip.setPropertyPercent(newShenbingUpStarRes.getPropertyPercent());
					newEquip.setIsDeal(entry.getValue().getIsDeal());
					newEquip.setTimeOut(entry.getValue().getTimeOut());
					for (AddProperty addProperty : entry.getValue().getAddPropertyList()) {
						newEquip.getAddPropertyList().add(addProperty);
					}
					entry.getValue().getAddPropertyList().clear();
					map.put(entry.getKey(), newEquip);
				}
			}
		}
		return map;
	}

	@Override
	public Object getResult(ResultSet rs, int parameter) throws SQLException {
		String v = rs.getString(parameter);
		HashMap<EEquip, Equip> map = JsonTool.decode(v, new TypeReference<HashMap<EEquip, Equip>>() {
		});
		if (map == null) {
			map = new HashMap<EEquip, Equip>();
		}
		Iterator<Entry<EEquip, Equip>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<EEquip, Equip> entry = it.next();
			if (entry.getValue() != null) {
				if (entry.getValue().getPart().equals(EEquip.shizhuang) && entry.getValue().getTimeOut() < System.currentTimeMillis()) {
					map.put(entry.getKey(), null);
				} else {

					Equip newEquip = (Equip) ItemJson.instance().getItem(entry.getValue().getId(), entry.getValue().getUid());

					newEquip.setSteps(entry.getValue().getSteps());
					newEquip.setStar(entry.getValue().getStar());
					newEquip.setLuck(entry.getValue().getLuck());
					ShenbingUpStarRes newShenbingUpStarRes = ShenbingJson.instance().getShenbingUpStarResMap().get(newEquip.getStar());
					newEquip.setLuckLimit(newShenbingUpStarRes.getLuckLimit());
					newEquip.setPropertyPercent(newShenbingUpStarRes.getPropertyPercent());
					newEquip.setIsDeal(entry.getValue().getIsDeal());
					newEquip.setTimeOut(entry.getValue().getTimeOut());
					for (AddProperty addProperty : entry.getValue().getAddPropertyList()) {
						newEquip.getAddPropertyList().add(addProperty);
					}
					entry.getValue().getAddPropertyList().clear();
					map.put(entry.getKey(), newEquip);
				}
			}
		}
		return map;
	}

	@Override
	public void setParameter(PreparedStatement prmt, int index, Object parameter, String jdbcType) throws SQLException {
		prmt.setString(index, JsonTool.encode(parameter));
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
