package com.it.lxr.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {
		@RequestMapping("index.shtml")
		public String index(){
			System.out.println("123123123");
			return "index";
		}
}
