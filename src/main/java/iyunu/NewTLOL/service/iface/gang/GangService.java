package iyunu.NewTLOL.service.iface.gang;

import iyunu.NewTLOL.model.gang.Gang;

import java.util.List;

public interface GangService {

	List<Gang> loadAllGang();

	void insertGang(Gang gang);

	void updateGang(Gang gang);

	void deleteGang(long gangId);

}
