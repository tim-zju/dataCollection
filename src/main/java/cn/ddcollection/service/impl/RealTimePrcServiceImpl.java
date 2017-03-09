package cn.ddcollection.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ddcollection.dao.RealTimePrcDao;
import cn.ddcollection.model.RealTimePrc;
import cn.ddcollection.service.RealTimePrcService;

@Service("realTimePrcService")
public class RealTimePrcServiceImpl implements RealTimePrcService{
	@Autowired
	RealTimePrcDao realTimePrcDao;
	
	@Override
	public int insertRealTimePrc(RealTimePrc realTimePrc) {
		return realTimePrcDao.insertRealTimePrc(realTimePrc);
	}

}
