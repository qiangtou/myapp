package cn.jiuling.distributedmanagement.service;


import cn.jiuling.distributedmanagement.Vo.Pager;
import cn.jiuling.distributedmanagement.model.User;

public interface UserService {
	public User findUserById(Integer userId);

	public boolean valideUser(User user);

	/**
	 * 取得登录用户列表
	 * @param page TODO
	 * @param rows TODO
	 * 
	 * @return
	 */
	public Pager listLoginedUser(Integer page, Integer rows);
}
