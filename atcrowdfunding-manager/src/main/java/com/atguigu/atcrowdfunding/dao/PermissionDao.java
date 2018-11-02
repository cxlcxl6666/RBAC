package com.atguigu.atcrowdfunding.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.User;

public interface PermissionDao {

	@Select("select * from t_permission where pid=#{id}")
	List<Permission> queryChildren(Integer id);

	@Select("select * from t_permission")
	List<Permission> queryAll();

	void insertPermission(Permission permission);

	@Select("select * from t_permission where id = #{id}")
	Permission queryPermissionById(Integer id);

	void updatePermission(Permission permission);

	@Delete("delete from t_permission where id = #{id}")
	void deletePermissionById(Integer id);

	@Select("select permissionid from t_role_permission where roleid=#{roleid}")
	List<Integer> queryAllPermissionidsByRoleid(Integer roleid);

	List<Permission> queryAllPermissionidsByUser(User dbUser);

}
