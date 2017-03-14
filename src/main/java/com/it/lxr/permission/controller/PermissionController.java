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
import com.it.lxr.permission.po.UPermission;
import com.it.lxr.permission.service.IPermissionService;
import com.it.lxr.permission.token.manager.TokenManager;
import com.it.lxr.user.manager.UserManager;
import com.it.lxr.user.po.UUser;
import com.it.lxr.user.service.IUserService;
import com.sun.tools.internal.ws.processor.model.Request;

import freemarker.template.utility.StringUtil;
import net.sf.json.JSONObject;

@Controller
@Scope(value="prototype")
@RequestMapping("permission")
public class PermissionController {
	@Autowired
	IPermissionService permissionService;
	protected int pageNo =1;
	public static  int pageSize = 10;
	
	Map<String, Object> resultMap = new HashMap<String,Object>();
	
	@RequestMapping(value="index")
	public ModelAndView index(String findContent,ModelMap modelMap,HttpServletRequest request){
		
		int pageNo = (int) RequestUtils.getParameterAsDefInInt(request, "pageNo", 1);
		if(StringUtils.isNotBlank(findContent)){
			modelMap.put("findContent", findContent);
		}
		modelMap.put("pageSize", pageSize);
		modelMap.put("pageNo", (pageNo-1)*pageSize);

		List<UPermission> permissions = permissionService.findPage(modelMap);
		
		int  pagecount = permissionService.findSumPage(modelMap);
		Pagination<UPermission> pagination = new Pagination<UPermission>(pageNo,pageSize,pagecount,permissions);
		return new ModelAndView("permission/index","page",pagination);
	}
	/**
	 * 权限添加
	 * @param role
	 * @return
	 */
	@RequestMapping(value="addPermission",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addPermission(UPermission psermission){
//		try {
//			UPermission entity = permissionService.insertSelective(psermission);
//			resultMap.put("status", 200);
//			resultMap.put("entity", entity);
//		} catch (Exception e) {
//			resultMap.put("status", 500);
//			resultMap.put("message", "添加失败，请刷新后再试！");
//			LoggerUtils.fmtError(getClass(), e, "添加权限报错。source[%s]", psermission.toString());
//		}
		return resultMap;
	}
	/**
	 * 删除权限，根据ID，但是删除权限的时候，需要查询是否有赋予给角色，如果有角色在使用，那么就不能删除。
	 * @param id
	 * @return
	 */
	@RequestMapping(value="deletePermissionById",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteRoleById(String ids){
		return permissionService.deletePermissionById(ids);
	}
//	
//	/**
//	 * 权限分配
//	 * @param modelMap
//	 * @param pageNo
//	 * @param findContent
//	 * @return
//	 */
//	@RequestMapping(value="allocation")
//	public ModelAndView allocation(ModelMap modelMap,Integer pageNo,String findContent){
////		modelMap.put("findContent", findContent);
////		Pagination<RolePermissionAllocationBo> boPage = roleService.findRoleAndPermissionPage(modelMap,pageNo,pageSize);
////		modelMap.put("page", boPage);
//		return new ModelAndView("permission/allocation");
//	}
//	
//	/**
//	 * 根据角色ID查询权限
//	 * @param id
//	 * @return
//	 */
//	@RequestMapping(value="selectPermissionById")
//	@ResponseBody
//	public List<UPermissionBo> selectPermissionById(Long id){
//		List<UPermissionBo> permissionBos = permissionService.selectPermissionById(id);
//		return permissionBos;
//	}
//	/**
//	 * 操作角色的权限
//	 * @param roleId 	角色ID
//	 * @param ids		权限ID，以‘,’间隔
//	 * @return
//	 */
//	@RequestMapping(value="addPermission2Role")
//	@ResponseBody
//	public Map<String,Object> addPermission2Role(Long roleId,String ids){
//		return permissionService.addPermission2Role(roleId,ids);
//	}
//	/**
//	 * 根据角色id清空权限。
//	 * @param roleIds	角色ID ，以‘,’间隔
//	 * @return
//	 */
//	@RequestMapping(value="clearPermissionByRoleIds")
//	@ResponseBody
//	public Map<String,Object> clearPermissionByRoleIds(String roleIds){
//		return permissionService.deleteByRids(roleIds);
//	}
}
