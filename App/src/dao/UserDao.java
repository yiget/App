
package dao;

import java.util.Map;

import entity.BackendUser;
import entity.DevUser;

public interface UserDao {
	//查询后台账户
	public BackendUser findCode(Map<String, String> map);
	//查询前台账户
	public DevUser findUser(Map<String, String> map);

}