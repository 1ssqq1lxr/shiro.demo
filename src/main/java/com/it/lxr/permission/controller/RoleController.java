package com.it.lxr.permission.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.it.lxr.common.utils.LoggerUtils;
import com.it.lxr.permission.service.IPermissionService;
import com.it.lxr.permission.token.manager.TokenManager;
import com.it.lxr.user.manager.UserManager;
import com.it.lxr.user.po.UUser;
import com.it.lxr.user.service.IUserService;

import net.sf.json.JSONObject;

@Controller
@Scope(value="prototype")
@RequestMapping("role")
public class RoleController {
	@Autowired
	IPermissionService permissionService;
}
