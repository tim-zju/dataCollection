package cn.ddcollection.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @ClassName: MainUIController
 * @Description: 主界面控制器
 * @author: 大鹤
 * @date: 2016年2月24日 下午9:40:33
 */
@Controller
@RequestMapping("/")
public class MainUIController {
	/**
	 * 
	 * @Title: index
	 * @Description: 跳转到index.jsp
	 * @return
	 * @return: String
	 */
	@RequestMapping("index")
	public String index() {
		return "index";
	}

	/**
	 * 跳转到instance.jsp
	 * 
	 * @Title: runInstance
	 * @Description: TODO
	 * @return
	 * @return: String
	 */
	@RequestMapping("runinstance")
	public String runInstance() {
		return "instance";
	}
}
