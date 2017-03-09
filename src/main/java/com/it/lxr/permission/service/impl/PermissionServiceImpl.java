package com.it.lxr.permission.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

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

}
