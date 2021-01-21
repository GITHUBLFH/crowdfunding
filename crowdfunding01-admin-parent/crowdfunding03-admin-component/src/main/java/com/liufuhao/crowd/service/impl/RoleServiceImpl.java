package com.liufuhao.crowd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liufuhao.crowd.entity.Role;
import com.liufuhao.crowd.entity.RoleExample;
import com.liufuhao.crowd.entity.RoleExample.Criteria;
import com.liufuhao.crowd.mapper.RoleMapper;
import com.liufuhao.crowd.service.api.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
		// 1.调用PageHelper的静态方法开启分页功能
		PageHelper.startPage(pageNum, pageSize);
		
		// 2.执行查询方法
		List<Role> list = roleMapper.selectRoleByKeyword(keyword);
		
		// 3.封装到PageInfo对象中
		return new PageInfo<Role>(list);
	}

	@Override
	public void saveRole(Role role) {
		roleMapper.insert(role);
	}

	@Override
	public void updateRole(Role role) {
		roleMapper.updateByPrimaryKey(role);
		
	}

	@Override
	public void removeRole(List<Integer> roleIdList) {
		
		RoleExample example = new RoleExample();
		
		Criteria criteria = example.createCriteria();
		
		criteria.andIdIn(roleIdList);
		
		roleMapper.deleteByExample(example);
	}

	@Override
	public List<Role> getAssignedRole(Integer adminId) {
		return roleMapper.selectAssignedRole(adminId);
	}

	@Override
	public List<Role> getUnAssignedRole(Integer adminId) {
		return roleMapper.selectUnAssignedRole(adminId);
	}
	
	
}
