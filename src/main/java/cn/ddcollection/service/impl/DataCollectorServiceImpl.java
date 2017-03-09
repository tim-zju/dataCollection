package cn.ddcollection.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ddcollection.dao.DataCollectorDao;
import cn.ddcollection.model.DataCollector;
import cn.ddcollection.service.DataCollectorService;

@Service("dataCollectorService")
public class DataCollectorServiceImpl implements DataCollectorService{
	@Autowired
	private DataCollectorDao dataCollectorDao;

	@Override
	public void insertDataCollector(DataCollector dataCollector) {
		dataCollectorDao.insertDataCollector(dataCollector);
	}

	@Override
	public DataCollector findDataCollectorByName(String name) {
		return dataCollectorDao.findDataCollector(name);
	}

	@Override
	public List<DataCollector> findDataCollectorAll() {
		return dataCollectorDao.findDataCollectorAll();
	}

	@Override
	public int deleteDataCollectorByName(String name) {
		return dataCollectorDao.deleteDataCollectorByName(name);
	}

	@Override
	public int updataDataCollectorDes(String description, String name) {
		return dataCollectorDao.updataDataCollectorDes(description, name);
	}

	@Override
	public int findDataCollectorIdByName(String name) {
		return dataCollectorDao.findDataCollectorIdByName(name);
	}

	@Override
	public List<DataCollector> findDataCollectorS3() {
		return dataCollectorDao.findDataCollectorS3();
	}

}
