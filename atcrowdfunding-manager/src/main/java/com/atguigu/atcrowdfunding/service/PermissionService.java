package com.atguigu.atcrowdfunding.service;

import java.util.List;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.User;

public interface PermissionService {

	List<Permission> queryChildrenPermissions(Permission parent);

	List<Permission> queryAllForTree();

	void insertPermission(Permission permission);

	Permission queryPermissionById(Integer id);

	void updatePermission(Permission permission);

	void deletePermissionById(Integer id);

	List<Permission> queryAll();

	List<Integer> queryAllPermissionidsByRoleid(Integer roleid);

	List<Permission> queryAllPermissionidsByUser(User dbUser);

}
