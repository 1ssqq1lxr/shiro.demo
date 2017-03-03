package com.it.lxr.user.dao;

import java.util.Map;

import com.it.lxr.user.po.UUser;

public interface UserDao {


	UUser queryUser(Map<String, Object> map);

}
