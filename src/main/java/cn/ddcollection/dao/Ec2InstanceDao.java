package cn.ddcollection.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.ddcollection.model.Ec2Instance;

/**
 * @author 作者 E-mail:
 * @date 创建时间：2016年3月15日 下午9:18:43
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public interface Ec2InstanceDao {
	@Insert("insert into ec2instance(name,ec2id,createtime,description,userdata,region,endpoint,state,dcid,"
			+ "userid,isschedule)"
			+ " values(#{ec2name},#{ec2id},#{createtime},#{description},#{userdata},#{region},#{endpoint},#{state},"
			+ "#{dcid},#{userid},#{isschedule})")
	public void insertEc2(Ec2Instance ec2Instance);
	
	@Select("select * from ec2instance")
	@ResultMap("ec2InstanceResultMap")
	public List<Ec2Instance> findEC2All();
	
	@Select("select b.name,ec2id,concat(AWSAccessKeyId,' ',AWSSecretKey) as userkey,"
			+ "a.name username,region,state from useraccount a,ec2instance b  "
			+ "where isschedule=1 and userid=a.id and (state='运行' or state='停止')")
	@ResultMap("ec2ScheduleResultMap")
	public List<Ec2Instance> findEC2Schedule();
	
	@Select("select state from ec2instance where ec2id=#{ec2id}")
	public String findStateById(String ec2id);
	
	@Select("select a.id,a.name,ec2id,createtime,description,userdata,region,endpoint,state,dcid,userid"
			+ " from ec2instance a,useraccount b where a.userid=b.id and b.name=#{0} and region=#{1}")
	@ResultMap("ec2InstanceResultMap")
	public List<Ec2Instance> findEC2ByNameRegion(String username,String region);
	
	@Update("update ec2instance set state=#{0} where ec2id=#{1}")
	public void updateState(String state,String id);
}
