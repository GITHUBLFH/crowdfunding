package com.liufuhao.crowd.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liufuhao.crowd.constant.CrowdConstant;
import com.liufuhao.crowd.entity.po.MemberPO;
import com.liufuhao.crowd.service.MemberService;
import com.liufuhao.crowd.util.ResultEntity;

@RestController
public class MemberProviderController {

	@Autowired
	private MemberService memberService;
	
	 @RequestMapping("/save/member/remote")
	 public ResultEntity<String> saveMember(@RequestBody MemberPO memberPO){
	        try {
	            memberService.saveMember(memberPO);
	            return ResultEntity.successWithOutData();
	        }catch (Exception e){
	            if (e instanceof DuplicateKeyException){
	                return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
	            }
	            return ResultEntity.failed(e.getMessage());
	        }
	    }
	
	@RequestMapping("/get/memberpo/by/login/acct/remote")
	public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct){

		try {
			// 1.调用本地service完成查询
			MemberPO memberPO = memberService.getMemberPOByLoginAcct(loginacct);
			// 2.如果没有抛出异常，那么返回成功的结果
			return ResultEntity.successWithData(memberPO);
		} catch (Exception e) {
			// 3.如果捕获到异常，则是返回失败的结果
			return ResultEntity.failed(e.getMessage());
		}
		
	}
}
