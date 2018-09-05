
package dao;

import java.util.List;
import java.util.Map;

import entity.AppCategory;
import entity.AppInfo;
import entity.BackendUser;
import entity.DataDictionary;
import entity.DevUser;

public interface UserDao {
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
	//根据父级id查询分类列表
	public List<AppCategory> fenlei(int id);
	
}