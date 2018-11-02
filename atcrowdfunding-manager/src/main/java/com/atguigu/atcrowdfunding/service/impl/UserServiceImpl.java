package com.atguigu.atcrowdfunding.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.dao.UserDao;
import com.atguigu.atcrowdfunding.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	public List<User> queryAll() {
		return userDao.queryAll();
	}

	public User query4Login(User user) {
		return userDao.query4Login(user);
	}

	public List<User> queryPageData(Map<String, Object> map) {
		return userDao.queryPageData(map);
	}

	public List<User> queryPageAll(Map<String, Object> map) {
		return userDao.queryPageAll(map);
	}

	public void insertUser(User user) {
		userDao.insertUser(user);
	}

	public User queryById(Integer id) {
		return userDao.queryById(id);
	}

	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	public void deleteUser(Integer id) {
		userDao.deleteUser(id);
	}

	public void deleteUsers(Map<String, Object> map) {
			userDao.deleteUsers(map);
	}

	public void insertUserRoles(Map<String, Object> map) {
		userDao.insertUserRoles(map);
	}

	public void deletetUserRoles(Map<String, Object> map) {
		userDao.deleteUserRoles(map);
	}
}
