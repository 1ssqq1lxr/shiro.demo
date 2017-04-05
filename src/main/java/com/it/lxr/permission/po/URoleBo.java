package com.it.lxr.permission.po;

import java.io.Serializable;
import java.util.List;

import com.it.lxr.common.utils.StringUtils;

public class URoleBo  implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID (用String， 考虑多个ID，现在只有一个ID)
	 */
	private String id;
	/**
	 * 
	 */
	private String name;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private boolean check;
	private List<String> roleIds;
	
	
	public List<String> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	public boolean isCheck(){
		int s=roleIds.indexOf(id.toString());
		if(s>-1){
			return true;
		}
		else{
			return	false;
		}
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
