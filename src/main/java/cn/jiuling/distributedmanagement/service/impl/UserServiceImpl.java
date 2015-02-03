package cn.jiuling.distributedmanagement.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.jiuling.distributedmanagement.Vo.Constant;
import cn.jiuling.distributedmanagement.Vo.Pager;
import cn.jiuling.distributedmanagement.dao.UserDao;
import cn.jiuling.distributedmanagement.model.User;
import cn.jiuling.distributedmanagement.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	private final Logger logger = Logger.getLogger(this.getClass());
	@Resource
	private UserDao userDao;

	public User findUserById(Integer userId) {
		return userDao.find(userId);
	}

	@Override
	public boolean valideUser(User user) {
		User u = userDao.findByUserName(user.getName());
		return null != u && user.getMd5pwd().equals(u.getMd5pwd());
	}

	@Override
	public Pager listLoginedUser(Integer page, Integer rows) {
		User u = new User();
		u.setLogined(Constant.USER_LOGINED);
		return userDao.list(u, page, rows);
	}
}
