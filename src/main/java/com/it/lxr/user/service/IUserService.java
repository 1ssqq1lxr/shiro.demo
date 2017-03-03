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
	 * 通过用户iD查询用户信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public UUser queryUser(Map<String, Object> map)throws Exception;
	
	
	
}
