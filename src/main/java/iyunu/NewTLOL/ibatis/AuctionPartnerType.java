package iyunu.NewTLOL.ibatis;

import iyunu.NewTLOL.json.PartnerJson;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.server.partner.PartnerServer;
import iyunu.NewTLOL.util.Translate;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibatis.sqlmap.engine.type.TypeHandler;

public class AuctionPartnerType implements TypeHandler {

	@Override
	public boolean equals(Object arg0, String arg1) {
		return false;
	}

	@Override
	public Object getResult(ResultSet rs, String parameter) throws SQLException {
		String v = rs.getString(parameter);
		if (v != null && !"".equals(v)) {
			String[] str = v.split("!");
			Partner partner = PartnerJson.instance().getPartner(Translate.stringToInt(str[0]));
			partner.decode(str[1]);
			PartnerServer.countPotential(partner);
			return partner;
		} else {
			return null;
		}

	}

	@Override
	public Object getResult(ResultSet rs, int parameter) throws SQLException {
		String v = rs.getString(parameter);
		if (v != null && !"".equals(v)) {
			String[] str = v.split("!");
			Partner partner = PartnerJson.instance().getPartner(Translate.stringToInt(str[0]));
			partner.decode(str[1]);
			PartnerServer.countPotential(partner);
			return partner;
		} else {
			return null;
		}
	}

	@Override
	public Object getResult(CallableStatement arg0, int arg1) throws SQLException {
		return null;
	}

	@Override
	public void setParameter(PreparedStatement prmt, int index, Object parameter, String jdbcType) throws SQLException {
		if(parameter != null){
			Partner partner = (Partner) parameter;
			String str = partner.getIndex() + "!" + partner.encode();
			prmt.setString(index, str);
		}else{
			prmt.setString(index, "");
		}
		
	}

	@Override
	public Object valueOf(String arg0) {
		return null;
	}

}
