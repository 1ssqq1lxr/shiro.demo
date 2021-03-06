package com.it.lxr.permission.realm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.security.auth.login.AccountException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.it.lxr.permission.service.IPermissionService;
import com.it.lxr.permission.token.ShiroToken;
import com.it.lxr.permission.token.manager.TokenManager;
import com.it.lxr.user.po.UUser;
import com.it.lxr.user.service.IUserService;


/**
 * lxr
 * 
 */
public class SampleRealm extends AuthorizingRealm {
	@Autowired
	IUserService userService;	
	@Autowired
	IPermissionService permissionService;	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
    	Long userId = TokenManager.getUserId();
		SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
		//根据用户ID查询角色（role），放入到Authorization里。
		Set<String> roles = permissionService.findRoleByUserId(userId);
		info.setRoles(roles);
		//根据用户ID查询权限（permission），放入到Authorization里。
		Set<String> permissions = permissionService.findPermissionByUserId(userId);
		info.setStringPermissions(permissions);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		// TODO Auto-generated method stub
		ShiroToken token = (ShiroToken) authcToken;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("email", token.getUsername());
		map.put("pswd",token.getPswd());
 		UUser user = null;
		try {
			user = userService.queryUser(map);
			if(null == user){
				throw new AccountException("帐号或密码不正确！");
			/**
			 * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
			 */
			}else if(UUser._0.equals(user.getStatus())){
				throw new DisabledAccountException("帐号已经禁止登录！");
			}else{
				//更新登录时间 last login time
				user.setLastLoginTime(new Date());
				userService.updateByPrimaryKeySelective(user);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new SimpleAuthenticationInfo(user,user.getPswd(), getName());		
	}

//	@Autowired
//	UUserService userService;
//	@Autowired
//	PermissionService permissionService;
//	@Autowired
//	RoleService roleService;
//	
//	public SampleRealm() {
//		super();
//	}
//	/**
//	 *  认证信息，主要针对用户登录， 
//	 */
//	protected AuthenticationInfo doGetAuthenticationInfo(
//			AuthenticationToken authcToken) throws AuthenticationException {
//		
//	
//    }
//
//	 /** 
//     * 授权 
//     */  
//    @Override  
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {  
//    	
//    	Long userId = TokenManager.getUserId();
//		SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
//		//根据用户ID查询角色（role），放入到Authorization里。
//		Set<String> roles = roleService.findRoleByUserId(userId);
//		info.setRoles(roles);
//		//根据用户ID查询权限（permission），放入到Authorization里。
//		Set<String> permissions = permissionService.findPermissionByUserId(userId);
//		info.setStringPermissions(permissions);
//        return info;  
//    }  
//    /**
//     * 清空当前用户权限信息
//     */
	public  void clearCachedAuthorizationInfo() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
	/**
	 * 指定principalCollection 清除
	 */
	public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
}
