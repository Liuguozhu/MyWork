package iyunu.NewTLOL.dao.iface;

import iyunu.NewTLOL.model.base.PayBackInfo;

public interface PayBackDao {

	PayBackInfo query(String userId);

	void delete(String userId);
}
