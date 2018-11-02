package com.atguigu.atcrowdfunding.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.AjaxResult;
import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.service.PermissionService;
import com.atguigu.atcrowdfunding.service.UserService;

@Controller
public class DispatcherController {

	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping("/error")
	public String error(){
		return "error";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session){
//		session.removeAttribute("user");
		session.invalidate();
		return "redirect:/login";
	}
	
	@RequestMapping("/main")
	public String main(){
		return "main";
	}
	
	@ResponseBody
	@RequestMapping(value="/doAjaxLogin",method=RequestMethod.POST)
	public Object doAjaxLogin(User user,HttpSession session,HttpServletRequest request){
		AjaxResult result = new AjaxResult();
		User dbUser = userService.query4Login(user);
		if(dbUser != null){
			result.setSuccess(true);
			
			//获取用户的权限
			List<Permission> permissions = permissionService.queryAllPermissionidsByUser(dbUser);
			//存放用户权限的 url 地址,注意加上当前项目路径
			List<String> userPermissionUrls = new ArrayList<String>();
			Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();
			for (Permission permission : permissions) {
				permissionMap.put(permission.getId(), permission);
				userPermissionUrls.add(request.getContextPath() + permission.getUrl());
			}
			
			Permission root = null;
			for (Permission permission : permissions) {
				if(permission.getPid() == 0){
					root = permission;
				}else{
					Permission parent = permissionMap.get(permission.getPid());
					parent.getChildren().add(permission);
				}
			}
			
			session.setAttribute("user", dbUser);
			session.setAttribute("rootPermission", root);
			session.setAttribute("userPermissionUrls", userPermissionUrls);
			return result;
		}else{
			result.setSuccess(false);
			return result;
		}
	}

	@RequestMapping(value = "/dologin", method = RequestMethod.POST)
	public String dologin(User user, Model model) {
		User dbUser = userService.query4Login(user);
		if (dbUser != null) {
			return "main";
		} else {
			String errorMsg = "登录账号或密码不正确，请重新登录";
			model.addAttribute("errorMsg", errorMsg);
			return "redirect:login";
		}
	}

	/**
	 * 执行登录功能
	 * 
	 * @return
	 */
	@RequestMapping("/login")
	public String login() {

		return "login";

	}
}
