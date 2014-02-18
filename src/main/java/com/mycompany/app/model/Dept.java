package com.mycompany.app.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="ftzj_dept")
public class Dept {
	@Id	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="dept_id")
	private Integer deptId;
	@Column(name="deptno")
	private Integer deptNo;
	@Column(name="father_id")
	private Integer fatherId;
	private String cname;
	private String dsc;
	@Column(name="created_date")
	private Timestamp createdDate;
	@Column(name="modified_date")
	private Timestamp modifiedDate;
	@Column(name="sort_order")
	private short sortOrder;
	private short groupbttype;
	private short quanzongno;
	@Column(name="is_delete")
	private byte isDelete;
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public Integer getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(Integer deptNo) {
		this.deptNo = deptNo;
	}
	public Integer getFatherId() {
		return fatherId;
	}
	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getDsc() {
		return dsc;
	}
	public void setDsc(String dsc) {
		this.dsc = dsc;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public short getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(short sortOrder) {
		this.sortOrder = sortOrder;
	}
	public short getGroupbttype() {
		return groupbttype;
	}
	public void setGroupbttype(short groupbttype) {
		this.groupbttype = groupbttype;
	}
	public short getQuanzongno() {
		return quanzongno;
	}
	public void setQuanzongno(short quanzongno) {
		this.quanzongno = quanzongno;
	}
	public byte getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(byte isDelete) {
		this.isDelete = isDelete;
	}
}
