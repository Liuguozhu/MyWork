package iyunu.NewTLOL.ibatis;

import iyunu.NewTLOL.model.task.BaseTask;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.ibatis.sqlmap.engine.type.TypeHandler;

public class TaskListType implements TypeHandler {

	@Override
	public Object getResult(ResultSet rs, String parameter) throws SQLException {
		String v = rs.getString(parameter);
		HashMap<Integer, BaseTask> tasks = BaseTask.decode(v);
		return tasks;
	}

	@Override
	public Object getResult(ResultSet rs, int parameter) throws SQLException {
		String v = rs.getString(parameter);
		HashMap<Integer, BaseTask> tasks = BaseTask.decode(v);
		return tasks;
	}

	@Override
	public void setParameter(PreparedStatement prmt, int index, Object parameter, String jdbcType) throws SQLException {

		String result = "";
		@SuppressWarnings("unchecked")
		Map<Integer, BaseTask> map = (Map<Integer, BaseTask>) parameter;
		Set<Entry<Integer, BaseTask>> taskSet = map.entrySet();
		for (Iterator<Entry<Integer, BaseTask>> taskIt = taskSet.iterator(); taskIt.hasNext();) {
			BaseTask baseTask = taskIt.next().getValue();
			result += baseTask.encode() + "!";
		}

		prmt.setString(index, result);
	}

	@Override
	public Object valueOf(String arg0) {
		return null;
	}

	@Override
	public boolean equals(Object arg0, String arg1) {
		return false;
	}

	@Override
	public Object getResult(CallableStatement arg0, int arg1) throws SQLException {
		return null;
	}
}
