package cn.jiuling.distributedmanagement.simple;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value="/u")
public class Hello2 {
	@RequestMapping(value="/{id}/1.do")
	public String hello(@PathVariable String id){
		System.out.println(id+"hello");
		return "hh";
	}
	
	@RequestMapping(value="/2.do")
	public String hello1(){
		System.out.println("hello");
		return "hh";
	}
	@RequestMapping(value="/3.do")
	public String hello3(){
		System.out.println("hello");
		return "hh";
	}
	@RequestMapping(value="/4.do")
	public @ResponseBody Uo hello4(){
		Uo u=new Uo();
		u.setName("ttt");
		return u;
	}

}

