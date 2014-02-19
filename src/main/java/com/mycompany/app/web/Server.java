package com.mycompany.app.web;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.app.Vo.Result;
import com.mycompany.app.Vo.Status;
import com.mycompany.app.service.ServerService;


@Controller
@RequestMapping("/server")
public class Server {
	private Logger logger=Logger.getLogger(Server.class);
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
		logger.info("add server");
		serverService.saveOrUpdate(server);
		return new Result(true,server);
	}
	
	@RequestMapping(value="/status.do",method=RequestMethod.POST)
	@ResponseBody
	public Result status(Integer serverId){
		logger.info("get server status");
		//TODO get server status
		Status data = new Status(1,2,3);
		return new Result(true,data);
	}
	
	
	public ServerService getServerService() {
		return serverService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}
}
