package iyunu.NewTLOL.model.room;

import iyunu.NewTLOL.model.role.Role;

import java.util.ArrayList;
import java.util.List;

public class Room {

	private static int autoincreaseid = 1; // 队伍编号计数器
	private int id;
	private String name;
	private String password = "";
	private List<Long> members = new ArrayList<Long>();
	private List<String> names = new ArrayList<String>();
	private List<String> msg = new ArrayList<String>();

	/**
	 * 构造方法
	 * 
	 * @param name
	 *            创建者名称
	 */
	public Room(Role role) {
		this.name = role.getNick() + "的房间";
		this.id = autoincreaseid++;
		if (autoincreaseid > 1000000000) {
			autoincreaseid = 1;
		}

		members.add(role.getId());
		names.add(role.getNick());
		addMsg("恭喜，" + role.getNick() + "的房间创建成功");
	}

	public void addMsg(String content) {
		msg.add(content);
		if (msg.size() > 10) {
			msg.remove(0);
		}
	}

	public void addMember(Role role) {
		members.add(role.getId());
	}

	public void removeMember(Role role) {
		if (members.contains(role.getId())) {
			members.remove(role.getId());
			names.remove(role.getNick());
			addMsg(role.getNick() + "退出房间");
		}
	}

	public int isPasswd() {
		if (password.equals("")) {
			return 0;
		} else {
			return 1;
		}
	}

	public int size() {
		return members.size();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the members
	 */
	public List<Long> getMembers() {
		return members;
	}

	/**
	 * @param members
	 *            the members to set
	 */
	public void setMembers(List<Long> members) {
		this.members = members;
	}

	/**
	 * @return the msg
	 */
	public List<String> getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(List<String> msg) {
		this.msg = msg;
	}

}
