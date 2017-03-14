package com.it.lxr.permission.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.ui.ModelMap;

import com.it.lxr.common.utils.Pagination;
import com.it.lxr.permission.po.UPermission;
import com.it.lxr.user.po.UUser;

/**
 * 权限Service
 * @author Administrator
 *
 */
public interface IPermissionService  {
	/**
	 * 通过userID查询权限
	 * @param userId
	 * @return
	 */
	Set<String> findRoleByUserId(Long userId);
	/**
	 * 通过userId查询角色
	 * @param userId
	 * @return
	 */
	Set<String> findPermissionByUserId(Long userId);
	/**
	 * 通过
	 * @param modelMap
	 * @return
	 */
	List<UPermission> findPage(ModelMap modelMap);
	/**
	 * 查询总共权限数
	 * @param modelMap
	 * @return
	 */
	int findSumPage(ModelMap modelMap);
	/**
	 *	删除所有权限
	 * @param ids
	 * @return
	 */
	Map<String, Object> deletePermissionById(String ids);

	
	
}