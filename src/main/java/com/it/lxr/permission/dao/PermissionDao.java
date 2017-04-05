package com.it.lxr.permission.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.ui.ModelMap;

import com.it.lxr.permission.po.RolePermissionAllocationBo;
import com.it.lxr.permission.po.UPermission;
import com.it.lxr.permission.po.UPermissionBo;
import com.it.lxr.permission.po.URole;
import com.it.lxr.permission.po.URoleBo;
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
	/**
	 * 查询我的角色权限（-对多）
	 * @param map
	 * @return
	 */
	List<URole> findNowAllPermission(Map<String, Object> map);
	/**
	 * 查询角色分配
	 * @param modelMap
	 * @return
	 */
	List<RolePermissionAllocationBo> findRoleAndPermission(ModelMap modelMap);
	/**
	 * 查询角色分配总数
	 * @param modelMap
	 * @return
	 */
	int findRoleAndPermissionNum(ModelMap modelMap);
	/**
	 * c通过用户查询权限
	 * @param id 
	 * @return
	 */
	List<String> selectPermissionById(Long id);
	/**
	 * 查询若有权限
	 * @return
	 */
	List<UPermissionBo> selectAll();
	/**
	 * 删除此角色权限
	 * @param roleId
	 */
	void deleteByRid(Long roleId);
	/**
	 * 角色权限建立关系
	 * @param roleId
	 * @param pid
	 * @return
	 */
	int insertSelective(Long roleId, String pid);
	/**
	 * 清空所有角色权限
	 * @param resultMap
	 */
	void deleteByRids(Map<String, Object> resultMap);
	/**
	 *用户角色分配
	 * @param modelMap
	 * @return
	 */
	List<RolePermissionAllocationBo> findUserAndRolePage(ModelMap modelMap);
	/**
	 * 用户角色分配总记录数
	 * @param modelMap
	 * @return
	 */
	int findUserAndRoleNum(ModelMap modelMap);
	/**
	 * 查询所有角色
	 * @return
	 */
	List<URoleBo> selectRoleAll();
	/**
	 * 通过用户id查询所有角色
	 * @param id
	 * @return
	 */
	List<String> selecRoleById(Long id);

}
