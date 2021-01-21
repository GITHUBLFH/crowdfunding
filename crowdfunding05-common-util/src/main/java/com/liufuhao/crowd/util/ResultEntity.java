package com.liufuhao.crowd.util;

/**
 * 统一整个项目中ajax请求返回的结果，（未来也可以用于分布式架构的各个模块之间的调用返回同一类型）
 * @author Administrator
 *
 * @param <T>
 */
public class ResultEntity<T> {

	private static final String SUCCESS = "SUCCESS";
	public static final String FAILED = "FAILED";

	// 用来封装当前请求处理的结果是成功还是失败
	private String result;

	// 请求处理失败时返回的错误消息
	private String message;

	// 要返回的数据
	private T data;

	/**
	 * 返回操作结果为成功，不带数据
	 * 
	 * @return
	 */
	public static <E> ResultEntity<E> successWithOutData() {
		return new ResultEntity<E>(SUCCESS, null, null);
	}

	/**
	 * 返回操作结果为成功，携带数据
	 * 
	 * @param data * @return
	 */
	public static <E> ResultEntity<E> successWithData(E data) {
		return new ResultEntity<E>(SUCCESS, null, data);
	}

	/**
	 * 返回操作结果为失败，不带数据
	 * 
	 * @param message 失败的消息
	 * @return
	 */
	public static <E> ResultEntity<E> failed(String message) {
		return new ResultEntity<E>(FAILED, message, null);
	}

	public ResultEntity() {

	}

	public ResultEntity(String result, String message, T data) {
		super();
		this.result = result;
		this.message = message;
		this.data = data;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResultEntity [result=" + result + ", message=" + message + ", data=" + data + "]";
	}

}
