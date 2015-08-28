package iyunu.NewTLOL.model.functionNotice;

public class FunctionNotice {

	/** 编号 **/
	private int id;
	/** 等级下限 **/
	private int minlevel;
	/** 等级上限 **/
	private int maxlevel;
	/** 名称 **/
	private String name;
	/** 描述 **/
	private String dec;
	/** 图标 **/
	private String icon;
	/** 缩放比例 **/
	private int bili;
	/** 开启等级(用于显示) **/
	private String startlevel;

	/**
	 * 复制
	 * 
	 * @return 自身对象
	 */
	public FunctionNotice copy() {
		FunctionNotice functionNotice = new FunctionNotice();
		functionNotice.setId(id);
		functionNotice.setMinlevel(minlevel);
		functionNotice.setMaxlevel(maxlevel);
		functionNotice.setName(name);
		functionNotice.setDec(dec);
		functionNotice.setIcon(icon);
		functionNotice.setBili(bili);
		functionNotice.setStartlevel(startlevel);
		return functionNotice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMinlevel() {
		return minlevel;
	}

	public void setMinlevel(int minlevel) {
		this.minlevel = minlevel;
	}

	public int getMaxlevel() {
		return maxlevel;
	}

	public void setMaxlevel(int maxlevel) {
		this.maxlevel = maxlevel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDec() {
		return dec;
	}

	public void setDec(String dec) {
		this.dec = dec;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getBili() {
		return bili;
	}

	public void setBili(int bili) {
		this.bili = bili;
	}

	public String getStartlevel() {
		return startlevel;
	}

	public void setStartlevel(String startlevel) {
		this.startlevel = startlevel;
	}

}
