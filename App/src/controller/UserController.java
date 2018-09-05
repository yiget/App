package controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import service.UserService;

import dao.UserDao;
import entity.AppInfo;
import entity.BackendUser;
import entity.DevUser;


import service.UserService;


@Controller
public class UserController {
	@Autowired
	private UserService userService;
	//返回后台登录页面
	@RequestMapping("/manager/login")
	public String login(){
		return "backendlogin";	
	}
	//返回前台登录页面
	@RequestMapping("/dev/login")
	public String login2(){
		return "devlogin";	
	}
	//后台登录方法
	@RequestMapping("/manager/dologin")
	public String dologin(HttpServletRequest request,HttpSession session){
		String userCode=request.getParameter("userCode");	
		String userPassword=request.getParameter("userPassword");
		Map<String, String> map=new HashMap<>();
		map.put("code", userCode);
		//这里执行查询方法
		BackendUser user=userService.findCode(map);
		if(user==null){
			request.setAttribute("error", "用户名错误");
			return "backendlogin"; //返回页面，提示错误信息
		}
		map.put("password", userPassword);
		//这里再次执行查询方法
		user=userService.findCode(map);
		if(user==null){
			request.setAttribute("error", "密码错误");
			return "backendlogin"; //返回页面，提示错误信息
		}
		session.setAttribute("userSession", user);
		return "backend/main";
	}
	//前台登录方法
	@RequestMapping("/dev/dologin2")
	public String dologin2(HttpServletRequest request,HttpSession session){
		String userCode=request.getParameter("devCode");	
		String userPassword=request.getParameter("devPassword");
		Map<String, String> map=new HashMap<>();
		map.put("code", userCode);
		//这里执行查询方法
		DevUser user=userService.findUser(map);
		if(user==null){
			request.setAttribute("error", "用户名错误");
			return "devlogin"; //返回页面，提示错误信息
		}
		map.put("password", userPassword);
		//这里再次执行查询方法
		user=userService.findUser(map);
		if(user==null){
			request.setAttribute("error", "密码错误");
			return "devlogin"; //返回页面，提示错误信息
		}
		session.setAttribute("devUserSession", user);
		return "developer/main";
	}

	//返回App审核页面
	@RequestMapping("/manager/backend/app/list")
	public String applist(HttpServletRequest request){
		Map<String, Object> map = new HashMap<>();
		List<AppInfo> list = userService.queryApp(map);
		request.setAttribute("appInfoList", list);
		return "backend/applist";
	}
}
