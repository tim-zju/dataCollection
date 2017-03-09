package cn.ddcollection.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ddcollection.dao.UserDao;
import cn.ddcollection.model.User;
import cn.ddcollection.service.UserService;
/**
 * 
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @author: 大鹤
 * @date: 2016年2月24日 下午9:50:19
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	public int insertUser(User user) {
		// TODO Auto-generated method stub
		return userDao.insertUser(user);
	}

}
