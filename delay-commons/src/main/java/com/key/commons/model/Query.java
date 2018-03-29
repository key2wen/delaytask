package com.key.commons.model;

import java.io.Serializable;

/**
 * 查询条件<br/>
 * 作为Facade层暴露给外部的通用Query对象父类
 * 定义一些子类共用属性
 *
 * @author luyun
 * @since 2017.01.16 (fund portfolio pre)
 */
public class Query implements Serializable {

    private static final long serialVersionUID = 1877004675323533451L;

    /**
     * 默认的最大查询条数
     */
    protected static final int DEFAULT_LIMIT = 1000;

    /**
     * 最大查询条数
     */
    protected static final int MAX_LIMIT = 5000;

    /**
     * 默认的偏移量
     */
    protected static final int DEFAULT_OFFSET = 0;

    /**
     * 查询条数
     */
    private int limit = DEFAULT_LIMIT;

    /**
     * 偏移量
     */
    private int offset = DEFAULT_OFFSET;
    
    /**
     * 排序字段
     */
    private OrderBy orderBy;

    /**
     * 分表后缀
     */
    private String tableSuffix;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public String getTableSuffix() {
		return tableSuffix;
	}

	public void setTableSuffix(String tableSuffix) {
		this.tableSuffix = tableSuffix;
	}

	/**
     * 针对公共查询做修饰处理，防止参数错误
     */
    public void decorate() {
        offset = offset < 0 ? 0 : offset;
        limit = limit <= 0 || limit > MAX_LIMIT ? DEFAULT_LIMIT : limit;
    }

}
