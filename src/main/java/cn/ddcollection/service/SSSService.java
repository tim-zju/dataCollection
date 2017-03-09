package cn.ddcollection.service;

import org.jets3t.service.S3Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author 作者 E-mail:
 * @date 创建时间：2016年3月6日 上午9:27:55
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public interface SSSService {
	/**
	 * 
	 * @Title: uploadObject
	 * @Description: TODO单线程简单上传Object到S3 Bucket中
	 * @param file
	 * @param filename
	 * @param path
	 * @param s3Service
	 * @param bucketName
	 * @return: void
	 */
	public Boolean uploadObject(CommonsMultipartFile file, String filename, String path, S3Service s3Service,
			String bucketName);

	/**
	 * 
	 * @Title: downloadObject
	 * @Description: TODO单线程简单下载Object到本地
	 * @param bucketName
	 * @param objectName
	 * @param s3Service
	 * @return: void
	 */
	public String downloadObject(String bucketName, String localpath, String objectName, S3Service s3Service) throws Exception;
	
	public void downloadFolder(String bucketName,String folderName,String localPath,S3Service s3Service) throws Exception;

}
