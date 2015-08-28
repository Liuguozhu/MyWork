package iyunu.NewTLOL.ibatis;

import iyunu.NewTLOL.json.RaidsJson;
import iyunu.NewTLOL.model.trials.Trials;
import iyunu.NewTLOL.model.trials.instance.TrialsInfo;
import iyunu.NewTLOL.util.json.JsonTool;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.alibaba.fastjson.TypeReference;
import com.ibatis.sqlmap.engine.type.TypeHandler;

public class TrialsType implements TypeHandler {

	@Override
	public Object getResult(ResultSet rs, String parameter) throws SQLException {
		String v = rs.getString(parameter);
		HashMap<Integer, Trials> map = JsonTool.decode(v, new TypeReference<HashMap<Integer, Trials>>() {
		});
		if (map == null) {
			map = new HashMap<Integer, Trials>();
			for (TrialsInfo trialsInfo : RaidsJson.instance().getTrialsMap().values()) {
				map.put(trialsInfo.getTrialsId(), new Trials(trialsInfo.getTrialsId(), trialsInfo.getDegree()));
			}
		}

		return map;
	}

	@Override
	public Object getResult(ResultSet rs, int parameter) throws SQLException {
		String v = rs.getString(parameter);
		HashMap<Integer, Trials> map = JsonTool.decode(v, new TypeReference<HashMap<Integer, Trials>>() {
		});
		if (map == null) {
			map = new HashMap<Integer, Trials>();
			for (TrialsInfo trialsInfo : RaidsJson.instance().getTrialsMap().values()) {
				map.put(trialsInfo.getTrialsId(), new Trials(trialsInfo.getTrialsId(), trialsInfo.getDegree()));
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
