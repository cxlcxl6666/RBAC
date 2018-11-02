package com.atguigu.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.dao.RoleDao;
import com.atguigu.atcrowdfunding.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	public void insertPermissions(Map<String, Object> map) {
		roleDao.DeleteAllPermissionsById(map);
		roleDao.insertPermissions(map);
	}
	
	public List<Role> queryPageData(Map<String, Object> map) {
		return roleDao.queryPageDat(map);
	}

	public List<Role> queryPageAll(Map<String, Object> map) {
		return roleDao.queryPageAll(map);
	}

	public List<Role> queryAll() {
		return roleDao.queryAll();
	}

	public List<Role> queryAssignRoles(Integer id) {
		return roleDao.queryAssignRoles(id);
	}

}
