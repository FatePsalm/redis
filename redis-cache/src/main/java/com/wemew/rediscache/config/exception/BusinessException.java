package com.wemew.rediscache.config.exception;

 /**
   * 作者 CG
   * 时间 2020/2/24 17:27
   * 注释 业务逻辑异常
   */
public class BusinessException extends RuntimeException{
	
	private static final long serialVersionUID = -1629974785937212496L;

	public BusinessException(String msg) {
		super(msg);
	}
	
}
