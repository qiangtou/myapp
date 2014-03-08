package cn.jiuling.distributedmanagement.web;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jiuling.distributedmanagement.Vo.Result;
import cn.jiuling.distributedmanagement.model.Server;
import cn.jiuling.distributedmanagement.service.ServerService;
import cn.jiuling.distributedmanagement.utils.HttpUtils;
import cn.jiuling.distributedmanagement.utils.PropertiesUtils;

@Controller
@RequestMapping(value = "/server", method = RequestMethod.POST)
public class ServerController {
	private Logger logger = Logger.getLogger(ServerController.class);
	@Resource
	private ServerService serverService;

	@RequestMapping(value = "/list.do")
	@ResponseBody
	public List list(Integer deptId) {
		List list = null;
		if (null != deptId && 0 < deptId) {
			list = serverService.getServerList(deptId);
		}
		return list;
	}

	@RequestMapping(value = "/add.do")
	@ResponseBody
	public Result add(@RequestBody Server server) {
		logger.info("add server");
		serverService.saveOrUpdate(server);
		return new Result(server);
	}

	@RequestMapping(value = "/update.do")
	@ResponseBody
	public Result update(@RequestBody Server server) {
		logger.info("update server:" + server);
		server = serverService.updateServer(server);
		return new Result(server);
	}

	@RequestMapping(value = "/del.do")
	@ResponseBody
	public Result del(@RequestBody Server server) {
		logger.info("del server" + server);
		// TODO del server
		serverService.del(server);
		return new Result(server);
	}

	/*
	 * @RequestMapping(value="/status.do",method=RequestMethod.POST)
	 * 
	 * @ResponseBody public Result status(Integer serverId){
	 * logger.info("get server status"); //TODO 取服务器运行状态,这里写死了,待添加 Status data =
	 * new Status(1,2,3); return new Result(data); }
	 */

	@RequestMapping(value = "/status.do")
	@ResponseBody
	public String status(Integer serverId, float sid) {
		// TODO 取服务器运行状态,这里写死了,要配置的
		// String status =
		// "<?xml version=\"1.0\" encoding=\"UTF-8\"?><result><ret>2</ret><str_desc>suc</str_desc><isValid>1</isValid><type></type><ID>1</ID><IP>192.168.1.61</IP><analysising>0</analysising><waitinganAlysising>0</waitinganAlysising><transcoding>0</transcoding><waitingTranscoding>0</waitingTranscoding><com_mem>38</com_mem><com_cup>6</com_cup></result>";
		String url = PropertiesUtils.get("server.status.host") + "/querySlvNodeInfo.php?&type=2&id=" + serverId + "&sid=" + sid;
		logger.info("get server status start \n url is:" + url);
		String status = HttpUtils.get(url);
		logger.info("get server status end, response is:\n" + status);
		return status;
	}

	@RequestMapping(value = "/enable.do")
	@ResponseBody
	public Result enable(@RequestBody Server server) {
		logger.info("enable server status" + server);
		serverService.enable(server);
		return new Result(true);
	}

	@RequestMapping(value = "/disable.do")
	@ResponseBody
	public Result disable(@RequestBody Server server) {
		logger.info("disable server status" + server);
		serverService.disable(server);
		return new Result(true);
	}
}
