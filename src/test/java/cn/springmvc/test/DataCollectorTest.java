package cn.springmvc.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.ddcollection.model.DataCollector;
import cn.ddcollection.service.DataCollectorService;

public class DataCollectorTest {
	private DataCollectorService dataCollectorService;
	//private static BeanCopier beancopy=BeanCopier.create(null, null, false);
	@SuppressWarnings("resource")
	@Before
	public void before(){
		  ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:conf/spring.xml"
	                ,"classpath:conf/spring-mybatis.xml"});
	        dataCollectorService = (DataCollectorService) context.getBean("dataColloctorService");
	}
	@Test
	public void testAddDataCollector(){
		System.out.println("come");
		DataCollector dataCollector = new DataCollector();
		dataCollector.setName("hello1");
		//add dataCollector
		DataCollector dataCollector3 = new DataCollector();
		dataCollector3.setName("hello3");
		dataCollectorService.insertDataCollector(dataCollector3);
		
		//delete dataCollector
//		dataCollector.setId(3);
//		dataCollectorService.deleteDataCollector(dataCollector);
		
		//updata dataCollector
//		dataCollector.setId(2);
//		dataCollector.setName("hello2");
//		dataCollectorService.updateDataCollector(dataCollector);
		
		//select dataCollector
//		DataCollector dataCollectorFind = dataCollectorService.findDataCollectorByName("hello2");
//		System.out.println(dataCollectorFind);
//		System.out.println(dataCollectorFind.getName());
		
		System.out.println("success");
	}
}
