package cn.jiuling.distributedmanagement.web;

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
@RequestMapping("/dept")
public class DeptController {
	private Logger logger=Logger.getLogger(DeptController.class);
	@Resource
	private DeptService deptService;
	
	@RequestMapping(value={"/add.do","/update.do"},method=RequestMethod.POST)
	@ResponseBody
	public Result add(@RequestBody Dept dept){
		deptService.saveOrUpdate(dept);
		logger.info(dept);
		return new Result(dept);
	}
	@RequestMapping(value="/del.do",method=RequestMethod.POST)
	@ResponseBody
	public Result del(@RequestBody Dept dept){
		deptService.delete(dept.getDeptId());
		logger.info(dept);
		return new Result(dept);
	}

	public DeptService getDeptService() {
		return deptService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
}
