package com.atguigu.atcrowdfunding.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.service.PermissionService;

public class PermissionInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private PermissionService permissionService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//获取用户的请求路径
		String uri = request.getRequestURI();
		
		User user = (User) request.getSession().getAttribute("user");
		//获取用户的权限
		List<Permission> permisssion = permissionService.queryAll();
		//用于存放所有权限的 url 集合
		List<String> urls = new ArrayList<String>();
		for (Permission permission : permisssion) {
			if(permission.getUrl() != null && !"".equals(permission.getUrl())){
				urls.add(request.getContextPath() + permission.getUrl());
			}
		}
		
		//如果当前路径在urls 中，则证明需要做权限验证
		if(urls.contains(uri)){
			//从session中取出用户的权限的url
			List<String> userPermissionUrls = (List<String>) request.getSession().getAttribute("userPermissionUrls");
			if(userPermissionUrls.contains(uri)){
				return true;	
			}else{
				response.sendRedirect(request.getContextPath() + "/error");
				return false;
			}
			
		}else{
			return true;
		}
	}
}
