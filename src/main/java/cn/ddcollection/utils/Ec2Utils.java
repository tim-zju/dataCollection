package cn.ddcollection.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairResult;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.DescribeKeyPairsResult;
import com.amazonaws.services.ec2.model.DescribeSecurityGroupsResult;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.KeyPair;
import com.amazonaws.services.ec2.model.KeyPairInfo;
import com.amazonaws.services.ec2.model.SecurityGroup;

/**
 * @author 作者 E-mail:
 * @date 创建时间：2016年3月9日 下午4:47:06
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class Ec2Utils {
	/**
	 * 
	 * @Title: listGroups
	 * @Description: TODO安全组列表
	 * @param ec2
	 * @return
	 * @return: List<SecurityGroup>
	 */
	public static List<SecurityGroup> listGroups(AmazonEC2Client ec2) {
		DescribeSecurityGroupsResult describeSecurityGroups = ec2.describeSecurityGroups();
		List<SecurityGroup> securityGroups = describeSecurityGroups.getSecurityGroups();
		System.out.println(securityGroups.size() + "-----------------------------");
		for (SecurityGroup sg : securityGroups) {
			System.out.println("**********************" + sg.getGroupName() + "***********************");
			System.out.println("description: " + sg.getDescription());
			System.out.println("GroupId    : " + sg.getGroupId());
			System.out.println("GroupName  : " + sg.getGroupName());
			System.out.println("VpcId      : " + sg.getVpcId());
			System.out.println("Tags       : " + sg.getTags());
			System.out.println("IpPermissions: " + sg.getIpPermissions());
			System.out.println("***********************************************************\n\n");
		}
		return securityGroups;
	}

	/**
	 * 
	 * @Title: listKeyPiars
	 * @Description: TODO返回KeyPairInfo列表
	 * @param ec2Client
	 * @return
	 * @return: List<KeyPairInfo>
	 */
	public static List<KeyPairInfo> listKeyPiars(AmazonEC2Client ec2Client) {
		DescribeKeyPairsResult describeKeyPairs = ec2Client.describeKeyPairs();
		List<KeyPairInfo> keyPairs = describeKeyPairs.getKeyPairs();
		System.out.println("There are :" + keyPairs.size() + "KeyPairs. \n\n");
		for (KeyPairInfo kpi : keyPairs) {
			System.out.println("KeyName       : " + kpi.getKeyName());
			System.out.println("		KeyFingerprint: " + kpi.getKeyFingerprint());
			System.out.println("----------------------------------------------");
		}
		return keyPairs;
	}

	/**
	 * 创建安全组
	 * 
	 * @Title: createGroup
	 * @Description: TODO
	 * @param ec2
	 * @return
	 * @return: List<String>
	 */
	public static List<String> createGroup(AmazonEC2Client ec2) {
		// name of the group
		String Temp_Group = "group" + "_zjucstlianghua";
		CreateSecurityGroupRequest r1 = new CreateSecurityGroupRequest(Temp_Group, "ProgramGroup");
		ec2.createSecurityGroup(r1);
		AuthorizeSecurityGroupIngressRequest r2 = new AuthorizeSecurityGroupIngressRequest();
		r2.setGroupName(Temp_Group);
		/************* the property of http *****************/
		IpPermission permission = new IpPermission();
		permission.setIpProtocol("tcp");
		permission.setFromPort(80);
		permission.setToPort(80);
		List<String> ipRanges = new ArrayList<String>();
		ipRanges.add("0.0.0.0/0");
		permission.setIpRanges(ipRanges);
		/************* the property of SSH **********************/
		IpPermission permission1 = new IpPermission();
		permission1.setIpProtocol("tcp");
		permission1.setFromPort(22);
		permission1.setToPort(22);
		List<String> ipRanges1 = new ArrayList<String>();
		ipRanges1.add("0.0.0.0/0");
		permission1.setIpRanges(ipRanges1);
		/************* the property of https **********************/
		IpPermission permission2 = new IpPermission();
		permission2.setIpProtocol("tcp");
		permission2.setFromPort(443);
		permission2.setToPort(443);
		List<String> ipRanges2 = new ArrayList<String>();
		ipRanges2.add("0.0.0.0/0");
		permission2.setIpRanges(ipRanges2);
		/************* the property of tcp **********************/
		IpPermission permission3 = new IpPermission();
		permission3.setIpProtocol("tcp");
		permission3.setFromPort(0);
		permission3.setToPort(65535);
		List<String> ipRanges3 = new ArrayList<String>();
		ipRanges3.add("0.0.0.0/0");
		permission3.setIpRanges(ipRanges3);
		/**********************
		 * add rules to the group
		 *********************/
		List<IpPermission> permissions = new ArrayList<IpPermission>();
		permissions.add(permission);
		permissions.add(permission1);
		permissions.add(permission2);
		permissions.add(permission3);
		r2.setIpPermissions(permissions);
		ec2.authorizeSecurityGroupIngress(r2);
		List<String> groupName = new ArrayList<String>();
		// wait to out our instance into this group
		groupName.add(Temp_Group);
		return groupName;
	}

	/**
	 * 
	 * @Title: createKeyPair
	 * @Description: TODO创建密钥对
	 * @param ec2
	 * @param username
	 * @param region
	 * @param fileName
	 * @param keyName
	 * @return
	 * @throws IOException
	 * @return: String
	 */
	public static String createKeyPair(AmazonEC2Client ec2, String username, String region, String fileName,
			String keyName) throws IOException {
//		String keyName = "zjucstlianghua";
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//		Date date =new Date();
//		keyName = username+region+df.format(date);
		keyName = username+region;
		CreateKeyPairRequest createKeyPairRequest = new CreateKeyPairRequest();
		createKeyPairRequest.setKeyName(keyName);
		CreateKeyPairResult createKeyPairResult = ec2.createKeyPair(createKeyPairRequest);
		KeyPair keyPair = createKeyPairResult.getKeyPair();
		// System.out.println("The key we created is : " + keyPair.getKeyName()
		// + "\n" + "The key's fingerprint is : "
		// + keyPair.getKeyFingerprint() + "\n" + "The key's material is : \r\n"
		// + keyPair.getKeyMaterial()
		// + "\n\n");
		/***************** store the key in a .pem file ****************/
		// 命名规则，keyName+用户名+region名
		// String fileName = "D://aws/keypair/" + keyName + "_+" + username +
		// "_" + region + ".pem";
		// String fileName =
		// this.getClass().getClassLoader().getResource("").getPath() + "conf/"
		// + keyName + "_+"
		// + username + "_" + region + ".pem";
		String path = fileName.substring(0,fileName.lastIndexOf("/"));
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		File file = new File(fileName);
		if(!file.exists()){
			file.createNewFile();
		}
		BufferedReader bufferedReader = new BufferedReader(new StringReader(keyPair.getKeyMaterial()));
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
		char buf[] = new char[1024];
		int len;
		while ((len = bufferedReader.read(buf)) != -1) {
			bufferedWriter.write(buf, 0, len);
		}
		bufferedWriter.flush();
		bufferedReader.close();
		bufferedWriter.close();
		System.out.println("The key was stored in : " + fileName + "/r/n/n");
		return keyPair.getKeyName();
	}

	/**
	 * 
	 * @Title: checkGroupNames
	 * @Description: TODO find whether the securityGroup is exists, if
	 *               exists,return List<String>; else return null
	 * @param ec2
	 * @param securityGroupName
	 * @return
	 * @return: List<String>
	 */
	public static List<String> checkGroupNames(AmazonEC2Client ec2, String securityGroupName) {

		List<String> group = new ArrayList<String>();
		DescribeSecurityGroupsResult describeSecurityGroups = ec2.describeSecurityGroups();
		List<SecurityGroup> securityGroups = describeSecurityGroups.getSecurityGroups();
		System.out.println(securityGroups.size() + "-----------------------------");
		for (SecurityGroup sg : securityGroups) {

			if (securityGroupName.equals(sg.getGroupName())) {
				group.add(securityGroupName);
				return group;
			}
		}
		return null;
	}

	/**
	 * 
	 * @Title: checkKeyPiars
	 * @Description: TODO To find whether the keyPair is exits if exists, return
	 *               true else return false
	 * @param ec2Client
	 * @param keyPairName
	 * @return
	 * @return: boolean
	 */
	public static boolean checkKeyPiars(AmazonEC2Client ec2Client, String keyPairName) {
		DescribeKeyPairsResult describeKeyPairs = ec2Client.describeKeyPairs();
		List<KeyPairInfo> keyPairs = describeKeyPairs.getKeyPairs();

		for (KeyPairInfo kpi : keyPairs) {
			if (keyPairName.equals(kpi.getKeyName())) {
				return true;
			}
		}
		return false;
	}
}
