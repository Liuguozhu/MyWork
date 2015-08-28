package iyunu.NewTLOL.enumeration;

public enum EColor {

	white("白色", 1), //
	green("绿色", 2), //
	blue("紫色", 3), //
	purple("紫色", 4), //
	orange("橙色", 5), //
	red("红色", 6), //
	;

	private String content;
	private int value; // 伙伴进化用的品质

	EColor(String content, int value) {
		this.content = content;
		this.value = value;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

}
