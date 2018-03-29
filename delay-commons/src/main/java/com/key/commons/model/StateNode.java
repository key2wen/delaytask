/**   
* @author si.dawei
* @date 2014年11月4日 下午5:21:09
* Copyright (c) 2012 T.b.j,Inc. All Rights Reserved. 
* @version V3.2.0
*/
package com.key.commons.model;

import java.io.Serializable;

/**
 * @Description: 交易记录/购买结果 等动态流程对象节点
 * @author si.dawei
 * @date 2014年11月4日
 */
public class StateNode implements Serializable {
	
	private static final long serialVersionUID = -7097024961745467894L;

	/**
	 * 是否到达当前位子，没有则画到前半格
	 */
	private boolean position;
	
	/**
	 * 当前节点标题
	 */
	private String title;
	
	/**
	 * 当前节点副标题
	 */
	private String subtitle;
	
	public StateNode(){
		
	}

	public StateNode(boolean position, String title, String subtitle){
		this.position = position;
		this.title = title;
		this.subtitle = subtitle;
	}

	/**
	 * @return the position
	 */
	public boolean isPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(boolean position) {
		this.position = position;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the subtitle
	 */
	public String getSubtitle() {
		return subtitle;
	}

	/**
	 * @param subtitle the subtitle to set
	 */
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
}
