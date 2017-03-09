package cn.ddcollection.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.ddcollection.model.AccessKey;
import cn.ddcollection.model.DataCollector;
import cn.ddcollection.model.Ec2Instance;
import cn.ddcollection.model.Region;
import cn.ddcollection.service.DataCollectorService;
import cn.ddcollection.service.Ec2Service;
import cn.ddcollection.service.S3ObjectService;
import cn.ddcollection.service.UserAccountService;
import cn.ddcollection.service.UserService;
import cn.ddcollection.utils.AWSCredentialsUtil;
import cn.ddcollection.utils.DownloadBG;
import cn.ddcollection.utils.PropertiesOperatator;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.ec2.AmazonEC2Client;

/**
 * 
 * @ClassName: Ec2Controller
 * @Description: ec2的控制器
 * @author: 大鹤
 * @date: 2016年2月24日 下午9:37:48
 */
@Controller
@RequestMapping("/ec2")
public class Ec2Controller {
	@Resource(name = "ec2Service")
	private Ec2Service ec2Service;
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "dataCollectorService")
	private DataCollectorService dataCollectorService;
	@Resource(name = "userAccountService")
	private UserAccountService userAccountService;
	private AmazonEC2Client ec2;

	/**
	 * 
	 * @Title: toEc2List
	 * @Description: 跳转到ec2list.jsp
	 * @return
	 * @return: String
	 */
	@RequestMapping("toec2list")
	public String toEc2List() {
		return "ec2/ec2list";
	}

	@RequestMapping("toec2schedule")
	public String toEc2Schedule() {
		return "ec2/ec2schedule";
	}
	
	/**
	 * 
	 * @Title: getUserKeyList
	 * @Description: 获取用户名和awsAccessKey、awsSecretAccessKey
	 * @return
	 * @throws IOException
	 * @return: List<AccessKey>
	 */
	@RequestMapping("getuserkeylist")
	public @ResponseBody List<AccessKey> getUserKeyList() throws IOException {
		return PropertiesOperatator
				.getUserName(this.getClass().getClassLoader().getResource("").getPath() + "conf/userkey.properties");
	}

	/**
	 * 
	 * @Title: getRegionList
	 * @Description: TODO获取region的名字和value，并传递给easyui-combobox
	 * @return
	 * @throws IOException
	 * @return: List<Region>
	 */
	@RequestMapping("getregionlist")
	public @ResponseBody List<Region> getRegionList() throws IOException {
		return PropertiesOperatator
				.getRegionName(this.getClass().getClassLoader().getResource("").getPath() + "conf/region.properties");
	}

	public String listEc2Instances(HttpServletRequest request) throws Exception {
		return "ec2/ec2list";
	}

	@RequestMapping("tocreateec2")
	public String Createec2() {
		return "ec2/ec2create";
	}
	
	@RequestMapping("toswitchec2")
	public String switchec2() {
		return "ec2/ec2switch";
	}

	@RequestMapping("launchinstance")
	public String launchInstance(@RequestParam("uploadShell") CommonsMultipartFile file,HttpServletRequest request) {
		String key = request.getParameter("userkey");
		BasicAWSCredentials credentials = AWSCredentialsUtil.getBasicAWSCredentials(key.split(" ")[0],
				key.split(" ")[1]);
		String username = request.getParameter("username");
		String region = request.getParameter("region");
		String description = request.getParameter("description");
		ec2 = new AmazonEC2Client(credentials);
		String endpoint = "https://" + region + ".amazonaws.com";
		ec2.setEndpoint(endpoint);
		ec2.setRegion(RegionUtils.getRegion(region.trim()));
		Ec2Instance ec2instance = new Ec2Instance();
		// String userData, String imageId, String username,
		// String region, String striam, String name
		
		ec2instance.setEc2name(request.getParameter("ec2name"));
		ec2instance.setDescription(description);
		
		ec2instance.setImageid(PropertiesOperatator
				.GetValueByKey(this.getClass().getClassLoader().getResource("").getPath() + "conf/regionami.properties",
						region)
				.trim());
		String iam = PropertiesOperatator.GetValueByKey(
				this.getClass().getClassLoader().getResource("").getPath() + "conf/iamuser.properties", username);
		ec2instance.setIam(iam);
///////////////////userdata
		if(file!=null){
			File dir = new File("F:/学习资料/测试文件/");
			if(!dir.exists()){
				dir.mkdirs();
			}
			File destFile = new File("F:/学习资料/测试文件/myShell.txt");
			try {
				if(!destFile.exists()){
					destFile.createNewFile();
				}
				FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
			} catch (IOException e) {
				e.printStackTrace();
			}// 复制临时文件到指定目录下
			ec2instance.setUserdata(ec2Service.getUserdataFromFile(destFile.getAbsolutePath()));
		}
		ec2instance.setRegion(region);
		ec2instance.setEndpoint(endpoint);
		int i = userAccountService.findIdByName(username);
		ec2instance.setUserid(i);
		String dataname = request.getParameter("dataCollector");
		if(!dataname.equals("")){
			int dcid = dataCollectorService.findDataCollectorIdByName(dataname);
			ec2instance.setDcid(dcid);
		}
		
		///////////////设置是否进行调度
		ec2instance.setIsschedule(0);
		
		try{
			ec2Service.launchInstance(ec2, ec2instance, username);
		}catch(Exception e){
			request.setAttribute("result", e.getMessage());
			return "ec2/ec2create";
		}
		request.setAttribute("result", "EC2创建成功");
		
		System.out.println("success");
		
		
		return "ec2/ec2create";
	}
	
	/**
	 * 
	 * @Title: getDataCollectorList
	 * @Description: TODO获取region的名字和value，并传递给easyui-combobox
	 * @return
	 * @throws IOException
	 * @return: List<Region>
	 */
	@RequestMapping("getDataCollectorlist")
	public @ResponseBody List<DataCollector> getDataCollectorList() throws IOException {
		return dataCollectorService.findDataCollectorAll();
	}
	
	
	/**
	 * 
	 * @Title: getDataCollectorList
	 * @Description: TODO获取region的名字和value，并传递给easyui-combobox
	 * @return
	 * @throws IOException
	 * @return: List<Region>
	 */
	@RequestMapping("getec2namelist")
	public @ResponseBody List<Ec2Instance> getEC2NameList(HttpServletRequest req) throws IOException {
		String username = req.getParameter("username").trim();
		String region = req.getParameter("region").trim();
		return ec2Service.findEC2ByNameRegion(username,region);
	}

	@RequestMapping("getstatus")
	public @ResponseBody ModelAndView getstatus(HttpServletRequest req,HttpServletResponse resp,ModelMap model) throws IOException {
		List<String> ls = new ArrayList<String>();
		String username = req.getParameter("username");
//		String key = PropertiesOperatator
//				.getUserkey(username,this.getClass().getClassLoader().getResource("").getPath() + "conf/userkey.properties");
//		BasicAWSCredentials credentials = AWSCredentialsUtil.getBasicAWSCredentials(key.split(" ")[0],
//				key.split(" ")[1]);
//		ec2 = new AmazonEC2Client(credentials);
//		
//		DescribeInstancesResult describeInstancesRequest = ec2.describeInstances();
//	    List<Reservation> reservations = describeInstancesRequest.getReservations();
//	    Set<Instance> allInstances = new HashSet<Instance>();
//	    for (Reservation reservation : reservations) {
//	      for (Instance instance : reservation.getInstances()) {
//	        if (instance.getInstanceId().equals(instanceId))
//	          instance.getState();
//	      }
//	    }
		String ec2name = req.getParameter("ec2name");
		String region = req.getParameter("region");
		return new ModelAndView("ec2/ec2switch",model);
	}
	
	@RequestMapping("switchstate")
	public String switchstate(HttpServletRequest req) throws IOException {
		String instanceId = req.getParameter("ec2id");
		String option = req.getParameter("option");
		String key = req.getParameter("userkey");
		String region = req.getParameter("region");
		List<String> ins = new ArrayList<String>();
		ins.add(instanceId);
		System.out.println("key:"+key);
		BasicAWSCredentials credentials = AWSCredentialsUtil.getBasicAWSCredentials(key.split(" ")[0],
				key.split(" ")[1]);
		ec2 = new AmazonEC2Client(credentials);
		ec2.setEndpoint("https://" + region + ".amazonaws.com");
		ec2.setRegion(RegionUtils.getRegion(region.trim()));
		System.out.println("-------------"+option);
//		DescribeInstancesResult describeInstancesResult = ec2.describeInstances();
//	    List<Reservation> reservations = describeInstancesResult.getReservations();
//	    for (Reservation reservation : reservations) {
//	      for (Instance instance : reservation.getInstances()) {
//	        System.out.println("------------------------**********"+instance.getInstanceId());
//	      }
//	    }
		if(option.equals("destroy")){
			try{
				ec2Service.terminateInstances(ec2, ins);
				ec2Service.updateState("销毁", instanceId);
			}catch(Exception e){
				req.setAttribute("result", e.getMessage());
				return "ec2/ec2switch";
			}
			req.setAttribute("result", "EC2已经成功销毁");
		}else if(option.equals("start")){
			try{
				ec2Service.startInstances(ec2, ins);
				ec2Service.updateState("运行", instanceId);
			}catch(Exception e){
				req.setAttribute("result", e.getMessage());
				return "ec2/ec2switch";
			}
			req.setAttribute("result", "EC2已经成功启动");
		}else if(option.equals("stop")){
			try{
				ec2Service.stopInstances(ec2, ins);
				ec2Service.updateState("停止", instanceId);
			}catch(Exception e){
				req.setAttribute("result", e.getMessage());
				return "ec2/ec2switch";
			}
			req.setAttribute("result", "EC2已经成功停止");
		}else if(option.equals("restart")){
			try{
				ec2Service.rebootInstances(ec2, ins);
				ec2Service.updateState("运行", instanceId);
			}catch(Exception e){
				req.setAttribute("result", e.getMessage());
				return "ec2/ec2switch";
			}
			req.setAttribute("result", "EC2已经成功重启");
		}
		return "ec2/ec2switch";
	}
	
	
	@RequestMapping("schedulecreate")
	public String schedulecreate(HttpServletRequest request) {
		String key = request.getParameter("userkey");
		BasicAWSCredentials credentials = AWSCredentialsUtil.getBasicAWSCredentials(key.split(" ")[0],
				key.split(" ")[1]);
		String username = request.getParameter("username");
		String region = request.getParameter("regionname");
		ec2 = new AmazonEC2Client(credentials);
		String endpoint = "https://" + region + ".amazonaws.com";
		ec2.setEndpoint(endpoint);
		ec2.setRegion(RegionUtils.getRegion(region.trim()));
		Ec2Instance ec2instance = new Ec2Instance();
		
		ec2instance.setEc2name(request.getParameter("ec2name"));
		
		ec2instance.setImageid(PropertiesOperatator
				.GetValueByKey(this.getClass().getClassLoader().getResource("").getPath() + "conf/regionami.properties",
						region)
				.trim());
		String iam = PropertiesOperatator.GetValueByKey(
				this.getClass().getClassLoader().getResource("").getPath() + "conf/iamuser.properties", username);
		ec2instance.setIam(iam);
///////////////////userdata
		ec2instance.setUserdata(getUserdata(username));
		
		ec2instance.setRegion(region);
		ec2instance.setEndpoint(endpoint);
		ec2instance.setIsschedule(1);
		int i = userAccountService.findIdByName(username);
		ec2instance.setUserid(i);
		try{
			ec2Service.launchInstance(ec2, ec2instance, username);
		}catch(Exception e){
			request.setAttribute("result", e.getMessage());
			return "ec2/ec2create";
		}
		request.setAttribute("result", "EC2创建成功");
		
		System.out.println("success");
		
		
		return "ec2/ec2schedule";
	}
	
	List<String> sc = new ArrayList<String>();
	
	@RequestMapping("schedulestart")
	public @ResponseBody String scheduleStart(HttpServletRequest req) {
		String instanceId = req.getParameter("ec2id");
		String option = req.getParameter("state");
		String key = req.getParameter("userkey");
		String region = req.getParameter("region");
		int index  = Integer.parseInt(req.getParameter("index"));
		final int len = Integer.parseInt(req.getParameter("length"));
		int time = Integer.parseInt(req.getParameter("time"));
		sc.add(instanceId+","+option+","+key+","+region+","+index);
		System.out.println("instanceid:"+instanceId+",option:"+option+",key:"+key+",region:"+region+",index:"+index
				+",len:"+len+",size:"+sc.size());
		if(sc.size()==len){
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
			String[] s = sc.get(0).split(",");
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>."+s[1]);
			TimerTask task = new TimerTask() {  
				int count = 0;
	            @Override  
	            public void run() {
	            	String[] s = sc.get(count).split(",");
	            	BasicAWSCredentials credentials = AWSCredentialsUtil.getBasicAWSCredentials(s[2].split(" ")[0],
	            			s[2].split(" ")[1]);
	        		ec2 = new AmazonEC2Client(credentials);
	        		ec2.setEndpoint("https://" + s[3] + ".amazonaws.com");
	        		ec2.setRegion(RegionUtils.getRegion(s[3].trim()));
	        		System.out.println(ec2Service.findStateById(s[0])+";"+s[0]);
            		if(ec2Service.findStateById(s[0]).equals("停止")){
            			List<String> ins = new ArrayList<String>();
            			ins.add(s[0]);
            			ec2Service.startInstances(ec2, ins);
        				ec2Service.updateState("运行", s[0]);
	            	}
            		else if(ec2Service.findStateById(s[0]).equals("运行")){
            			List<String> ins = new ArrayList<String>();
            			ins.add(s[0]);
            			ec2Service.stopInstances(ec2, ins);
        				ec2Service.updateState("停止", s[0]);
	            	}
            		count = (count+1)%sc.size();
	            }  
	        };  
	        Timer timer = new Timer();  
	        long delay = 0;  
	        long intevalPeriod = time*1000*60;  
	        timer.scheduleAtFixedRate(task, delay, intevalPeriod);
			return "EC2 schedule start";
		}
		return null;
	}
	
	
	@RequestMapping("getschedulelist")
	public @ResponseBody List<Ec2Instance> getScheduleList(HttpServletRequest req) throws IOException {
		if(req.getParameter("query").equals("true")){
			return ec2Service.findEC2Schedule();
		}
		return null;
	}
	
	@SuppressWarnings("restriction")
	private String getUserdata(String username){
		String userdata = "#!/bin/bash"+"\n"
				+ " mkdir /home/ec2-user/environmentsoft /home/ec2-user/java "+"\n"
				+ "aws s3 cp s3://resources_"+username+"/jdk-8u65-linux-x64.tar.gz /home/ec2-user/environmentsoft"+"\n"
				+ "tar -zxvf /home/ec2-user/environmentsoft/jdk-8u65-linux-x64.tar.gz"+"\n"
				+ "mv jdk1.8.0_65/ /home/ec2-user/./java/"+"\n"
				+ "rpm -e  --nodeps `rpm -qa|grep jdk`"+"\n"
				+ "echo \"export JAVA_HOME=/home/ec2-user/java/jdk1.8.0_65\" >> /home/ec2-user/.bash_profile"+"\n"
				+ "source /home/ec2-user/.bash_profile"+"\n"
				+ "echo \"export CLASSPATH=/home/ec2-user/$CLASSPATH:$JAVA_HOME/lib:$JAVA_HOME/jre/lib\" >> /home/ec2-user/.bash_profile"+"\n"
				+ "echo \"export PATH=/home/ec2-user/$PATH:$JAVA_HOME/bin:$JAVA_HOME/jre/bin\" >> /home/ec2-user/.bash_profile"+"\n"
				+ "source /home/ec2-user/.bash_profile"+"\n"
				+ "mkdir /home/ec2-user/scrpys"+"\n"
				+ "aws s3 cp s3://datacollector_"+username+"/datacollector.jar /home/ec2-user/scrpys"+"\n"
				+ "touch /home/ec2-user/scrpys/crontab_scanfile.sh"+"\n"
				+ "chmod u+x /home/ec2-user/scrpys/crontab_scanfile.sh"+"\n"
				+ "echo \"#/bin/sh\" >> /home/ec2-user/scrpys/crontab_scanfile.sh"+"\n"
				+ "echo \"chmod -R 777 /home/ec2-user/datas/\" >> /home/ec2-user/scrpys/crontab_scanfile.sh"+"\n"
				+ "echo \"aws s3 mv /home/ec2-user/datas/ s3://dataresult_"+username+"/ --recursive\" >> /home/ec2-user/scrpys/crontab_scanfile.sh"+"\n"
				+ "source /home/ec2-user/scrpys/crontab_scanfile.sh"+"\n"
				+ "echo \"*/2 * * * * root /home/ec2-user/scrpys/crontab_scanfile.sh\" >> /etc/crontab"+"\n"
				+ "source /etc/crontab"+"\n"
				+ "echo \"/home/ec2-user/java/jdk1.8.0_65/bin/java -jar /home/ec2-user/scrpys/datacollector.jar /home/ec2-user/datas/mydata/\" >> /etc/rc.d/rc.local"+"\n"
				+ "source /etc/rc.d/rc.local"+"\n";
		
		return new sun.misc.BASE64Encoder().encode(userdata.getBytes());		
	}
	
}
