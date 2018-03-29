package com.key.commons.lang;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 推荐只在远程调用的接口层使用, 即对外提供服务的最外层.<br>
 * 例如:<br>
 * <li>facade - service - dao 这种, 请在 facade 层使用 Result,并且无论任何情况,承诺 result 都不能为 null.</li>
 * <li>不建议 service 层的返回结果使用 Result， 一般 service 调用都是同JVM本地调用，增加复杂度而已。 </li>
 *
 * 
 * @param <V> data 对象的泛型
 */
@XmlRootElement
public class Result<V> implements Serializable {

	private static final long serialVersionUID = 6781030660269943247L;

	/**
	 * 表示是否正常的处理了这个请求的，如果处理中发生异常，没有按照预期逻辑处理这个请求，应当返回 false
	 */
	private boolean success = false;

	/**
	 * 不建议使用这个属性， status 应该属于具体业务的信息，具体业务的内容建议放在 data 中封装返回
	 */
	@Deprecated
	private String status;

	/**
	 * 结果数据，如果正常的处理了请求，返回相应的逻辑结果。
	 */
	private V data;

	/**
	 * 错误信息，建议简单易懂，可直接给使用者或用户直接展示，与 errorCode 配合使用
	 */
	private String errorMsg;
	/**
	 * 错误信息代码， 与 errorMsg 配合使用
	 */
	private String errorCode;
	/**
	 * 异常信息，一般是 e.getMessage();
	 */
	private String exceptionMsg;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public V getData() {
		return data;
	}

	public void setData(V data) {
		this.data = data;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	/**
	 * 将 {@link #success} , {@link #errorMsg} , {@link #errorCode},
	 * {@link #exceptionMsg} 拼接成String 返回, 方便调用方记录Log.
	 * 
	 * @return
	 */
	@JsonIgnore
	public String getErrorString() {
		return "ResultErrorString [success=" + success + ", errorCode=" + errorCode + ", errorMsg=" + errorMsg + ", exceptionMsg="
				+ exceptionMsg + "]";
	}

	public static <T> Result<T> buildFail(String errorCode) {
		return buildFail(errorCode, null);
	}

	public static <T> Result<T> buildFail(String errorCode, String errorMsg) {
		return buildFail(errorCode, errorMsg, null);
	}

	public static <T> Result<T> buildFail(String errorCode, String errorMsg, String exceptionMsg) {
		Result<T> result = new Result<T>();
		result.setSuccess(false);
		result.setErrorCode(errorCode);
		result.setErrorMsg(errorMsg);
		result.setExceptionMsg(exceptionMsg);
		return result;
	}

	public static <T> Result<T> buildSucc(T data) {
		Result<T> result = new Result<T>();
		result.setSuccess(true);
		result.setData(data);
		return result;
	}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
