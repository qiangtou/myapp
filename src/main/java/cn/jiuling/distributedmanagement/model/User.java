package cn.jiuling.distributedmanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity()
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "UserId")
	private Integer userId;
	@Column(name = "user")
	private String userName;
	@Column(name = "md5pwd")
	private String passWord;
	@Column(name = "user_real_name")
	private String realName;
	@Column(name = "user_tel")
	private String telephone;
	@Column(name = "user_email")
	private String email;
	@Column(name = "permission_level")
	private byte permissionLevel;

	public String getEmail() {
		return email;
	}

	public String getPassWord() {
		return passWord;
	}

	public byte getPermissionLevel() {
		return permissionLevel;
	}

	public String getRealName() {
		return realName;
	}

	public String getTelephone() {
		return telephone;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public void setPermissionLevel(byte permissionLevel) {
		this.permissionLevel = permissionLevel;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", passWord=" + passWord + ", permissionLevel=" + permissionLevel + ", realName=" + realName + ", telephone="
				+ telephone + ", userId=" + userId + ", userName=" + userName + "]";
	}
}
