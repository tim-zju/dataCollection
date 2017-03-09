package cn.ddcollection.dao;

import org.apache.ibatis.annotations.Insert;

import cn.ddcollection.model.User;

public interface UserDao {
	@Insert("insert into user(state,nickname) values(#{state},#{nickname})")
	public int insertUser(User user);
}
