package com.geeke.databus.entity;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.sys.utils.SessionUtils;
import com.geeke.utils.IdGen;
import com.geeke.utils.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 主数据批次分发记录Entity
 * @author
 * @version
 */

public class MasterDataPushInterfaceRecords extends DataEntity<MasterDataPushInterfaceRecords> {

    private static final long serialVersionUID = 1442285064940249168L;

    private MasterDataPushRecords masterDataPushRecords; // 主数据推送记录表id

    private String pushDate; // 推送时间

    private String pushData; // push_data

    private String pushStatus; // 推送状态

    private String pushFailInfo; // 推送失败信息

    private String returnData; // 返回数据

    // 构造方法
    public MasterDataPushInterfaceRecords() {
        super();
    }

    public MasterDataPushInterfaceRecords(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "主数据推送记录表id不能为空")
    public MasterDataPushRecords getMasterDataPushRecords() {
        return masterDataPushRecords;
    }

    public void setMasterDataPushRecords(MasterDataPushRecords masterDataPushRecords) {
        this.masterDataPushRecords = masterDataPushRecords;
    }

    @Length(min = 1, max = -1, message = "推送时间长度必须介于 1 和 -1 之间")
    public String getPushDate() {
        return pushDate;
    }

    public void setPushDate(String pushDate) {
        this.pushDate = pushDate;
    }

    @Length(min = 0, max = -1, message = "push_data长度必须介于 0 和 -1 之间")
    public String getPushData() {
        return pushData;
    }

    public void setPushData(String pushData) {
        this.pushData = pushData;
    }

    @Length(min = 0, max = 8, message = "推送状态长度必须介于 0 和 8 之间")
    public String getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(String pushStatus) {
        this.pushStatus = pushStatus;
    }

    @Length(min = 0, max = 255, message = "推送失败信息长度必须介于 0 和 255 之间")
    public String getPushFailInfo() {
        return pushFailInfo;
    }

    public void setPushFailInfo(String pushFailInfo) {
        this.pushFailInfo = pushFailInfo;
    }

    @Length(min = 0, max = 255, message = "返回数据长度必须介于 0 和 255 之间")
    public String getReturnData() {
        return returnData;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "master_data_push_interface_records";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1442285064940249168";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
    @Override
    public void preInsert() {
        if (StringUtils.isBlank(getId())) {
            setId(IdGen.uuid());
        }
        try {
            JSONObject userObj = SessionUtils.getUserJson();
            if (userObj != null && StringUtils.isNotBlank(userObj.getString("id"))) {
                this.updateBy = userObj.getString("name") + "(" + userObj.getString("loginName") + ")";
                this.createBy = userObj.getString("name") + "(" + userObj.getString("loginName") + ")";
            }else{
                this.updateBy = "SYSTEM" + "(" + "SYSTEM" + ")";
                this.createBy = "SYSTEM" + "(" + "SYSTEM" + ")";
            }
        } catch (Exception e) {
            this.updateBy = "SYSTEM" + "(" + "SYSTEM" + ")";
            this.createBy = "SYSTEM" + "(" + "SYSTEM" + ")";
        }
        this.updateDate = simpleDateFormat.format(new Date());
        this.createDate = this.updateDate;
    }

    @Override
    public void preUpdate() {
        try{
            JSONObject userObj = SessionUtils.getUserJson();
            if (userObj != null && StringUtils.isNotBlank(userObj.getString("id"))) {
                this.updateBy = userObj.getString("name") + "(" + userObj.getString("loginName") + ")";
            }else{
                this.updateBy = "SYSTEM" + "(" + "SYSTEM" + ")";
            }
        }catch (Exception e){
            this.updateBy = "SYSTEM" + "(" + "SYSTEM" + ")";
        }
        this.oldUpdateDate = this.updateDate;
        this.updateDate = simpleDateFormat.format(new Date());
    }
}
