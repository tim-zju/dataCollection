package cn.ddcollection.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ddcollection.dao.S3ObjectDao;
import cn.ddcollection.model.MyS3Object;
import cn.ddcollection.service.S3ObjectService;

@Service("s3ObjectService")
public class S3ObjectServiceImpl implements S3ObjectService{

	@Autowired
	S3ObjectDao s3ObjectDao;
	
	@Override
	public int insertS3Object(MyS3Object o) {
		return s3ObjectDao.insertS3Object(o);
	}

	@Override
	public int findIdByLocation(String location) {
		return s3ObjectDao.findIdByLocation(location);
	}

	@Override
	public int deleteByName(String name) {
		return s3ObjectDao.deleteByName(name);
	}

	@Override
	public int deleteByBucket(int userid, String bucketname) {
		return s3ObjectDao.deleteByBucket(userid, bucketname);
	}

	@Override
	public List<MyS3Object> findObjects(int userid, String bucketname) {
		return s3ObjectDao.findObjects(userid, bucketname);
	}

	@Override
	public List<MyS3Object> findBuckets(int userid) {
		return s3ObjectDao.findBuckets(userid);
	}

	@Override
	public int deleteObject(int userid, String bucketname, String objectName) {
		return s3ObjectDao.deleteObject(userid, bucketname, objectName);
	}

}
