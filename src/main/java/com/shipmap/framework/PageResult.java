package com.shipmap.framework;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页结果对象,这里以layui框架的table为标准
 *
 * @author wangfan
 * @date 2017-7-24 下午4:28:59
 */
public class PageResult {
    private PageResult() {
    }

    public static <E> Result toResult(List<E> rows, long total) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("count", total);
        map.put("list", rows);
        return Result.success(map);
    }

    public static <E> Result toResult(List<E> rows) {
        return toResult(rows, rows.size());
    }
}
