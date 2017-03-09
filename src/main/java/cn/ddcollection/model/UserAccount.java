package cn.ddcollection.model;

public class UserAccount {
	private int id;
	private String name;
	private String AWSAccessKeyId;
	private String AWSSecretKey;
	private String iam;
	
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
	public String getAWSAccessKeyId() {
		return AWSAccessKeyId;
	}
	public void setAWSAccessKeyId(String aWSAccessKeyId) {
		AWSAccessKeyId = aWSAccessKeyId;
	}
	public String getAWSSecretKey() {
		return AWSSecretKey;
	}
	public void setAWSSecretKey(String aWSSecretKey) {
		AWSSecretKey = aWSSecretKey;
	}
	public String getIam() {
		return iam;
	}
	public void setIam(String iam) {
		this.iam = iam;
	}
}
