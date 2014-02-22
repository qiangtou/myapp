package cn.jiuling.distributedmanagement.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jiuling.distributedmanagement.dao.UserDao;
import cn.jiuling.distributedmanagement.model.User;
import cn.jiuling.distributedmanagement.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	private final Logger logger=Logger.getLogger(this.getClass());
	@Autowired
	private UserDao userDao;

	public User findUserById(Integer userId) {
		return userDao.find(userId);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean valideUser(User user) {
		User u=userDao.findByUserName(user.getUserName());
		return null!=u &&user.getPassWord().equals(u.getPassWord());
	}
}
