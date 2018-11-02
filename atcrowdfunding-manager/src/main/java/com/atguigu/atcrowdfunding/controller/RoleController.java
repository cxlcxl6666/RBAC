package com.atguigu.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.AjaxResult;
import com.atguigu.atcrowdfunding.bean.Page;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@ResponseBody
	@RequestMapping("/doAssign")
	public Object doAssign(Integer roleid, Integer[] permissionids){
		AjaxResult result = new AjaxResult();
		
		try {
			Map<String , Object> map = new HashMap<String, Object>();
			map.put("roleid", roleid);
			map.put("permissionids", permissionids);
			roleService.insertPermissions(map);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	@RequestMapping("/assign")
	public String assign(){
		return "role/assign";
	}
	
	
	@ResponseBody
	@RequestMapping("/queryPage")
	public Object queryPage(String queryText, Integer pageno,Integer pagesize){
		AjaxResult result = new AjaxResult();
		//分页查询
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", (pageno-1)*pagesize);
		map.put("size", pagesize);
		map.put("queryText", queryText);
		
		List<Role> roles = roleService.queryPageData(map);
		
		//查询所有的账户信息，得到总记录数
		List<Role> list = roleService.queryPageAll(map);
		Integer totalsize = list.size();
		//最大页码
		Integer totalno = 0;
		if(totalsize % pagesize == 0){
			totalno = totalsize / pagesize;
		}else{
			totalno = totalsize / pagesize + 1;
		}
		try {
			Page<Role> rolePage = new Page<Role>();
			rolePage.setDatas(roles);
			rolePage.setPageno(pageno);
			rolePage.setTotalno(totalno);
			rolePage.setTotalsize(totalsize);
			result.setData(rolePage);
			result.setSuccess(true);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			return result;
		}
	}
	
	@RequestMapping("/index")
	public String index(){
		return "role/index";
	}
	
}
