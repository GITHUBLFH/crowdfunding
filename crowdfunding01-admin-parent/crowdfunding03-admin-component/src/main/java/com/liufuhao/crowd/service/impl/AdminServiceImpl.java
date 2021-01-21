package com.liufuhao.crowd.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liufuhao.crowd.constant.CrowdConstant;
import com.liufuhao.crowd.entity.Admin;
import com.liufuhao.crowd.entity.AdminExample;
import com.liufuhao.crowd.entity.AdminExample.Criteria;
import com.liufuhao.crowd.exception.LoginAcctAlreadyInUseException;
import com.liufuhao.crowd.exception.LoginAcctAlreadyInUseforUpdateException;
import com.liufuhao.crowd.exception.LoginFailedException;
import com.liufuhao.crowd.mapper.AdminMapper;
import com.liufuhao.crowd.service.api.AdminService;
import com.liufuhao.crowd.util.CrowdUtil;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public void saveAdmin(Admin admin) {

		// 1.密码加密
		String userPswd = admin.getUserPswd();
//		userPswd = CrowdUtil.md5(userPswd);
		userPswd = passwordEncoder.encode(userPswd);
		admin.setUserPswd(userPswd);

		// 2.设置生成时间
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = format.format(date);
		admin.setCreateTime(createTime);

		// 3.执行保存
		try {
			adminMapper.insert(admin);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (e instanceof DuplicateKeyException) {
				throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
			}
		}
	}

	@Override
	public List<Admin> getAll() {
		return adminMapper.selectByExample(new AdminExample());
	}

	@Override
	public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
		// 1.根据登录账号查询 Admin 对象
		// ①创建 AdminExample 对象
		AdminExample adminExample = new AdminExample();
		// ②创建 Criteria 对象
		Criteria criteria = adminExample.createCriteria();
		// ③在 Criteria 对象中封装查询条件
		criteria.andLoginAcctEqualTo(loginAcct);
		// ④调用 AdminMapper 的方法执行查询
		List<Admin> list = adminMapper.selectByExample(adminExample);
		// 2.判断 Admin 对象是否为 null
		if (list == null || list.size() == 0) {
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		if (list.size() > 1) {
			throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
		}
		Admin admin = list.get(0);
		// 3.如果 Admin 对象为 null 则抛出异常
		if (admin == null) {
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		// 4.如果 Admin 对象不为 null 则将数据库密码从 Admin 对象中取出
		String userPswdDB = admin.getUserPswd();
		// 5.将表单提交的明文密码进行加密
		String userPswdForm = CrowdUtil.md5(userPswd);
		// 6.对密码进行比较
		if (!Objects.equals(userPswdDB, userPswdForm)) {
			// 7.如果比较结果是不一致则抛出异常
			throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		// 8.如果一致则返回 Admin 对象
		return admin;
	}

	@Override
	public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {

		// 1.调用PageHelper的静态方法开启分页功能
		PageHelper.startPage(pageNum, pageSize);

		// 2.执行查询方法
		List<Admin> list = adminMapper.selectAdminByKeyword(keyword);

		// 3.封装到PageInfo对象中
		return new PageInfo<Admin>(list);
	}

	@Override
	public void remove(Integer adminId) {
		adminMapper.deleteByPrimaryKey(adminId);
	}

	@Override
	public Admin getAdminById(Integer adminId) {
		return adminMapper.selectByPrimaryKey(adminId);
	}

	@Override
	public void update(Admin admin) {
		try {
			adminMapper.updateByPrimaryKeySelective(admin);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (e instanceof DuplicateKeyException) {
				throw new LoginAcctAlreadyInUseforUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
			}
		}
	}

	@Override
	public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
		// 1.根据 adminId 删除旧的关联关系数据
		adminMapper.deleteOLdRelationship(adminId);
		// 2.根据 roleIdList 和 adminId 保存新的关联关系
		if(roleIdList != null && roleIdList.size() > 0) {
		adminMapper.insertNewRelationship(adminId, roleIdList);
		}
	}

	@Override
	public Admin getAdminByLoginAcct(String username) {
		
		AdminExample example = new AdminExample();
		
		Criteria criteria = example.createCriteria();
		
		criteria.andLoginAcctEqualTo(username);
		
		List<Admin> list = adminMapper.selectByExample(example);
		
		Admin admin = list.get(0);
		
		return admin;
	}

}
