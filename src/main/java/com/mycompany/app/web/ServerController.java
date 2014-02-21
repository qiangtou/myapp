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
import com.mycompany.app.model.Server;
import com.mycompany.app.service.ServerService;


@Controller
@RequestMapping("/server")
public class ServerController {
	private Logger logger=Logger.getLogger(ServerController.class);
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
	public Result add(@RequestBody Server server){
		logger.info("add server");
		serverService.saveOrUpdate(server);
		return new Result(server);
	}
	
	@RequestMapping(value="/update.do",method=RequestMethod.POST)
	@ResponseBody
	public Result update(@RequestBody Server server){
		logger.info("update server:"+server);
		server=serverService.updateIpAndDept(server);
		return new Result(server);
	}
	
	@RequestMapping(value="/del.do",method=RequestMethod.POST)
	@ResponseBody
	public Result del(@RequestBody Server server){
		logger.info("del server"+server);
		//TODO del server
		serverService.del(server);
		return new Result(server);
	}
	
	@RequestMapping(value="/status.do",method=RequestMethod.POST)
	@ResponseBody
	public Result status(Integer serverId){
		logger.info("get server status");
		//TODO get server status
		Status data = new Status(1,2,3);
		return new Result(data);
	}
	
	@RequestMapping(value="/active.do",method=RequestMethod.POST)
	@ResponseBody
	public Result active(@RequestBody Server server){
		logger.info("active server status"+server);
		serverService.active(server);
		return new Result(true);
	}
	
	
	public ServerService getServerService() {
		return serverService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}
}
