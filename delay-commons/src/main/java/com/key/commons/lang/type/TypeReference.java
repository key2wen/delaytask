package com.key.commons.lang.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author kongyi
 * @since 2016-12-05
 */
public class TypeReference<T> {

    private final Type type;

    protected TypeReference(){
        Type superClass = getClass().getGenericSuperclass();
        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    public Type getType() {
        return type;
    }

}
