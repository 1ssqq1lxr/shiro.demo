package com.it.lxr.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it.lxr.user.dao.UserDao;
import com.it.lxr.user.service.IUserService;
@Service("userService")
public class UserServiceImpl implements IUserService{
	@Autowired	
	UserDao userDao; 
}
