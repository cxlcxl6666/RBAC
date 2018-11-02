package com.atguigu.atcrowdfunding.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.AjaxResult;
import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.service.PermissionService;
import com.sun.mail.imap.protocol.ListInfo;

@Controller
@RequestMapping("/permission")
public class PermissionController {

	
	@Autowired
	private PermissionService permissionService;
	
	
	
	@ResponseBody
	@RequestMapping("/deletePermission")
	public Object deletePermission(Integer id){
		AjaxResult result = new AjaxResult();
		
		try {
			permissionService.deletePermissionById(id);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Object update(Permission permission){
		AjaxResult result = new AjaxResult();
		
		try {
			permissionService.updatePermission(permission);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return  result;
	}
	
	
	@RequestMapping("/edit")
	public String edit(Integer id,Model model){
		Permission permission = permissionService.queryPermissionById(id);
		model.addAttribute("permission", permission);
		return "permission/edit";
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert(Permission permission){
		AjaxResult result = new AjaxResult();
		
		try {
			permissionService.insertPermission(permission);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return  result;
	}
	
	
	@RequestMapping("/add")
	public String add(){
		return "permission/add";
	}
	
	@ResponseBody
	@RequestMapping("/loadAssignData")
	public Object loadAssignData(Integer roleid){
		List<Permission> ps = new ArrayList<Permission>();
		List<Permission> permissions = permissionService.queryAll();
		
		//查询该角色所分配的permissionid
		List<Integer> permissionids = permissionService.queryAllPermissionidsByRoleid(roleid);
		
		Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();
		//将所有的数据放入到 map 中，并使用其之间作为 map 的键
		for (Permission permission : permissions) {
			permissionMap.put(permission.getId(), permission);
		}
		
		for (Permission permission : permissions) {
			if(permissionids.contains(permission.getId())){
				permission.setChecked(true);
			}
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
	
	
	@ResponseBody
	@RequestMapping("/loadData")
	public Object loadData(){
//		List<Permission> permissions = new ArrayList<Permission>();
		/*Permission root = new Permission();
		root.setName("顶级节点");
		
		Permission children = new Permission();
		children.setName("孩子节点");
		
		root.getChildren().add(children);
		
		permissions.add(root);*/
		 
		//使用递归算法，效率太低
//		Permission parent = new Permission();
//		parent.setId(0);
//		List<Permission> permissions = permissionService.queryChildrenPermissions(parent);
		List<Permission> permissions = permissionService.queryAllForTree();
		
		return permissions;
	}
	
	@RequestMapping("/index")
	public String index(){
		return "permission/index";
	}
}
