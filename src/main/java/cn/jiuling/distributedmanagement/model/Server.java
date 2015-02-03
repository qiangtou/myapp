package cn.jiuling.distributedmanagement.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Transcodeserver entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mst_transcodeserver")
public class Server implements java.io.Serializable {

	// Fields

	public static final Short VALID = 1;
	public static final Short INVALID = 0;
	private Long id;
	private String ipAddr;
	private Short isValid;
	private Short isEnhance;
	private Long deptId;

	// Constructors

	/** default constructor */
	public Server() {
	}

	/** full constructor */
	public Server(String ipaddr, Short isValid, Short isEnhance, Long deptId) {
		this.ipAddr = ipaddr;
		this.isValid = isValid;
		this.isEnhance = isEnhance;
		this.deptId = deptId;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "IPAddr", nullable = false)
	public String getIpAddr() {
		return this.ipAddr;
	}

	public void setIpAddr(String ipaddr) {
		this.ipAddr = ipaddr;
	}

	@Column(name = "isValid", nullable = false)
	public Short getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Short isValid) {
		this.isValid = isValid;
	}

	@Column(name = "isEnhance", nullable = false)
	public Short getIsEnhance() {
		return this.isEnhance;
	}

	public void setIsEnhance(Short isEnhance) {
		this.isEnhance = isEnhance;
	}

	@Column(name = "dept_id", nullable = false)
	public Long getDeptId() {
		return this.deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

}