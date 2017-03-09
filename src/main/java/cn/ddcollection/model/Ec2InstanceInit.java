package cn.ddcollection.model;

import java.io.Serializable;
import java.util.Collection;

import com.amazonaws.services.ec2.model.IamInstanceProfileSpecification;

/**
 * @author 作者 E-mail:
 * @date 创建时间：2016年2月29日 下午7:10:56
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class Ec2InstanceInit implements Serializable {

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
	// IAM角色
	private IamInstanceProfileSpecification iamInstanceProfile;
	// 安全组
	private Collection<String> securityGroups;
	// keyName，生成.pom文件
	private String keyName;
	// iam
	private String username;

	// 静态内部类实现单例模式
	private static class LazyHolder {
		private static final Ec2InstanceInit EC2_INSTANCE_INIT = new Ec2InstanceInit();
	}

	private Ec2InstanceInit() {
	}

	public static final Ec2InstanceInit getEc2InstanceInit() {
		return LazyHolder.EC2_INSTANCE_INIT;
	}

	private Ec2InstanceInit(IamInstanceProfileSpecification iamInstanceProfile, Collection<String> securityGroups,
			String keyName, String username) {
		super();
		this.iamInstanceProfile = iamInstanceProfile;
		this.securityGroups = securityGroups;
		this.keyName = keyName;
		this.username = username;
	}

	public IamInstanceProfileSpecification getIamInstanceProfile() {
		return iamInstanceProfile;
	}

	public void setIamInstanceProfile(IamInstanceProfileSpecification iamInstanceProfile) {
		this.iamInstanceProfile = iamInstanceProfile;
	}

	public Collection<String> getSecurityGroups() {
		return securityGroups;
	}

	public void setSecurityGroups(Collection<String> securityGroups) {
		this.securityGroups = securityGroups;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
