package com.mycompany.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="mst_transcodeserver")
public class Server {
	@Id	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Integer Id;
	@Column(name="IPAddr")
	private String ipAddr;
	private byte isValid;
	private byte isEnhance;
	@Column(name="dept_id")
	private Integer deptId;
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public byte getIsValid() {
		return isValid;
	}
	public void setIsValid(byte isValid) {
		this.isValid = isValid;
	}
	public byte getIsEnhance() {
		return isEnhance;
	}
	public void setIsEnhance(byte isEnhance) {
		this.isEnhance = isEnhance;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
}