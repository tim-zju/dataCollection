package cn.ddcollection.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ddcollection.model.UserAccount;
import cn.ddcollection.service.UserAccountService;

@Controller
@RequestMapping("/useraccount")
public class UserController {

	@Resource(name="userAccountService")
	private UserAccountService userAccountService;

	@RequestMapping("toaccount")
	public String toAccount() {
		return "useraccount/account";
	}
	
	@RequestMapping("toiam")
	public String toIAM() {
		return "useraccount/iam";
	}
	
	@RequestMapping("createuser")
	public String createUser(@RequestParam("username") String username,@RequestParam("AWSAccessKeyId") String AWSAccessKeyId,
			@RequestParam("AWSSecretKey") String AWSSecretKey, HttpServletRequest request) {
		UserAccount u = new UserAccount();
		u.setName(username);
		u.setAWSAccessKeyId(AWSAccessKeyId);
		u.setAWSSecretKey(AWSSecretKey);
		
		try{
			userAccountService.insertUser(u);
		}catch(Exception e){
			request.setAttribute("result", e.getMessage());
			return "useraccount/account";
		}
		request.setAttribute("result", "账户添加成功");
		
		return "useraccount/account";
	}
	
	@RequestMapping("updateaccount")
	public String updateAccount(@RequestParam("accountname") String accountname,@RequestParam("AWSAccessKeyId") String AWSAccessKeyId,
			@RequestParam("AWSSecretKey") String AWSSecretKey,HttpServletRequest request) {
		
		try{
			userAccountService.updateUser(accountname, AWSAccessKeyId, AWSSecretKey);
		}catch(Exception e){
			request.setAttribute("result", e.getMessage());
			return "useraccount/account";
		}
		request.setAttribute("result", "账户更新成功");
		
		return "useraccount/account";
	}
	
	@RequestMapping("addiam")
	public String addIAM(@RequestParam("username") String username,@RequestParam("iam") String iam,HttpServletRequest request) {
		try{
			userAccountService.addIAM(username, iam);
		}catch(Exception e){
			request.setAttribute("result", e.getMessage());
			return "useraccount/account";
		}
		request.setAttribute("result", "iam添加成功");
		return "useraccount/iam";
	}
	
	@RequestMapping("updateiam")
	public String updateIAM(@RequestParam("username") String username,@RequestParam("iam") String iam,HttpServletRequest request) {
		try{
			userAccountService.addIAM(username, iam);
		}catch(Exception e){
			request.setAttribute("result", e.getMessage());
			return "useraccount/iam";
		}
		request.setAttribute("result", "iam更新成功");
		return "useraccount/iam";
	}
	
	@RequestMapping("deleteiam")
	public String deleteIAM(@RequestParam("username") String username,HttpServletRequest request) {
		try{
			userAccountService.addIAM(username, "");
		}catch(Exception e){
			request.setAttribute("result", e.getMessage());
			return "useraccount/iam";
		}
		request.setAttribute("result", "iam删除成功");
		return "useraccount/iam";
	}
	
	@RequestMapping("getnamelist")
	public @ResponseBody List<UserAccount> getNameList() {
		return userAccountService.findNames();
	}

	

}
