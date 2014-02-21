package cn.jiuling.distributedmanagement.Vo;

public class Status {
private Integer status1;
private Integer status2;
private Integer status3;

public Status(Integer status1, Integer status2, Integer status3) {
	super();
	this.status1 = status1;
	this.status2 = status2;
	this.status3 = status3;
}
public Integer getStatus1() {
	return status1;
}
public void setStatus1(Integer status1) {
	this.status1 = status1;
}
public Integer getStatus2() {
	return status2;
}
public void setStatus2(Integer status2) {
	this.status2 = status2;
}
public Integer getStatus3() {
	return status3;
}
public void setStatus3(Integer status3) {
	this.status3 = status3;
}
@Override
public String toString() {
	return "Status [status1=" + status1 + ", status2=" + status2 + ", status3=" + status3 + "]";
}
}
