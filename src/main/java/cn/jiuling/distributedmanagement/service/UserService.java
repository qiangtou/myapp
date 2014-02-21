package cn.jiuling.distributedmanagement.service;

import java.math.BigDecimal;

import cn.jiuling.distributedmanagement.model.User;

public interface UserService {
	public User findUserById(Integer userId);
	public BigDecimal deductPoint(Integer userId,BigDecimal point);
	public void setPoint(Integer userId,BigDecimal point);
}
