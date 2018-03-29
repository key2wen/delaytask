package com.key.commons.redis.component;

import com.key.commons.lang.type.TypeReference;

import java.util.List;

/**
 * Created by shuguang on 18/3/29.
 */

public interface Serializer {
    byte[] serialzation(Object object);

    <T> T deserialization(byte[] byteArray, Class<T> c);

    <T> T deserialization(byte[] byteArray, TypeReference<T> type);

    <E> List<E> deserializationList(byte[] byteArray, Class<E> elementC);
}

