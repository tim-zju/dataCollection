package cn.ddcollection.model;

import java.util.List;
/**
 * 
 * @ClassName: MyBucket
 * @Description: MyBucket
 * @author: 大鹤
 * @date: 2016年2月24日 下午9:49:37
 */
public class MyBucket {
	private String name;
	private String details;
	private List<MyObject> myobjects;

	public MyBucket() {
	}

	public MyBucket(String name, String details, List<MyObject> myobjects) {
		super();
		this.name = name;
		this.details = details;
		this.myobjects = myobjects;
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

	public List<MyObject> getMyobjects() {
		return myobjects;
	}

	public void setMyobjects(List<MyObject> myobjects) {
		this.myobjects = myobjects;
	}


}
