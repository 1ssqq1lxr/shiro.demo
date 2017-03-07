package com.it.lxr.user.controller;

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
import com.it.lxr.permission.token.manager.TokenManager;
import com.it.lxr.user.manager.UserManager;
import com.it.lxr.user.po.UUser;
import com.it.lxr.user.service.IUserService;

import net.sf.json.JSONObject;

@Controller
@Scope(value="prototype")
@RequestMapping("user")
public class UserController {
	@Autowired
	IUserService userService;
	Map<String, Object>  resultMap = new HashMap<String,Object>();
	
	@RequestMapping(value="index",method=RequestMethod.GET)
	public String userIndex(){
		
		return "user/index";
	}
	/**
	 * 偷懒一下，通用页面跳转
	 * @param page
	 * @return
	 */
	@RequestMapping(value="{page}",method=RequestMethod.GET)
	public ModelAndView toPage(@PathVariable("page")String page){
		return new ModelAndView(String.format("user/%s", page));
	}
	/**
	 * 密码修改
	 * @return
	 */
	@RequestMapping(value="updatePswd",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updatePswd(String pswd,String newPswd){
		//根据当前登录的用户帐号 + 老密码，查询。
		String email = TokenManager.getToken().getEmail();
				pswd = UserManager.md5Pswd(email, pswd);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("email", email);
				map.put("pswd",pswd);
				
		UUser user;
		try {
			user = userService.queryUser(map);
			if("admin".equals(email)){
				resultMap.put("status", 300);
				resultMap.put("message", "管理员不准修改密码。");
				return resultMap;
			}
			
			if(null == user){
				resultMap.put("status", 300);
				resultMap.put("message", "密码不正确！");
			}else{
				user.setPswd(newPswd);
				//加工密码
				user = UserManager.md5Pswd(user);
				//修改密码
				userService.updateByPrimaryKeySelective(user);
				resultMap.put("status", 200);
				resultMap.put("message", "修改成功!");
				//重新登录一次
				TokenManager.login(user, Boolean.TRUE);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	

		return resultMap;
	}
	/**
	 * 个人资料修改
	 * @return
	 */
	@RequestMapping(value="updateSelf",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateSelf(UUser entity){
		try {
			userService.updateByPrimaryKeySelective(entity);
			resultMap.put("status", 200);
			resultMap.put("message", "修改成功!");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "修改失败!");
			LoggerUtils.fmtError(getClass(), e, "修改个人资料出错。[%s]", JSONObject.fromObject(entity).toString());
		}
		return resultMap;
	}
	
}
