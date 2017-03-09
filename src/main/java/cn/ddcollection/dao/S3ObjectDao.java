package cn.ddcollection.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import cn.ddcollection.model.MyS3Object;

public interface S3ObjectDao {
	@Insert("insert into s3object(name,location,type,userid) values(#{name},#{location},#{type},#{userid})")
	public int insertS3Object(MyS3Object o);
	
	@Select("select id from s3object where location=#{loaction}")
	public int findIdByLocation(String location);
	
	@Select("select * from s3object where userid=#{0} and location like "
			+ "concat('https://s3.amazonaws.com/',#{1},'/%')")
	public List<MyS3Object> findObjects(int userid,String bucketname);
	
	@Select("select * from s3object where userid=#{userid} and type='bucket'")
	public List<MyS3Object> findBuckets(int userid);
	
	@Delete("delete from s3object where name=#{name}")
	public int deleteByName(String name);
	
	@Delete("delete from s3object where userid=#{0} and location LIKE concat('https://s3.amazonaws.com/',#{1},'/%') "
			+ " or location= concat('https://s3.amazonaws.com/',#{1})")
	public int deleteByBucket(int userid,String bucketname);
	
	@Delete("delete from s3object where userid=#{0} and location= concat('https://s3.amazonaws.com/',#{1},'/',#{2})")
	public int deleteObject(int userid,String bucketname,String objectName);
}
