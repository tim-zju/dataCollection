package cn.ddcollection.model;

import java.util.Collection;
import java.util.Date;

import com.amazonaws.services.ec2.model.IamInstanceProfileSpecification;

/**
 * @author 作者 E-mail:
 * @date 创建时间：2016年2月27日 上午10:20:24
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class Ec2Instance {
	private int id;// 实例ID
	private IamInstanceProfileSpecification iamInstanceProfile;// IAM角色 3
	private String iam;// Iam name
	private String region;// 区域 9
	private String endpoint;// 节点 10
	private String userdata;// 脚本 4
	private String description;// 描述
	private long timeconsuming;// 耗时
	private String state;// 运行状态 12
	private String imageid;// ImageId 1
	private String instancetype;// 实例类型 2
	private int minInstanceCount;// minInstanceCount实例数量 5
	private int maxInstanceCount;// maxInstanceCount实例数量 6
	private Collection<String> securityGroups;// 安全组 7
	private String keyName;// keyname 11
	private String spotprice;// SpotPrice 8
	private Date createtime;// 创建时间
	private int dcid;// 爬虫程序ID
	private String ec2name;// EC2名字，需要手动添加到参数
	private int userid;
	private String ec2id;
	private int isschedule;


	public int getIsschedule() {
		return isschedule;
	}

	public void setIsschedule(int isschedule) {
		this.isschedule = isschedule;
	}

	public String getEc2id() {
		return ec2id;
	}

	public void setEc2id(String ec2id) {
		this.ec2id = ec2id;
	}

	public Ec2Instance() {
		super();
	}

	public Ec2Instance(IamInstanceProfileSpecification iamInstanceProfile, String userdata, String region,
			String description, String imageid, int minInstanceCount, int maxInstanceCount,
			Collection<String> securityGroups) {
		// 需要动态设置的值
		this.iamInstanceProfile = iamInstanceProfile;// IAM角色
		this.region = region;// 区域
		endpoint = "https://" + region + ".ec2.amazonaws.com";// 节点
		this.userdata = userdata;// 脚本
		this.description = description;// 描述
		this.minInstanceCount = minInstanceCount;// minInstanceCount实例数量
		this.maxInstanceCount = maxInstanceCount;// maxInstanceCount实例数量
		this.securityGroups = securityGroups;// 安全组
		// 默认值设置
		this.imageid = imageid;// 需要读取properties文件，在业务层里面进行设置，model不适合做这样的操作
		this.instancetype = "t2.micro";// 实例类型

	}

	public Ec2Instance(int id, IamInstanceProfileSpecification iamInstanceProfile, String iam, String region,
			String endpoint, String userdata, String description, long timeconsuming, String state, String imageid,
			String instancetype, int minInstanceCount, int maxInstanceCount, Collection<String> securityGroups,
			String keyName, String spotprice, Date createtime, int dcid, String ec2name) {
		super();
		this.id = id;
		this.iamInstanceProfile = iamInstanceProfile;
		this.iam = iam;
		this.region = region;
		this.endpoint = endpoint;
		this.userdata = userdata;
		this.description = description;
		this.timeconsuming = timeconsuming;
		this.state = state;
		this.imageid = imageid;
		this.instancetype = instancetype;
		this.minInstanceCount = minInstanceCount;
		this.maxInstanceCount = maxInstanceCount;
		this.securityGroups = securityGroups;
		this.keyName = keyName;
		this.spotprice = spotprice;
		this.createtime = createtime;
		this.dcid = dcid;
		this.ec2name = ec2name;
	}

	public String getEc2name() {
		return ec2name;
	}

	public void setEc2name(String ec2name) {
		this.ec2name = ec2name;
	}

	public String getIam() {
		return iam;
	}

	public void setIam(String iam) {
		this.iam = iam;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public IamInstanceProfileSpecification getIamInstanceProfile() {
		return iamInstanceProfile;
	}

	public void setIamInstanceProfile(IamInstanceProfileSpecification iamInstanceProfile) {
		this.iamInstanceProfile = iamInstanceProfile;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getUserdata() {
		return userdata;
	}

	public void setUserdata(String userdata) {
		this.userdata = userdata;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getTimeconsuming() {
		return timeconsuming;
	}

	public void setTimeconsuming(long timeconsuming) {
		this.timeconsuming = timeconsuming;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getImageid() {
		return imageid;
	}

	public void setImageid(String imageid) {
		this.imageid = imageid;
	}

	public String getInstancetype() {
		return instancetype;
	}

	public void setInstancetype(String instancetype) {
		this.instancetype = instancetype;
	}

	public int getMinInstanceCount() {
		return minInstanceCount;
	}

	public void setMinInstanceCount(int minInstanceCount) {
		this.minInstanceCount = minInstanceCount;
	}

	public int getMaxInstanceCount() {
		return maxInstanceCount;
	}

	public void setMaxInstanceCount(int maxInstanceCount) {
		this.maxInstanceCount = maxInstanceCount;
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

	public String getSpotprice() {
		return spotprice;
	}

	public void setSpotprice(String spotprice) {
		this.spotprice = spotprice;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getDcid() {
		return dcid;
	}

	public void setDcid(int dcid) {
		this.dcid = dcid;
	}

}
