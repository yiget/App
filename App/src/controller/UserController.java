package controller;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.sun.org.apache.regexp.internal.recompile;

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
	//注销后台
	@RequestMapping("/manager/dologinman")
	public String loginman(HttpSession session){
		session.removeAttribute("userSession");
		return "backendlogin";
	}
	//注销前台
	@RequestMapping("/dev/dologindev")
	public String logindev(HttpSession session){
		session.removeAttribute("devUserSession");
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
		request.setAttribute("appVersionList", appVersion);
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
		List<AppVersion> appVersion1 = userService.queryBb(id);
		AppVersion appVersion=new AppVersion();
		appVersion.setAppId(Integer.valueOf(id));
		if(appVersion1.size()>0){
			appVersion=appVersion1.get(0);
		}
		request.setAttribute("appVersionList", appVersion1);
		request.setAttribute("appVersion", appVersion);
		return "developer/appversionadd";
	}
	//返回修改App版本信息页面
	@RequestMapping("dev/flatform/app/appversionmodify")
	public String appversionmodify(HttpServletRequest request){
		String aid = request.getParameter("aid");
		String vid = request.getParameter("vid");
		List<AppVersion> appVersion = userService.queryBb(aid);
		AppVersion appVersion1 = userService.queryBbid(vid);
		request.setAttribute("appVersionList", appVersion);
		request.setAttribute("appVersion", appVersion1);
		return "developer/appversionmodify";
	}
	//修改App版本信息
	@RequestMapping("dev/flatform/app/appversionmodifysave")
	public String appversionmodifysave(AppVersion appVersion,HttpServletRequest request,HttpSession session,@RequestParam(value="attach",required=false)MultipartFile attach){
		String idPicPath=null;
//		appVersion.setDownloadLink(null);
//		appVersion.setApkLocPath(null);
//		appVersion.setApkFileName(null);
		//更新者、创建人、创建时间
		Integer id = Integer.valueOf(request.getParameter("id"));
		appVersion.setId(id);
		DevUser devUser = (DevUser)request.getSession().getAttribute("devUserSession");
		appVersion.setModifyBy(devUser.getId());
		Date date=new Date();
		appVersion.setModifyDate(new java.sql.Date(date.getTime()));
		//判断文件是否为空
		if(!attach.isEmpty()){
			String path=request.getSession().getServletContext().getRealPath("statics//uploadfiles");
			String oldFileName=attach.getOriginalFilename();//原文件名
			String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀
			if(!prefix.equals("apk")){
				request.setAttribute("fileUploadError", "上传失败，文件类型必须是APK类型！");
				List<AppVersion> appVersion1 = userService.queryBb(String.valueOf(appVersion.getAppId()));
				AppVersion appVersion2 = userService.queryBbid(String.valueOf(appVersion.getId()));
				request.setAttribute("appVersionList", appVersion1);
				request.setAttribute("appVersion", appVersion2);
				return "developer/appversionmodify";
			}
			appVersion.setDownloadLink("/App/statics/uploadfiles/"+oldFileName);
			appVersion.setApkLocPath(path+"//"+oldFileName);
			appVersion.setApkFileName(oldFileName);
			File targetFile=new File(path,oldFileName);
			if(!targetFile.exists()){
				targetFile.mkdirs(); //如果文件夹不存在，就新建
			}
			//保存
			try {
				attach.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("uploadFileError", "上传失败！");
			}
			idPicPath=path+File.pathSeparator+oldFileName;	
		}	
		int i=userService.updateApp(appVersion);
		appinfolist(request);
		return "developer/appinfolist";
	}
	//删除修改App版本里的Apk文件
	@RequestMapping("/delfile.json")
	public void delete(HttpServletResponse response) throws IOException{
		PrintWriter out=response.getWriter();
		out.println("{\"result\":\"success\"}");
	}
	//删除App信息
	@RequestMapping("/delapp.json")
	public void del(@RequestParam(value="id",required=false)int id,HttpServletResponse response) throws IOException{
		PrintWriter out=response.getWriter();
		int i = userService.delInfo(id);
		if(i > 0){
			//删除对应的版本信息
			userService.delVersion(id);
			out.println("{\"delResult\":\"true\"}");
		}else{
			out.println("{\"delResult\":\"false\"}");
		}
	}
	//App上下架
	@ResponseBody
	@RequestMapping("/sale.json")
	public String aaa(HttpServletRequest request) {
		Map<String,Object> map = new HashMap<>();
		Integer id = Integer.valueOf(request.getParameter("id"));
		String obj = request.getParameter("obj");
		map.put("errorCode", "0");
		map.put("resultMsg", "success");
		if(obj.equals("open")){
			//上架
			AppInfo appInfo = new AppInfo();
			appInfo.setId(id);
			appInfo.setStatus(4);
			userService.updateSxj(appInfo);
		}else if(obj.equals("close")){
			//下架
			AppInfo appInfo = new AppInfo();
			appInfo.setId(id);
			appInfo.setStatus(5);
			userService.updateSxj(appInfo);
		}else{
			map.put("resultMsg", "failed");
		}
		return JSON.toJSONString(map);
	}
	//新增App版本信息
	@RequestMapping("/dev/flatform/app/addversionsave")
	public String Add(AppVersion appVersion,HttpServletRequest request,HttpSession session,@RequestParam(value="a_downloadLink",required=false)MultipartFile attach){
		String idPicPath=null;
		Date date=new Date();
		appVersion.setCreationDate(new java.sql.Date(date.getTime()));
		appVersion.setCreatedBy(((DevUser)session.getAttribute("devUserSession")).getId());
		if(!attach.isEmpty()){
			String path=request.getSession().getServletContext().getRealPath("statics//uploadfiles");
			String oldFileName=attach.getOriginalFilename();//原文件名
			String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀
			appVersion.setDownloadLink("/App/statics/uploadfiles/"+oldFileName);
			appVersion.setApkLocPath(path+"//"+oldFileName);
			appVersion.setApkFileName(oldFileName);
			File targetFile=new File(path,oldFileName);
			if(!prefix.equals("apk")){
				request.setAttribute("fileUploadError", "上传失败，文件类型必须是APK类型！");
				List<AppVersion> appVersion1 = userService.queryBb(String.valueOf(appVersion.getAppId()));
				request.setAttribute("appVersionList", appVersion1);
				return "developer/appversionadd";
			}
			if(!targetFile.exists()){
				targetFile.mkdirs(); //如果文件夹不存在，就新建
			}
			//保存
			try {
				attach.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("uploadFileError", "上传失败！");
				return "index";
			}
			idPicPath=path+File.pathSeparator+oldFileName;	
		}	
		int i=userService.Add(appVersion);
		if(i>0){
			Map<String, Integer> map=new HashMap<>();
			map.put("versionId", appVersion.getId());
			map.put("appId", appVersion.getAppId());
			int y=userService.updateAppinfo(map);
		}
		appinfolist(request);
		return "developer/appinfolist";
	}
	
	//根据id返回app基础信息
	@RequestMapping("/dev/flatform/app/appinfomodify")
	public String appInfo(@RequestParam(value="id",required=false)int id,HttpServletRequest request,HttpSession session){
		AppInfo ai=userService.queryInfo(id);
		request.setAttribute("appInfo", ai);
		return "developer/appinfomodify";	
	}
	//修改基础信息
	@RequestMapping("/dev/flatform/app/appinfomodifysave")
	public String updateAppInfo(AppInfo appinfo,HttpServletRequest request,HttpSession session,@RequestParam(value="attach",required=false)MultipartFile attach) {
		String idPicPath=null;
//		appinfo.setLogoLocPath(null);
//		appinfo.setLogoPicPath(null);
		Date date=new Date();
		appinfo.setModifyDate(new java.sql.Date(date.getTime()));
		appinfo.setModifyBy(((DevUser)session.getAttribute("devUserSession")).getId());
		System.out.println(((DevUser)session.getAttribute("devUserSession")).getId());
		if(!attach.isEmpty()){
			String path=request.getSession().getServletContext().getRealPath("statics//images");
			String oldFileName=attach.getOriginalFilename();//原文件名
			String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀
			if(!prefix.equals("jpg")&&!prefix.equals("png")&&!prefix.equals("jpeg")){
				request.setAttribute("fileUploadError", "文件类型不匹配");
				return "developer/appinfomodify";
			}
			appinfo.setLogoLocPath(path+"//"+oldFileName);
			appinfo.setLogoPicPath("/App/statics/images/"+oldFileName);
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
				request.setAttribute("fileUploadError", "上传失败！");
				return "index";
			}
			idPicPath=path+File.pathSeparator+oldFileName;	
		}	
		int i=userService.updateInfo(appinfo);
		appinfolist(request);
		return "developer/appinfolist";
	}
	//ajax下拉列表分类
	@ResponseBody
	@RequestMapping("/categorylevellist.json")
	public String fenlei(@RequestParam(value="pid",required=false)Integer pid){
		List<AppCategory> list=userService.fenlei(pid);
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
			appinfo.setDevId(((DevUser)session.getAttribute("devUserSession")).getId());
			appinfo.setCreatedBy(((DevUser)session.getAttribute("devUserSession")).getId());
			Date date=new Date();
			appinfo.setCreationDate(new java.sql.Date(date.getTime()));
			if(!attach.isEmpty()){
				String path=request.getSession().getServletContext().getRealPath("statics//images");
				String oldFileName=attach.getOriginalFilename();//原文件名
				String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀
				appinfo.setLogoLocPath(path+"//"+oldFileName);
				appinfo.setLogoPicPath("/App/statics/images/"+oldFileName);
				File targetFile=new File(path,oldFileName);
				if(!prefix.equals("jpg")){
					request.setAttribute("fileUploadError", "上传失败，文件类型必须是JPG类型！");
					return "developer/appinfoadd";
				}
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
