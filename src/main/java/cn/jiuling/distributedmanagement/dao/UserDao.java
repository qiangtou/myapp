package cn.jiuling.distributedmanagement.dao;

import cn.jiuling.distributedmanagement.model.User;

public interface UserDao extends BaseDao<User> {

	public User findByUserName(String userName);

}
