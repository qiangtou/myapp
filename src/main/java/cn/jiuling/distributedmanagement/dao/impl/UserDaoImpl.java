package cn.jiuling.distributedmanagement.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jiuling.distributedmanagement.dao.UserDao;
import cn.jiuling.distributedmanagement.model.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	public User findByUserName(String userName) {
		User user = null;
		List<User> userList = getHibernateTemplate().find("from User u where u.userName=?", userName);
		if (userList.size() > 0) {
			user = userList.get(0);
		}
		return user;
	};

}
