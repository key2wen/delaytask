package com.key.delay.server.biz.service;

import com.key.delay.server.biz.model.DelayTask;
import com.key.facade.request.DelayTaskComQuery;
import com.key.facade.request.DelayTaskQuery;

import java.util.List;

public interface DelayTaskService {

    /**
     * 根据查询参数查询数据
     *
     * @param query 查询参数
     */
    List<DelayTask> query(DelayTaskQuery query);

    List<DelayTask> queryCom(DelayTaskComQuery query);

    /**
     * 根据查询参数查询数据总量
     *
     * @param query 查询参数
     */
    Integer count(DelayTaskQuery query);

    /**
     * 根据ID查询
     *
     * @param id 数据库ID
     */
    DelayTask findById(Long id);

    /**
     * 根据id更新一调数据
     *
     * @param updateParam 更新参数
     */
    int updateById(DelayTask updateParam);

    /**
     * 新增一条记录
     */
    int insert(DelayTask delayTask);

    int findFinishAndInsertToHis(Long nextExecTime);

    int deleteFinish(Long nextExecTime);
}