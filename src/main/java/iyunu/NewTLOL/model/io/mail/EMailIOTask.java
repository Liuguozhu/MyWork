package iyunu.NewTLOL.model.io.mail;

public enum EMailIOTask {

	insert, // 添加新邮件
	update, // 邮件更新
	mark, // 邮件标记已读
	delete, // 删除邮件
	deleteRead, // 删除已读
	clean, // 清除邮箱
	check, // 邮箱检查
	;

}