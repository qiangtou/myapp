package cn.jiuling.distributedmanagement.service.impl;

import java.math.BigDecimal;

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

	public BigDecimal deductPoint(Integer userId,BigDecimal point) {
		User user=findUserById(userId);
		BigDecimal oldPoint= user.getPoint();
		if(oldPoint.compareTo(BigDecimal.ZERO)>-1 &&point.compareTo(BigDecimal.ZERO)>-1 && oldPoint.compareTo(point)>-1){
			user.setPoint(oldPoint.subtract(point));
			userDao.update(user);
		}else{
			logger.info("oldPoint:"+oldPoint);
		}
		return user.getPoint();
	}

	public void setPoint(Integer userId, BigDecimal point) {
		User user=findUserById(userId);
		user.setPoint(point);
		userDao.update(user);
	}
}
