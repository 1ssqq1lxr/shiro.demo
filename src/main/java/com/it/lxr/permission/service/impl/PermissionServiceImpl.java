package com.it.lxr.permission.service.impl;

import java.io.IOException;
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

import com.it.lxr.common.utils.INI4j;
import com.it.lxr.common.utils.LoggerUtils;
import com.it.lxr.permission.dao.PermissionDao;
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

}
