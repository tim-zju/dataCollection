package cn.ddcollection.service;

import java.util.List;

import cn.ddcollection.model.UserAccount;

public interface UserAccountService {
	
	public int insertUser(UserAccount u);
	
	public int findIdByName(String name);
	
	public List<UserAccount> findNames();
	
	public int updateUser(String name,String AWSAccessKeyId,String AWSSecretKey);
	
	public int addIAM(String name,String iam);
}
