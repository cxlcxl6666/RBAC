package com.atguigu.atcrowdfunding.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import com.atguigu.atcrowdfunding.bean.User;

public interface UserDao {
	
	@Select("select * from t_user")
	List<User> queryAll();

	@Select("select * from t_user where loginacct = #{loginacct} and userpswd=#{userpswd}")
	User query4Login(User user);

	List<User> queryPageData(Map<String, Object> map);

	List<User> queryPageAll(Map<String, Object> map);

	void insertUser(User user);

	@Select("select * from t_user where id = #{id}")
	User queryById(Integer id);

	void updateUser(User user);

	@Delete("delete from t_user where id = #{id}")
	void deleteUser(Integer id);

	void deleteUsers(Map<String, Object> map);

	void insertUserRoles(Map<String, Object> map);

	void deleteUserRoles(Map<String, Object> map);

}
