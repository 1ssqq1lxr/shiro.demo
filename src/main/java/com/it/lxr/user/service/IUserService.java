package com.it.lxr.user.service;

import java.util.Map;

import com.it.lxr.user.po.UUser;

/**
 * 用户Service
 * @author Administrator
 *
 */
public interface IUserService  {
	/**
	 * 通过用户email 跟 paswd 查询用户信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public UUser queryUser(Map<String, Object> map);
	/**
	 * 通过注解更新数据
	 * @param user
	 */
	public void updateByPrimaryKeySelective(UUser user);
	/**
	 * 插入注册用户数据
	 * @param entity
	 * @return
	 */
	public UUser insert(UUser entity);

	
	
	
}
