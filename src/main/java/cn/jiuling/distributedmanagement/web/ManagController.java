package cn.jiuling.distributedmanagement.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.jiuling.distributedmanagement.model.Dept;
import cn.jiuling.distributedmanagement.service.DeptService;


@Controller
@RequestMapping("/manage")
public class ManagController {
	@Resource
	private DeptService deptService;
	
	@RequestMapping(value="/index.do")
	public ModelAndView index(){
		ModelAndView mv=new ModelAndView("management");
		List<Dept> deptList= deptService.getDeptList();
		mv.addObject("deptList", deptList);
		return mv;
	}
	
}
