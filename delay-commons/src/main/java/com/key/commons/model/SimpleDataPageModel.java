package com.key.commons.model;

import java.io.Serializable;
import java.util.List;

public class SimpleDataPageModel<T> implements Serializable {

	private static final long serialVersionUID = 985131631545674105L;

	/**
	 * true,已为最后一条； false,后面还有记录；
	 */
	private Boolean endFlag;

	/**
	 * 结果列表
	 */
	private List<T> dataList;

	public Boolean getEndFlag() {
		return endFlag;
	}

	public void setEndFlag(Boolean endFlag) {
		this.endFlag = endFlag;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public static <T> SimpleDataPageModel<T> buildRetResult(List<T> dataList, boolean endFlag) {
		SimpleDataPageModel<T> pageModel = new SimpleDataPageModel<T>();
		pageModel.setEndFlag(endFlag);
		pageModel.setDataList(dataList);

		return pageModel;
	}

}
