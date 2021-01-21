package com.liufuhao.crowd.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import com.liufuhao.crowd.constant.CrowdConstant;

/**
 * 
 * @author Administrator
 *
 */
public class CrowdUtil {
	
	
    /*
     * 给远程第三方短信接口发送请求把验证码发送到用户手机上
     * */
    public static ResultEntity<String> sendShortMessage(String phoneNum) {
    	// 生成验证码
    	StringBuilder builder = new StringBuilder();
    	for (int i = 0; i < 4; i++) {
    		int random = (int) (Math.random() * 10);
    		builder.append(random);
    	}
    	String code = builder.toString();
    	System.out.println("========"+phoneNum+"验证码是："+code);
    	return ResultEntity.successWithData(code);
    }
	
	/**
	 * 对明文字符串进行MD5加密
	 * @param source 传入的明文字符串
	 * @return 加密的结果
	 */
	public static String md5(String source) {
		
		//1.判断source是否有效
		if(source == null || source.length() == 0) {
			//2.如果不是有效的字符串抛出异常
			throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
		}
		//3.获取MessageDigest对象
		String algorithm = "md5";
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			
			// 4.获取明文字符串对应的字节数组
			byte[] input = source.getBytes();
			
			// 5.执行加密
			byte[] output = messageDigest.digest(input);
			
			// 6.创建BigInteger对象
			int signum = 1;
			BigInteger bigInteger = new BigInteger(signum, output);
			
			// 7.按照 16 进制将 bigInteger 的值转换为字符串
			int radix = 16;
			String encoded = bigInteger.toString(radix).toUpperCase();
			
			return encoded;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 判断当前请求是否为ajax请求
	 * @param request
	 * @return
	 * 			true: 当前请求是Ajax请求
	 * 			false: 当前请求不是ajax请求
	 */
	public static boolean judgeRequestType(HttpServletRequest request) {
		
		
		// 1.获取请求消息头信息
		String acceptHeader = request.getHeader("Accept");
		String xRequestHeader = request.getHeader("X-Requested-With");
		
		// 2.检查并返回
				
		return (acceptHeader != null && acceptHeader.contains("application/json")
				|| 
				(xRequestHeader != null && xRequestHeader.contains("XMLHttpRequest"))
				);
	}
	
}
