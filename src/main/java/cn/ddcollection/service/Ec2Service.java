package cn.ddcollection.service;

import java.util.List;

import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesResult;
import com.amazonaws.services.ec2.model.StopInstancesResult;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;

import cn.ddcollection.model.Ec2Instance;

/**
 * @author 作者 E-mail:
 * @date 创建时间：2016年2月27日 上午11:27:00
 * @version 1.0
 * @parameter
 * @since
 * @return
 */

public interface Ec2Service {
	/**
	 * 
	 * @Title: launchInstance
	 * @Description: 创建Ec2Instance
	 * @param ec2instance
	 * @return: void
	 */
	public void insertEc2(Ec2Instance ecInstance);
	/**
	 * 
	 * @Title: launchInstance
	 * @Description: 创建Ec2Instance
	 * @param ec2instance
	 * @return: void
	 */
	public RunInstancesResult launchInstance(AmazonEC2Client ec2, Ec2Instance ec2instance, String username);

	/**
	 * 
	 * @Title: InitAllInstances
	 * @Description: 初始化所有用户下面的每个区域的EC2实例，成功后销毁，将所有默认信息都保存在配置文件中
	 * @return: void
	 */
	public void InitAllInstances();

	/**
	 * 
	 * @Title: startInstances
	 * @Description: TODO启动虚拟机
	 * @param ec2
	 * @param instanceIds
	 * @return
	 * @return: StartInstancesResult
	 */
	public StartInstancesResult startInstances(AmazonEC2Client ec2, List<String> instanceIds);
	
	/**
	 * 
	 * @Title: rebootInstances
	 * @Description: TODO重启虚拟机
	 * @param ec2
	 * @param instanceIds
	 * @return
	 * @return: void
	 */
	public void rebootInstances(AmazonEC2Client ec2, List<String> instanceIds);

	/**
	 * 
	 * @Title: stopInstances
	 * @Description: TODO关闭虚拟机实例
	 * @param instanceIds实例ID列表
	 * @return
	 * @return: StopInstancesResult
	 */
	public StopInstancesResult stopInstances(AmazonEC2Client ec2, List<String> instanceIds);

	/**
	 * 
	 * @Title: terminateInstances
	 * @Description: TODO终止虚拟机实例
	 * @param ec2
	 * @param instanceIds实例ID列表
	 * @return
	 * @return: TerminateInstancesResult
	 */
	public TerminateInstancesResult terminateInstances(AmazonEC2Client ec2, List<String> instanceIds);

	/**
	 * 
	 * @Title: calculateInstancesTimes
	 * @Description: TODO计算该用户所有虚拟机实例本月运行的总时长
	 * @param username用户名
	 * @return
	 * @return: long
	 */
	public long calculateInstancesTimes(String username);
	
	public String getUserdataFromFile(String fileName);
	
	public List<Ec2Instance> findEC2All();
	
	public List<Ec2Instance> findEC2ByNameRegion(String username,String region);
	
	public void updateState(String state,String id);
	
	public List<Ec2Instance> findEC2Schedule();
	
	public String findStateById(String ec2id);
	
}
