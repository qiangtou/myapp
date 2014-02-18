package com.mycompany.app.dao;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mycompany.app.model.User;

public class UserDaoTest {
	private UserDao userDao;
	private static final Logger log=Logger.getLogger(UserDaoTest.class);

	@Before
	public void prepare() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-*.xml");
		userDao = (UserDao) ctx.getBean("userDaoImpl");

	}

	public void getUserTest() {
		
		User user= userDao.find(1);
		Assert.assertNotEquals("user is not null",null,user);
		String loginName=user.getLoginName();
		Assert.assertEquals("loginName:","cf1",loginName);
	}
	public void userTest() {
		User user= new User();
		String loginName = "limin";
		user.setLoginName(loginName);
		Integer userId=userDao.save(user);
		user.setLoginName("hehe");
		userDao.update(user);
		log.info("userId :"+userId);
		Assert.assertEquals("loginName:",loginName,user.getLoginName());
	}

}
