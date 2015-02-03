package cn.jiuling.distributedmanagement.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jiuling.distributedmanagement.Vo.Result;
import cn.jiuling.distributedmanagement.model.Server;
import cn.jiuling.distributedmanagement.service.ServerService;
import cn.jiuling.distributedmanagement.utils.HttpUtils;
import cn.jiuling.distributedmanagement.utils.PropertiesUtils;

/**
 * 服务器Controller
 * 
 * @author phq
 * 
 * @date 2014-12-8
 */
@Controller
@RequestMapping(value = "/server", method = RequestMethod.POST)
public class ServerController {
	private Logger logger = Logger.getLogger(ServerController.class);
	@Resource
	private ServerService serverService;

	/**
	 * 取得部门下的服务器列表.
	 * 
	 * @param deptId
	 *            部门id
	 * @return 该部门下的服务器列表
	 */
	@RequestMapping(value = "/list.do")
	@ResponseBody
	public List list(Long deptId) {
		List list = null;
		if (null != deptId && deptId >= 0) {
			list = serverService.getServerList(deptId);
		}
		return list;
	}

	@RequestMapping(value = "/add.do")
	@ResponseBody
	public Result add(@RequestBody Server server) {
		logger.info("add server");
		if (null == server.getIsValid()) {
			server.setIsValid(Server.VALID);
		}
		return new Result(serverService.add(server));
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

	@RequestMapping(value = "/status.do")
	@ResponseBody
	public String status(Integer serverId, float sid, HttpServletRequest request) {
		// TODO 网址写死了,以后扩展api后再改过来
		String host = PropertiesUtils.get("server.status.host");
		if (StringUtils.isEmpty(host)) {
			host = request.getScheme() + "://" + request.getServerName();
		}
		String url = host + "/master/querySlvNodeInfo.php?&type=2&id=" + serverId + "&sid=" + sid;
		String status = HttpUtils.get(url);
		logger.info("get server status start \n url:" + url + "\n,status:" + status);
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
