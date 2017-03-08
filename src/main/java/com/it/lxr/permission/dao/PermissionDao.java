package com.it.lxr.permission.dao;

import java.util.Map;
import java.util.Set;

import com.it.lxr.user.po.UUser;

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




}
