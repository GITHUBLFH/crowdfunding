package com.liufuhao.crowd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.liufuhao.crowd.entity.po.MemberPO;
import com.liufuhao.crowd.entity.po.MemberPOExample;
import com.liufuhao.crowd.entity.po.MemberPOExample.Criteria;
import com.liufuhao.crowd.mapper.MemberPOMapper;
import com.liufuhao.crowd.service.MemberService;

//在类上使用@Transactional(readOnly = true)针对查询操作设置事务属性
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberPOMapper memberPOMapper;

	@Override
	public MemberPO getMemberPOByLoginAcct(String loginacct) {
		
		MemberPOExample example = new MemberPOExample();
		
		Criteria criteria = example.createCriteria();
		criteria.andLoginacctEqualTo(loginacct);
		
		List<MemberPO> list = memberPOMapper.selectByExample(example);
		
		if(list == null || list.size() == 0) {
			return null;
		}
		
		return list.get(0);
	}

	@Transactional(
			propagation = Propagation.REQUIRES_NEW, 
			rollbackFor = Exception.class,
			readOnly = false)
	@Override
	public void saveMember(MemberPO memberPO) {
		memberPOMapper.insertSelective(memberPO);
	}
	
	
	
}
