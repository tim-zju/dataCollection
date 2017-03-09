package cn.ddcollection.service;

import java.util.List;

import cn.ddcollection.model.MyS3Object;
import cn.ddcollection.model.UserAccount;

public interface S3ObjectService {
	
	public int insertS3Object(MyS3Object o);
	
	public int findIdByLocation(String location);
	
	public int deleteByName(String name);
	
	public int deleteByBucket(int userid,String bucketname);
	
	public List<MyS3Object> findObjects(int userid,String bucketname);
	
	public List<MyS3Object> findBuckets(int userid);
	
	public int deleteObject(int userid,String bucketname,String objectName);
	
	
}
