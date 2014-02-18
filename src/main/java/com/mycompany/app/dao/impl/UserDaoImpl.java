package com.mycompany.app.dao.impl;

import org.springframework.stereotype.Repository;

import com.mycompany.app.dao.UserDao;
import com.mycompany.app.model.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{


}
