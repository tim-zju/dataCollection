package cn.ddcollection.model;
/**
 * 
 * @ClassName: User
 * @Description: User
 * @author: 大鹤
 * @date: 2016年2月24日 下午9:49:57
 */
public class User {
	private int id;
	private int state;
	private String nickname;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
