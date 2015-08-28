package iyunu.NewTLOL.event.uptip;

import iyunu.NewTLOL.model.role.Role;

public interface UptipInstance {

	public boolean check(Role role, boolean tag);

	public boolean countBefore(Role role);
}
