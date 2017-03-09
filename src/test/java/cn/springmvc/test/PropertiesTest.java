package cn.springmvc.test;

import javax.annotation.Resource;

import org.junit.Test;

import cn.ddcollection.service.Ec2Service;
import cn.ddcollection.service.impl.Ec2ServiceImpl;

/**
 * @author 作者 E-mail:
 * @date 创建时间：2016年2月29日 上午11:07:44
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class PropertiesTest {
//	@Resource
	private Ec2Service ec2service=new Ec2ServiceImpl();
	@Test
	public void testWriteListToProperties() {
		ec2service.InitAllInstances();
	}

}
