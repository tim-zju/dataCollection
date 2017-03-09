package cn.ddcollection.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ddcollection.dao.Ec2InstanceDao;
import cn.ddcollection.model.AccessKey;
import cn.ddcollection.model.Ec2Instance;
import cn.ddcollection.model.Ec2InstanceInit;
import cn.ddcollection.service.Ec2Service;
import cn.ddcollection.utils.AWSCredentialsUtil;
import cn.ddcollection.utils.Ec2Utils;
import cn.ddcollection.utils.PropertiesOperatator;
import cn.ddcollection.utils.SerializeUtils;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.IamInstanceProfileSpecification;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.RebootInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StartInstancesResult;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesResult;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;

/**
 * @author 作者 E-mail:
 * @date 创建时间：2016年2月27日 上午11:31:00
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@Service("ec2Service")
public class Ec2ServiceImpl implements Ec2Service {
	// Ec2InstanceInit，将Ec2InstanceInit进行序列化
	public Ec2InstanceInit ec2InstanceInit;
	@Autowired
	private Ec2InstanceDao ec2InstanceDao;
	@Resource(name = "ec2Service")
	private Ec2Service ec2Service;
	
	public void insertEc2(Ec2Instance ec2Instance){
		ec2InstanceDao.insertEc2(ec2Instance);
	}

	public RunInstancesResult launchInstance(AmazonEC2Client ec2, Ec2Instance ec2instance, String username) {
		return launchInstance(ec2, ec2instance.getUserdata(), ec2instance.getImageid(), username,
				ec2instance.getRegion(), ec2instance.getIam(), ec2instance.getEc2name(),
				ec2instance.getDescription(),ec2instance.getEndpoint(),ec2instance.getDcid(),
				ec2instance.getUserid(), ec2instance.getIsschedule());
	}

	public void InitAllInstances() {
		// 获取AccessKey(userName、userKey)
		List<AccessKey> accessKeys = PropertiesOperatator
				.getUserName(this.getClass().getClassLoader().getResource("").getPath() + "conf/userkey.properties");
		try {
			// 获取每个区域的region和AMI
			Map<String, String> regionMap = PropertiesOperatator.GetAllProperties(
					this.getClass().getClassLoader().getResource("").getPath() + "conf/regionami.properties");
			// 遍历accessKeys，对每一个用户的每个区域下的EC2进行第一次初始化设置
			for (AccessKey accessKey : accessKeys) {
				// 初始化BasicAWSCredentials对象
				BasicAWSCredentials credentials = AWSCredentialsUtil.getBasicAWSCredentials(
						accessKey.getUserkey().split(" ")[0], accessKey.getUserkey().split(" ")[1]);
				AmazonEC2Client ec2 = new AmazonEC2Client(credentials);
				// 遍历每个区域,key为区域
				for (String key : regionMap.keySet()) {
					System.out.println("--------------------region:" + key);
					ec2.setEndpoint("https://" + key + ".amazonaws.com");
					ec2.setRegion(RegionUtils.getRegion(key.trim()));
					RunInstancesResult runInstancesResult = launchInstance(ec2, null, regionMap.get(key),
							accessKey.getUsername(), key,
							PropertiesOperatator
									.GetValueByKey(this.getClass().getClassLoader().getResource("").getPath()
											+ "conf/iamuser.properties", accessKey.getUsername()),
							null,null,null,null,null,1);
					System.out.println("----------------------------------------imageid--------"+regionMap.get(key));
					List<Instance> Instances = runInstancesResult.getReservation().getInstances();
					terminateInstances(ec2, Arrays.asList(new String[] { Instances.get(0).getInstanceId() }));
				}
				ec2InstanceInit.setUsername(accessKey.getUsername());
				// 序列化到文件中
				SerializeUtils.SerializeObject(ec2InstanceInit,
						this.getClass().getClassLoader().getResource("").getPath() + "conf/ec2instanceinit.txt");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: launchInstance
	 * @Description: TODO创建EC2
	 * @param ec2
	 * @param userData
	 * @param imageId
	 * @param username
	 * @param region
	 * @param striam
	 * @param name实例的名字
	 * @return
	 * @return: RunInstancesResult
	 */
	@SuppressWarnings("static-access")
	public RunInstancesResult launchInstance(AmazonEC2Client ec2, String userData, String imageId, String username,
			String region, String striam, String name, String description,String endpoint,
			Integer dcid,Integer userid,int isschedule) {
		System.out.println("*******************Create an Instance (Starting)*******************\n");
		int minInstanceCount = 1; // create one instance
		int maxInstanceCount = 1;
		RunInstancesRequest rir = new RunInstancesRequest(imageId, minInstanceCount, maxInstanceCount);
		rir.setInstanceType("t2.micro");
		// give the instance the key we just created
//		String keyName = "zjucstlianghua";
		String keyName = username+region;
//		String fileName = this.getClass().getClassLoader().getResource("").getPath() + "conf/" + keyName + "_+"
//				+ username + "_" + region + ".pem";
		String fileName = "F:/学习资料/测试文件/private key/"+keyName+".pem";
		// judge KeyPiar exist
		if (Ec2Utils.checkKeyPiars(ec2, keyName)) {
			rir.setKeyName(keyName);
		} else {
			try {
				rir.setKeyName(Ec2Utils.createKeyPair(ec2, username, region, fileName, keyName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// judge SecurityGroup exist
		List<String> groupNames = Ec2Utils.checkGroupNames(ec2, "group_zjucstlianghua");
		if (groupNames == null) {
			// set the instance in the group we just created
			rir.setSecurityGroups(Ec2Utils.createGroup(ec2));
		} else {
			// set the instance in the group we just created
			rir.setSecurityGroups(groupNames);
		}
		// set IAM
		IamInstanceProfileSpecification iam = new IamInstanceProfileSpecification();
		iam.withArn(striam);
		System.out.println("-----------------------------------------" + striam);
		rir.setIamInstanceProfile(iam);
		// 设置需要序列化的对象Ec2InstanceInit
		ec2InstanceInit = Ec2InstanceInit.getEc2InstanceInit();
		ec2InstanceInit.setIamInstanceProfile(iam);
		ec2InstanceInit.setKeyName(rir.getKeyName());
		ec2InstanceInit.setSecurityGroups(rir.getSecurityGroupIds());
		if (userData != null) {
			// set userData which can be executed while launching the instance
			rir.setUserData(userData);
		}
		RunInstancesResult result = ec2.runInstances(rir);
		System.out.println(result.getReservation().getInstances().get(0).getInstanceId());
		/***********
		 * to make sure the instance's state is "running instead of  pending"
		 **********/
		/*********** we wait for a while **********/
		System.out.println("waiting.....");
		try {
			Thread.currentThread().sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("The instance launched .....\n\n");
		System.out.println("*******************Create an Instance (Ending)*******************\n\n\n");
		//insert ec2Instance
		Ec2Instance ec2instance = new Ec2Instance();
		ec2instance.setEc2name(name);
		ec2instance.setRegion(region);
		ec2instance.setEc2id(result.getReservation().getInstances().get(0).getInstanceId());
		ec2instance.setDescription(description);
		ec2instance.setUserdata(userData);
		ec2instance.setEndpoint(endpoint);
		ec2instance.setDcid(dcid);
		ec2instance.setUserid(userid);
		ec2instance.setState("运行");
		ec2instance.setCreatetime(new Date());
		ec2instance.setIsschedule(isschedule);
		ec2Service.insertEc2(ec2instance);
		System.out.println("success----------------------------");
		
		return result;
	}

	@Override
	public StopInstancesResult stopInstances(AmazonEC2Client ec2, List<String> instanceIds) {
		if (instanceIds.size() != 0) {
			// return the stopInstancesResult
			return ec2.stopInstances(new StopInstancesRequest(instanceIds));
		}
		// list is null ,return
		return null;
	}

	@Override
	public TerminateInstancesResult terminateInstances(AmazonEC2Client ec2, List<String> instanceIds) {
		if (instanceIds.size() != 0) {
			// return terminateInstancesResult
			return ec2.terminateInstances(new TerminateInstancesRequest(instanceIds));
		}
		// if list is null,return
		return null;
	}

	@Override
	public void rebootInstances(AmazonEC2Client ec2, List<String> instanceIds){
		ec2.rebootInstances(new RebootInstancesRequest(instanceIds));
	}
	
	@Override
	public StartInstancesResult startInstances(AmazonEC2Client ec2, List<String> instanceIds) {
		if (instanceIds.size() != 0) {
			// return terminateInstancesResult
			return ec2.startInstances(new StartInstancesRequest(instanceIds));
		}
		// if list is null,return
		return null;
	}

	@Override
	public long calculateInstancesTimes(String username) {

		return 0;
	}
	
	@SuppressWarnings("restriction")
	@Override
	public String getUserdataFromFile(String filename) {
		String result = "";
		BufferedReader br = null;
		try {
			File file = new File(filename);
			br = new BufferedReader(new FileReader(file));
			String data = null;
			while((data=br.readLine())!= null){
				result+= data+"\n";
			}
			result = new sun.misc.BASE64Encoder().encode(result.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public List<Ec2Instance> findEC2All() {
		return ec2InstanceDao.findEC2All();
	}

	@Override
	public List<Ec2Instance> findEC2ByNameRegion(String username, String region) {
		return ec2InstanceDao.findEC2ByNameRegion(username,region);
	}
	
	@Override
	public void updateState(String state,String id){
		ec2InstanceDao.updateState(state, id);
	}

	@Override
	public List<Ec2Instance> findEC2Schedule() {
		return ec2InstanceDao.findEC2Schedule();
	}

	@Override
	public String findStateById(String ec2id) {
		return ec2InstanceDao.findStateById(ec2id);
	}
	

}
