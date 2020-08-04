package com.jo.websocket.exception;

/**
 * @version 1.0
 * @author linwei
 * @data 2018年4月11日
 * @描述:
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String code;
	private String message;

	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		this.message = message;
	}

	public BusinessException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	public String getCode() {
		return this.code;
	}

	/**
	 * 不打印业务异常堆栈
	 */
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

}
