package cn.ddcollection.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.ddcollection.model.UserAccount;

public interface UserAccountDao {
	@Insert("insert into useraccount(name,AWSAccessKeyId,AWSSecretKey,iam) values(#{name},#{AWSAccessKeyId},#{AWSSecretKey},#{iam})")
	public int insertUser(UserAccount u);
	
	@Select("select id from useraccount where name=#{name}")
	public int findIdByName(String name);
	
	@Select("select * from useraccount ")
	public List<UserAccount> findNames();
	
	@Update("update useraccount set AWSAccessKeyId=#{1},AWSSecretKey=#{2} where name=#{0}")
	public int updateUser(String name,String AWSAccessKeyId,String AWSSecretKey);
	
	@Update("update useraccount set iam=#{1} where name=#{0}")
	public int addIAM(String name,String iam);
}
