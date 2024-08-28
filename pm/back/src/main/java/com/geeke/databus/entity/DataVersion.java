package com.geeke.databus.entity;

import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;

import java.util.List;

public class DataVersion extends DataEntity<DataVersion> {
    private static final long serialVersionUID = 1434746435262390344L;
    private String dataVersion;
    private List<RequestJson> jsonlist = Lists.newArrayList(); // 子表列表

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    public DataVersion() {
        super();
    }
    public DataVersion(String dataVersion, List<RequestJson> jsonlist) {
        this.dataVersion = dataVersion;
        this.jsonlist = jsonlist;
    }

    public DataVersion(String id, String dataVersion, List<RequestJson> jsonlist) {
        super(id);
        this.dataVersion = dataVersion;
        this.jsonlist = jsonlist;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public List<RequestJson> getJsonlist() {
        return jsonlist;
    }

    public void setJsonlist(List<RequestJson> jsonlist) {
        this.jsonlist = jsonlist;
    }
}
