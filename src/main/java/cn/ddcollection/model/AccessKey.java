package cn.ddcollection.model;
/**
 * 
 * @ClassName: AccessKey
 * @Description: AccessKey
 * @author: 大鹤
 * @date: 2016年2月24日 下午9:49:25
 */
public class AccessKey {
	private String username;
	private String userkey;

	public AccessKey() {

	}

	public AccessKey(String username, String userkey) {
		super();
		this.username = username;
		this.userkey = userkey;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserkey() {
		return userkey;
	}

	public void setUserkey(String userkey) {
		this.userkey = userkey;
	}

}
