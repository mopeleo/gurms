package org.gurms.common.exception;

/**
 * 公用的Exception.
 * 继承自RuntimeException,会触发Spring的事务管理引起事务回退.
 */
public class GurmsException extends RuntimeException {
	public GurmsException() {
		super();
	}

	public GurmsException(String message, Throwable cause) {
		super(message, cause);
	}

	public GurmsException(String message) {
		super(message);
	}

	public GurmsException(Throwable cause) {
		super(cause);
	}
}
