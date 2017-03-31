package com.it.lxr.permission.po;

import java.io.Serializable;
import java.util.List;

import com.it.lxr.common.utils.StringUtils;

/**
 * 
 * 权限选择
 * @author zhou-baicheng
 *
 */
public class UPermissionBo  implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setCheck(boolean check) {
		this.check = check;
	}

	/**
	 * 是否勾选
	 */
	private String marker;
	/**
	 * role Id
	 */
	private String roleId;
	
	private String name;
	
	private boolean check;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private List<String> roleIds;
	
	
	public List<String> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
		this.check= roleIds.contains(id);
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
	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}
