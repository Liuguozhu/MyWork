package iyunu.NewTLOL.event;

import iyunu.NewTLOL.model.role.Role;

import org.springframework.context.ApplicationEvent;

public class BattleFightEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8207379385220572638L;
	private Role role;

	/**
	 * 攻城事件
	 * 
	 * @param source
	 *            事件源
	 * @param battleFight
	 *            攻城战斗对象
	 */
	public BattleFightEvent(Object source, Role role) {
		super(source);
		this.role = role;
	}


}
