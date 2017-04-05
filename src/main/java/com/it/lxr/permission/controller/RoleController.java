package com.it.lxr.permission.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.it.lxr.common.utils.LoggerUtils;
import com.it.lxr.common.utils.Pagination;
import com.it.lxr.common.utils.RequestUtils;
import com.it.lxr.permission.po.RolePermissionAllocationBo;
import com.it.lxr.permission.po.UPermission;
import com.it.lxr.permission.po.URole;
import com.it.lxr.permission.po.URoleBo;
import com.it.lxr.permission.po.UserRoleAllocationBo;
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
	Map<String, Object> resultMap = new HashMap<String,Object>(225);
	@Autowired
	IPermissionService permissionService;
	protected int pageNo =1;
	public static  int pageSize = 10;
	@RequestMapping(value="index")
	public ModelAndView index(String findContent,ModelMap modelMap,HttpServletRequest request){
		int pageNo = (int) RequestUtils.getParameterAsDefInInt(request, "pageNo", 1);
		if(StringUtils.isNotBlank(findContent)){
			modelMap.put("findContent", findContent);
		}
		modelMap.put("pageSize", pageSize);
		modelMap.put("pageNo", (pageNo-1)*pageSize);

		modelMap.put("findContent", findContent);
		
		List<URole> roles = permissionService.findRolePage(modelMap);
		
		int  pagecount = permissionService.findRoleSumPage(modelMap);
		Pagination<UPermission> pagination = new Pagination<UPermission>(pageNo,pageSize,pagecount,roles);
		return new ModelAndView("role/index","page",pagination);
	}
//	/**
//	 * 角色添加
//	 * @param role
//	 * @return
//	 */
	@RequestMapping(value="addRole",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addRole(URole role){
		try {
			int count = permissionService.insertRole(role);
			resultMap.put("status", 200);
			resultMap.put("successCount", count);
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "添加失败，请刷新后再试！");
			LoggerUtils.fmtError(getClass(), e, "添加角色报错。source[%s]",role.toString());
		}
		return resultMap;
	}
	/**
	 * 删除角色，根据ID，但是删除角色的时候，需要查询是否有赋予给用户，如果有用户在使用，那么就不能删除。
	 * @param id
	 * @return
	 */
	@RequestMapping(value="deleteRoleById",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteRoleById(String ids){
		return permissionService.deleteRoleById(ids);
	}
	/**
	 * 我的权限页面
	 * @return
	 */
	@RequestMapping(value="mypermission",method=RequestMethod.GET)
	public ModelAndView mypermission(){
		return new ModelAndView("permission/mypermission");
	}
	/**
	 * 我的权限 bootstrap tree data
	 * @return
	 */
	@RequestMapping(value="getPermissionTree",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getPermissionTree(){
		//查询我所有的角色 ---> 权限
		List<URole> roles = permissionService.findNowAllPermission();
		//把查询出来的roles 转换成bootstarp 的 tree数据
		List<Map<String, Object>> data = UserManager.toTreeData(roles);
		return data;
	}
	/**
	 * 用户角色权限分配
	 * @param modelMap
	 * @param pageNo
	 * @param findContent
	 * @return
	 */
	@RequestMapping(value="allocation")
	public ModelAndView allocation(ModelMap modelMap,String findContent,HttpServletRequest request){
		int pageNo = (int) RequestUtils.getParameterAsDefInInt(request, "pageNo", 1);
		if(StringUtils.isNotBlank(findContent)){
			modelMap.put("findContent", findContent);
		}
		modelMap.put("pageSize", pageSize);
		modelMap.put("pageNo", (pageNo-1)*pageSize);
		List<RolePermissionAllocationBo> boPage = permissionService.findUserAndRolePage(modelMap);
		int num= permissionService.findUserAndRoleNum(modelMap);
		Pagination<UserRoleAllocationBo> pagination = new Pagination<UserRoleAllocationBo>(pageNo,pageSize,num,boPage);
		modelMap.put("page", pagination);
		return new ModelAndView("role/allocation");
	}
	
	/**
	 * 根据用户ID查询权限
	 * @param id
	 * @return
	 */
	@RequestMapping(value="selectRoleByUserId")
	@ResponseBody
	public List<URoleBo> selectRoleByUserId(Long id){
		List<URoleBo> bos = permissionService.selectRoleByUserId(id);
		return bos;
	}
	/**
	 * 操作用户的角色
	 * @param userId 	用户ID
	 * @param ids		角色ID，以‘,’间隔
	 * @return
	 */
	@RequestMapping(value="addRole2User")
	@ResponseBody
	public Map<String,Object> addRole2User(Long userId,String ids){
		return permissionService.addRole2User(userId,ids);
	}
	/**
	 * 根据用户id清空角色。
	 * @param userIds	用户ID ，以‘,’间隔
	 * @return
	 */
	@RequestMapping(value="clearRoleByUserIds")
	@ResponseBody
	public Map<String,Object> clearRoleByUserIds(String userIds){
		return permissionService.deleteRoleByUserIds(userIds);
	}
}
