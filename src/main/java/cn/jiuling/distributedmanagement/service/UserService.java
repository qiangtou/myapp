package cn.jiuling.distributedmanagement.service;

import cn.jiuling.distributedmanagement.model.User;

public interface UserService {
	public User findUserById(Integer userId);

	public boolean valideUser(User user);
}
