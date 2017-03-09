package cn.ddcollection.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.ddcollection.model.DataCollector;

public interface DataCollectorDao {
	@Insert("insert into datacollector(name,datasources,s3id,description,filename)"
			+ " values(#{name},#{datasources},#{s3id},#{description},#{filename})")
	public void insertDataCollector(DataCollector dataCollector);
	
	@Update("update datacollector set description=#{0} where name=#{1}")
	public int updataDataCollectorDes(String description,String name);
	
	@Select("select * from datacollector where name=#{name}")
	public DataCollector findDataCollector(String name);
	
	@Select("select * from datacollector")
	@ResultMap("DataCollectorResultMap")
	public List<DataCollector> findDataCollectorAll();
	
	@Select("select a.name,c.name username,a.description,b.location from datacollector a,s3object b,"
			+ "useraccount c where a.s3id=b.id and b.userid=c.id")
	@ResultMap("DataS3ResultMap")
	public List<DataCollector> findDataCollectorS3();
	
	@Select("select id from datacollector where name=#{name}")
	public int findDataCollectorIdByName(String name);
	
	@Delete("delete from datacollector where name=#{name}")
	public int deleteDataCollectorByName(String name);
}
