package iyunu.NewTLOL.service.iface.cdKey;

import iyunu.NewTLOL.model.base.CdKeyInfo;

import java.util.List;

public interface CdKeyService {

	List<CdKeyInfo> query();

	void delete(String cdKey);
}
