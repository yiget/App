package service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.UserDao;
import entity.AppInfo;
import entity.BackendUser;
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
	public List<AppInfo> queryApp(Map<String, String> map) {
		return userDao.queryApp(map);
	}

}
