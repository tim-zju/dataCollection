package cn.ddcollection.model;

/**
 * @author 作者 E-mail:
 * @date 创建时间：2016年3月16日 下午3:31:51
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class Region {
	private String name;// region name
	private String value;// region value

	public Region() {
	}

	public Region(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
