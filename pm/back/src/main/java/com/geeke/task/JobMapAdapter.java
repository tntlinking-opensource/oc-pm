package com.geeke.task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;

/**
 * job map适配器
 * @Author: lys
 * @DateTime: 2023/8/2
 */
public class JobMapAdapter {
    private static Map<String, ScheduledFuture<?>> scheduledFutureMap = new HashMap<String, ScheduledFuture<?>>();

    public static void put(String code, ScheduledFuture<?> scheduledFuture) {
        scheduledFutureMap.put(prefixTenant(code), scheduledFuture);
    }

    public static void remove(String code) {
        scheduledFutureMap.remove(prefixTenant(code));
    }

    public static ScheduledFuture<?> get(String code) {
        return scheduledFutureMap.get(prefixTenant(code));
    }

    private static String prefixTenant(String code) {
        String tenantId = DynamicDataSourceContextHolder.peek();
        if(tenantId == null) {
            return "0_" + code;
        }
        return tenantId + "_" + code;
    }
}

