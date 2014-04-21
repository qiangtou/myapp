package cn.jiuling.distributedmanagement.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_user")
public class User implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private String fullName;
	private Long groupId;
	private Boolean blocked;
	private Long createId;
	private Timestamp createTime;
	private Integer taskPriority;
	private Boolean updateTimeRegular;
	private String md5pwd;
	private Timestamp lastUpdatePasswordTime;
	private Timestamp lastLoginTime;
	private Integer totalLoginDuration;
	private Integer loginCount;

	// Constructors

	/** default constructor */
	public User() {
	}

	public User(Long id, String name, String md5pwd) {
		super();
		this.id = id;
		this.name = name;
		this.md5pwd = md5pwd;
	}

	/** full constructor */
	public User(String name, String fullName, Long groupId, Boolean blocked, Long createId, Timestamp createTime, Integer taskPriority,
			Boolean updateTimeRegular, String md5pwd, Timestamp lastUpdatePasswordTime, Timestamp lastLoginTime, Integer totalLoginDuration, Integer loginCount) {
		this.name = name;
		this.fullName = fullName;
		this.groupId = groupId;
		this.blocked = blocked;
		this.createId = createId;
		this.createTime = createTime;
		this.taskPriority = taskPriority;
		this.updateTimeRegular = updateTimeRegular;
		this.md5pwd = md5pwd;
		this.lastUpdatePasswordTime = lastUpdatePasswordTime;
		this.lastLoginTime = lastLoginTime;
		this.totalLoginDuration = totalLoginDuration;
		this.loginCount = loginCount;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 256)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "full_name", nullable = false, length = 256)
	@Type(type = "cn.jiuling.distributedmanagement.utils.UTF8String")
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "GroupID", nullable = false)
	public Long getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	@Column(name = "blocked", nullable = false)
	public Boolean getBlocked() {
		return this.blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	@Column(name = "create_id", nullable = false)
	public Long getCreateId() {
		return this.createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	@Column(name = "create_time", nullable = false, length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "task_priority", nullable = false)
	public Integer getTaskPriority() {
		return this.taskPriority;
	}

	public void setTaskPriority(Integer taskPriority) {
		this.taskPriority = taskPriority;
	}

	@Column(name = "update_time_regular", nullable = false)
	public Boolean getUpdateTimeRegular() {
		return this.updateTimeRegular;
	}

	public void setUpdateTimeRegular(Boolean updateTimeRegular) {
		this.updateTimeRegular = updateTimeRegular;
	}

	@Column(name = "md5pwd", nullable = false, length = 256)
	public String getMd5pwd() {
		return this.md5pwd;
	}

	public void setMd5pwd(String md5pwd) {
		this.md5pwd = md5pwd;
	}

	@Column(name = "last_update_password_time", nullable = false, length = 19)
	public Timestamp getLastUpdatePasswordTime() {
		return this.lastUpdatePasswordTime;
	}

	public void setLastUpdatePasswordTime(Timestamp lastUpdatePasswordTime) {
		this.lastUpdatePasswordTime = lastUpdatePasswordTime;
	}

	@Column(name = "last_login_time", nullable = false, length = 19)
	public Timestamp getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Column(name = "total_login_duration", nullable = false)
	public Integer getTotalLoginDuration() {
		return this.totalLoginDuration;
	}

	public void setTotalLoginDuration(Integer totalLoginDuration) {
		this.totalLoginDuration = totalLoginDuration;
	}

	@Column(name = "login_count", nullable = false)
	public Integer getLoginCount() {
		return this.loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

}