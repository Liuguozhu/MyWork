package iyunu.NewTLOL.model.io.role.instance;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.common.RoleCommon;
import iyunu.NewTLOL.model.io.role.ERoleIOTask;
import iyunu.NewTLOL.model.io.role.RoleIOTask;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.service.iface.role.RoleServiceIfce;

public class UpdateTask extends RoleIOTask {

	public UpdateTask(ERoleIOTask id, Role role) {
		super(id, role);
	}

	@Override
	public void excute() {

		if (role != null && role.isLogon()) {
			RoleServiceIfce roleService = Spring.instance().getBean("roleService", RoleServiceIfce.class);
			role.setUpdateTime(System.currentTimeMillis() + RoleCommon.UPDATE_TIME); // 设置自动更新时间
			roleService.update(role);
		}

	}

	@Override
	public void callBack() {

	}

}
