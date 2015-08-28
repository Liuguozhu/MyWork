package iyunu.NewTLOL.message;

import iyunu.NewTLOL.json.RaidsJson;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.trials.Trials;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 试炼消息
 * 
 * @author SunHonglei
 * 
 */
public class TrialsMessage {

	/**
	 * @function 发送已击杀怪物的位置
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @date 2014年4月10日
	 */
	public static void sendKillPosition(Role role, int trialsId) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshKillPosition");
				llpMessage.write("position", role.getTrials().get(trialsId).getPosition());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送已击杀怪物的位置");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新试炼状态
	 * 
	 * @param role
	 */
	public static void refreshTrialsState(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshTrialsState");
				llpMessage.write("state", role.getTrialsState());
				for (Trials trials : role.getTrials().values()) {
					LlpMessage message = llpMessage.write("trialsStateList");
					message.write("id", trials.getId());
					message.write("state", trials.getState());
					message.write("position", trials.getPosition());
//					System.out.println("id = " + trials.getId() + " state = " + trials.getState() + " position = " + trials.getPosition());
				}
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新试炼状态");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新试炼重置次数
	 * 
	 * @param role
	 */
	public static void refreshTrialsReSetNum(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshReSetNum");

				for (Integer degree : RaidsJson.instance().getTrialsListMap().keySet()) {
					LlpMessage trialsReSetNum = llpMessage.write("trialsReSetNumList");
					int trialsResetAdd = role.getVip().getLevel().getTrialsResetAdd();
					int useResetTrials = 0;
					if (role.getResetTrials().containsKey(degree)) {
						useResetTrials = role.getResetTrials().get(degree);
					}

					trialsReSetNum.write("state", degree);
					trialsReSetNum.write("reSetNum", (trialsResetAdd - useResetTrials) > 0 ? (trialsResetAdd - useResetTrials) : 0);
				}
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新试练重置次数");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}
