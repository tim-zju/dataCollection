package cn.ddcollection.service;

import java.util.List;

import cn.ddcollection.model.DataCollector;

public interface DataCollectorService {
	public void insertDataCollector(DataCollector datacollector);

	public int updataDataCollectorDes(String description,String name);

	public DataCollector findDataCollectorByName(String name);

	public List<DataCollector> findDataCollectorAll();
	
	public int deleteDataCollectorByName(String name);
	
	public int findDataCollectorIdByName(String name); 
	
	public List<DataCollector> findDataCollectorS3();
}
