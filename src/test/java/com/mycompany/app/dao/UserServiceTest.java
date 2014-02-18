package com.mycompany.app.dao;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mycompany.app.service.UserService;

public class UserServiceTest {
	private UserService userService;
	private static final Logger log=Logger.getLogger(UserServiceTest.class);
	private Integer userId=1;

	@Before
	public void prepare() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-*.xml");
		userService = (UserService) ctx.getBean("userService");
		userService.setPoint(userId,BigDecimal.TEN);
	}

	public void userTest() {
		
		for(int i=0;i<1000;i++){
			new Thread(new DeDuctPointThread(i)).start();
		}

		try {
			Thread.sleep(4*1000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class DeDuctPointThread implements Runnable{
		private int count;
		
		public DeDuctPointThread(int count) {
			super();
			this.count = count;
		}
		public void run() {
			BigDecimal point=userService.deductPoint(userId, BigDecimal.valueOf(2l));
			log.info(this.count+",用户积分"+point);
		}
	}

}
