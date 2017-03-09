package cn.ddcollection.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.http.util.EncodingUtils;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.ServiceException;
import org.jets3t.service.model.S3Object;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.ddcollection.service.SSSService;

/**
 * @author 作者 E-mail:
 * @date 创建时间：2016年3月6日 上午9:33:24
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
@Service("sssService")
public class SSSServiceImpl implements SSSService {

	
	public Boolean uploadObject(CommonsMultipartFile file, String filename, String path, S3Service s3Service,
			String bucketName) {
		File destFile = new File(path);
		try {
			// FileUtils.copyInputStreamToFile()这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
			FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);// 复制临时文件到指定目录下
			// start upload file to S3
//			File fileData = new File(path);
			// Create an empty object with a key/name, and print the object’s details.
			S3Object fileObject = new S3Object(destFile);
			//System.out.println("S3Object before upload: " + fileObject);
			// Upload the object to our test bucket in S3.
			s3Service.putObject(bucketName, fileObject);
			// Print the details about the uploaded object, which contains more information.  
			//System.out.println("S3Object after upload: " + fileObject);
			return true;
		} catch (IOException | NoSuchAlgorithmException | S3ServiceException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String downloadObject(String bucketName, String localpath, String objectName, S3Service s3Service) throws Exception{
		if(localpath.charAt(localpath.length()-1)!='/'){
			localpath+="/";
		}
		File dir = new File(localpath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		String filename = objectName;
		if(objectName.lastIndexOf("/")!=-1&&objectName.contains(".")){//////////////////如果是文件夹中的文件
			filename = objectName.substring(objectName.lastIndexOf("/")+1);
		}
//		System.out.println(localpath+filename);
		FileWriter fw = new FileWriter(localpath+filename);
		BufferedWriter bw = new BufferedWriter(fw);
		// Retrieve the HEAD of the data object we created previously.
		S3Object objectComplete = s3Service.getObject(bucketName, objectName);
		System.out.println("S3Object, details only: " + objectComplete);
		// Read the data from the object's DataInputStream using a loop,and
		// print it out
		BufferedReader reader = new BufferedReader(new InputStreamReader(objectComplete.getDataInputStream(),"utf-8"));
		String data = null;
		while ((data = reader.readLine()) != null) {
			System.out.println(data);
			bw.write(new String(data.getBytes())+"\n");
		}
		bw.close();
		fw.close();
		reader.close();
		return filename;
	}
	
	/////////下载某个文件夹及下面的所有文件和文件夹 首先创建文件夹(需要除了文件名的全路径,即下面的path)，然后下载文件，即写数据到本地文件中（写的时候会自动创建文件）
	public void downloadFolder(String bucketName,String folderName,String localPath,S3Service s3Service) throws Exception{
		List<String> list = new ArrayList<String>();	
		//////首先获取包含有 所需文件夹名字 的对象，让后面去下载
		for(S3Object object:s3Service.listObjects(bucketName)){
			String fullName = object.getName();///////fullName是某个桶下，文件的全路径，例如outdir/output.txt
			if(fullName.contains(folderName)&&fullName.contains(".")){/////////////排除S3存储桶中的文件夹
				list.add(fullName);
	//			System.out.println(object.getName());
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
	//		System.out.println(localPath+fullName);
			String fileName = localPath+fullName;/////////////////系统全路径名
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(fw);
			/////注意，不能直接通过list.get(i)来获得对象，因为这个s3Service.listObjects(bucketName)返回的每个对象只包含基本的信息，没有数据流
			S3Object objectComplete = s3Service.getObject(bucketName, fullName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(objectComplete.getDataInputStream(),"utf-8"));
	//		BufferedReader reader = new BufferedReader(new InputStreamReader(list.get(i).getDataInputStream()));
			String data = null;
			while ((data = reader.readLine()) != null) {
				bw.write(new String(data.getBytes())+"\n");
			}
			bw.close();
			fw.close();
			reader.close();
		}
	}

}
