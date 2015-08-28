package iyunu.NewTLOL.dao.iface;

import iyunu.NewTLOL.model.base.CdKeyInfo;

import java.util.List;

public interface CdKeyDao {

	List<CdKeyInfo> query();

	void delete(String cdKey);
}
