package com.liufuhao.crowd.Controller;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liufuhao.crowd.api.MySQLRemoteService;
import com.liufuhao.crowd.api.RedisRemoteService;
import com.liufuhao.crowd.constant.CrowdConstant;
import com.liufuhao.crowd.entity.po.MemberPO;
import com.liufuhao.crowd.entity.vo.MemberLoginVO;
import com.liufuhao.crowd.entity.vo.MemberVO;
import com.liufuhao.crowd.util.CrowdUtil;
import com.liufuhao.crowd.util.ResultEntity;

@Controller
public class MemberController {
	
	@Autowired
	private RedisRemoteService redisRemoteService;
	
	@Autowired
    private MySQLRemoteService mySQLRemoteService;
	
	 @RequestMapping("/auth/member/logout")
    public String logout(HttpSession httpSession){
        httpSession.invalidate();
        return "redirect:http://localhost:80/";
    }
	
	@RequestMapping("/auth/member/do/login")
    public String login(@RequestParam("loginacct") String loginacct,
                        @RequestParam("userpswd") String userpswd,
                        ModelMap modelMap,
                        HttpSession httpSession) {
        // 1.调用远程接口根据登录账号查询MemberPO对象
        ResultEntity<MemberPO> resultEntity = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginacct);
        if ("FAILED".equals(resultEntity.getResult())) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, resultEntity.getMessage());
            return "member-login";
        }
        MemberPO memberPO = resultEntity.getData();
        if (memberPO == null) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }
        // 2.比较密码
        String userpswdDataBase = memberPO.getUserpswd();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matchesResult = bCryptPasswordEncoder.matches(userpswd, userpswdDataBase);
        if (!matchesResult) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }
        // 3.创建MemberLoginVO对象存入Session域
        MemberLoginVO memberLoginVO = new MemberLoginVO(memberPO.getId(), memberPO.getUsername(), memberPO.getEmail());
        httpSession.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER, memberLoginVO);

        return "redirect:http://localhost:80/auth/member/to/center/page";
    }
	
	@RequestMapping(value = "/auth/do/member/register",method = RequestMethod.POST)
    public String register(MemberVO memberVO, ModelMap modelMap) {
        // 1.获取用户输入的手机号
        String phoneNum = memberVO.getPhoneNum();
        // 2.拼redis中存储验证码的key
        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
        // 3.从redis读取key对应的value
        ResultEntity<String> resultEntity = redisRemoteService.getRedisStringValueByKey(key);
        // 4.查询操作是否有效
        String result = resultEntity.getResult();
        if ("FAILED".equals(result)) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, resultEntity.getMessage());
            return "member-reg";
        }
        String redisCode = resultEntity.getData();
        if (redisCode == null) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
            return "member-reg";
        }
        // 5.如果能够到value则比较表单的验证码和redis的验证码
        String formCode = memberVO.getCode();
        if (!Objects.equals(formCode, redisCode)) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_INVALID);
            return "member-reg";
        }
        // 6.如果验证码一致，则从redis删除
        redisRemoteService.removeRedisKeyRemote(key);
        // 7.执行密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String userpswd = memberVO.getUserpswd();
        String encode = passwordEncoder.encode(userpswd);
        memberVO.setUserpswd(encode);
        // 8.执行保存
        // 创建空的MemberPO对象
        MemberPO memberPO = new MemberPO();
        // 复制属性
        BeanUtils.copyProperties(memberVO, memberPO);
        // 调用远程方法
        ResultEntity<String> saveMemberResultEntity = mySQLRemoteService.saveMember(memberPO);
        if ("FAILED".equals(saveMemberResultEntity.getResult())) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveMemberResultEntity.getMessage());
            return "member-reg";
        }
        return "redirect:http://localhost:80/auth/member/to/login/page";
    }
	
	@ResponseBody
	@PostMapping("/auth/member/send/short/message")
	public ResultEntity<String> sendMessage(@RequestParam("phoneNum") String phoneNum){
		// 1.发送短信
		ResultEntity<String> sendShortMessage = CrowdUtil.sendShortMessage(phoneNum);
		
		// 2.判断短信的结果
		if ("SUCCESS".equals(sendShortMessage.getResult())) {
			// 3.如果发送成功，则将验证码存入redis中
			String code = sendShortMessage.getData();
			
			String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
			String value = code;
			Long time = 15L;
			TimeUnit timeUnit = TimeUnit.MINUTES;
			ResultEntity<String> setRedisKeyValueRemoteWithTimeout = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, value, time, timeUnit);
			
			if("SUCCESS".equals(setRedisKeyValueRemoteWithTimeout.getResult())){
				return ResultEntity.successWithOutData();
			}else {
				return setRedisKeyValueRemoteWithTimeout;
			}
		}else {
			return sendShortMessage;
		}
	}

}
