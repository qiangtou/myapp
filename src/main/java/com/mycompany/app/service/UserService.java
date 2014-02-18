package com.mycompany.app.service;

import java.math.BigDecimal;

import com.mycompany.app.model.User;

public interface UserService {
	public User findUserById(Integer userId);
	public BigDecimal deductPoint(Integer userId,BigDecimal point);
	public void setPoint(Integer userId,BigDecimal point);
}
