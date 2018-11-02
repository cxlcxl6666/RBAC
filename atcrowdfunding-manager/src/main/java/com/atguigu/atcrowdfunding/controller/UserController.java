package com.atguigu.atcrowdfunding.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.AjaxResult;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.bean.Page;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.service.RoleService;
import com.atguigu.atcrowdfunding.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	
	@ResponseBody
	@RequestMapping("/deleteAssignRole")
	public Object deleteAssignRole(Integer[] unassignRoleids, Integer userid){
		AjaxResult result = new AjaxResult();
		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("unassginRoleids", unassignRoleids);
			map.put("userid", userid);
			userService.deletetUserRoles(map);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/doAssignRole")
	public Object doAssignRole(Integer[] assginRoleids, Integer userid){
		AjaxResult result = new AjaxResult();
		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("assginRoleids", assginRoleids);
			map.put("userid", userid);
			userService.insertUserRoles(map);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	
	@RequestMapping("/assign")
	public String assign(Integer id,Model model){
		User user = userService.queryById(id);
		List<Role> roles = roleService.queryAll();
		List<Role> assignRoles = roleService.queryAssignRoles(id);
		List<Role> unassignRoles = new ArrayList<Role>();
		for (Role role : roles) {
			if(!assignRoles.contains(role)){
				unassignRoles.add(role);
			}
		}
		model.addAttribute("user", user);
		model.addAttribute("assignRoles", assignRoles);
		model.addAttribute("unassignRoles", unassignRoles);
		return "user/assign";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/deleteUsers",method=RequestMethod.POST)
	public Object deleteUsers(Integer[] userIds){
		AjaxResult result = new AjaxResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userIds", userIds);
			userService.deleteUsers(map);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/deleteUser",method=RequestMethod.POST)
	public Object deleteUser(Integer id){
		AjaxResult result = new AjaxResult();
		try {
			userService.deleteUser(id);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Object update(User user){
		AjaxResult result = new AjaxResult();
		try {
			userService.updateUser(user);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	
	@RequestMapping("/edit")
	public String edit(Integer id,Model model){
		User user = userService.queryById(id);
		model.addAttribute("user", user);
		return "user/edit";
	}
	
	@ResponseBody
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public Object insert(User user){
		AjaxResult result = new AjaxResult();
		try {
			Date date = new Date();
			String createtime = dateFormat.format(date);
			user.setCreatetime(createtime);
			user.setUserpswd("123456");
			userService.insertUser(user);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/add")
	public String add(){
		return "user/add";
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/queryPage",method=RequestMethod.POST)
	public Object queryPage(String queryText, Integer pageno,Integer pagesize){
		AjaxResult result = new AjaxResult();
		//分页查询
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", (pageno-1)*pagesize);
		map.put("size", pagesize);
		map.put("queryText", queryText);
		
		List<User> users = userService.queryPageData(map);
		//查询所有的账户信息，得到总记录数
		List<User> list = userService.queryPageAll(map);
		Integer totalsize = list.size();
		//最大页码
		Integer totalno = 0;
		if(totalsize % pagesize == 0){
			totalno = totalsize / pagesize;
		}else{
			totalno = totalsize / pagesize + 1;
		}
		try {
			Page<User> userPage = new Page<User>();
			userPage.setDatas(users);
			userPage.setPageno(pageno);
			userPage.setTotalno(totalno);
			userPage.setTotalsize(totalsize);
			result.setData(userPage);
			result.setSuccess(true);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			return result;
		}
	}
	
	@RequestMapping("/index")
	public Object index(){
		return "user/index";
	}
	
	
	@RequestMapping("/index1")
	public String index1(@RequestParam(required=false,defaultValue="1") Integer pageno, @RequestParam(required=false,defaultValue="2") Integer pagesize,
			Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", (pageno-1)*pagesize);
		map.put("size", pagesize);
		List<User> users = userService.queryPageData(map);
		List<User> list = userService.queryPageAll(map);
		Integer totalsize = list.size();
		Integer totalno = 0;
		if(totalsize % pagesize == 0){
			totalno = totalsize / pagesize;
		}else{
			totalno = totalsize / pagesize + 1;
		}
		
		model.addAttribute("totalsize", totalsize);
		model.addAttribute("totalno", totalno);
		model.addAttribute("users", users);
		model.addAttribute("pageno",pageno);
		
		return "user/index";
	}
}
