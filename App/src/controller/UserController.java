package controller;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import service.UserService;
import entity.AppCategory;
import entity.AppInfo;
import entity.AppVersion;
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
		String flatformId = request.getParameter("queryFlatformId");
		String categoryLevel1 = request.getParameter("queryCategoryLevel1");
		String categoryLevel2 = request.getParameter("queryCategoryLevel2");
		String categoryLevel3 = request.getParameter("queryCategoryLevel3");
		Page page = new Page();
		if(currentPageNo == null){
			page.setCurrentPageNo(1);
		}else{
			page.setCurrentPageNo(Integer.valueOf(currentPageNo));
		}
		Map<String, Object> map = new HashMap<>();
		map.put("softwareName", softwareName);
		map.put("flatformId", flatformId);
		map.put("categoryLevel1", categoryLevel1);
		map.put("categoryLevel2", categoryLevel2);
		map.put("categoryLevel3", categoryLevel3);
		map.put("pageIndex",(page.getCurrentPageNo()-1)*5);
		
		int totalCount = userService.count(map);
		int totalPageCount = totalCount%5==0?totalCount/5:totalCount/5+1;
		page.setTotalCount(totalCount);
		page.setTotalPageCount(totalPageCount);
		
		List<AppCategory> list=userService.fenlei(null);
		List<AppInfo> listApp = userService.queryApp(map);
		List<DataDictionary> listPt = userService.queryPt();
		List<AppCategory> lister =userService.queryEr();
		List<AppCategory> listsan =userService.querySan();
		request.setAttribute("categoryLevel2List", lister);
		request.setAttribute("categoryLevel3List", listsan);
		request.setAttribute("appInfoList",listApp);
		request.setAttribute("flatFormList",listPt);
		request.setAttribute("categoryLevel1List",list);
		request.setAttribute("querySoftwareName", softwareName);
		request.setAttribute("queryFlatformId", flatformId);
		request.setAttribute("queryCategoryLevel1", categoryLevel1);
		request.setAttribute("queryCategoryLevel2", categoryLevel2);
		request.setAttribute("queryCategoryLevel3", categoryLevel3);
		request.setAttribute("totalPageCount", page.getTotalPageCount());
		request.setAttribute("currentPageNo", page.getCurrentPageNo());
		request.setAttribute("totalCount", page.getTotalCount());
		request.setAttribute("pages", page);

		return "backend/applist";
	}
	//返回App维护页面
	@RequestMapping("/dev/flatform/app/list")
	public String appinfolist(HttpServletRequest request){
		String currentPageNo = request.getParameter("pageIndex");
		String softwareName = request.getParameter("querySoftwareName");
		String FlatformId = request.getParameter("queryFlatformId");
		String Status = request.getParameter("queryStatus");
		String categoryLevel1 = request.getParameter("queryCategoryLevel1");
		String categoryLevel2 = request.getParameter("queryCategoryLevel2");
		String categoryLevel3 = request.getParameter("queryCategoryLevel3");

		Page page = new Page();
		if(currentPageNo == null){
			page.setCurrentPageNo(1);
		}else{
			page.setCurrentPageNo(Integer.valueOf(currentPageNo));
		}
		Map<String, Object> map = new HashMap<>();
		map.put("softwareName", softwareName);
		map.put("flatformId", FlatformId);
		map.put("STATUS", Status);
		map.put("categoryLevel1", categoryLevel1);
		map.put("categoryLevel2", categoryLevel2);
		map.put("categoryLevel3", categoryLevel3);
		map.put("pageIndex",(page.getCurrentPageNo()-1)*5);
		
		int totalCount = userService.count(map);
		int totalPageCount = totalCount%5==0?totalCount/5:totalCount/5+1;
		page.setTotalCount(totalCount);
		page.setTotalPageCount(totalPageCount);
		
		List<AppCategory> list=userService.fenlei(null);
		List<AppInfo> listApp = userService.queryApp(map);
		List<DataDictionary> listPt = userService.queryPt();
		List<DataDictionary> listZt = userService.queryZt();
		List<AppCategory> lister =userService.queryEr();
		List<AppCategory> listsan =userService.querySan();
		request.setAttribute("categoryLevel2List", lister);
		request.setAttribute("categoryLevel3List", listsan);
		request.setAttribute("appInfoList",listApp);
		request.setAttribute("flatFormList",listPt);
		request.setAttribute("statusList",listZt);
		request.setAttribute("categoryLevel1List",list);
		request.setAttribute("querySoftwareName", softwareName);
		request.setAttribute("queryFlatformId", FlatformId);
		request.setAttribute("queryStatus", Status);
		request.setAttribute("queryCategoryLevel1", categoryLevel1);
		request.setAttribute("queryCategoryLevel2", categoryLevel2);
		request.setAttribute("queryCategoryLevel3", categoryLevel3);
		request.setAttribute("totalPageCount", page.getTotalPageCount());
		request.setAttribute("currentPageNo", page.getCurrentPageNo());
		request.setAttribute("totalCount", page.getTotalCount());
		request.setAttribute("pages", page);
		
		return "developer/appinfolist";
	}
	//返回App审核页面
	@RequestMapping("/manager/backend/app/check")
	public String check(HttpServletRequest request){
		String id = request.getParameter("aid");
		AppInfo appinfo = userService.queryID(id);
		List<AppVersion> appVersion = userService.queryBb(id);
		request.setAttribute("appInfo", appinfo);
		request.setAttribute("appVersion", appVersion);
		return "backend/appcheck";
	}
	//App审核
	@RequestMapping("/manager/backend/app/checksave")
	public String checksave(AppInfo appInfo,HttpServletRequest request){
		userService.update(appInfo);
		applist(request);
		return "backend/applist";
	}
	//返回查看App信息页面
	@RequestMapping("/dev/flatform/app/appview")
	public String appinfoview(HttpServletRequest request){
		String id = request.getParameter("id");
		AppInfo appinfo = userService.queryID(id);
		List<AppVersion> appVersion = userService.queryBb(id);
		request.setAttribute("appInfo", appinfo);
		request.setAttribute("appVersionList", appVersion);
		return "developer/appinfoview";
	}
	//返回新增App版本信息页面
	@RequestMapping("dev/flatform/app/appversionadd")
	public String appversionadd(HttpServletRequest request){
		String id = request.getParameter("id");
		AppInfo appinfo = userService.queryID(id);
		List<AppVersion> appVersion = userService.queryBb(id);
		request.setAttribute("appInfo", appinfo);
		request.setAttribute("appVersionList", appVersion);
		return "developer/appversionadd";
	}
	//删除App信息
	@RequestMapping("/delapp.json")
	public void del(@RequestParam(value="id",required=false)int id,HttpServletResponse response) throws IOException{
		PrintWriter out=response.getWriter();
		int i = userService.delInfo(id);
		if(i > 0){
			userService.delVersion(id);
			out.println("{\"delResult\":\"true\"}");
		}else{
			out.println("{\"delResult\":\"false\"}");
		}
		//return JSON.toJSONString(delResult);
	}
	//新增App版本信息
	@RequestMapping("/dev/flatform/app/addversionsave")
	public String Add(AppVersion appVersion,HttpServletRequest request,@RequestParam(value="a_downloadLink",required=false)MultipartFile attach){
//		String idPicPath=null;
//		//判断文件是否为空
//		if(!attach.isEmpty()){
//			String path=request.getSession().getServletContext().getRealPath("/statics/images");
//			System.out.println("路径"+path);
//			String oldFileName=attach.getOriginalFilename();//原文件名
//			System.out.println("原文件名"+oldFileName);
//			String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀
//			System.out.println("原文件后缀名"+prefix);
//			int filesize=104857600;
//			System.out.println("上传文件大小"+attach.getSize());
//			if(attach.getSize()>filesize){
//				request.setAttribute("uploadFileError", "上传文件大小不得超过100MB");
//				return "index";
//			}
//			File targetFile=new File(path,oldFileName);
//			if(!targetFile.exists()){
//				targetFile.mkdirs(); //如果文件夹不存在，就新建
//			}
//			//保存
//			try {
//				attach.transferTo(targetFile);
//			} catch (Exception e) {
//				e.printStackTrace();
//				request.setAttribute("uploadFileError", "上传失败！");
//				return "index";
//			}
//			idPicPath=path+File.pathSeparator+oldFileName;	
//		}	
		System.out.println(1111111111);
//		System.out.println(appVersion.getVersionNo());
//		int i = userService.Add(appVersion);
		return "developer/appinfolist";
	}
	
	//ajax下拉列表分类
	@ResponseBody
	@RequestMapping("/categorylevellist.json")
	public String fenlei(@RequestParam(value="pid",required=false)Integer pid){
		//String pid=request.getParameter("pid");
		//System.out.println(pid);
		//这里写查询方法
		//Integer id=null;
		
		//if(pid!=null){
		//id=Integer.valueOf(pid);
		//}
		List<AppCategory> list=userService.fenlei(pid);
		
		//返回json格式
		System.out.println(JSON.toJSONString(list));
		return JSON.toJSONString(list);
	}
	//返回新增App基础信息页面
	@RequestMapping("/dev/flatform/app/appinfoadd")
	public String appinfoadd(){
		return "developer/appinfoadd";
	}
	//所属平台ajax
	@ResponseBody
	 @RequestMapping("/datadictionarylist.json")
	public String pingtai(){	 
		 List<DataDictionary> listPt = userService.queryPt();
		 return JSON.toJSONString(listPt);
	}
	
	//新增App基础信息
	@RequestMapping("/dev/flatform/app/appinfoaddsave")
	public String appinfoaddsave(AppInfo appinfo,HttpServletRequest request,HttpSession session,@RequestParam(value="a_logoPicPath",required=false)MultipartFile attach){
			String idPicPath=null;
			//判断文件是否为空
			//appinfo.setDevId(((DevUser)session.getAttribute("devUserSession")).getId());
			//appinfo.setCreatedBy(((DevUser)session.getAttribute("devUserSession")).getId());
		//	appinfo.setDevld(1);//测试代码
			System.out.println(appinfo.getAPKName());
			if(!attach.isEmpty()){
				String path=request.getSession().getServletContext().getRealPath("statics//images");
				System.out.println("路径"+path);
				String oldFileName=attach.getOriginalFilename();//原文件名
				System.out.println("原文件名"+oldFileName);
				String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀
				System.out.println("原文件后缀名"+prefix);
				appinfo.setLogoLocPath(path+"//"+oldFileName);
				appinfo.setLogoPicPath("/App/statics/images/"+oldFileName);
				int filesize=200000;
				System.out.println("上传文件大小"+attach.getSize());
				if(attach.getSize()>filesize){
					request.setAttribute("uploadFileError", "上传文件大小不得超过200kb");
					return "index";
				}
				File targetFile=new File(path,oldFileName);
				if(!targetFile.exists()){
					targetFile.mkdirs(); //如果文件夹不存在，就新建
				}
				//保存
				try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					request.setAttribute("uploadFileError", "上传失败！");
					return "index";
				}
				idPicPath=path+File.pathSeparator+oldFileName;	
			}	
			Integer i=userService.addappinfo(appinfo);
			appinfolist(request);
		 return "developer/appinfolist";
	 }

	//验证Apk名称唯一性
	 @ResponseBody
	 @RequestMapping("/apkexist.json")
	 public String apkexist(HttpServletRequest request){
		 String APKName=request.getParameter("APKName");
		 Map<String, String> map=new HashMap<>();
		 if(APKName==null||APKName==""){
			 map.put("APKName","empty");
			 return JSON.toJSONString(map);
		}
		 AppInfo appinfo=userService.findappinfo(APKName);
		 if(appinfo!=null){
			 map.put("APKName","exist");
			 return JSON.toJSONString(map);
		 }else{
			 map.put("APKName","noexist");
		 }
		 return JSON.toJSONString(map);
	 }
}
