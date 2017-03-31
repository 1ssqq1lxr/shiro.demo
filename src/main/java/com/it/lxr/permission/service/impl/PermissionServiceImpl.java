package com.it.lxr.permission.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.it.lxr.common.utils.LoggerUtils;
import com.it.lxr.common.utils.Pagination;
import com.it.lxr.permission.dao.PermissionDao;
import com.it.lxr.permission.po.RolePermissionAllocationBo;
import com.it.lxr.permission.po.UPermission;
import com.it.lxr.permission.po.UPermissionBo;
import com.it.lxr.permission.po.URole;
import com.it.lxr.permission.service.IPermissionService;
import com.it.lxr.permission.token.manager.TokenManager;
/**
 * permission service
 *
 */
@Service("permissionService")
public class PermissionServiceImpl implements IPermissionService {
	@Autowired
	PermissionDao permissionDao;

	@Override
	public Set<String> findRoleByUserId(Long userId) {
		// TODO Auto-generated method stub
		Set<String>  roles=	permissionDao.findRoleByUserId(userId);
		return roles;
	}

	@Override
	public Set<String> findPermissionByUserId(Long userId) {
		Set<String>  permissions=	permissionDao.findPermissionByUserId(userId);
		// TODO Auto-generated method stub
		return permissions;
	}

	@Override
	public List<UPermission> findPage(ModelMap modelMap) {
		
		List<UPermission> list =  permissionDao.findPage(modelMap);
		
		return list;
	}

	@Override
	public int findSumPage(ModelMap modelMap) {
		int sum= permissionDao.findSumPage(modelMap);
		// TODO Auto-generated method stub
		return sum;
	}

	@Override
	public Map<String, Object> deletePermissionById(String ids) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			int successCount=0,errorCount=0;
			String resultMsg ="删除%s条，失败%s条";
			String[] idArray = new String[]{};
			if(StringUtils.contains(ids, ",")){
				idArray = ids.split(",");
			}else{
				idArray = new String[]{ids};
			}
			
			for (String idx : idArray) {
				Long id = new Long(idx);
				//查询是否有在使用中的权限（已分配给角色）
				List<UPermission> rolePermissions= permissionDao.findRolePermissionByPid(id);
				if(null != rolePermissions && rolePermissions.size() > 0){
					errorCount += rolePermissions.size();
				}else{
					//删出权限列表
					successCount+=permissionDao.deletePermissionById(id);
				}
			}
			resultMap.put("status", 200);
			//如果有成功的，也有失败的，提示清楚。
			if(errorCount > 0){
				resultMsg = String.format(resultMsg, successCount,errorCount);
			}else{
				resultMsg = "操作成功";
			}
			resultMap.put("resultMsg", resultMsg);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS删除用户出现错误，ids[%s]", ids);
			resultMap.put("status", 500);
			resultMap.put("message", "删除出现错误，请刷新后再试！");
			ArrayList  list = new ArrayList();
		}
		return resultMap;
		

		// TODO Auto-generated method stub
	}

	@Override
	public void insertSelective(UPermission psermission) {
		// TODO Auto-generated method stub
		permissionDao.insertPermission(psermission);
	}

	@Override
	public List<URole> findRolePage(ModelMap modelMap) {
		// TODO Auto-generated method stub
		return 	permissionDao.findRolePage(modelMap);
	}

	@Override
	public int findRoleSumPage(ModelMap modelMap) {
		// TODO Auto-generated method stub
		return permissionDao.findRoleSumPage(modelMap);
	}

	@Override
	public int insertRole(URole role) {
		// TODO Auto-generated method stub
		
		return permissionDao.insertRole(role);
	}

	@Override
	public Map<String, Object> deleteRoleById(String ids) {
		// TODO Auto-generated method stub
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			int count=0;
			String resultMsg = "删除成功。";
			String[] idArray = new String[]{};
			if(StringUtils.contains(ids, ",")){
				idArray = ids.split(",");
			}else{
				idArray = new String[]{ids};
			}
			
			c:for (String idx : idArray) {
				Long id = new Long(idx);
				if(new Long(1).equals(id)){
					resultMsg = "操作成功，But'系统管理员不能删除。";
					continue c;
				}else{
					count+=permissionDao.deleteByPrimaryKey(id);
				}
			}
			resultMap.put("status", 200);
			resultMap.put("count", count);
			resultMap.put("resultMsg", resultMsg);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS删除用户出现错误，ids[%s]", ids);
			resultMap.put("status", 500);
			resultMap.put("message", "删除出现错误，请刷新后再试！");
		}
		return resultMap;
	}

	@Override
	public List<URole> findNowAllPermission() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", TokenManager.getUserId());
		
		List<URole>  roles=permissionDao.findNowAllPermission(map);
		// TODO Auto-generated method stub
		return roles;
	}

	@Override
	public List<RolePermissionAllocationBo> findRoleAndPermissionPage(ModelMap modelMap) {
		// TODO Auto-generated method stub
		return permissionDao.findRoleAndPermission(modelMap);
	}

	@Override
	public int findRoleAndPermissionNum(ModelMap modelMap) {
		// TODO Auto-generated method stub
		return permissionDao.findRoleAndPermissionNum(modelMap);
	}

	@Override
	public List<UPermissionBo> selectPermissionById(Long id) {
		// TODO Auto-generated method stub
		 List<UPermissionBo> selectPermissionById = permissionDao.selectAll();
		 List<String> permissions = permissionDao.selectPermissionById(id);
		 selectPermissionById.forEach(p->p.setRoleIds(permissions));
		 selectPermissionById.forEach(p->p.isCheck());
		return selectPermissionById;
	}
	

}
