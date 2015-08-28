package iyunu.NewTLOL.ibatis;

import iyunu.NewTLOL.model.bag.BagStone;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibatis.sqlmap.engine.type.TypeHandler;

public class BagStoneType implements TypeHandler {

	@Override
	public boolean equals(Object arg0, String arg1) {
		return false;
	}

	@Override
	public Object getResult(ResultSet rs, String parameter) throws SQLException {
		String v = rs.getString(parameter);
		return BagStone.decode(v);
	}

	@Override
	public Object getResult(ResultSet rs, int parameter) throws SQLException {
		String v = rs.getString(parameter);
		return BagStone.decode(v);
	}

	@Override
	public Object getResult(CallableStatement arg0, int arg1) throws SQLException {
		return null;
	}

	@Override
	public void setParameter(PreparedStatement prmt, int index, Object parameter, String jdbcType) throws SQLException {
		prmt.setString(index, parameter.toString());
	}

	@Override
	public Object valueOf(String arg0) {
		return null;
	}

}
