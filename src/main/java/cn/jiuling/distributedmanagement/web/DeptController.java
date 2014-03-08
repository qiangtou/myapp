package cn.jiuling.distributedmanagement.web;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jiuling.distributedmanagement.Vo.Result;
import cn.jiuling.distributedmanagement.model.Dept;
import cn.jiuling.distributedmanagement.service.DeptService;

@Controller
@RequestMapping(value = "/dept", method = RequestMethod.POST)
public class DeptController {
	private Logger logger = Logger.getLogger(DeptController.class);
	@Resource
	private DeptService deptService;

	@RequestMapping(value = { "/add.do" })
	@ResponseBody
	public Result add(@RequestBody Dept dept) {
		Result rs = new Result(true);
		try {
			Timestamp createdDate = new Timestamp(System.currentTimeMillis());
			dept.setCreatedDate(createdDate);
			dept.setModifiedDate(createdDate);
			dept.setFatherId(0);
			deptService.saveOrUpdate(dept);
			logger.info(dept);
			rs.setData(dept);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			rs.setSuccess(false);
		}
		return rs;
	}

	@RequestMapping(value = { "/update.do" })
	@ResponseBody
	public Result update(@RequestBody Dept dept) {
		logger.info(dept);
		deptService.update(dept);
		return new Result(dept);
	}

	@RequestMapping(value = "/del.do")
	@ResponseBody
	public Result del(@RequestBody Dept dept) {
		deptService.delete(dept.getDeptId());
		logger.info(dept);
		return new Result(dept);
	}

	@RequestMapping(value = "/list.do")
	@ResponseBody
	public Result list() {
		List<Dept> deptList = deptService.getDeptList();
		return new Result(deptList);
	}
}
