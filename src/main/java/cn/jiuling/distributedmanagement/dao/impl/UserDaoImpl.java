package cn.jiuling.distributedmanagement.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jiuling.distributedmanagement.dao.UserDao;
import cn.jiuling.distributedmanagement.model.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	public User findByUserName(String userName) {
		User user = null;
		List<User> userList = getHibernateTemplate().find("select new User(u.id,u.name,u.md5pwd) from User u where u.name=?", userName);
		if (userList.size() > 0) {
			user = userList.get(0);
		}
		return user;
	};

}
