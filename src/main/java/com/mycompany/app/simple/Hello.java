package com.mycompany.app.simple;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class Hello {
	@RequestMapping(value="/{id}/1.do")
	public String hello(@PathVariable String id){
		System.out.println(id+"hello");
		return "hh";
	}
	
	@RequestMapping(value="/2.do")
	public ModelAndView hello1(){
		System.out.println("2");
		ModelAndView mv=new ModelAndView("2");
		mv.addObject("ggg", "gg21123");
		Uo u=new Uo();
		u.setName("sdfas");
		mv.addObject("u",u);
		return mv;
	}
	@RequestMapping(value="/3.do")
	public String hello3(){
		System.out.println("hello");
		return "hh";
	}
	@RequestMapping(value="/4.do")
	public String hello4(){
		System.out.println("hello");
		return "hh";
	}

}
