package com.key.commons.lang;

import java.io.Serializable;

public class Sort implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3992572843995793776L;
	public enum SortType {
		ASC(0, "ASC"),
		DESC(1, "DESC");
		
		private int value;

	    private String message = null;

	    private SortType(int value, String message) {
	        this.value = value;
	        this.message = message;
	    }

	    public int getValue() {
	        return value;
	    }

	    public String getMessage() {
	        return message;
	    }
	    
	    /**
	     * 通过枚举<code>value</code>获得枚举
	     * 
	     * @param value
	     * @return
	     */
	    public static SortType getByValue(int value) {
	        for (SortType sortTypeEnum : values()) {
	            if (sortTypeEnum.getValue() == value) {
	                return sortTypeEnum;
	            }
	        }
	        return null;
	    }

	    @Override
	    public String toString() {
	        return value + "|" + message;
	    }
	    
	}
	
	private String column;
	private SortType type;
	
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public SortType getType() {
		return type;
	}
	public void setType(SortType type) {
		this.type = type;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sort other = (Sort) obj;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
}
