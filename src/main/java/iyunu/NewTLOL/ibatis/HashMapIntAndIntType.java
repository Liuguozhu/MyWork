package iyunu.NewTLOL.ibatis;

import iyunu.NewTLOL.util.json.JsonTool;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.alibaba.fastjson.TypeReference;
import com.ibatis.sqlmap.engine.type.TypeHandler;

public class HashMapIntAndIntType implements TypeHandler {

	@Override
	public Object getResult(ResultSet rs, String parameter) throws SQLException {
		String v = rs.getString(parameter);
		HashMap<Integer, Integer> map = JsonTool.decode(v, new TypeReference<HashMap<Integer, Integer>>() {
		});
		if (map == null) {
			map = new HashMap<Integer, Integer>();
		}
		return map;
	}

	@Override
	public Object getResult(ResultSet rs, int parameter) throws SQLException {
		String v = rs.getString(parameter);
		HashMap<Integer, Integer> map = JsonTool.decode(v, new TypeReference<HashMap<Integer, Integer>>() {
		});
		if (map == null) {
			map = new HashMap<Integer, Integer>();
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
