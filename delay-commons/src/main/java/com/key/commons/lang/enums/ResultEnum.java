package com.key.commons.lang.enums;

import com.wucai.commons.lang.Result;

public enum ResultEnum {

	RESULT_NOT_FOUND("RESULT_NOT_FOUND", "结果为空"), SYSTEM_EXCEPTION("SYSTEM_EXCEPTION", "系统异常");

	private String code;
	private String message;

	private ResultEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public <T> Result<T> toErrorResult(String exceptionMsg) {
		return Result.buildFail(this.code, this.message, exceptionMsg);
	}

	public <T> Result<T> toErrorResult() {
		return toErrorResult(null);
	}

	public <T> Result<T> toSuccessResult() {
		return toSuccessResult(null);
	}

	public <T> Result<T> toSuccessResult(String exceptionMsg) {
		Result<T> result = Result.buildFail(this.code, this.message, exceptionMsg);
		result.setSuccess(true);
		return result;
	}

}
