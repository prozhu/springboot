package com.open.utils.list;

import java.util.ArrayList;
import java.util.List;

/**
 * list集合相关的工具类
 * @author zhucheng
 * @date 2023/7/25 10:36
 */
public class ListUtils {

    /**
     * 平均拆分list方法
     * @param source
     * @param n
     * @param <T>
     * @return  n = 10
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        //余数
        int remainder = source.size() % n;
        int number = source.size() / n;
        int offset = 0;
        //偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }
}
