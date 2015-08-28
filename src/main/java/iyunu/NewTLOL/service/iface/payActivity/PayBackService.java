package iyunu.NewTLOL.service.iface.payActivity;

import iyunu.NewTLOL.model.base.PayBackInfo;

public interface PayBackService {

	PayBackInfo query(String userId);

	void delete(String userId);
}
