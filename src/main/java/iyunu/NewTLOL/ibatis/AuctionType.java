package iyunu.NewTLOL.ibatis;


import iyunu.NewTLOL.model.auction.EAuctionType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibatis.sqlmap.engine.type.TypeHandler;

public class AuctionType implements TypeHandler {

	@Override
	public boolean equals(Object arg0, String arg1) {
		return false;
	}

	@Override
	public Object getResult(ResultSet rs, String parameter) throws SQLException {
		int v = rs.getInt(parameter);
		return EAuctionType.values()[v];
	}

	@Override
	public Object getResult(ResultSet rs, int parameter) throws SQLException {
		int v = rs.getInt(parameter);
		return EAuctionType.values()[v];
	}

	@Override
	public Object getResult(CallableStatement arg0, int arg1) throws SQLException {
		return null;
	}

	@Override
	public void setParameter(PreparedStatement prmt, int index, Object parameter, String arg3) throws SQLException {
		EAuctionType auctionType = (EAuctionType) parameter;
		prmt.setInt(index, auctionType.ordinal());
	}

	@Override
	public Object valueOf(String arg0) {
		return null;
	}

}
