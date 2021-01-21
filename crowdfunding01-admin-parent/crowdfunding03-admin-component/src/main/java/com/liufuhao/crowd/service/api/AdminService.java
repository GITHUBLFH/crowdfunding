package com.liufuhao.crowd.service.api;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.liufuhao.crowd.entity.Admin;

public interface AdminService {

	void saveAdmin(Admin admin);

	List<Admin> getAll();

	Admin getAdminByLoginAcct(String loginAcct, String userPswd);
	
	PageInfo<Admin> getPageInfo(String keyword,Integer pageNum,Integer pageSize);

	void remove(Integer adminId);

	Admin getAdminById(Integer adminId);

	void update(Admin admin);

	void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);

	Admin getAdminByLoginAcct(String username);
}
