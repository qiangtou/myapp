package com.mycompany.app.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.app.Vo.Result;
import com.mycompany.app.service.ServerService;


@Controller
@RequestMapping("/server")
public class Server {
	@Resource
	private ServerService serverService;
	
	@RequestMapping(value="/list.do",method=RequestMethod.POST)
	@ResponseBody
	public List list(Integer deptId){
		List list=null;
		if(null!=deptId&&0<deptId){
			list= serverService.getServerList(deptId);
		}
		return list;
	}
	
	@RequestMapping(value="/add.do",method=RequestMethod.POST)
	@ResponseBody
	public Result add(@RequestBody com.mycompany.app.model.Server server){
		serverService.saveOrUpdate(server);
		return new Result(true);
	}
	
	
	public ServerService getServerService() {
		return serverService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}
}
