package com.atguigu.atcrowdfunding.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.atguigu.atcrowdfunding.bean.Role;

public interface RoleDao {

//	@Select("select * from t_role limit #{start},#{size}")
	List<Role> queryPageDat(Map<String, Object> map);

//	@Select("select * from t_role")
	List<Role> queryPageAll(Map<String, Object> map);

	@Select("select * from t_role")
	List<Role> queryAll();

	List<Role> queryAssignRoles(Integer id);

	void insertPermissions(Map<String, Object> map);

	@Delete("delete from t_role_permission where roleid = #{roleid}")
	void DeleteAllPermissionsById(Map<String, Object> map);

}
