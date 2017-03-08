package com.it.lxr.permission.service;

import java.util.Map;
import java.util.Set;

import com.it.lxr.user.po.UUser;

/**
 * 权限Service
 * @author Administrator
 *
 */
public interface IPermissionService  {

	Set<String> findRoleByUserId(Long userId);

	Set<String> findPermissionByUserId(Long userId);

	
	
}
