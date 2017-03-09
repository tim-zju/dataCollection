package cn.springmvc.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.junit.Before;
import org.junit.Test;

import cn.ddcollection.service.Ec2Service;
import cn.ddcollection.service.impl.Ec2ServiceImpl;

/**
 * @author 浣滆�� E-mail:
 * @date 鍒涘缓鏃堕棿锛�2016骞�3鏈�13鏃� 涓婂崍9:47:39
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class TestEc2 {
	@Before
	public void setInfo() {
		System.out.println("寮�濮嬶紒");
	}

	@Test
	public void testGetInfo() throws IOException {
		// AmazonEC2Client ec2Client=new
		// AmazonEC2Client(AWSCredentialsUtil.getBasicAWSCredentials("", ""));
		System.out.println(retrieveInstanceId());
	}
	
	@Test
	public void testCreateEc2() throws IOException {
		Ec2Service ec2Service = new Ec2ServiceImpl();
		ec2Service.InitAllInstances();
	}

	public String retrieveInstanceId() throws IOException {
		String EC2Id = null;
		String inputLine;
		URL EC2MetaData = new URL("http://169.254.169.254/latest/meta-data/instance-id");
		URLConnection EC2MD = EC2MetaData.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(EC2MD.getInputStream()));
		while ((inputLine = in.readLine()) != null) {
			EC2Id = inputLine;
		}
		in.close();
		return EC2Id;
	}
}
