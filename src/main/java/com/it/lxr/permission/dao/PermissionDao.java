package com.it.lxr.permission.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.ui.ModelMap;

import com.it.lxr.permission.po.UPermission;
import com.it.lxr.user.po.UUser;
/**
 * lxr
 * @author Administrator
 *
 */
public interface PermissionDao {
		/**
		 * 查询当前用户所有角色
		 * @param userId
		 * @return
		 */
	Set<String> findRoleByUserId(Long userId);
		/**
		 * 查询当前用户所有权限
		 * @param userId
		 * @return
		 */
	Set<String> findPermissionByUserId(Long userId);
	/**
	 * 查询所有权限分页
	 * @param modelMap
	 * @return
	 */
	List<UPermission> findPage(ModelMap modelMap);
	/**
	 *查询所有权限数
	 * @param modelMap
	 * @return
	 */
	int findSumPage(ModelMap modelMap);




}
