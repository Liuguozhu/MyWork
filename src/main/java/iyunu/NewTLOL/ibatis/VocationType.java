package iyunu.NewTLOL.ibatis;

import iyunu.NewTLOL.enumeration.Vocation;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibatis.sqlmap.engine.type.TypeHandler;

public class VocationType implements TypeHandler {

	@Override
	public boolean equals(Object arg0, String arg1) {
		return false;
	}

	@Override
	public Object getResult(ResultSet rs, String parameter) throws SQLException {
		int v = rs.getInt(parameter);
		return Vocation.values()[v];
	}

	@Override
	public Object getResult(ResultSet rs, int parameter) throws SQLException {
		int v = rs.getInt(parameter);
		return Vocation.values()[v];
	}

	@Override
	public Object getResult(CallableStatement arg0, int arg1) throws SQLException {
		return null;
	}

	@Override
	public void setParameter(PreparedStatement prmt, int index, Object parameter, String arg3) throws SQLException {
		Vocation vocation = (Vocation) parameter;
		prmt.setInt(index, vocation.ordinal());
	}

	@Override
	public Object valueOf(String arg0) {
		return null;
	}

}
