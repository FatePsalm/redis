package com.wemew.rediscache.model;

/**
 * 通过此对象封装控制层返回的JSON结果
 * 便于对控制层返回数据进行统一格式化,
 * 友好性管理*/
public class JsonResult {
    public static final int SUCCESS=1000;
    public static final int ERROR=1001;
	/**服务端的响应状态*/
	private int status;
	/**信息(给用户的提示)*/
	private String message;
	/**具体业务数据*/
	private Object data;
	
	public JsonResult() {
		this.status=SUCCESS;
		this.message="ok";
	}

	public JsonResult(Object data) {
		this();
		this.data=data;
	}

	public JsonResult(int status, String message) {
		this.status=status;
		this.message=message;
	}

	public JsonResult(Throwable e, int error){
		this.status=error;
		this.message=e.getMessage();
	}
	public JsonResult(Throwable e){
		this.status=ERROR;
		this.message=e.getMessage();
	}
	public String getMessage() {
		return message;
	}
	public Object getData() {
		return data;
	}
	public int getStatus() {
		return status;
	}



}
