package com.atguigu.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.atguigu.atcrowdfunding.bean.Role;

public interface RoleService {

	List<Role> queryPageData(Map<String, Object> map);

	List<Role> queryPageAll(Map<String, Object> map);

	List<Role> queryAll();

	List<Role> queryAssignRoles(Integer id);

	void insertPermissions(Map<String, Object> map);

}
