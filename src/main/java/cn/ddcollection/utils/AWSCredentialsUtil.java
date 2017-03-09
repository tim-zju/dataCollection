package cn.ddcollection.utils;

import com.amazonaws.auth.BasicAWSCredentials;

/**
 * @author 作者 E-mail:
 * @date 创建时间：2016年2月28日 下午10:48:20
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class AWSCredentialsUtil {
	/**
	 * 
	 * @Title: getBasicAWSCredentials
	 * @Description: 获取BasicAWSCredentials
	 * @param awsAccessKey
	 * @param awsSecretAccessKey
	 * @return
	 * @return: BasicAWSCredentials
	 */
	public static BasicAWSCredentials getBasicAWSCredentials(String awsAccessKey, String awsSecretAccessKey) {
		BasicAWSCredentials credentials = new BasicAWSCredentials(awsAccessKey, awsSecretAccessKey);
		return credentials;
	}
}
