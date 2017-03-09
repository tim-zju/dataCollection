package cn.ddcollection.model;

public class MyS3Object {
	private int id;
	private String name;
	private String location;
	private String type;
	private int userid;
	
	public MyS3Object(){
		
	}
	
	public MyS3Object(String name,String location,String type,int userid){
		this.name = name;
		this.location = location;
		this.type = type;
		this.userid = userid;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLoaction(String location) {
		this.location = location;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	
	
}
