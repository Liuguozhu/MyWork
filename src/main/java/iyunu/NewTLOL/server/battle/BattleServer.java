package iyunu.NewTLOL.server.battle;

import iyunu.NewTLOL.common.BattleForm;
import iyunu.NewTLOL.enumeration.Vocation;
import iyunu.NewTLOL.enumeration.common.EExp;
import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.enumeration.partner.EGetPartner;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.MonsterJson;
import iyunu.NewTLOL.json.PartnerJson;
import iyunu.NewTLOL.json.QiancengtaJson;
import iyunu.NewTLOL.json.SkillJson;
import iyunu.NewTLOL.manager.BattleManager;
import iyunu.NewTLOL.manager.DailyManager;
import iyunu.NewTLOL.manager.PartnerManager;
import iyunu.NewTLOL.manager.RaidsManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.TaskManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.FightMessage;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.CommandInfo;
import iyunu.NewTLOL.model.battle.EBattleBuffType;
import iyunu.NewTLOL.model.battle.EDirection;
import iyunu.NewTLOL.model.battle.amin.BattleAmin;
import iyunu.NewTLOL.model.battle.amin.BattleAminInfo;
import iyunu.NewTLOL.model.battle.buff.BattleBuff;
import iyunu.NewTLOL.model.ifce.ICharacter;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.instance.MapGangInfo;
import iyunu.NewTLOL.model.map.instance.MapRaidsInfo;
import iyunu.NewTLOL.model.monster.MonsterDropItem;
import iyunu.NewTLOL.model.monster.MonsterDropPartner;
import iyunu.NewTLOL.model.monster.instance.Monster;
import iyunu.NewTLOL.model.monster.instance.MonsterGroup;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.qiancengta.instance.QiancengtaInfo;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.skill.ESkillType;
import iyunu.NewTLOL.model.skill.instance.BaseSkill;
import iyunu.NewTLOL.model.skill.instance.PartnerSkill;
import iyunu.NewTLOL.model.skill.species.ParamTemp;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskState;
import iyunu.NewTLOL.model.task.instance.KillTask;
import iyunu.NewTLOL.model.task.instance.YingxiongtieTask;
import iyunu.NewTLOL.model.team.Team;
import iyunu.NewTLOL.server.award.AwardServer;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.partner.PartnerServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BattleServer {

	/**
	 * 战斗预处理
	 * 
	 * @param role
	 *            角色对象
	 * @param monsterGroup
	 *            怪物组合
	 * @param type
	 *            战斗类型
	 * @return 战斗对象
	 */
	public static BattleInfo preBattle(Role role, MonsterGroup monsterGroup, int type, BaseMap baseMap, boolean isAuto) {

		int size = 1;
		int monsterSize = 1;
		if (role.getTeam() != null) {
			size = role.getTeam().getMemberSize();
			monsterSize = size;
		}

		BattleInfo battleInfo = new BattleInfo(size, type, isAuto);
		battleInfo.setMonsterGroup(monsterGroup);
		int sumLevel = role.getLevel();
		// ======填充右边队伍信息（永远是人，自己的队伍）======
		if (role.getTeam() == null) {
			BattleCharacter battleCharacter = BattleServer.changeBattleCharacter(7, role, EDirection.right);
			battleInfo.addCharacterInToRight(7, battleCharacter); // 玩家

			Iterator<Entry<Integer, Long>> it = role.getPartnerFight().entrySet().iterator();
			int num = 0;
			while (it.hasNext()) {
				Entry<Integer, Long> entry = it.next();
				if (entry.getValue() != 0) {
					Partner partner = role.findPartner(entry.getValue());
					if (partner != null) {
						battleCharacter.addPartner(entry.getValue()); // 添加出战伙伴
						int site = BattleServer.partnerRightSite(entry.getKey());
						battleInfo.addCharacterInToRight(site, BattleServer.changeBattleCharacter(site, partner, EDirection.right)); // 伙伴
						num++;
					}
				}
			}
			monsterSize += num / 2;
			battleInfo.setRightLeader(role);
		} else {
			sumLevel = 0;
			Team team = role.getTeam();
			for (int i = 0; i < size; i++) {
				Role member = team.getMember().get(i);
				sumLevel += member.getLevel();
				BattleCharacter battleCharacter = BattleServer.changeBattleCharacter(7 + i, member, EDirection.right);
				battleInfo.addCharacterInToRight(7 + i, battleCharacter); // 玩家
				Long partnerId = member.getPartnerFight().get(1);
				if (partnerId != null && partnerId != 0) {
					Partner partner = member.findPartner(partnerId);
					if (partner != null) {
						battleCharacter.addPartner(partnerId); // 添加出战伙伴
						battleInfo.addCharacterInToRight(10 + i, BattleServer.changeBattleCharacter(10 + i, partner, EDirection.right)); // 伙伴
					}
				}
				if (i == 0) {
					battleInfo.setRightLeader(member);
				}
			}
		}

		// ======填充左边队伍信息（怪物）======
		List<Monster> monsters = MonsterJson.instance().getMonstersByGroupId(monsterGroup, monsterSize, sumLevel / size);
		for (int i = 0; i < monsters.size(); i++) {
			Monster monster = monsters.get(i);
			if (monster != null) {
				int index = i + 1;
				battleInfo.addCharacterInToLeft(index, BattleServer.changeBattleCharacter(index, monster, EDirection.left));
			}
		}

		// ======填充战斗信息======
		battleInfo.setBattleId(BattleManager.instance().getBattleId()); // 设置战斗编号
		if (battleInfo.isAuto()) {
			battleInfo.setWaitingTime(System.currentTimeMillis()); // 设置战斗时间
		} else {
			battleInfo.setWaitingTime(System.currentTimeMillis() + BattleManager.BATTLE_DURATION); // 设置战斗时间
		}
		battleInfo.setBaseMap(baseMap); // 设置战斗所在地图

		return battleInfo;
	}

	public static int partnerRightSite(int index) {
		switch (index) {
		case 1:
			return 10;
		case 2:
			return 11;
		case 3:
			return 12;
		case 4:
			return 8;
		default:
			return 9;
		}
	}

	public static int partnerLeftSite(int index) {
		switch (index) {
		case 1:
			return 4;
		case 2:
			return 5;
		case 3:
			return 6;
		case 4:
			return 2;
		default:
			return 3;
		}
	}

	/**
	 * 获取怪物数量
	 * 
	 * @param size
	 *            队伍人数
	 * @return 怪物数量
	 */
	public static int randomMonsterNumber(int size) {

		switch (size) {
		case 1:
			if (Util.probable()) {
				return 1;
			}
			return 2;
		case 2:
			if (Util.probable()) {
				return 3;
			}
			return 4;
		default:
			return 6;
		}
	}

	/**
	 * 战斗对象转换
	 * 
	 * @param index
	 *            队伍索引
	 * @param character
	 *            对象
	 * @param direction
	 *            方位
	 * @return 战斗对象
	 */
	public static BattleCharacter changeBattleCharacter(int index, ICharacter character, EDirection direction) {
		return new BattleCharacter(character, index, direction);
	}

	/**
	 * 计算BUFF
	 * 
	 * @param battleInfo
	 *            战斗对象
	 */
	public static void countBuff(BattleInfo battleInfo) {

		// 清空BUFF动画
		battleInfo.clearBuffAmin();
		battleInfo.checkOrder(); // 检查战斗队列
		while (true) {

			BattleCharacter battleCharacter = battleInfo.getNextICharacter();
			if (battleCharacter == null) {
				break;
			}

			if (battleCharacter.isDead() || battleCharacter.isEscape()) {
				continue;
			}

			// 重置战斗属性
			battleCharacter.resetPropertyBattle();

			// 计算BUFF状态
			for (BattleBuff buff : battleCharacter.getBuffs().values()) {
				int duration = buff.getDuration() - 1;
				if (duration <= 0) { // buff失效
					battleCharacter.getDisappearBuffs().add(buff.getId());
				}

				buff.setDuration(duration); // 修改buff时效
				if (buff.getType().equals(EBattleBuffType.b1) || buff.getType().equals(EBattleBuffType.b2) || buff.getType().equals(EBattleBuffType.b3) || buff.getType().equals(EBattleBuffType.b4)) {
					continue; // 特殊buff，无需计算，对属性没有影响
				}
				buff.effect(battleCharacter, battleInfo); // 计算buff效果
			}

			if (!battleInfo.isAuto()) { // 自动战斗不同步生命值和内力值、伙伴生命值
				BattleServer.syncHpAndMp(battleCharacter);
			}
		}
	}

	/**
	 * 战斗
	 * 
	 * @param battleInfo
	 *            战斗信息
	 * @return 战斗结束
	 */
	public static boolean battle(BattleInfo battleInfo) {

		if (battleInfo.isAuto()) { // 自动战斗

			battleInfo.setWaitingTime(System.currentTimeMillis() + BattleManager.BATTLE_DURATION); // 设置战斗时间
			battleInfo.cleanBattleAmin(); // 战斗动画——清除战斗动画

			// 计算BUFF
			countBuff(battleInfo);

			boolean finish = false;
			while (true) {

				BattleCharacter battleCharacter = battleInfo.getNextICharacter();

				if (battleCharacter == null) { // 所有人都执行完毕，战斗未结束
					finish = false;
					break;
				}

				if (battleCharacter.isDead() || battleCharacter.isEscape()) {
					battleCharacter.setCommandInfo(null); // 清除指令
					continue;
				}

				if (battleCharacter.cantMove()) { // 被封印不能动，则不可以任何操作
					continue;
				}

				if (fight(battleCharacter, battleInfo, battleInfo.isAuto())) {
					finish = true;
					break;
				}
			}

			battleInfo.counterClear(); // 重置计数器
			battleInfo.setTurn(battleInfo.getTurn() + 1); // 设置轮数
			battleInfo.setWaitingTime(System.currentTimeMillis()); // 设置战斗时间
			return finish;

		} else { // 正常战斗

			battleInfo.setWaitingTime(System.currentTimeMillis() + BattleManager.BATTLE_DURATION); // 设置战斗时间
			battleInfo.cleanBattleAmin(); // 战斗动画——清除战斗动画

			// 计算BUFF
			countBuff(battleInfo);

			boolean finish = false;
			while (true) {

				BattleCharacter battleCharacter = battleInfo.getNextICharacter();

				if (battleCharacter == null) { // 所有人都执行完毕，战斗未结束
					finish = false;
					break;
				}

				if (battleCharacter.isDead() || battleCharacter.isEscape()) {
					battleCharacter.setCommandInfo(null); // 清除指令
					continue;
				}

				if (battleCharacter.cantMove()) { // 被封印不能动，则不可以任何操作
					continue;
				}

				if (fight(battleCharacter, battleInfo, battleInfo.isAuto())) {
					finish = true;
					break;
				}
			}

			battleInfo.counterClear(); // 重置计数器
			battleInfo.setTurn(battleInfo.getTurn() + 1); // 设置轮数
			return finish;
		}
	}

	/**
	 * 战斗单回合流程
	 * 
	 * @param attactCharacter
	 *            攻击者
	 * @param battleInfo
	 *            战斗信息
	 * @return 战斗结束
	 */
	public static boolean fight(BattleCharacter attactCharacter, BattleInfo battleInfo, boolean isAuto) {
		boolean result = false;

		if (isAuto) {

			if (attactCharacter.getCharacter() instanceof Role) {
				Role role = (Role) attactCharacter.getCharacter();
				int skillId = 0;
				if (role.getSkillMap().containsKey(1)) {
					skillId = role.getSkillMap().get(1);
				}

				result = attack(SkillJson.instance().getSkillById(skillId), battleInfo, attactCharacter, 0);
			} else {
				result = attack(SkillJson.instance().getSkillById(40000), battleInfo, attactCharacter, 0);
			}

		} else {

			CommandInfo commandInfo = attactCharacter.getCommandInfo();
			if (commandInfo == null) { // 无指令攻击，随机普通攻击
				if (attactCharacter.getCharacter().getType() == 0) {
					result = attack(SkillJson.instance().getSkillById(0), battleInfo, attactCharacter, 0);
				} else if (attactCharacter.getCharacter().getType() == 1) {
					result = attack(SkillJson.instance().getSkillById(40000), battleInfo, attactCharacter, 0);
				} else {
					Monster monster = (Monster) attactCharacter.getCharacter();
					int skillId = 40000;
					int sum = 0;
					int finalRate = Util.getRandom(10000);
					for (Iterator<Entry<Integer, Integer>> it = monster.getActiveSkills().entrySet().iterator(); it.hasNext();) {
						Entry<Integer, Integer> entry = it.next();
						sum += entry.getValue();
						if (finalRate < sum) {
							skillId = entry.getKey();
							break;
						}
					}
					result = attack(SkillJson.instance().getSkillById(skillId), battleInfo, attactCharacter, 0);
				}
			} else { // 指定攻击（技能、防御、逃跑、使用物品、召唤伙伴）
				switch (commandInfo.getType()) {
				case 1: // 防御
					attactCharacter.setDefense(true);
					break;
				case 2: // 逃跑
					result = escape(battleInfo, attactCharacter);
					break;
				case 3: // 使用物品
					result = useItem(attactCharacter, battleInfo, (int) commandInfo.getSkill(), commandInfo.getReceiver());
					break;
				case 4: // 召唤伙伴
					result = summon(attactCharacter, battleInfo, commandInfo.getSkill());
					break;
				default: // 攻击、技能
					result = attack(SkillJson.instance().getSkillById((int) commandInfo.getSkill()), battleInfo, attactCharacter, commandInfo.getReceiver());
				}
			}
		}
		attactCharacter.resetCommand(); // 置空命令
		if (!battleInfo.isAuto()) { // 自动战斗不同步生命值和内力值、伙伴生命值
			BattleServer.syncHpAndMp(attactCharacter);
		}
		return result;
	}

	/**
	 * 使用物品
	 * 
	 * @param attactCharacter
	 *            使用者
	 * @param battleInfo
	 *            战斗动画
	 * @param index
	 *            物品索引
	 * @param foeIndex
	 *            被使用者索引
	 * @return
	 */
	public static boolean useItem(BattleCharacter attactCharacter, BattleInfo battleInfo, int index, Integer foeIndex) {

		Item item = null;
		Role role = null;
		if (attactCharacter.getCharacter() instanceof Role) {
			role = (Role) attactCharacter.getCharacter();
		} else if (attactCharacter.getCharacter() instanceof Partner) {
			Partner partner = (Partner) attactCharacter.getCharacter();
			role = partner.getRole();
		}
		if (role == null) {
			return false;
		}

		Cell cell = role.getBag().getCells()[index];
		item = cell.getItem();
		if (item == null) { // 物品不存在，自动转为普通攻击
			if (attactCharacter.getCharacter().getType() == 0) {
				return attack(SkillJson.instance().getSkillById(0), battleInfo, attactCharacter, 0);
			} else if (attactCharacter.getCharacter().getType() == 1) {
				return attack(SkillJson.instance().getSkillById(40000), battleInfo, attactCharacter, 0);
			}
			return false;
		}
		if (item.use(attactCharacter, foeIndex, battleInfo)) {
			Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
			role.getBag().removeByIndex(index, 1, cellsMap, EItemCost.battleUse); // 删除物品
			BagMessage.sendBag(role, cellsMap);
		}

		return false;
	}

	/**
	 * 召唤伙伴
	 * 
	 * @param attactCharacter
	 *            召唤者
	 * @param battleInfo
	 *            战斗动画
	 * @param partnerId
	 *            伙伴编号
	 * @return 战斗结束
	 */
	public static boolean summon(BattleCharacter attactCharacter, BattleInfo battleInfo, long partnerId) {
		if (attactCharacter.getCharacter() instanceof Role) {
			Role role = (Role) attactCharacter.getCharacter();

			int index = attactCharacter.getIndex() + 3; // 计算伙伴战斗位置索引
			BattleCharacter oldPartnerBattleCharacter = battleInfo.getAll().get(index);

			int orderIndex = battleInfo.getCharacterIndexInOrder(oldPartnerBattleCharacter); // 获取伙伴在战斗队列中的位置

			Partner partner = role.findPartner(partnerId);
			if (partner.getLevel() > role.getLevel() + 5) { // 伙伴等级大于角色等级5级，则不出战
				return false;
			}
			if (partner.getFightLevel() > role.getLevel()) {// 伙伴战斗等级大于角色等级，则不出战
				return false;
			}
			if (partner != null && !attactCharacter.checkpartner(partnerId)) {
				if (oldPartnerBattleCharacter != null) {
					oldPartnerBattleCharacter.setDead(true); // 标记死亡
					// role.setPartner(null); // 取消出战伙伴
					// Partner oldPartner = (Partner)
					// oldPartnerBattleCharacter.getCharacter();
					// oldPartner.setBattle(false);
				}

				attactCharacter.addPartner(partner.getId()); // 记录已出战过伙伴
				// role.setPartner(partner);
				// partner.setBattle(true);
				role.getPartnerFight().put(1, partnerId); // 设置出战伙伴
				BattleAminInfo battleAminInfo = BattleServer.createBattleAminInfo(battleInfo, attactCharacter, 4, partnerId); // 创建战斗动画对象

				BattleCharacter battleCharacter = BattleServer.changeBattleCharacter(index, partner, attactCharacter.getDirection());
				battleCharacter.propertyBattleLog();
				battleAminInfo.setMember(battleCharacter); // 记录出战伙伴信息
				// 添加至队列中
				battleInfo.putCharacterInToBattle(index, battleCharacter, orderIndex); // 添加至队列中
				battleCharacter.setCommandInfo(new CommandInfo(index, 0, 1, 0, battleInfo.getTurn())); // 设置战斗命令
				PartnerMessage.refreshFightPartner(role); // 刷新出战伙伴
			}
		}
		return false;
	}

	/**
	 * 攻击、技能计算
	 * 
	 * @param battleAminInfo
	 *            战斗动画
	 * @param roleSkill
	 *            技能
	 * @param battleInfo
	 *            战斗信息
	 * @param attactCharacter
	 *            攻击者
	 * @param defenceCharacter
	 *            防御者
	 * @return 战斗结束
	 */
	public static boolean attack(BaseSkill skill, BattleInfo battleInfo, BattleCharacter attactCharacter, Integer foeIndex) {

		if (skill == null) {

		}
		if (skill.getId() != 0 && attactCharacter.cantSkill()) {
			// 被封印不能使用技能
			return false;
		}

		if ((skill.getId() == 0 || skill.getId() == 40000) && attactCharacter.cantAttack()) {
			// 被封印不能普通攻击
			return false;
		}

		boolean result = skill.count(attactCharacter, foeIndex, battleInfo);

		if (result) {
			if (battleInfo.isFinish()) { // 判断战斗是否结束
				return true;
			}
		}
		return false;
	}

	/**
	 * 战斗结束重置生命值和内力值
	 * 
	 * @param battleInfo
	 *            战斗信息
	 */
	public static void resetHpAndMp(BattleInfo battleInfo) {
		if (!battleInfo.isAuto()) { // 自动战斗不同步
			for (BattleCharacter battleCharacter : battleInfo.getAll().values()) {
				if (battleCharacter.getCharacter().getType() != 2) {
					BattleServer.syncHpAndMp(battleCharacter); // 同步生命值和内力值
					autoRecover(battleCharacter);
				}
			}
		}
	}

	/**
	 * 自动恢复生命值和内力值，伙伴生命值
	 * 
	 * @param battleCharacter
	 */
	public static void autoRecover(BattleCharacter battleCharacter) {

		ICharacter character = battleCharacter.getCharacter();
		if (character instanceof Role) { // 自动恢复角色生命值和内力值
			Role role = (Role) character;
			RoleServer.autoRecoverHp(role);
			RoleServer.autoRecoverMp(role);
			SendMessage.sendHpAndMp(role); // 刷新生命值和内力值
		}

		if (character instanceof Partner) { // 自动恢复伙伴生命值
			Partner partner = (Partner) character;
			PartnerServer.autoRecoverHp(partner.getRole());
		}
	}

	/**
	 * 同步生命值和内力值
	 * 
	 * @param battleCharacter
	 *            战斗对象
	 * @param autoRecover
	 *            是否自动补满
	 */
	public static void syncHpAndMp(BattleCharacter battleCharacter) {

		ICharacter character = battleCharacter.getCharacter();
		if (character.getType() == 0) { // 如果是玩家，则同步生命值和内力值
			int hp = battleCharacter.getPropertyBattle().getHp();
			hp = hp < 1 ? 1 : hp;
			character.setHp(hp);
			character.setMp(battleCharacter.getPropertyBattle().getMp());

			// SendMessage.sendHpAndMp((Role) character);
		} else if (character.getType() == 1) { // 如果是伙伴，则同步生命值
			int hp = battleCharacter.getPropertyBattle().getHp();
			hp = hp < 1 ? 1 : hp;
			character.setHp(hp);

			// Partner partner = (Partner) character;
			// if (partner != null) {
			// PartnerMessage.sendPartnerHp(partner.getRole(), partner);
			// }
		}
	}

	/**
	 * 战斗奖励
	 * 
	 * @param role
	 *            角色对象
	 * @param battleInfo
	 *            战斗信息
	 */
	public static void award(Role role, BattleInfo battleInfo) {

		boolean isAward = true;
		if (battleInfo.getAwardType() == 1) {
			if (battleInfo.getRightLeader() != null) {
				if (!ServerManager.instance().isOnline(battleInfo.getRightLeader().getId())) {
					isAward = false;
				} else {
					if (battleInfo.getRightLeader().getLogonTime() != ServerManager.instance().getOnlinePlayer(battleInfo.getRightLeader().getId()).getLogonTime()) {
						isAward = false;
					}
				}
			} else {
				isAward = false;
			}
		}

		if (isAward) {

			HashMap<Integer, MonsterDropItem> itemIds = new HashMap<Integer, MonsterDropItem>();
			HashMap<Integer, MonsterDropPartner> partnerIndex = new HashMap<Integer, MonsterDropPartner>();
			HashMap<Integer, MonsterDropItem> taskItemIds = new HashMap<Integer, MonsterDropItem>();
			int sumGold = 0;
			int roleExp = 0;

			boolean isDouble = false;
			if (battleInfo.getBaseMap() instanceof MapRaidsInfo) { // 如果是在副本地图中
				if (RaidsManager.isDouble()) {
					isDouble = true;
				}
			}
			for (BattleCharacter battleCharacter : battleInfo.getLeft().values()) {
				Monster monster = (Monster) battleCharacter.getCharacter();
				sumGold += monster.getGold();
				roleExp += monster.getRoleExp();

				// ======掉落物品======
				if (isDouble) {
					for (int i = 0; i < 2; i++) {
						for (MonsterDropItem monsterDropItem : monster.drop()) {
							itemIds.put(monsterDropItem.getItemId(), monsterDropItem);
						}
					}
				} else {
					for (MonsterDropItem monsterDropItem : monster.drop()) {
						itemIds.put(monsterDropItem.getItemId(), monsterDropItem);
					}
				}

				// ======收集任务======
				for (MonsterDropItem taskMonsterItem : monster.dropTask()) {
					taskItemIds.put(taskMonsterItem.getItemId(), taskMonsterItem);
				}

				// ======掉落伙伴======
				if (isDouble) {
					for (int i = 0; i < 2; i++) {
						for (MonsterDropPartner monsterDropPartner : monster.dropPartner()) {
							partnerIndex.put(monsterDropPartner.getPartnerIndex(), monsterDropPartner);
						}
					}
				} else {
					for (MonsterDropPartner monsterDropPartner : monster.dropPartner()) {
						partnerIndex.put(monsterDropPartner.getPartnerIndex(), monsterDropPartner);
					}
				}

				// ======杀怪任务======
				for (Integer taskId : monster.getTasks()) {
					if (role.getTasks().containsKey(taskId)) {
						BaseTask baseTask = role.getTasks().get(taskId);
						if (baseTask instanceof KillTask) {
							KillTask killTask = (KillTask) baseTask;
							if (killTask.getState().equals(ETaskState.during)) {
								killTask.add(monster.getId(), 1);
							}
						}
					}
				}
				// ========日常杀怪=========
				try {
					Map<Long, List<Integer>> dailyMonster = DailyManager.instance().getDailyMonsterMap();
					if (dailyMonster.containsKey(monster.getId())) {
						List<Integer> dailyMonsterList = dailyMonster.get(monster.getId());
						for (Integer dailyId : dailyMonsterList) {
							if (DailyManager.instance().checkEvent(dailyId)) {
								DailyManager.instance().finishDaily(role.getDailyMap().get(dailyId), role, 1);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ======英雄帖======
				if (role.getYingxiongtie() != null) {
					YingxiongtieTask yingxiongtieTask = (YingxiongtieTask) role.getTasks().get(role.getYingxiongtie().getId());
					if (yingxiongtieTask.getState().equals(ETaskState.during)) {
						yingxiongtieTask.add(1);
					}
				}
			}

			// if (monsterItem != null) {
			// itemIds.put(monsterItem.getItemId(), monsterItem);
			// }

			Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

			if (battleInfo.getType() == 3) {
				// ======获得银两======
				RoleServer.addGold(role, sumGold, EGold.trials);
				// ======获得经验======
				RoleServer.addExp(role, roleExp, EExp.trials);

				// ======获得物品======
				Iterator<Entry<Integer, MonsterDropItem>> itItem = itemIds.entrySet().iterator();
				while (itItem.hasNext()) {
					Entry<Integer, MonsterDropItem> entry = itItem.next();
					Item item = ItemJson.instance().getItem(entry.getKey());
					if (item != null) {
						item.setIsDeal(entry.getValue().getIsBind());
						BagServer.add(role, item, entry.getValue().getNum(), cellsMap, EItemGet.trials);
					}
				}
			} else if (battleInfo.getType() == 5) {
				QiancengtaInfo qiancengtaInfo = QiancengtaJson.instance().getQiancengtaMap().get(battleInfo.getCurrentFloorId());

				// ======获得银两======
				RoleServer.addGold(role, qiancengtaInfo.getGold(), EGold.qiancengta);
				// ======获得经验======
				RoleServer.addExp(role, qiancengtaInfo.getExp(), EExp.qiancengta);

				LogManager.qiancengta(role.getId(), role.getUserId(), EItemGet.qiancengta.ordinal(), 0, 0, 0, qiancengtaInfo.getGold(), qiancengtaInfo.getExp());
				// ======获得物品======
				MonsterDropItem monsterItem = qiancengtaInfo.drop();
				if (monsterItem != null) {
					Item item = ItemJson.instance().getItem(monsterItem.getItemId());
					if (item != null) {
						item.setIsDeal(monsterItem.getIsBind());
						BagServer.add(role, item, monsterItem.getNum(), cellsMap, EItemGet.qiancengta);
						LogManager.qiancengta(role.getId(), role.getUserId(), EItemGet.qiancengta.ordinal(), item.getId(), monsterItem.getNum(), 0, 0, 0);
					}
				}

			} else {
				RoleServer.addGold(role, sumGold, EGold.monster);
				RoleServer.addExp(role, roleExp, EExp.monster);
				int ifTeamLeaderDrop = battleInfo.getMonsterGroup().getTeamLeader();
				if (!(ifTeamLeaderDrop == 1 && role.getTeam() != null && role.getTeam().getLeader().getId() != role.getId())) {
					// ======获得帮贡======
					if (role.getGangId() != 0 && role.getGang() != null && role.getMapInfo().getBaseMap() instanceof MapGangInfo) {
						if (role.getKillMonsterNum() > 0) {
							role.setKillMonsterNum(role.getKillMonsterNum() - 1);
							RoleServer.addTribute(role, 6);
							RoleServer.addGangActivity(role, 1);
							role.getGang().addTribute(2, role);
						}
					}

					// ======获得物品======
					Iterator<Entry<Integer, MonsterDropItem>> itItem = itemIds.entrySet().iterator();

					while (itItem.hasNext()) {
						Entry<Integer, MonsterDropItem> entry = itItem.next();
						Item item = ItemJson.instance().getItem(entry.getKey());
						if (item != null) {
							item.setIsDeal(entry.getValue().getIsBind());
							BagServer.add(role, item, entry.getValue().getNum(), cellsMap, EItemGet.monster);
						}
					}

					// ======获得伙伴======
					List<Partner> partnerList = new ArrayList<>();
					Iterator<Entry<Integer, MonsterDropPartner>> itPartner = partnerIndex.entrySet().iterator();
					while (itPartner.hasNext()) {
						Entry<Integer, MonsterDropPartner> entry = itPartner.next();

						for (int i = 0; i < entry.getValue().getNum(); i++) {
							if (role.getPartnerMap().size() < PartnerManager.MAX_NUM) {
								Partner newPartner = PartnerJson.instance().getNewPartner(entry.getKey());
								// 获得伙伴日志
								newPartner = PartnerServer.addPartner(role, newPartner, EGetPartner.monster);
								newPartner.setIsBind(entry.getValue().getIsBind());
								newPartner.setOperateFlag(EpartnerOperate.add);
								partnerList.add(newPartner);
								AwardServer.addPartner(role, newPartner);

							} else {
								break;
							}
						}
					}
					PartnerMessage.sendPartners(role, partnerList);

					// ======获得任务物品======
					Iterator<Entry<Integer, MonsterDropItem>> itTask = taskItemIds.entrySet().iterator();
					while (itTask.hasNext()) {
						Entry<Integer, MonsterDropItem> entry = itTask.next();
						Item item = ItemJson.instance().getItem(entry.getKey());
						if (item != null && TaskManager.checkCollectionTaskItem(role, item)) {
							if (!role.getBag().isFull()) {
								item.setIsDeal(entry.getValue().getIsBind());
								BagServer.add(role, item, entry.getValue().getNum(), cellsMap, EItemGet.monster);
							} else {
								AwardServer.bagFull(role);
							}
						}
					}

					BagMessage.sendBag(role, cellsMap); // 刷新背包
				}
			}
		}
	}

	/**
	 * 计算最终伤害值
	 * 
	 * @param harm
	 *            初始伤害值
	 * @param battleAminInfo
	 *            战斗动画
	 * @param slef
	 *            攻击者
	 * @param foe
	 *            防御者
	 * @param harmType
	 *            攻击类型（0.外功，1.内功，-1.固定伤害）
	 * @param isCrit
	 *            是否暴击
	 * @return 最终伤害值
	 */
	public static int harm(float harm, BattleAmin defenderAmin, BattleCharacter self, BattleCharacter foe, int harmType, boolean isCrit) {
		int attackerLevel = self.getCharacter().getLevel();
		int defencerLevel = foe.getCharacter().getLevel();
		if (isCrit && !foe.getPropertyBattle().isGuhua() && BattleForm.crit(self.getPropertyBattle().getCrit(), self.getPropertyBattle().getSkillAddCrit(), attackerLevel, defencerLevel)) { // 暴击
			harm = harm * 2f;
			defenderAmin.setCrit(1); // 战斗动画——暴击
		}
		if (harmType != -1 && foe.isDefense()) {
			harm = harm * 0.5f;
			defenderAmin.setIsDefense(1); // 战斗动画——防御
		}
		if (BattleForm.parry(foe.getPropertyBattle().getParry(), foe.getPropertyBattle().getSkillAddParry(), attackerLevel, defencerLevel)) { // 格挡
			harm = harm * 0.25f;
			defenderAmin.setParry(1); // 战斗动画——格挡
		}

		if (harmType == 0) {
			harm = harm * (1 + self.getPropertyBattle().getBuffPharm() / 10000f);
		} else if (harmType == 1) {
			harm = harm * (1 + self.getPropertyBattle().getBuffMharm() / 10000f);
		}

		if (harmType != -1) {
			harm = harm * Util.getRandomHarm();
			harm = harm < 1f ? 1f : harm;
		}
		return (int) harm; // 最终伤害值
	}

	/**
	 * 获取被攻击者动画
	 * 
	 * @param battleCharacter
	 *            被攻击者
	 * @return 被攻击者动画
	 */
	public static BattleAmin getDefenderAmin(BattleCharacter foe) {
		BattleAmin defenderAmin = new BattleAmin();
		defenderAmin.setIndex(foe.getIndex()); // 战斗动画——防守者——记录索引
		return defenderAmin;
	}

	/**
	 * 生命值结算
	 * 
	 * @param battleInfo
	 *            战斗信息
	 * @param battleCharacter
	 *            结算者战斗对象
	 * @param battleAmin
	 *            结算者战斗动画
	 * @param harm
	 *            伤害值
	 * @return
	 */
	public static boolean hpChange(BattleInfo battleInfo, BattleCharacter battleCharacter, BattleAmin battleAmin, int harm, ParamTemp paramTemp) {

		if (battleCharacter.getBuffs().containsKey(EBattleBuffType.b20)) {
			BattleBuff battleBuff = battleCharacter.getBuffs().get(EBattleBuffType.b20);
			harm = (int) (harm * (1 - battleBuff.getValue() / 10000f));
		}

		harm = harm < 1 ? 1 : harm;

		if (battleCharacter.getPropertyBattle().getHp() > harm) {
			battleCharacter.getPropertyBattle().setHp(battleCharacter.getPropertyBattle().getHp() - harm);
			battleAmin.addHp(harm * -1); // 战斗动画——被攻击者——记录血量变化
			paramTemp.setFinalHarm(harm);
			// ======同步生命值和内力值======
			if (!battleInfo.isAuto()) { // 自动战斗不同步生命值和内力值、伙伴生命值
				syncHpAndMp(battleCharacter);
			}
			return false;
		} else {

			paramTemp.setFinalHarm(battleCharacter.getPropertyBattle().getHp());

			if (battleCharacter.getPropertyBattle().getHp() < battleCharacter.getPropertyBattle().getHpMax()) {

				if (battleCharacter.getPropertyBattle().isShenyou()) { // 神佑
					BattleBuff battleBuff = battleCharacter.getBuffs().get(EBattleBuffType.b18);
					if (battleBuff != null && Util.probable(battleBuff.getProbability())) {
						int hp = (int) (battleCharacter.getPropertyBattle().getHpMax() * (battleBuff.getValue() / 10000f) - battleCharacter.getPropertyBattle().getHp());
						battleAmin.addHp(hp); // 战斗动画——被攻击者——记录血量变化
						battleAmin.setSkill(battleBuff.getSkillId());
						battleCharacter.getPropertyBattle().setHp(battleCharacter.getPropertyBattle().getHp() + hp);

						// ======同步生命值和内力值======
						if (!battleInfo.isAuto()) { // 自动战斗不同步生命值和内力值、伙伴生命值
							syncHpAndMp(battleCharacter);
						}
						return false;
					}
				}

			}

			battleAmin.addHp(harm * -1); // 战斗动画——被攻击者——记录血量变化
			battleCharacter.getPropertyBattle().setHp(0);
			battleCharacter.setDead(true);

			battleAmin.setIsDead(1); // 战斗动画——被攻击者——记录死亡

			// ======同步生命值和内力值======
			if (!battleInfo.isAuto()) { // 自动战斗不同步生命值和内力值、伙伴生命值
				syncHpAndMp(battleCharacter);
			}
			return true;
		}
	}

	/**
	 * 逃跑
	 * 
	 * @param battleInfo
	 *            战斗信息
	 * @param attactCharacter
	 *            攻击者
	 * @return 战斗结束
	 */
	public static boolean escape(BattleInfo battleInfo, BattleCharacter attactCharacter) {

		if (Util.probable()) {
			attactCharacter.setEscape(true); // 标记逃跑
			BattleServer.createBattleAminInfo(battleInfo, attactCharacter, 2, 1); // 创建战斗动画对象

			if (battleInfo.isFinish(attactCharacter.getDirection())) { // 战斗结束，逃跑方位全部死亡
				return true;
			}

			// battleInfo.removeCharacterInToOrder(attactCharacter); // 从战斗队列中删除

			if (attactCharacter.getCharacter() instanceof Role) {
				Role role = (Role) attactCharacter.getCharacter();
				int index = attactCharacter.getIndex() + 3;
				BattleCharacter partnerBattleCharacter = battleInfo.getBattleCharacter(index);
				if (partnerBattleCharacter != null) {
					partnerBattleCharacter.setEscape(true); // 角色逃跑，伙伴也置为逃跑
					// battleInfo.removeCharacterInToOrder(partnerBattleCharacter);
					// // 从战斗队列中删除
				}
				battleInfo.decreaseNumber();
				FightMessage.sendFightResult(battleInfo, role);
			}
			return false;
		} else {
			BattleServer.createBattleAminInfo(battleInfo, attactCharacter, 2, 0); // 创建战斗动画对象
			return false;
		}
	}

	/**
	 * 创建战斗动画对象
	 * 
	 * @param battleInfo
	 *            战斗信息
	 * @param battleCharacter
	 *            攻击者对象
	 * @param skillId
	 *            技能编号
	 * @param cost
	 *            消耗内力值
	 * @return 战斗动画对象
	 */
	public static BattleAminInfo createBattleAminInfo(BattleInfo battleInfo, BattleCharacter battleCharacter, int type, long skillId, int cost) {
		BattleAminInfo battleAminInfo = new BattleAminInfo(); // 战斗动画——创建战斗动画对象
		battleInfo.addBattleAmin(battleAminInfo); // 战斗动画——将战斗动画对象添加至战斗信息中
		battleAminInfo.getAttackerAmin().setIndex(battleCharacter.getIndex()); // 战斗动画——攻击者——记录索引
		battleAminInfo.getAttackerAmin().setType(type); // 战斗动画——攻击者——操作类型技能
		battleAminInfo.getAttackerAmin().setSkill(skillId); // 战斗动画——攻击者——记录技能
		battleAminInfo.getAttackerAmin().setMp(cost * -1); // 战斗动画——攻击者——记录内力值消耗
		battleCharacter.getPropertyBattle().addMp(cost * -1); // 消耗内力值
		return battleAminInfo;
	}

	/**
	 * 创建战斗动画对象
	 * 
	 * @param battleInfo
	 *            战斗信息
	 * @param battleCharacter
	 *            攻击者对象
	 * @param skillId
	 *            技能编号
	 * @return 战斗动画对象
	 */
	public static BattleAminInfo createBattleAminInfo(BattleInfo battleInfo, BattleCharacter battleCharacter, int type, long skillId) {
		BattleAminInfo battleAminInfo = new BattleAminInfo(); // 战斗动画——创建战斗动画对象
		battleInfo.addBattleAmin(battleAminInfo); // 战斗动画——将战斗动画对象添加至战斗信息中
		battleAminInfo.getAttackerAmin().setIndex(battleCharacter.getIndex()); // 战斗动画——攻击者——记录索引
		battleAminInfo.getAttackerAmin().setType(type); // 战斗动画——攻击者——操作类型技能
		battleAminInfo.getAttackerAmin().setSkill(skillId); // 战斗动画——攻击者——记录技能
		return battleAminInfo;
	}

	/**
	 * 内力值不足
	 * 
	 * @param self
	 *            攻击者
	 * @param foeIndex
	 *            被攻击者索引
	 * @param battleInfo
	 *            战斗信息
	 * @return 是否死亡
	 */
	public static boolean hpNotEnough(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo) {
		if (self.getCharacter().getType() == 0) {
			return BattleServer.commonFight(self, foeIndex, battleInfo);
		} else {
			return BattleServer.partnerCommonFight(self, foeIndex, battleInfo);
		}
	}

	/**
	 * 普通攻击
	 * 
	 * @param self
	 *            攻击者
	 * @param foeIndex
	 *            被攻击者索引
	 * @param battleInfo
	 *            战斗信息
	 * @return 是否死亡
	 */
	public static boolean commonFight(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo) {

		BattleCharacter foe = battleInfo.getOppositeBattleCharacter(foeIndex, self.getDirection());
		if (foe == null) {
			return true;
		}

		BattleAminInfo battleAminInfo = BattleServer.createBattleAminInfo(battleInfo, self, 0, 0); // 创建战斗动画对象

		BattleAmin defenderAmin = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
		battleAminInfo.getDefenderAmins().add(defenderAmin); // 存储被攻击者动画

		int attackerLevel = self.getCharacter().getLevel();
		int defencerLevel = foe.getCharacter().getLevel();

		if (!BattleForm.hit(self.getPropertyBattle().getHit(), foe.getPropertyBattle().getDodge(), attackerLevel, defencerLevel)) { // 未命中
			defenderAmin.setMiss(1);
			return false;
		}
		int harmType = 0;
		float harm = 0f;
		boolean isSkill = false;
		switch (self.getCharacter().getVocation()) { // 角色普通攻击都改为外功攻击
		case ng:
			harm = BattleForm.harm(self.getPropertyBattle().getMattack(), foe.getPropertyBattle().getMdefence(), 10000); // 内功伤害值
			harmType = 1;
			isSkill = true;
		default:
			harm = BattleForm.harm(self.getPropertyBattle().getPattack(), foe.getPropertyBattle().getPdefence(), 10000); // 外功伤害值
		}

		int finalHarm = BattleServer.harm(harm, defenderAmin, self, foe, harmType, true); // 计算最终伤害值

		// 生命值计算
		return foe.defend(isSkill).defend(battleInfo, foe, self, defenderAmin, battleAminInfo.getAttackerAmin(), finalHarm, battleInfo.getTurn(), harmType, null);
	}

	/**
	 * 伙伴普通攻击
	 * 
	 * @param self
	 *            攻击者
	 * @param foeIndex
	 *            被攻击者索引
	 * @param battleInfo
	 *            战斗信息
	 * @return 是否死亡
	 */
	public static boolean partnerCommonFight(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo) {
		BattleCharacter foe = battleInfo.getOppositeBattleCharacter(foeIndex, self.getDirection());
		if (foe == null) {
			return true;
		}

		BattleAminInfo battleAminInfo = BattleServer.createBattleAminInfo(battleInfo, self, 0, 0); // 创建战斗动画对象

		return battle(battleAminInfo, self, foe, battleInfo, true);
	}

	public static boolean battle(BattleAminInfo battleAminInfo, BattleCharacter self, BattleCharacter foe, BattleInfo battleInfo, boolean first) {

		BattleAmin defenderAmin = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
		battleAminInfo.getDefenderAmins().add(defenderAmin); // 存储被攻击者动画

		int attackerLevel = self.getCharacter().getLevel();
		int defencerLevel = foe.getCharacter().getLevel();

		if (!BattleForm.hit(self.getPropertyBattle().getHit(), foe.getPropertyBattle().getDodge(), attackerLevel, defencerLevel)) { // 未命中
			defenderAmin.setMiss(1);
			return false;
		}

		switch (self.getCharacter().getVocation()) {
		case ng:
			return neigong(battleInfo, self, foe, battleAminInfo, defenderAmin, first); // 内功伤害值
		default:
			return waigong(battleInfo, self, foe, battleAminInfo, defenderAmin, first); // 外功伤害值
		}

	}

	public static boolean waigong(BattleInfo battleInfo, BattleCharacter self, BattleCharacter foe, BattleAminInfo battleAminInfo, BattleAmin defenderAmin, boolean first) {
		int finalHarm = BattleServer.harm(BattleForm.harm(self.getPropertyBattle().getPattack(), foe.getPropertyBattle().getPdefence(), 10000), defenderAmin, self, foe, 0, true); // 计算最终伤害值
		boolean isLianji = false;
		boolean isXixue = false;
		int xixuePercent = 0;
		boolean isXuanyun = false;
		PartnerSkill xuanyun = null;
		boolean isZhuiji = false;

		for (Integer partnerSkillId : self.getCharacter().getSkills()) {
			PartnerSkill partnerSkill = SkillJson.instance().getPartnerSkillById(partnerSkillId);
			if (partnerSkill != null) {
				if (partnerSkill.getType().equals(ESkillType.activeTrigger) && (self.getCharacter().getVocation().equals(partnerSkill.getVocation()) || self.getCharacter().getVocation().equals(Vocation.none)) && Util.probable(partnerSkill.getProbability())) {
					switch (partnerSkill.getCategory()) {
					case lianji:
					case gaojilianji:
						if (first) {
							battleAminInfo.getAttackerAmin().setSkill(partnerSkill.getId()); // 战斗动画——攻击者——记录技能
							isLianji = true;
						}
						break;
					case xixue:
					case gaojixixue:
						isXixue = true;
						xixuePercent = partnerSkill.getPercent() > xixuePercent ? partnerSkill.getPercent() : xixuePercent;
						break;
					case xuanyun:
						if (xuanyun == null) {
							isXuanyun = true;
							xuanyun = partnerSkill;
						}
						break;
					case gaojixuanyun:
						isXuanyun = true;
						xuanyun = partnerSkill;
						break;
					case zhuiji:
						isZhuiji = true;
						break;
					default:
						break;
					}
				}
			}
		}

		ParamTemp paramTemp = new ParamTemp();
		// 生命值计算
		boolean result = foe.defend(false).defend(battleInfo, foe, self, defenderAmin, battleAminInfo.getAttackerAmin(), finalHarm, battleInfo.getTurn(), 0, paramTemp);

		if (!result && isXuanyun && !foe.getPropertyBattle().isTianxiang()) { // 被攻击者没有【天相】【高级净化】
			// ======BUFF动画======
			if (foe.addBuff(SkillJson.instance().getBattleBuff(xuanyun.getBuff(), xuanyun.getBuffValue(), xuanyun.getDuration()))) {
				defenderAmin.getAddBuffs().add(xuanyun.getBuff());
			}
		}

		if (isXixue && !foe.getPropertyBattle().isFanzhen()) { // 被攻击者没有【反震】
			int selfHarm = (int) (paramTemp.getFinalHarm() * (xixuePercent / 10000f));
			if (selfHarm > 0) {
				selfHarm = self.getPropertyBattle().addHp(selfHarm); // 攻击者——记录血量变化
				battleAminInfo.getAttackerAmin().addHp(selfHarm); // 战斗动画——攻击者——记录血量变化
			}
		}

		if (result && first) {
			battleAminInfo.getAttackerAmin().setSkill(0); // 战斗动画——攻击者——记录技能
		}

		if (result && !self.isDead() && isZhuiji) {
			return effect(self, battleInfo, 0);
		}

		if (!result && isLianji && !self.isDead()) {
			result = battle(battleAminInfo, self, foe, battleInfo, false);
		}

		// 生命值计算
		return result;
	}

	public static boolean effect(BattleCharacter self, BattleInfo battleInfo, int skillId) {
		BattleCharacter foe = battleInfo.randomBattleCharacterOpposite(self.getDirection());
		if (foe == null) {
			return true;
		}

		BattleAminInfo battleAminInfo = BattleServer.createBattleAminInfo(battleInfo, self, 0, skillId); // 创建战斗动画对象

		BattleAmin defenderAmin = BattleServer.getDefenderAmin(foe); // 获取被攻击者动画
		battleAminInfo.getDefenderAmins().add(defenderAmin); // 存储被攻击者动画

		int attackerLevel = self.getCharacter().getLevel();
		int defencerLevel = foe.getCharacter().getLevel();

		if (!BattleForm.hit(self.getPropertyBattle().getHit(), foe.getPropertyBattle().getDodge(), attackerLevel, defencerLevel)) { // 未命中
			defenderAmin.setMiss(1);
			return true;
		}

		float harm = BattleForm.harm(self.getPropertyBattle().getPattack(), foe.getPropertyBattle().getPdefence(), 10000); // 外功伤害值

		boolean isXixue = false;
		int xixuePercent = 0;
		boolean isXuanyun = false;
		PartnerSkill xuanyun = null;

		for (Integer partnerSkillId : self.getCharacter().getSkills()) {
			PartnerSkill partnerSkill = SkillJson.instance().getPartnerSkillById(partnerSkillId);
			if (partnerSkill != null) {
				if (partnerSkill.getType().equals(ESkillType.activeTrigger) && (self.getCharacter().getVocation().equals(partnerSkill.getVocation()) || self.getCharacter().getVocation().equals(Vocation.none)) && Util.probable(partnerSkill.getProbability())) {
					switch (partnerSkill.getCategory()) {
					case xixue:
					case gaojixixue:
						isXixue = true;
						xixuePercent = partnerSkill.getPercent() > xixuePercent ? partnerSkill.getPercent() : xixuePercent;
						break;
					case xuanyun:
						if (xuanyun == null) {
							isXuanyun = true;
							xuanyun = partnerSkill;
						}
						break;
					case gaojixuanyun:
						isXuanyun = true;
						xuanyun = partnerSkill;
						break;
					default:
						break;
					}
				}
			}
		}
		int finalHarm = BattleServer.harm(harm, defenderAmin, self, foe, 0, true); // 计算最终伤害值

		ParamTemp paramTemp = new ParamTemp();
		// 生命值计算
		boolean result = foe.defend(false).defend(battleInfo, foe, self, defenderAmin, battleAminInfo.getAttackerAmin(), finalHarm, battleInfo.getTurn(), 0, paramTemp);

		if (!result && isXuanyun && !foe.getPropertyBattle().isTianxiang()) { // 被攻击者没有【天相】
			// ======BUFF动画======
			if (foe.addBuff(SkillJson.instance().getBattleBuff(xuanyun.getBuff(), xuanyun.getBuffValue(), xuanyun.getDuration()))) {
				defenderAmin.getAddBuffs().add(xuanyun.getBuff());
			}
		}

		if (isXixue && !foe.getPropertyBattle().isFanzhen()) { // 被攻击者没有【反震】
			int selfHarm = (int) (paramTemp.getFinalHarm() * (xixuePercent / 10000f));
			if (selfHarm > 0) {
				selfHarm = self.getPropertyBattle().addHp(selfHarm); // 攻击者——记录血量变化
				battleAminInfo.getAttackerAmin().addHp(selfHarm); // 战斗动画——攻击者——记录血量变化
			}
		}

		return true;
	}

	public static boolean neigong(BattleInfo battleInfo, BattleCharacter self, BattleCharacter foe, BattleAminInfo battleAminInfo, BattleAmin defenderAmin, boolean first) {
		int finalHarm = BattleServer.harm(BattleForm.harm(self.getPropertyBattle().getMattack(), foe.getPropertyBattle().getMdefence(), 10000), defenderAmin, self, foe, 1, true); // 计算最终伤害值
		if (finalHarm <= 0) {
			System.out.println();
		}
		boolean isTianji = false;
		boolean isXuruo = false;
		PartnerSkill xuruo = null;
		boolean isLianhuan = false;
		PartnerSkill lianhuan = null;

		for (Integer partnerSkillId : self.getCharacter().getSkills()) {
			PartnerSkill partnerSkill = SkillJson.instance().getPartnerSkillById(partnerSkillId);
			if (partnerSkill != null) {
				if (partnerSkill.getType().equals(ESkillType.activeTrigger) && (self.getCharacter().getVocation().equals(partnerSkill.getVocation()) || self.getCharacter().getVocation().equals(Vocation.none)) && Util.probable(partnerSkill.getProbability())) {
					switch (partnerSkill.getCategory()) {
					case tianji:
					case gaojitianji:
						if (first && !isLianhuan) {
							battleAminInfo.getAttackerAmin().setSkill(partnerSkill.getId()); // 战斗动画——攻击者——记录技能
							isTianji = true;
						}
						break;
					case xuruo:
						if (xuruo == null) {
							isXuruo = true;
							xuruo = partnerSkill;
						}
						break;
					case gaojixuruo:
						isXuruo = true;
						xuruo = partnerSkill;
						break;
					case lianhuan:
						if (first && !isTianji && lianhuan == null) {
							lianhuan = partnerSkill;
							isLianhuan = true;
						}
					case gaojilianhuan:
						if (first && !isTianji) {
							lianhuan = partnerSkill;
							isLianhuan = true;
						}
						break;
					default:
						break;
					}
				}
			}
		}

		if (isXuruo) {
			// ======BUFF动画======
			if (foe.addBuff(SkillJson.instance().getBattleBuff(xuruo.getBuff(), xuruo.getBuffValue(), xuruo.getDuration()))) {
				defenderAmin.getAddBuffs().add(xuruo.getBuff());
			}
		}

		// 生命值计算
		boolean result = foe.defend(true).defend(battleInfo, foe, self, defenderAmin, battleAminInfo.getAttackerAmin(), finalHarm, battleInfo.getTurn(), 1, null);

		if (result && first) {
			battleAminInfo.getAttackerAmin().setSkill(0); // 战斗动画——攻击者——记录技能
		}

		if (isLianhuan) {
			BattleCharacter otherFoe = battleInfo.randomBattleCharacterOpposite(self.getDirection(), foe.getIndex());

			if (otherFoe != null) {
				BattleAmin otherDefenderAmin = BattleServer.getDefenderAmin(otherFoe); // 获取被攻击者动画
				battleAminInfo.getDefenderAmins().add(otherDefenderAmin); // 存储被攻击者动画

				int otherFinalHarm = (int) (finalHarm * (lianhuan.getPercent() / 10000f));
				result = otherFoe.defend(true).defend(battleInfo, otherFoe, self, otherDefenderAmin, battleAminInfo.getAttackerAmin(), otherFinalHarm, battleInfo.getTurn(), 1, null);
			}
		}

		if (!result && isTianji && !self.isDead()) {
			result = battle(battleAminInfo, self, foe, battleInfo, false);
		}

		return result;
	}
}
