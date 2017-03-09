package cn.ddcollection.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.model.container.ObjectKeyAndVersion;
import org.jets3t.service.multithread.DownloadPackage;
import org.jets3t.service.multithread.S3ServiceSimpleMulti;
import org.jets3t.service.security.AWSCredentials;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.ddcollection.model.AccessKey;
import cn.ddcollection.model.MyBucket;
import cn.ddcollection.model.MyObject;
import cn.ddcollection.model.MyS3Object;
import cn.ddcollection.service.S3ObjectService;
import cn.ddcollection.service.SSSService;
import cn.ddcollection.service.UserAccountService;
import cn.ddcollection.utils.PropertiesOperatator;

/**
 * 
 * @ClassName: S3Controller
 * @Description: s3的控制器
 * @author: 大鹤
 * @date: 2016年2月24日 下午9:41:46
 */
@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/s3")
public class S3Controller {
	@Resource(name = "sssService")
	private SSSService sssService;
	@Resource(name = "s3ObjectService")
	private S3ObjectService s3ObjectService;
	@Resource(name = "userAccountService")
	private UserAccountService userAccountService;
	// // 创建awsCredentials对象
	// String awsAccessKey = "AKIAJF2ZVSWLMSHTBTOA";
	// String awsSecretAccessKey = "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";
	// AWSCredentials awsCredentials = new AWSCredentials(awsAccessKey,
	// awsSecretAccessKey);
	AWSCredentials awsCredentials = null;
	// 创建S3Service对象
	// S3Service s3Service = new RestS3Service(awsCredentials);
	S3Service s3Service = null;
	// 声明bucket
	S3Bucket testBucket;

	/**
	 * 
	 * @Title: uploadObject
	 * @Description: TODO上传Object
	 * @param file
	 * @param request
	 * @param filename
	 * @param bucketName
	 * @return
	 * @return: String
	 */
	@RequestMapping(value = "uploadobject", method = RequestMethod.POST)
	public String uploadObject(@RequestParam("uploadfile") CommonsMultipartFile file, HttpServletRequest request,
			@RequestParam("username") String username, @RequestParam("bucketname") String bucketName) {
		String key = PropertiesOperatator.GetValueByKey(
				this.getClass().getClassLoader().getResource("").getPath() + "conf/userkey.properties", username);
		awsCredentials = new AWSCredentials(key.split(" ")[0], key.split(" ")[1]);
		s3Service = new RestS3Service(awsCredentials);
		// MultipartFile是对当前上传的文件的封装，当要同时上传多个文件时，可以给定多个 MultipartFile参数(数组)
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			// String type =
			// file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));//
			// 取文件格式后缀名
			// 取当前时间戳作为文件名
			// String path =
			// request.getSession().getServletContext().getRealPath("/upload/" +
			// fileName + type);// 存放位置
			try{
				String path = request.getSession().getServletContext().getRealPath("/upload/" + fileName);
				sssService.uploadObject(file, fileName, path, s3Service, bucketName);
				int userid = userAccountService.findIdByName(username);
				s3ObjectService.insertS3Object(new MyS3Object(fileName,"https://s3.amazonaws.com/"+bucketName+"/"+fileName,"file",userid));
			}catch(Exception e){
				request.setAttribute("result", e.getMessage());
				return "s3/filemanagement";
			}
			request.setAttribute("result", "对象上传成功");
			
			return "s3/filemanagement";
		} else {
			request.setAttribute("result", "未添加上传的对象");
			return "s3/filemanagement";
		}
	}

	/**
	 * 
	 * @Title: downloadObject
	 * @Description: TODO下载object
	 * @param bucketName
	 * @param objectName
	 * @return
	 * @throws ServiceException
	 * @return: String
	 */
	@RequestMapping("downloadobject")
	public String downloadObject(@RequestParam("bucketName") String bucketName,
			@RequestParam("objectName") String objectName,HttpServletRequest request,
			@RequestParam("userkey") String key) throws ServiceException {
		try{
			awsCredentials = new AWSCredentials(key.split(" ")[0], key.split(" ")[1]);
			s3Service = new RestS3Service(awsCredentials);
			String localpath = "F:/学习资料/测试文件/result";
			sssService.downloadObject(bucketName,localpath, objectName, s3Service);
		}catch(Exception e){
			request.setAttribute("result", e.getMessage());
			return "s3/filemanagement";
		}
		request.setAttribute("result", "对象下载成功");
		
		return "s3/filemanagement";
	}

	// Create a simple multi-threading service based on our existing S3Service
	S3ServiceSimpleMulti simpleMulti = new S3ServiceSimpleMulti(s3Service);

	/**
	 * 多线程上传Object
	 * 
	 * @Title: multipleDownloads
	 * @Description: TODO
	 * @return
	 * @return: String
	 */
	@RequestMapping("multipleDownloads")
	public String multipleDownloads() {
		S3Bucket testBucket;
		try {
			testBucket = s3Service.getBucket("test-bucket-erosares");
			// Create a DownloadPackage for each object, to associate the object
			// with an output file.

			DownloadPackage[] downloadPackages = new DownloadPackage[5];
			downloadPackages[0] = new DownloadPackage(s3Service.getObject(testBucket, "MyData"), new File("MyData"));
			downloadPackages[1] = new DownloadPackage(s3Service.getObject(testBucket, "MyData"), new File("MyData"));
			downloadPackages[2] = new DownloadPackage(s3Service.getObject(testBucket, "MyData"), new File("MyData"));
			// Download the objects.

			simpleMulti.downloadObjects(testBucket, downloadPackages);

			System.out.println("Downloaded objects to current working directory");
		} catch (S3ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "s3/filemanagement";
	}

	/**
	 * 
	 * @Title: getUserKeyList
	 * @Description: 返回用户的一对key值，并转为json在s3list中显示
	 * @return
	 * @throws IOException
	 * @return: List<AccessKey>
	 */
	@RequestMapping("getuserkeylist")
	public @ResponseBody List<AccessKey> getUserKeyList() throws IOException {
		System.out.println(this.getClass().getClassLoader().getResource("").getPath() + "conf/userkey.properties");
		return PropertiesOperatator
				.getUserName(this.getClass().getClassLoader().getResource("").getPath() + "conf/userkey.properties");
	}

	/**
	 * 
	 * @Title: toS3Index
	 * @Description: TODO跳转到s3index
	 * @return
	 * @throws Exception
	 * @return: String
	 */
	@RequestMapping("tos3index")
	public String toS3Index() throws Exception {
		return "s3/s3index";
	}

	/**
	 * 
	 * @Title: toBucketManage
	 * @Description: TODO跳转到BucketManagement
	 * @return
	 * @throws Exception
	 * @return: String
	 */
	@RequestMapping("tobucketmanagement")
	public String toBucketManagement() throws Exception {
		return "s3/bucketmanagement";
	}

	/**
	 * 
	 * @Title: toFileManagement
	 * @Description: TODO跳转到FileManagement
	 * @return
	 * @throws Exception
	 * @return: String
	 */
	@RequestMapping("tofilemanagement")
	public String toFileManagement() throws Exception {
		return "s3/filemanagement";
	}

	/**
	 * 
	 * @Title: createBucket
	 * @Description: TODO创建bucket
	 * @return
	 * @throws Exception
	 * @return: String
	 */
	@RequestMapping("createbucket")
	public String createBucket(@RequestParam("userkey") String userkey, @RequestParam("bucketname") String bucketname,
			@RequestParam("username") String username,HttpServletRequest request) throws Exception {
		System.out.println("bucketname----------------------------"+bucketname);
		awsCredentials = new AWSCredentials(userkey.split(" ")[0].trim(), userkey.split(" ")[1].trim());
		s3Service = new RestS3Service(awsCredentials);
		try {
			s3Service.createBucket(bucketname + "_" + username);
			int userid = userAccountService.findIdByName(username);
			s3ObjectService.insertS3Object(new MyS3Object(bucketname + "_" + username, "https://s3.amazonaws.com/"+bucketname + "_" + username, "bucket", userid));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", e.getMessage());
			return "s3/bucketmanagement";
		}
		request.setAttribute("result", "存储桶创建成功");
		return "s3/bucketmanagement";
	}
	
	@RequestMapping("deletebucket")
	public String deletebucket(@RequestParam("userkey") String userkey, @RequestParam("bucketname") String bucketname,
			@RequestParam("username") String username,HttpServletRequest request) throws Exception {
		awsCredentials = new AWSCredentials(userkey.split(" ")[0].trim(), userkey.split(" ")[1].trim());
		s3Service = new RestS3Service(awsCredentials);
		try {//////////////首先判断存储桶是否为空，不为空就先删除桶内对象
			S3Object[] objects = s3Service.listObjects(bucketname);
			for(int i=0;i<objects.length;i++){
				s3Service.deleteObject(bucketname, objects[i].getName());
			}
			s3Service.deleteBucket(bucketname);
			int userid = userAccountService.findIdByName(username);
			s3ObjectService.deleteByBucket(userid, bucketname);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", e.getMessage());
			return "s3/bucketmanagement";
		}
		request.setAttribute("result", "存储桶删除成功");
		return "s3/bucketmanagement";
	}
	
	@RequestMapping("bucketmanagement")
	public String bucketmanagement() {
		return "s3/bucketmanagement";
	}
	
	@RequestMapping("filemanagement")
	public String filemanagement() {
		return "s3/filemanagement";
	}
	

	List<MyBucket> myBuckets = new ArrayList<MyBucket>();

	/**
	 * 
	 * @Title: listBucket
	 * @Description: 查询用户的bucket和object，并显示表格
	 * @param request
	 * @return
	 * @throws Exception
	 * @return: String
	 */
	@RequestMapping("listbucket")
	public String listBucket(HttpServletRequest request) throws Exception {
		myBuckets.clear();
		String[] keys = request.getParameterValues("userkey");
		for (String key : keys) {
			AWSCredentials awsCredentials = new AWSCredentials(key.split(" ")[0], key.split(" ")[1]);
			S3Service s3Service = new RestS3Service(awsCredentials);
			for (S3Bucket s3bucket : s3Service.listAllBuckets()) {
				List<MyObject> myObjects = new ArrayList<MyObject>();
				for (S3Object s3object : s3Service.listObjects(s3bucket)) {
					// 目前假设存储名字和详细信息
					myObjects.add(new MyObject(s3object.getName(), s3object.getKey()));
				}
				// 目前假设存储名字和详细信息
				myBuckets.add(new MyBucket(s3bucket.getName(), s3bucket.getLocation(), myObjects));
			}
		}
		request.setAttribute("mybuckets", myBuckets);
		return "s3/s3test";
	}

	/**
	 * 
	 * @Title: getBucketNames
	 * @Description: TODO获取某个用户下的所有bucket
	 * @param userkey
	 * @param request
	 * @return
	 * @throws Exception
	 * @return: List<MyBucket>
	 */
	@RequestMapping("getbucketnames")
	public @ResponseBody List<MyBucket> getBucketNames(HttpServletRequest request) throws Exception {
		myBuckets.clear();
		String username = request.getParameter("username");
		String key = PropertiesOperatator.GetValueByKey(
				this.getClass().getClassLoader().getResource("").getPath() + "conf/userkey.properties", username);
		awsCredentials = new AWSCredentials(key.split(" ")[0], key.split(" ")[1]);
		s3Service = new RestS3Service(awsCredentials);
		for (S3Bucket s3bucket : s3Service.listAllBuckets()) {
			String bucketname = s3bucket.getName();
			if(!bucketname.equals("datacollector_"+username)){
				myBuckets.add(new MyBucket(bucketname, null, null));
			}
		}
		return myBuckets;
	}
	
	@RequestMapping("getbucketnamesdb")
	public @ResponseBody List<MyS3Object> getBucketNamesDB(HttpServletRequest request) throws Exception {
		String username = request.getParameter("username");
		int userid = userAccountService.findIdByName(username);
		return s3ObjectService.findBuckets(userid);
	}
	
	@RequestMapping("getobjectnames")
	public @ResponseBody List<MyS3Object> getObjectNames(HttpServletRequest request) throws Exception {
		String username = request.getParameter("username");
		String bucketName = request.getParameter("bucketName");
		int userid = userAccountService.findIdByName(username);
		return s3ObjectService.findObjects(userid, bucketName);
	}
	
	@RequestMapping("deleteobject")
	public String deleteobject(@RequestParam("userkey") String userkey, @RequestParam("bucketName") String bucketname,
			@RequestParam("username") String username,
			@RequestParam("objectName") String objectName,HttpServletRequest request) throws Exception {
		awsCredentials = new AWSCredentials(userkey.split(" ")[0].trim(), userkey.split(" ")[1].trim());
		s3Service = new RestS3Service(awsCredentials);
		try {//////////////首先判断存储桶是否为空，不为空就先删除桶内对象
			s3Service.deleteObject(bucketname, objectName);
			int userid = userAccountService.findIdByName(username);
			s3ObjectService.deleteObject(userid, bucketname, objectName);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", e.getMessage());
			return "s3/filemanagement";
		}
		request.setAttribute("result", "对象删除成功");
		return "s3/filemanagement";
	}
}
