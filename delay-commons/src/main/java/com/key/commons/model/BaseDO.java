package com.key.commons.model;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * Description: 该类提供了dao操作时的对象set属性的属性和值的键值对， 用拦截器来监控set的属性值，并进行dao的增加、更新操作 BaseDO.java Create on 2013年8月16日 下午5:28:11
 * </p>
 * 
 * @author <a href=" mailto:zhou.huajun@tongbanjie.com">zhou.huajun</a>
 * @version 1.0 Copyright (c) 2013 T.b.j,Inc. All Rights Reserved.
 */
public abstract class BaseDO implements Serializable {

    private static final long serialVersionUID = 1667380188057698747L;

    protected BaseDO(){
    }

    /**
     * 通过cglib来代理被调用的方法，并且只监控set而且是单一参数的方法
     */
    protected Map<String, Object> settedMap;

    /**
     * 得到一个已经被调用过set方法的javabean 字段跟值的 map对象
     * 
     * @return
     */
    public Map<String, Object> getSettedMap() {
        return settedMap;
    }

    public boolean setterInited() {
        return settedMap != null;
    }

    public void setSettedMap(Map<String, Object> settedMap) {
        this.settedMap = settedMap;
    }

}
