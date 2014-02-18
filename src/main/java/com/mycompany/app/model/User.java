package com.mycompany.app.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer userId;
	private String loginName;
	private String sign;
	private String nickName;
	private BigDecimal point;
	private byte userType;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public BigDecimal getPoint() {
		return point;
	}
	public void setPoint(BigDecimal point) {
		this.point = point;
	}
	public byte getUserType() {
		return userType;
	}
	public void setUserType(byte userType) {
		this.userType = userType;
	}
	@Override
	public String toString() {
		return "User [loginName=" + loginName + ", nickName=" + nickName + ", point=" + point + ", sign=" + sign + ", userId=" + userId + ", userType=" + userType + "]";
	}
	public static void main(String[] args) {
		User u=new User();
		u.userType=1;
		System.out.println(u);
	}
}
