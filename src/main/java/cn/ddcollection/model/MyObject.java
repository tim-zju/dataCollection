package cn.ddcollection.model;
/**
 * 
 * @ClassName: MyObject
 * @Description: MyObject
 * @author: 大鹤
 * @date: 2016年2月24日 下午9:49:46
 */
public class MyObject {
	private String name;
	private String details;

	public MyObject() {
	}

	public MyObject(String name, String details) {
		super();
		this.name = name;
		this.details = details;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
