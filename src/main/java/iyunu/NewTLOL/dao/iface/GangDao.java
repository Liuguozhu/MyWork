package iyunu.NewTLOL.dao.iface;

import iyunu.NewTLOL.model.gang.Gang;

import java.util.List;

public interface GangDao {

	List<Gang> loadAllGang();

	void insertGang(Gang gang);

	void updateGang(Gang gang);

	void deleteGang(long gangId);
}
