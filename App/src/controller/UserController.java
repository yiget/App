package controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import service.UserService;
import entity.AppCategory;
import entity.AppInfo;
import entity.BackendUser;
import entity.DataDictionary;
import entity.DevUser;
import entity.Page;



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

		
		String currentPageNo = request.getParameter("pageIndex");
		String softwareName = request.getParameter("querySoftwareName");

		Page page = new Page();
		if(currentPageNo == null){
			page.setCurrentPageNo(1);
		}else{
			page.setCurrentPageNo(Integer.valueOf(currentPageNo));
		}
		Map<String, Object> map = new HashMap<>();
		map.put("softwareName", softwareName);
		map.put("pageIndex",(page.getCurrentPageNo()-1)*5);
		
		int totalCount = userService.count(map);
		int totalPageCount = totalCount%5==0?totalCount/5:totalCount/5+1;
		page.setTotalCount(totalCount);
		page.setTotalPageCount(totalPageCount);
		
		List<AppInfo> listApp = userService.queryApp(map);
		List<DataDictionary> listPt = userService.queryPt();
		request.setAttribute("appInfoList",listApp);
		request.setAttribute("flatFormList",listPt);
		request.setAttribute("querySoftwareName", softwareName);
		request.setAttribute("totalPageCount", page.getTotalPageCount());
		request.setAttribute("currentPageNo", page.getCurrentPageNo());
		request.setAttribute("totalCount", page.getTotalCount());
		request.setAttribute("pages", page);
		
//		List<AppCategory> listYi = userService.queryYi();
//		List<AppCategory> listEr = userService.queryEr();
//		List<AppCategory> listSan = userService.querySan();
//		System.out.println(listYi.size());
//		System.out.println(listEr.size());
//		request.setAttribute("categoryLevel1List", listYi);
//		request.setAttribute("categoryLevel2List", listEr);
//		request.setAttribute("categoryLevel3List", listSan);
		
		return "backend/applist";
	}
	/**
	 * ajax下拉列表分类
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("categorylevellist.json")
	public Object fenlei(HttpServletRequest request){
		String pid=request.getParameter("pid");
		//这里写查询方法
		int id=Integer.valueOf(pid);
		List<AppCategory> list=userService.fenlei(id);
		//返回json格式
		return JSON.toJSON(list);
	}
}
