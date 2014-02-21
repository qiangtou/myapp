package cn.jiuling.distributedmanagement.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.jiuling.distributedmanagement.dao.DeptDao;
import cn.jiuling.distributedmanagement.model.Dept;
import cn.jiuling.distributedmanagement.service.DeptService;

@Service("deptService")
public class DeptServiceImpl implements DeptService {
	@Resource
	private DeptDao deptDao;

	public List<Dept> getDeptList() {
		return deptDao.getUnDeletedList();
	}

	public DeptDao getDeptDao() {
		return deptDao;
	}

	public void setDeptDao(DeptDao deptDao) {
		this.deptDao = deptDao;
	}

	public void saveOrUpdate(Dept dept) {
		deptDao.saveOrUpdate(dept);
	}

	public boolean delete(Integer deptId) {
		boolean hasServer=deptDao.hasServer(deptId);
		if(!hasServer){
			//修改删除状态
			Dept dept=deptDao.find(deptId);
			dept.setIsDelete(Dept.DELETE);
			deptDao.update(dept);
		}
		return !hasServer;
		
	}

}
