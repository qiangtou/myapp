package cn.jiuling.distributedmanagement.Vo;

import java.util.List;

public class DeptVo {
	private Integer deptId;
	private Integer deptNo;
	private String cname;
	private String dsc;
	private List servers;
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
	public List getServers() {
		return servers;
	}
	public void setServers(List servers) {
		this.servers = servers;
	}
}
