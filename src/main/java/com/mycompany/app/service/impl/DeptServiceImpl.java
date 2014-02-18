package com.mycompany.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.app.dao.DeptDao;
import com.mycompany.app.model.Dept;
import com.mycompany.app.service.DeptService;

@Service("deptService")
public class DeptServiceImpl implements DeptService {
	@Resource
	private DeptDao deptDao;

	public List<Dept> getDeptList(Integer deptId) {
		return deptDao.getAll();
	}

	public DeptDao getDeptDao() {
		return deptDao;
	}

	public void setDeptDao(DeptDao deptDao) {
		this.deptDao = deptDao;
	}

}
