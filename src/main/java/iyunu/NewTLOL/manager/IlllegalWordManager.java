package iyunu.NewTLOL.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 非法字符
 * 
 * @author SunHonglei
 * 
 */
public final class IlllegalWordManager {

	/**
	 * 私有构造方法
	 */
	private IlllegalWordManager() {

	}

	private static IlllegalWordManager instance = new IlllegalWordManager();

	/**
	 * 获取IlllegalWord对象
	 * 
	 * @return IlllegalWord对象
	 */
	public static IlllegalWordManager instance() {
		return instance;
	}

	/** 屏蔽词列表 **/
	private List<String> list = new ArrayList<String>();

	/**
	 * 读取屏蔽词TXE数据初始化
	 */
//	public void init() {
//		try {
//			clear();
//			File file = new File("./conf/illlegalWord.txt");
//			if (file.isFile() && file.exists()) {
//				InputStreamReader read = new InputStreamReader(new FileInputStream(file), "GBK");
//				BufferedReader bufferedReader = new BufferedReader(read);
//				String shieldingWord = "";
//				while ((shieldingWord = bufferedReader.readLine()) != null) {
//					writeToList(shieldingWord.toString().trim());
//				}
//				read.close();
//			} else {
//				LogManager.error("找不到指定的文件！");
//			}
//		} catch (Exception e) {
//			LogManager.error("读取文件内容操作出错");
//			e.printStackTrace();
//		}
//	}

	/**
	 * 数据重置
	 */
	public void clear() {
		list.clear();
	}

	/**
	 * 将屏蔽词放入List中
	 * 
	 * @param content
	 *            屏蔽词
	 */
	public void writeToList(String content) {
		String[] str = content.split(";");
		for (String string : str) {
			list.add(string);
		}
	}

	/**
	 * 是否是非法名字
	 * 
	 * @param name
	 *            名字
	 * @return 是否
	 */
	public boolean exist(String name) {
		for (String str : list) {
			if (name.contains(str)) {
				return true;
			}
		}
		return false;
	}

	public String existStr(String name) {
		for (String str : list) {
			if (name.contains(str)) {
				return str;
			}
		}
		return null;
	}

	public String checkString(String string) {
		for (String str : list) {
			if (string.contains(str)) {
				string = string.replaceAll(str, "***");
			}
		}
		return string;
	}

	/**
	 * 字符串匹配（数字、字母）
	 * 
	 * @param str
	 *            字符串
	 * @return 匹配结果
	 */
	public static boolean matchWithoutZn(String str) {
		return Pattern.matches("[a-zA-Z0-9]*", str);
	}

	/**
	 * 字符串匹配（中文）
	 * 
	 * @param str
	 *            字符串
	 * @return 匹配结果
	 */
	public static boolean match(String str) {
		return Pattern.matches("[a-zA-Z0-9,\u4e00-\u9fa5]*", str);
	}

	/**
	 * 匹配手机号
	 * 
	 * @param phone
	 *            手机号
	 * @return 匹配结果
	 */
	public static boolean matchPhone(String phone) {
		return Pattern.matches("^1[0-9]{10}$", phone);
	}
}