package cn.ddcollection.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jets3t.service.S3Service;
import org.jets3t.service.model.S3Object;

import cn.ddcollection.model.DataCollector;
import cn.ddcollection.service.DataCollectorService;
import cn.ddcollection.service.impl.DataCollectorServiceImpl;

/**
 * @author 作者 E-mail:
 * @date 创建时间：2016年2月29日 下午9:17:56
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class Test {
	
	public static void main(String[] args) throws Exception {
//		Ec2Service ec2service = new Ec2ServiceImpl();
//		ec2service.InitAllInstances();
//		String awsAccessKey = "AKIAJF2ZVSWLMSHTBTOA";
//		String awsSecretAccessKey = "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";
//		AWSCredentials awsCredentials = new AWSCredentials(awsAccessKey, awsSecretAccessKey);
//		new RestS3Service(awsCredentials);
//		//new Test().downloadFolder("tim2015-11-29", "indir", "F:学习资料/测试文件/",s3Service);
//		String filename = "F:/学习资料/解决方案.txt";
//		new Test().getUserdataFromFile(filename);
//		Ec2Service ec2service = new Ec2ServiceImpl();
//		Ec2Instance ec2 = new Ec2Instance();
//		ec2.setCreatetime(new Date());
//		ec2.setDcid(1);
//		ec2.setDescription("123");
//		ec2.setEc2name("hello");
//		ec2.setEndpoint("www");
//		ec2.setIam("avc");
//		ec2.setRegion("west");
//		ec2service.insertEc2(ec2);
		DataCollectorService ds = new DataCollectorServiceImpl();
		DataCollector dc = new DataCollector();
		ds.insertDataCollector(dc);
	}
	
	/////////下载某个文件夹及下面的所有文件和文件夹 首先创建文件夹(需要除了文件名的全路径,即下面的path)，然后下载文件，即写数据到本地文件中（写的时候会自动创建文件）
	public void downloadFolder(String bucketName,String folderName,String localPath,S3Service s3Service) throws Exception{
		List<String> list = new ArrayList<String>();	
		//////首先获取包含有 所需文件夹名字 的对象，让后面去下载
		for(S3Object object:s3Service.listObjects(bucketName)){
			String fullName = object.getName();///////fullName是某个桶下，文件的全路径，例如outdir/output.txt
			if(fullName.contains(folderName)){
				list.add(fullName);
//				System.out.println(object.getName());
			}
		}
		for(int i=0;i<list.size();i++){
			String fullName = list.get(i);
			int index = fullName.lastIndexOf("/");
			String path = localPath+fullName.substring(0, index);/////////系统全路径文件夹名
			File file = new File(path);
			if(!file.exists()){
				file.mkdirs();//////级联创建文件夹
			}
//			System.out.println(localPath+fullName);
			String fileName = localPath+fullName;/////////////////系统全路径名
			FileWriter fw = new FileWriter(fileName);
			/////注意，不能直接通过list.get(i)来获得对象，因为这个s3Service.listObjects(bucketName)返回的每个对象只包含基本的信息，没有数据流
			S3Object objectComplete = s3Service.getObject(bucketName, fullName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(objectComplete.getDataInputStream()));
//			BufferedReader reader = new BufferedReader(new InputStreamReader(list.get(i).getDataInputStream()));
			String data = null;
			while ((data = reader.readLine()) != null) {
				fw.write(data);
			}
			fw.close();
			reader.close();
		}
	}
	
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
}
