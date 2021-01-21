package com.liufuhao.crowd.test;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.liufuhao.crowd.entity.Admin;
import com.liufuhao.crowd.entity.Role;
import com.liufuhao.crowd.mapper.AdminMapper;
import com.liufuhao.crowd.mapper.RoleMapper;
import com.liufuhao.crowd.service.api.AdminService;
import com.liufuhao.crowd.service.api.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class CrowdTest {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Test
	public void testRole() {
		for (int i = 0; i < 238; i++) {
			roleMapper.insert(new Role(null,"role"+i));
		}
	}
	@Test
	public void test() {
		for (int i = 0; i < 238; i++) {
			adminMapper.insert(new Admin(null,"loginAcct"+i,"123456","userName"+i,"userName"+i+"@qq.com",null));
		}
	}
	
	@Test
	public void testTx() {
		Admin admin = new Admin(null,"jerry","123456","杰瑞","jerry@qq.com",null);
		adminService.saveAdmin(admin);
	}
	@Test
	public void testConnection() throws SQLException {
		
		Connection connection = dataSource.getConnection();
		System.out.println(connection);
	}
	
	@Test
	public void testMapper() {
		
		Admin admin = new Admin(null,"tom","123123","汤姆","tom@qq.com",null);
		
		int insert = adminMapper.insert(admin);
		System.out.println(insert);
	}
}
