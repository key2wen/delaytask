package com.key.commons.lang;

import java.io.Serializable;

public class Range implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1998324749024246401L;
	private Object start;
	private Object end;
	
	public Object getStart() {
		return start;
	}
	public void setStart(Object start) {
		this.start = start;
	}
	public Object getEnd() {
		return end;
	}
	public void setEnd(Object end) {
		this.end = end;
	}
	
}
