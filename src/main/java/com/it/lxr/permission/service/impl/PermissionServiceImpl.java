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
import com.it.lxr.permission.po.URoleBo;
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

	@Override
	public Map<String, Object> addPermission2Role(Long roleId, String ids) {
		permissionDao.deleteByRid(roleId);
		return executePermission(roleId, ids);
	}
	/**
	 * 处理权限 
	 * @param roleId
	 * @param ids
	 * @return
	 */
	private Map<String, Object> executePermission(Long roleId, String ids){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		int count = 0;
		try {
			//如果ids,permission 的id 有值，那么就添加。没值象征着：把这个角色（roleId）所有权限取消。
			if(StringUtils.isNotBlank(ids)){
				String[] idArray = null;
				
				//这里有的人习惯，直接ids.split(",") 都可以，我习惯这么写。清楚明了。
				if(StringUtils.contains(ids, ",")){
					idArray = ids.split(",");
				}else{
					idArray = new String[]{ids};
				}
				//添加新的。
				for (String pid : idArray) {
					//这里严谨点可以判断，也可以不判断。这个{@link StringUtils 我是重写了的} 
					if(StringUtils.isNotBlank(pid)){
//						URolePermission entity = new URolePermission(roleId,new Long(pid));
						count += permissionDao.insertSelective(roleId,pid);
					}
				}
			}
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功");
		} catch (Exception e) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作失败，请重试！");
		}
		//清空拥有角色Id为：roleId 的用户权限已加载数据，让权限数据重新加载  此处查出userid 然后通过redis 查出此userid的session 再clear权限信息 此项目没有整合redis
//		List<Long> userIds = permissionDao.findUserIdByRoleId(roleId);
//		
//		TokenManager.clearUserAuthByUserId(userIds);
		resultMap.put("count", count);
		return resultMap;
		
	}

	@Override
	public Map<String, Object> deleteByRids(String roleIds) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("roleIds", roleIds);
			permissionDao.deleteByRids(resultMap);
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功");
		} catch (Exception e) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作失败，请重试！");
		}
		return resultMap;
	}

	@Override
	public List<RolePermissionAllocationBo> findUserAndRolePage(ModelMap modelMap) {
		// TODO Auto-generated method stub
		return permissionDao.findUserAndRolePage(modelMap);
	}

	@Override
	public int findUserAndRoleNum(ModelMap modelMap) {
		// TODO Auto-generated method stub
		return permissionDao.findUserAndRoleNum(modelMap);
	}

	@Override
	public List<URoleBo> selectRoleByUserId(Long id) {
		// TODO Auto-generated method stub
		 List<URoleBo> selectPermissionById = permissionDao.selectRoleAll();
		 List<String> roles = permissionDao.selecRoleById(id);
		 selectPermissionById.forEach(p->p.setRoleIds(roles));
		 selectPermissionById.forEach(p->p.isCheck());
		return selectPermissionById;
	}

	@Override
	public Map<String, Object> addRole2User(Long userId, String ids) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		int count = 0;
		try {
			//先删除原有的。
			permissionDao.deleteRoleByUserId(userId);
			//如果ids,role 的id 有值，那么就添加。没值象征着：把这个用户（userId）所有角色取消。
			if(StringUtils.isNotBlank(ids)){
				String[] idArray = null;
				
				//这里有的人习惯，直接ids.split(",") 都可以，我习惯这么写。清楚明了。
				if(StringUtils.contains(ids, ",")){
					idArray = ids.split(",");
				}else{
					idArray = new String[]{ids};
				}
				//添加新的。
				for (String rid : idArray) {
					//这里严谨点可以判断，也可以不判断。这个{@link StringUtils 我是重写了的} 
					if(StringUtils.isNotBlank(rid)){
						count += permissionDao.insertUserAndRole(userId,rid);
					}
				}
			}
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功");
		} catch (Exception e) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作失败，请重试！");
		}
		//清空用户的权限，迫使再次获取权限的时候，得重新加载
//		TokenManager.clearUserAuthByUserId(userId);
		resultMap.put("count", count);
		return resultMap;
	}

	@Override
	public Map<String, Object> deleteRoleByUserIds(String userIds) {
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("userIds", userIds);
			permissionDao.deleteRoleByUserIds(userIds);
			resultMap.put("status", 200);
			resultMap.put("message", "操作成功");
		} catch (Exception e) {
			resultMap.put("status", 200);
			resultMap.put("message", "操作失败，请重试！");
		}
		// TODO Auto-generated method stub
		return resultMap;
	}
	

}
