package com.it.lxr.permission.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.ui.ModelMap;

import com.it.lxr.common.utils.Pagination;
import com.it.lxr.permission.po.UPermission;
import com.it.lxr.permission.po.URole;
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
	 * 分页查询权限
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
	/**
	 *插入新的权限
	 * @param psermission
	 */
	void insertSelective(UPermission psermission);
	/**
	 * 分页查询角色
	 * @param modelMap
	 * @return
	 */
	List<URole> findRolePage(ModelMap modelMap);
	/**
	 * 查询所有角色
	 * @param modelMap
	 * @return
	 */
	int findRoleSumPage(ModelMap modelMap);
	/**
	 * 插入角色
	 * @param role
	 * @return
	 */
	int insertRole(URole role);
	/**
	 *根据id删除角色
	 * @param ids
	 * @return
	 */
	Map<String, Object> deleteRoleById(String ids);
	/**
	 * 查询我的角色权限
	 * @return
	 */
	List<URole> findNowAllPermission();

	
	
}
