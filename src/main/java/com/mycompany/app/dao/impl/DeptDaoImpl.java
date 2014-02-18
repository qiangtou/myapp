package com.mycompany.app.dao.impl;

import org.springframework.stereotype.Repository;

import com.mycompany.app.dao.DeptDao;
import com.mycompany.app.model.Dept;

@Repository("deptDao")
public class DeptDaoImpl extends BaseDaoImpl<Dept> implements DeptDao{

}
