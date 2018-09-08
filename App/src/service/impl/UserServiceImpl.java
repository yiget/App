package service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.UserDao;
import entity.AppCategory;
import entity.AppInfo;
import entity.AppVersion;
import entity.BackendUser;
import entity.DataDictionary;
import entity.DevUser;
import service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;

	//后台登录
	public BackendUser findCode(Map<String, String> map) {
		return userDao.findCode(map);
	}

	//前台登录
	public DevUser findUser(Map<String, String> map) {
		return userDao.findUser(map);
	}

	//查询所有APP信息
	public List<AppInfo> queryApp(Map<String, Object> map) {
		return userDao.queryApp(map);
	}
	//查询APP信息表总页数
	public int count(Map<String, Object> map) {
		return userDao.count(map);
	}
	
	//查询所属平台
	public List<DataDictionary> queryPt(){
		return userDao.queryPt();
	}

	//查询APP状态
	public List<DataDictionary> queryZt() {
		return userDao.queryZt();
	}
	
	//根据ID查看APP信息
	public AppInfo queryID(String id){
		return userDao.queryID(id);
	}
	
	//新增App版本信息
	public int Add(AppVersion appVersion){
		return userDao.Add(appVersion);
	}
	//根据ID查询历史版本
	public List<AppVersion> queryBb(String id){
		return userDao.queryBb(id);
	}
	//App审核
	public int update(AppInfo appInfo){
		return userDao.update(appInfo);
	}
	//删除App信息
	public int delInfo(int id){
		return userDao.delInfo(id);
	}
	//删除App版本信息
	public int delVersion(int id){
		return userDao.delVersion(id);
	}
	//根据父级id查询分类列表
	public List<AppCategory> fenlei(Integer id) {		
		return userDao.fenlei(id);
	}
	//查询二级分类
	public List<AppCategory> queryEr(){
		return userDao.queryEr();
	}
	//查询三级分类
	public List<AppCategory> querySan(){
		return userDao.querySan();
	}
	//新增app信息
	public Integer addappinfo(AppInfo appinfo){
		return userDao.addappinfo(appinfo);
	}
	//查询app名称
	public  AppInfo findappinfo(String apkname){
		return userDao.findappinfo(apkname);
	}
}
