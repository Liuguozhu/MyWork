package iyunu.NewTLOL.util.mail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Email {
	private static String host = "smtp.exmail.qq.com";// smtp服务器
	private static String user = "lzjh@iyunu.com"; // 用户名
	private static String pwd = "x6lm0GfX"; // 密码
	private static String from = "lzjh@iyunu.com"; // 发件人地址
	private static List<String> addressee = new ArrayList<>(); // 收件人
	private static Logger logger = LoggerFactory.getLogger(Email.class);

	public static void clear() {
		addressee.clear();
		host = "";
		user = "";
		pwd = "";// 密码
		from = "";// 发件人地址
	}

	public static void init() {
		try {
			InputStream in = new FileInputStream("./conf/email.properties");
			Properties p = new Properties();
			p.load(in);
			clear();
			host = p.getProperty("host");
			user = p.getProperty("user");
			pwd = p.getProperty("pwd");
			from = p.getProperty("user");
			for (String str : p.getProperty("addressee").split(",")) {
				addressee.add(str);
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void exmple() {
		Email.send("邮件", "我就不发文件给你了，到百度上copy");
		Email.send("带附件的邮件", "我就不发文件给你了，到百度上copy", "D:\\test.xlsx", "日志.xlsx");
	}

	/**
	 * 发送邮件
	 * 
	 * @param tittle
	 *            标题
	 * @param content
	 *            内容
	 * @param tos
	 *            收件人集合
	 */
	public static void send(String tittle, String content) {

		Properties props = new Properties();
		// 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
		props.put("mail.smtp.host", host);
		// 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
		props.put("mail.smtp.auth", "true");
		// 用刚刚设置好的props对象构建一个session
		Session session = Session.getDefaultInstance(props);
		// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
		// 用（你可以在控制台（console)上看到发送邮件的过程）
		session.setDebug(false); // 开启日志
		// 用session为参数定义消息对象
		MimeMessage message = new MimeMessage(session);
		try {
			// 加载发件人地址
			message.setFrom(new InternetAddress(from));
			// 加载收件人地址
			for (String to : addressee) {
				logger.info("发送邮件，标题《" + tittle + "》，内容【" + content + "】，收件人【" + to + "】");
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			}
			// 加载标题
			message.setSubject(tittle);
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();
			// 设置邮件的文本内容
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setText(content);
			multipart.addBodyPart(contentPart);

			// 将multipart对象放到message中
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();
			// 发送邮件
			Transport transport = session.getTransport("smtp");
			// 连接服务器的邮箱
			transport.connect(host, user, pwd);
			// 把邮件发送出去
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送邮件（带附件）
	 * 
	 * @param tittle
	 *            标题
	 * @param content
	 *            内容
	 * @param affix
	 *            附件路径
	 * @param affixName
	 *            附件名称
	 */
	public static void send(String tittle, String content, String affix, String affixName) {
		Properties props = new Properties();
		// 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
		props.put("mail.smtp.host", host);
		// 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
		props.put("mail.smtp.auth", "true");
		// 用刚刚设置好的props对象构建一个session
		Session session = Session.getDefaultInstance(props);
		// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
		// 用（你可以在控制台（console)上看到发送邮件的过程）
		session.setDebug(false); // 开启日志
		// 用session为参数定义消息对象
		MimeMessage message = new MimeMessage(session);
		try {
			// 加载发件人地址
			message.setFrom(new InternetAddress(from));
			// 加载收件人地址
			for (String to : addressee) {
				logger.info("发送邮件，标题《" + tittle + "》，内容【" + content + "】，收件人【" + to + "】，附件【" + affix + "//" + affixName + "】");
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			}
			// 加载标题
			message.setSubject(tittle);
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();
			// 设置邮件的文本内容
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setText(content);
			multipart.addBodyPart(contentPart);
			// 添加附件
			BodyPart messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(affix);
			// 添加附件的内容
			messageBodyPart.setDataHandler(new DataHandler(source));
			// 添加附件的标题
			// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
			messageBodyPart.setFileName(MimeUtility.encodeText(affixName, "UTF-8", "B"));
			multipart.addBodyPart(messageBodyPart);
			// 将multipart对象放到message中
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();
			// 发送邮件
			Transport transport = session.getTransport("smtp");
			// 连接服务器的邮箱
			transport.connect(host, user, pwd);
			// 把邮件发送出去
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
