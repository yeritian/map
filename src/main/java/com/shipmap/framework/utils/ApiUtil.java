package com.shipmap.framework.utils;

import com.shipmap.modules.base.model.PortDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiUtil {
    public Map<String, Object> returnJson(int code, String msg) {
        Map map = new HashMap();
        map.put("code", code);
        map.put("msg", msg);
        return map;
    }

    public Map<String, Object> returnJson(int code, String msg, List<Map> data) {
        Map map = new HashMap();
        map.put("code", code);
        map.put("msg", msg);
        map.put("data", data);
        return map;
    }

    public Map<String, Object> returnJson(int code, String msg, Map data) {
        Map map = new HashMap();
        map.put("code", code);
        map.put("msg", msg);
        map.put("data", data);
        return map;
    }

    public Map<String, Object> returnJson(int code, String msg, int count, List<PortDetail> data) {
        Map map = new HashMap();
        map.put("code", code);
        map.put("msg", msg);
        map.put("count", count);
        map.put("data", data);
        return map;
    }
}