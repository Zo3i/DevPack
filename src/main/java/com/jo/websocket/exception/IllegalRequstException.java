package com.jo.websocket.exception;

/**
 * 非法请求，记录IP
 */
public class IllegalRequstException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalRequstException(String message) {
		super(message);
	}

	/**
	 * 不打印业务异常堆栈
	 */
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}
}
