package com.geeke.databus.request;


import lombok.Data;

@Data
public class DataBusBaseReceiveRequest{
    /**
     * 主数据类型id
     */
    private String data_type;
    private SingleData[] data;
    @Data
    public static class SingleData {
        private String id;
        private String code;
        private String name;
    }
}


