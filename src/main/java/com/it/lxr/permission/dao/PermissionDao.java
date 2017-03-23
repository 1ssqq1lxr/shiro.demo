package com.it.lxr.permission.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.ui.ModelMap;

import com.it.lxr.permission.po.UPermission;
import com.it.lxr.permission.po.URole;
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
	/**
	 *查询是否有在使用中的权限（已分配给角色）
	 * @param id
	 * @return
	 */
	List<UPermission> findRolePermissionByPid(Long id);
	/**
	 *	通过id删除权限
	 * @param id
	 * @return
	 */
	int deletePermissionById(Long id);
	/**
	 * 
	 * @param psermission
	 */
	void insertPermission(UPermission psermission);
	/**
	 * 分页查询所有角色
	 * @param modelMap
	 * @return
	 */
	List<URole> findRolePage(ModelMap modelMap);
	/**
	 * 查询角色总数
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
	 * 删除角色
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);

}
