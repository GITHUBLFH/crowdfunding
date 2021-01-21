package com.liufuhao.crowd.constant;

/**
 * 创建一个类，管理我的平时用的常量的使用
 * @author Administrator
 *
 */
public class CrowdConstant {
	
	public static final String MESSAGE_LOGIN_FAILED = "登录失败！请确认账号密码是否正确！";
	public static final String MESSAGE_LOGIN_ACCT_ALREADY_IN_USE = "抱歉，这个账号已经被使用了！";
	public static final String MESSAGE_ACCESS_FORBIDEN = "请登录以后再访问！";
	public static final String MESSAGE_ACCESS_DENIED = "抱歉！你不能访问这个资源！";
	public static final String MESSAGE_STRING_INVALIDATE = "字符串不合法！请不要传入空字符串！";
	public static final String MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE = "系统出错，账号不唯一！";
	public static final String MESSAGE_CODE_NOT_EXISTS = "验证码已过期！请重新发送！";
	public static final Object MESSAGE_CODE_INVALID = "验证码不正确！";
	public static final Object MESSAGE_HEADER_PIC_EMPTY = "照片不允许为空！";
	public static final String MESSAGE_DETAIL_PIC_EMPTY = "上传为空！";
	public static final Object MESSAGE_HEADER_PIC_UPLOAD_FAILED = "头像上传失败！";
	public static final Object MESSAGE_DETAIL_PIC_UPLOAD_FAILED = "详图上传失败！";
	public static final String MESSAGE_TEMPLE_PROJECT_MISSING = "临时存储的project对象丢失！";
	
	public static final String ATTR_NAME_EXCEPTION = "exception";
	public static final String ATTR_NAME_LOGIN_ADMIN = "loginAdmin";
	public static final String ATTR_NAME_LOGIN_MEMBER = "loginMember";
	public static final String ATTR_NAME_PAGE_INFO = "pageInfo";
	public static final String REDIS_CODE_PREFIX = "REDIS_CODE_PREFIX_";
	public static final String ATTR_NAME_MESSAGE = "message";
	public static final String ATTR_NAME_TEMPLE_PROJECT = "tempProject";
	public static final String ATTR_NAME_PORTAL_DATA = "portal_data";

}
