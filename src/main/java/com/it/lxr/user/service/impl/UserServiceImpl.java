package com.it.lxr.user.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.lxr.common.utils.LoggerUtils;
import com.it.lxr.user.dao.UserDao;
import com.it.lxr.user.po.UUser;
import com.it.lxr.user.service.IUserService;
@Service("userService")
public class UserServiceImpl implements IUserService{
	@Autowired	
	UserDao userDao;



	@Override
	public UUser queryUser(Map<String, Object> map)  {
		// TODO Auto-generated method stub
			UUser user = new UUser();
				try {
					user = userDao.queryUser(map);
				} catch (Exception e) {
					// TODO: handle exception
					LoggerUtils.error(UserServiceImpl.class,  e.getMessage());
				}
				return user;
	}



	@Override
	public void updateByPrimaryKeySelective(UUser user) {
		try {
			userDao.updateByPrimaryKeySelective(user);
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		
	}



	@Override
	public UUser insert(UUser entity) {
		// TODO Auto-generated method stub
		
		return userDao.insert(entity);
	}



	
}
