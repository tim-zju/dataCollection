package cn.ddcollection.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ddcollection.dao.UserAccountDao;
import cn.ddcollection.model.UserAccount;
import cn.ddcollection.service.UserAccountService;

@Service("userAccountService")
public class UserAccountServiceImpl implements UserAccountService{

	@Autowired
	private UserAccountDao userAccountDao;
	
	@Override
	public int insertUser(UserAccount u) {
		return userAccountDao.insertUser(u);
	}

	@Override
	public int findIdByName(String name) {
		return userAccountDao.findIdByName(name);
	}

	@Override
	public List<UserAccount> findNames() {
		return userAccountDao.findNames();
	}

	@Override
	public int updateUser(String name, String AWSAccessKeyId,
			String AWSSecretKey) {
		return userAccountDao.updateUser(name, AWSAccessKeyId, AWSSecretKey);
	}

	@Override
	public int addIAM(String name, String iam) {
		return userAccountDao.addIAM(name, iam);
	}

}
