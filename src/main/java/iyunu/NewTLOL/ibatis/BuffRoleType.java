package iyunu.NewTLOL.ibatis;

import iyunu.NewTLOL.model.buffRole.EBuffType;
import iyunu.NewTLOL.model.buffRole.instance.BuffRole;
import iyunu.NewTLOL.util.json.JsonTool;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.alibaba.fastjson.TypeReference;
import com.ibatis.sqlmap.engine.type.TypeHandler;

public class BuffRoleType implements TypeHandler {

	@Override
	public Object getResult(ResultSet rs, String parameter) throws SQLException {
		String v = rs.getString(parameter);
		HashMap<EBuffType, BuffRole> map = JsonTool.decode(v, new TypeReference<HashMap<EBuffType, BuffRole>>() {
		});
		if (map == null) {
			map = new HashMap<EBuffType, BuffRole>();
		}
		return map;
	}

	@Override
	public Object getResult(ResultSet rs, int parameter) throws SQLException {
		String v = rs.getString(parameter);
		HashMap<EBuffType, BuffRole> map = JsonTool.decode(v, new TypeReference<HashMap<EBuffType, BuffRole>>() {
		});
		if (map == null) {
			map = new HashMap<EBuffType, BuffRole>();
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
