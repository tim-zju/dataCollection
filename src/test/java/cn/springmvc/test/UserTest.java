package cn.springmvc.test;


import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.ddcollection.model.User;
import cn.ddcollection.service.UserService;

public class UserTest {

	private UserService userService;
	//private static BeanCopier beancopy=BeanCopier.create(null, null, false);
	@SuppressWarnings("resource")
	@Before
	public void before(){
		  ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:conf/spring.xml"
	                ,"classpath:conf/spring-mybatis.xml"});
	        userService = (UserService) context.getBean("userService");
	}
	@Test
	public void testAddUser(){
		User user=new User();
		user.setNickname("wwww");
		user.setState(2);
		System.out.println(userService.insertUser(user));
	}
}
