package com.it.lxr.permission.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.it.lxr.common.utils.INI4j;
import com.it.lxr.common.utils.LoggerUtils;
import com.it.lxr.common.utils.Pagination;
import com.it.lxr.permission.dao.PermissionDao;
import com.it.lxr.permission.po.UPermission;
import com.it.lxr.permission.service.IPermissionService;
import com.it.lxr.permission.service.ShiroManager;
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
		}
		return resultMap;
		// TODO Auto-generated method stub
	}

}
