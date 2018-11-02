package com.atguigu.atcrowdfunding.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.dao.PermissionDao;
import com.atguigu.atcrowdfunding.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService{

	@Autowired
	private PermissionDao permissionDao;
	
	public List<Permission> queryAllPermissionidsByUser(User dbUser) {
		return permissionDao.queryAllPermissionidsByUser(dbUser);
	}
	
	public List<Integer> queryAllPermissionidsByRoleid(Integer roleid) {
		return permissionDao.queryAllPermissionidsByRoleid(roleid);
	}
	
	public void deletePermissionById(Integer id) {
		permissionDao.deletePermissionById(id);
	}
	
	public void updatePermission(Permission permission) {
		permissionDao.updatePermission(permission);
	}
	
	public Permission queryPermissionById(Integer id) {
		return permissionDao.queryPermissionById(id);
	}
	
	public void insertPermission(Permission permission) {
		permissionDao.insertPermission(permission);
	}
	
	public List<Permission> queryChildrenPermissions(Permission parent) {
		queryChildren(parent);
		return parent.getChildren();
	}
	
	public void queryChildren(Permission parent){
		List<Permission> children = permissionDao.queryChildren(parent.getId());
		for (Permission permission : children) {
			queryChildren(permission);
		}
		parent.setChildren(children);
	}

	public List<Permission> queryAllForTree() {
		List<Permission> ps = new ArrayList<Permission>();
		List<Permission> permissions = permissionDao.queryAll();
		
		//此种方式使用ArrayList，未使用到索引，查询效率较低
		/*for (Permission permission : permissions) {
			//如果pid==0,则是root节点，直接返回
			if(permission.getPid() == 0){
				ps.add(permission);
			}else{
				//如果不是root节点，则寻找其父节点
				for (Permission innerPermission : permissions) {
					//如果找到一个permission的id与其pid相等，则找到其父节点，让其加入到父节点中
					//然后结束当前循环
					if(permission.getPid().equals(innerPermission.getId())){
						innerPermission.getChildren().add(permission);
						break;
					}
				}
			}
		}*/
		Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();
		//将所有的数据放入到 map 中，并使用其之间作为 map 的键
		for (Permission permission : permissions) {
			permissionMap.put(permission.getId(), permission);
		}
		
		for (Permission permission : permissions) {
			if(permission.getPid() == 0){
				ps.add(permission);
			}else{
				//更具其 pid 从 map 中找出其符节点，这样就少去了一侧循环，使时间复杂度降低
				Permission parent = permissionMap.get(permission.getPid());
				parent.getChildren().add(permission);
			}
		}
		
		return ps;
	}

	public List<Permission> queryAll() {
		return permissionDao.queryAll();
	}

}
