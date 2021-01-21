package com.liufuhao.crowd.mvc.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.liufuhao.crowd.constant.CrowdConstant;
import com.liufuhao.crowd.entity.Admin;
import com.liufuhao.crowd.service.api.AdminService;

@Controller
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping("/admin/update.html")
	public String update(
			Admin admin,
			@RequestParam("pageNum") Integer pageNum,
			@RequestParam("keyword") String keyword
			) {
		adminService.update(admin);
		return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
	}
	
	@RequestMapping("/admin/to/edit/page.html")
	public String toEditPage(
			@RequestParam("adminId") Integer adminId,
			ModelMap modelMap
			) {
		
		Admin admin = adminService.getAdminById(adminId);
		
		modelMap.addAttribute("admin",admin);
		
		return "admin-edit";
	}
	
	@PreAuthorize("hasAuthority('user:save')")
	@RequestMapping("/admin/save.html")
	public String save(Admin admin) {
		adminService.saveAdmin(admin);
		return "redirect:/admin/get/page.html?pageNum="+Integer.MAX_VALUE;
	}
	
	@RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
	public String remove(
			@PathVariable("adminId") Integer adminId,
			@PathVariable("pageNum") Integer pageNum,
			@PathVariable("keyword") String keyword
			) {
		adminService.remove(adminId);
		//页面跳转：
		return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
	}
	
	@RequestMapping("/admin/get/page.html")
	public String getPageInfo(
			@RequestParam(value = "keyword",defaultValue = "") String keyword,
			@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
			ModelMap modelMap
			) {
		PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
		
		modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
		
		return "admin-page";
	}
	
	@RequestMapping("/admin/do/logout.html")
	public String doLogout(HttpSession session) {
		
		// 强制session失效
		session.invalidate();
		
		return "redirect:/admin/to/login/page.html";
	}
	
	
	@RequestMapping("/admin/do/login.html")
	public String doLogin(
			@RequestParam("loginAcct") String loginAcct,
			@RequestParam("userPswd") String userPswd,
			HttpSession session) {
		// 调用 Service 方法执行登录检查
		// 这个方法如果能够返回 admin 对象说明登录成功，如果账号、密码不正确则会抛出异常
		Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);
		// 将登录成功返回的 admin 对象存入 Session 域
		session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);

		return "redirect:/admin/to/main/page.html";
	}
	

}
