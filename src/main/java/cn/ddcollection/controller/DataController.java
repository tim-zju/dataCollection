package cn.ddcollection.controller;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jets3t.service.S3Service;
import org.jets3t.service.ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.security.AWSCredentials;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.ddcollection.model.AccessKey;
import cn.ddcollection.model.DataCollector;
import cn.ddcollection.model.MyS3Object;
import cn.ddcollection.service.DataCollectorService;
import cn.ddcollection.service.S3ObjectService;
import cn.ddcollection.service.SSSService;
import cn.ddcollection.service.UserAccountService;
import cn.ddcollection.service.impl.RealTimePrcServiceImpl;
import cn.ddcollection.utils.DownloadBG;
import cn.ddcollection.utils.PropertiesOperatator;

/**
 * @author 作者 E-mail:
 * @date 创建时间：2016年4月6日 下午3:41:22
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@Controller
@RequestMapping("/data")
public class DataController{

	@Resource(name = "sssService")
	private SSSService sssService;
	@Resource(name = "dataCollectorService")
	private DataCollectorService dataCollectorService;
	@Resource(name = "s3ObjectService")
	private S3ObjectService s3ObjectService;
	@Resource(name = "userAccountService")
	private UserAccountService userAccountService;
	@Resource(name = "realTimePrcService")
	private RealTimePrcServiceImpl realTimePrcService;

	AWSCredentials awsCredentials = null;
	// 创建S3Service对象
	S3Service s3Service = null;
	// 声明bucket
	S3Bucket testBucket;

	@RequestMapping("todata")
	public String toData() {
		return "data/datacol";
	}

	@RequestMapping("addCollector")
	public String addCollector(@RequestParam("datacollectorname") String datacollectorname,
			@RequestParam("description") String description,
			@RequestParam("uploadfile") CommonsMultipartFile file, HttpServletRequest request) {
		List<AccessKey> accessKeys = PropertiesOperatator
				.getUserName(this.getClass().getClassLoader().getResource("").getPath() + "conf/userkey.properties");
		if (!file.isEmpty()){
			String fileName = file.getOriginalFilename();
			String path = request.getSession().getServletContext().getRealPath("/upload/" + fileName);
			DataCollector dc = dataCollectorService.findDataCollectorByName(fileName);
			if (dc==null){
				for (AccessKey accessKey : accessKeys) {
					awsCredentials = new AWSCredentials(accessKey.getUserkey().split(" ")[0],
							accessKey.getUserkey().split(" ")[1]);
					s3Service = new RestS3Service(awsCredentials);
					try {
						// 上传S3
						if(sssService.uploadObject(file, fileName, path, s3Service,
								"datacollector_" + accessKey.getUsername())){
							// 存储数据库，先存到s3object表中,然后存到datacollector中
							String location = "https://s3.amazonaws.com/"
									+ "datacollector_" + accessKey.getUsername() + "/" + fileName;
							int userid = userAccountService.findIdByName(accessKey.getUsername());
							MyS3Object s3Object = new MyS3Object(fileName,location,"file",userid);
							s3ObjectService.insertS3Object(s3Object);
							////////////插入到datacollector表中
							int s3id = s3ObjectService.findIdByLocation(s3Object.getLocation());
							dataCollectorService.insertDataCollector(
										new DataCollector(fileName, null,description, null,s3id));
						}else{
							request.setAttribute("result", "注意：数据采集程序上传不成功！");
							return "data/datacol";
						}
						 
					} catch (Exception e) {
						e.printStackTrace();
						request.setAttribute("result", e.getMessage());
						return "data/datacol";
					}
				}
				request.setAttribute("result", "数据采集程序上传成功");
			} else {
				request.setAttribute("result", "数据采集程序名已存在,请重新命名");
				return "data/datacol";
			}
		} else {
			request.setAttribute("result", "上传文件为空,请重新上传文件");
			return "data/datacol";
		}
		
		return "data/datacol";
	}
	
	@RequestMapping("delCollector")
	public String delCollector(@RequestParam("dataname") String datacollectorname,HttpServletRequest request){
		List<AccessKey> accessKeys = PropertiesOperatator
				.getUserName(this.getClass().getClassLoader().getResource("").getPath() + "conf/userkey.properties");
		for (AccessKey accessKey : accessKeys) {
			awsCredentials = new AWSCredentials(accessKey.getUserkey().split(" ")[0],
					accessKey.getUserkey().split(" ")[1]);
			s3Service = new RestS3Service(awsCredentials);
			try {
				s3Service.deleteObject("datacollector_"+accessKey.getUsername(), datacollectorname);
				int i = dataCollectorService.deleteDataCollectorByName(datacollectorname);
				s3ObjectService.deleteByName(datacollectorname);
				System.out.println("ok--------------------"+i);
			} catch (ServiceException e) {
				e.printStackTrace();
				request.setAttribute("result", e.getMessage());
				return "data/datacol";
			}
			request.setAttribute("result", "数据采集程序已经成功删除");
		}
		return "data/datacol";
	}
	
	@RequestMapping("getdatalist")
	public @ResponseBody List<DataCollector> getDataList(){
		return dataCollectorService.findDataCollectorAll();
	}
	
	@RequestMapping("getdatacol")
	public @ResponseBody List<DataCollector> getDataCol(HttpServletRequest req){
		String query = req.getParameter("query");
		if(query.equals("true")){
			return dataCollectorService.findDataCollectorS3();
		}
		
		return null;
	}
	
	@RequestMapping("updatedata")
	public String updatedata(@RequestParam("dataname") String datacollectorname,HttpServletRequest request,
			@RequestParam("description") String description){
		try {
			dataCollectorService.updataDataCollectorDes(description, datacollectorname);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", e.getMessage());
			return "data/datacol";
		}
		request.setAttribute("result", "数据采集程序已经成功更新");
		return "data/datacol";
	}
	
	@RequestMapping("downloadbg")
	public String downloadBG(@RequestParam("option") int time,HttpServletRequest request){
		try {
			TimerTask task = new TimerTask() {  
	            @Override  
	            public void run() {  
	            	DownloadBG.execute(sssService, realTimePrcService);
	            }  
	        };  
	        Timer timer = new Timer();  
	        long delay = 0;  
	        long intevalPeriod = time * 1000*60/2;  
	        timer.scheduleAtFixedRate(task, delay, intevalPeriod);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", e.getMessage());
			return "data/datacol";
		}
		request.setAttribute("result", "后台下载程序一开始执行");
		return "data/datacol";
	}
	
}


