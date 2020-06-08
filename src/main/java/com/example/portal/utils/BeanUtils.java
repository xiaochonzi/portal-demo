package com.example.portal.utils;

import java.util.Collection;
import java.util.stream.Collectors;

public class BeanUtils extends org.springframework.beans.BeanUtils {
    /**
     * 将一个集合的对象转成目标类型
     *
     * @param sourceList
     * @param target
     * @param <T>
     * @return
     */
    public static <T> Collection<T> convert(Collection<?> sourceList, Class<T> target) {
        if (sourceList != null && sourceList.size() > 0) {
            Collection<T> targetList = sourceList.stream().map(item -> {
                T obj = null;
                try {
                    obj = target.newInstance();
                    copyProperties(item, obj);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return obj;
            }).collect(Collectors.toList());
            return targetList;
        }
        return null;
    }
}
