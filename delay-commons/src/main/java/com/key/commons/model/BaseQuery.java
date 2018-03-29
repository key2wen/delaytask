package com.key.commons.model;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 公共的分页查询类
 * 建议只给web端使用，
 * 后端通过调用getPagingParam(int count)来简化参数
 * </p>
 * 
 * @author <a href=" mailto:zhou.huajun@tongbanjie.com">zhou.huajun</a>
 * @version 1.0 Copyright (c) 2013 T.b.j,Inc. All Rights Reserved.
 */
public class BaseQuery extends BaseDO implements Serializable {

    private static final long     serialVersionUID = -1682356143695291160L;

    /** must lower case! */
    private static final String[] sqlKeys          = { ";", "select", "delete", "update", "create", "drop", "insert",
            "alter", "truncate", "declare", "\\", "/", "=", "#", "$", "&", "(", ")", "exec ", " or " };

    private static final String[] filterkeys       = { "_create" };

    @SuppressWarnings("rawtypes")
    private List resultList;

    /**
     * @return the maxpage
     */
    public int getMaxpage() {
        return maxpage;
    }

    public void setMaxpage(int maxPage) {

    }

    /**
     * 默认分页记录显示数
     */
    public static final int DEFAULT_PAGE_SIZE = 20;

    private int             pageSize          = DEFAULT_PAGE_SIZE; // 每页记录数. webPage15,数据型应用10~25
    private int             totalCount        = -1;               // 总记录数
    private int             pageNo            = 1;                // 当前页码
    private int             pageCount         = 1;                //
    private int             startRow          = 0;
    private int             endRow            = 20;
    private String countAlias        = "";               // 汇总字段的表或视图的别名.
    private String orderByClause;                        // order by

    private final int       maxpage           = 10;

    /**
     * 非数据库字段-标记
     */
    private String jflag;

    private int             startPage         = 1;

    private int             endPage;

    public int getStartPage() {

        if (pageCount <= maxpage) {

            startPage = 1;
            return startPage;
        }
        if (pageNo + 5 <= maxpage) {
            startPage = 1;
            return startPage;
        }

        if (pageNo + 5 >= pageCount) {
            startPage = pageCount - maxpage + 1;
            return startPage;
        }

        startPage = pageNo + 5 - maxpage + 1;

        return startPage;
    }

    public void setStartPage(int startPage) {
    }

    public int getEndPage() {
        if (pageCount <= maxpage) {

            endPage = pageCount;
            return endPage;
        }
        if (pageNo + 5 <= maxpage) {
            endPage = maxpage;
            return endPage;
        }

        if (pageNo + 5 >= pageCount) {
            endPage = pageCount;
            return endPage;
        }

        endPage = pageNo + 5;

        return endPage;
    }

    public void setEndPage(int endPage) {

    }

    public String getJflag() {
        return jflag;
    }

    public void setJflag(String jflag) {
        this.jflag = jflag;
    }

    public BaseQuery(){
    }

    /**
     * 设置分页各参数
     * 
     * @param count
     */
    public void doPage(int count) {

        this.totalCount = count;
        if (pageSize <= 0) this.pageSize = DEFAULT_PAGE_SIZE;
        int mod = -1;
        if (totalCount != -1) {
            if (totalCount > pageSize) {
                mod = totalCount % pageSize;
                if (mod != 0) {
                    pageCount = (totalCount / pageSize) + 1;
                } else {
                    pageCount = (totalCount / pageSize);
                }
            } else {
                pageCount = 1;
            }
        }
        if (pageNo <= 0) this.pageNo = 1;
        if (pageNo > pageCount) this.pageNo = pageCount;
        this.startRow = (this.pageNo - 1) * this.pageSize; // mysql: 0; Oracle: 1
        this.endRow = this.pageNo * this.pageSize;
    }

    public void doPage(int count, int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.doPage(count);
    }

    public boolean isFirstPage() {
        return this.getPageNo() == 1;
    }

    public void setFirstPage(boolean isFirstPage) {
    }

    public boolean isLastPage() {
        return this.getPageCount() == this.getPageNo();
    }

    public void setLastPage(boolean isLastPage) {
    }

    /**
     * currentPage The currentPage to set.
     */
    public void setCurrentPage(Integer cPage) {
        this.pageNo = ((cPage == null) || (cPage.intValue() <= 0)) ? 1 : cPage;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int iPageNo) {
        this.pageNo = iPageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    /**
     * 客户端程序设置每页条数
     * 
     * @param iPageSize
     */
    public void setPageSize(int iPageSize) {
        if (iPageSize >= 1) {
            this.pageSize = iPageSize;
        }
    }

    /**
     * 客户端程序设置总数 一般不推荐使用 该值自己算
     * 
     * @param iPageSize
     */

    public int getTotalCount() {
        return this.totalCount == -1 ? 0 : totalCount;
    }

    public void setTotalCount(int iTotalCount) {
        if (iTotalCount > 0) {
            this.totalCount = iTotalCount;
        }
    }

    public int getPageCount() {
        return pageCount;
    }

    /**
     * 这个不能被客户端程序调用
     * 
     * @param iPageCount
     */
    public void setPageCount(int iPageCount) {
        this.pageCount = iPageCount;
    }

    public boolean isHasNextPage() {
        return this.getPageNo() < this.getPageCount();
    }

    public void setHasNextPage(boolean isHasNextPage) {
    }

    public String getCountAlias() {
        return countAlias;
    }

    public void setCountAlias(String countAlias) {
        this.countAlias = countAlias;
    }

    public boolean isHasPrevPage() {
        return this.getPageNo() > 1;
    }

    public void setHasPrevPage(boolean isHasPrevPage) {
    }

    public int getPrePage() {
        return (this.pageNo < 2) ? 1 : this.pageNo - 1;
    }

    public void setPrePage(int prePage) {
    }

    public int getNextPage() {
        return (this.pageNo >= this.pageCount) ? this.pageCount : this.pageNo + 1;
    }

    public void setNextPage(int nextPage) {

    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        if(startRow<0){
            startRow=0;
        }
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * 排序字段，不允许包含关键字 ==final
     * 
     * @param orderByClause
     */
    public final void setOrderByClauses(String orderByClause) {
        if (StringUtils.isBlank(orderByClause)) return;
        orderByClause = orderByClause.toLowerCase();
        for (int i = 0; i < sqlKeys.length; i++) {
            if (orderByClause.indexOf(sqlKeys[i]) >= 0) {
                for (int j = 0; j < filterkeys.length; j++) {
                    if (orderByClause.indexOf(filterkeys[j]) >= 0) {
                        continue;
                    } else {
                        return;
                    }
                }
            }
        }
        this.orderByClause = orderByClause;
    }

    public List<Integer> getCurPageList() {
        List<Integer> list = new ArrayList<Integer>();
        int startPage = getStartPage();
        int endPage = getEndPage();
        if (startPage <= endPage) {
            for (int i = startPage; i <= endPage; i++) {
                list.add(i);
            }
        }
        return list;
    }

    @SuppressWarnings("rawtypes")
	public List getResultList() {
        return resultList;
    }

    @SuppressWarnings("rawtypes")
	public void setResultList(List resultList) {
        this.resultList = resultList;
    }
    
    /**
     * 
     * @param count 总记录数
     * @return
     */
    public RowBounds getRowBounds(int count){
        this.doPage(count);
        RowBounds rowBounds = new RowBounds(this.getStartRow(), this.getPageSize());
        return rowBounds;
    }
    
    /**
     * 获取调用后端接口的查询参数。
     * @param count 总记录数
     * @return
     */
    public PagingParam getPagingParam(int count){
        this.doPage(count);
        PagingParam pagingParam = new PagingParam(this.getPageNo(), this.getPageSize());
        return pagingParam;
    }

}
