package service;

import java.util.List;
import java.util.Map;

import entity.AppCategory;
import entity.AppInfo;
import entity.AppVersion;
import entity.BackendUser;
import entity.DataDictionary;
import entity.DevUser;

public interface UserService {
	//后台登录
	public BackendUser findCode(Map<String, String> map);
	//前台登录
	public DevUser findUser(Map<String, String> map);
	//查询所有APP信息
	public List<AppInfo> queryApp(Map<String, Object> map);
	//查询APP信息表总页数
	public int count(Map<String,Object> map);
	//查询所属平台
	public List<DataDictionary> queryPt();
	//查询APP状态
	public List<DataDictionary> queryZt();
	//根据ID查看APP信息
	public AppInfo queryID(String id);
	//新增App版本信息
	public int Add(AppVersion appVersion);
	//更新info表的最新版本信息
	public int updateAppinfo(Map<String, Integer> map);
	//根据ID查询所有历史版本
	public List<AppVersion> queryBb(String id);
	//根据ID查询当前版本信息
	public AppVersion queryBbid(String id);
	//修改App版本信息
	public int updateApp(AppVersion appVersion);
	//App审核
	public int update(AppInfo appInfo);
	//删除App信息
	public int delInfo(int id);
	//删除App版本信息
	public int delVersion(int id);
	//App上下架
	public int updateSxj(AppInfo appInfo);
	//根据父级id查询分类列表
	public List<AppCategory> fenlei(Integer id);
	//查询二级分类
	public List<AppCategory> queryEr();
	//查询三级分类
	public List<AppCategory> querySan();
	//新增app信息
	public Integer addappinfo(AppInfo appinfo);
	//查询app名称
	public  AppInfo findappinfo(String apkname);
	//修改info表
	public int updateInfo(AppInfo info);
	//根据条件查找App基础信息
	public AppInfo queryInfo(int id);
}
