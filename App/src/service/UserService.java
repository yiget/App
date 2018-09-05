package service;

import java.util.List;
import java.util.Map;

import entity.AppInfo;
import entity.BackendUser;
import entity.DevUser;

public interface UserService {
	//后台登录
	public BackendUser findCode(Map<String, String> map);
	//前台登录
	public DevUser findUser(Map<String, String> map);
	//查询所有APP信息
	public List<AppInfo> queryApp(Map<String, Object> map);
}
