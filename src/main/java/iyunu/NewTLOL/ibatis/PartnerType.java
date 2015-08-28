package iyunu.NewTLOL.ibatis;

import iyunu.NewTLOL.json.PartnerJson;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.server.partner.PartnerServer;
import iyunu.NewTLOL.util.Translate;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.engine.type.TypeHandler;

public class PartnerType implements TypeHandler {

	@Override
	public boolean equals(Object arg0, String arg1) {
		return false;
	}

	@Override
	public Object getResult(ResultSet rs, String parameter) throws SQLException {
		String v = rs.getString(parameter);
		ArrayList<Partner> list = new ArrayList<Partner>();
		if (v != null) {
			String[] strs = v.split("#");
			for (String string : strs) {
				String[] str = string.split("!");
				Partner partner = PartnerJson.instance().getPartner(Translate.stringToInt(str[0]));
				partner.decode(str[1]);
				PartnerServer.countPotential(partner);
				list.add(partner);
			}
		}
		return list;
	}

	@Override
	public Object getResult(ResultSet rs, int parameter) throws SQLException {
		String v = rs.getString(parameter);
		ArrayList<Partner> list = new ArrayList<Partner>();
		if (v != null) {
			String[] strs = v.split("#");
			for (String string : strs) {
				String[] str = string.split("!");
				Partner partner = PartnerJson.instance().getPartner(Translate.stringToInt(str[0]));
				partner.decode(str[1]);
				PartnerServer.countPotential(partner);
				list.add(partner);
			}
		}
		return list;
	}

	@Override
	public Object getResult(CallableStatement arg0, int arg1) throws SQLException {
		return null;
	}

	@Override
	public void setParameter(PreparedStatement prmt, int index, Object parameter, String jdbcType) throws SQLException {

		@SuppressWarnings("unchecked")
		List<Partner> partners = (List<Partner>) parameter;
		if (partners.size() == 0) {
			prmt.setString(index, "");
		} else {
			String str = "";
			for (Partner partner : partners) {
				str += "#" + partner.getIndex() + "!" + partner.encode();
			}
			prmt.setString(index, str.substring(1));
		}
	}

	@Override
	public Object valueOf(String arg0) {
		return null;
	}

}
