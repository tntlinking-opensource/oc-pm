
package com.geeke.databus.entity;


import cn.hutool.core.util.ObjUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 平台主数据Entity
 *
 * @author
 */

public class StandardField extends DataEntity<StandardField> {


    private static final long serialVersionUID = 1434554759461847041L;


    private String masterDataTypeId;      // 主数据类型id

    private Integer dataKey;     //是否为主键1必填  0非必填 默认必填

    private String filedCode;        // 字段code


    private String filedName;        // 字段名称


    private String filedType;        // 字段类型


    private String filedSize;        // 字段长度


    private String filedPrecision;        // 字段精度


    private Integer required = 1;        // 是否必填 1必填  0非必填 默认必填


    // 构造方法
    public StandardField() {
        super();
    }

    public StandardField(String id) {
        super(id);
    }


    // 生成get和set方法


    @NotNull(message = "主数据类型id不能为空")

    public String getMasterDataTypeId() {
        return masterDataTypeId;
    }

    public void setMasterDataTypeId(String masterDataTypeId) {
        this.masterDataTypeId = masterDataTypeId;
    }


    @Length(min = 1, max = 16, message = "字段code长度必须介于 1 和 16 之间")


    public String getFiledCode() {
        return filedCode;
    }

    public void setFiledCode(String filedCode) {
        this.filedCode = filedCode;
    }


    @Length(min = 0, max = 32, message = "字段名称长度必须介于 0 和 32 之间")


    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }


    @Length(min = 0, max = 16, message = "字段类型长度必须介于 0 和 16 之间")


    public String getFiledType() {
        return filedType;
    }

    public void setFiledType(String filedType) {
        this.filedType = filedType;
    }


    @Length(min = 1, max = 4, message = "字段长度长度必须介于 1 和 4 之间")


    public String getFiledSize() {
        return filedSize;
    }

    public void setFiledSize(String filedSize) {
        this.filedSize = filedSize;
    }


    @Length(min = 0, max = 4, message = "字段精度长度必须介于 0 和 4 之间")


    public String getFiledPrecision() {
        return filedPrecision;
    }

    public void setFiledPrecision(String filedPrecision) {
        this.filedPrecision = filedPrecision;
    }


    @NotNull(message = "是否必填 1必填  0非必填 默认必填不能为空")


    public Integer getRequired() {
        return required;
    }

    public void setRequired(Integer required) {
        this.required = required;
    }

    public Integer getDataKey() {
        return dataKey;
    }

    public void setDataKey(Integer dataKey) {
        this.dataKey = dataKey;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "standard_field";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1434554759461847041";
    }


    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {

        return true;

    }

    public boolean checkBasicData(Object field) {
        int sizeFiled = Integer.parseInt(filedSize);
        String fieldString = String.valueOf(field);
        switch (filedType.toLowerCase()) {
            case "bigint":
            case "int":
            case "string":
            case "long":
            case "char":
                return fieldString.length() < sizeFiled;
            case "double":
                // 将d转换为字符串，并指定小数点后面保留15位
                String doubleStr = String.format("%.15f", field);
                int decimalPlaces = doubleStr.length() - doubleStr.indexOf('.') - 1;
                return decimalPlaces < sizeFiled;
            case "boolean":
                Boolean aBoolean = Boolean.valueOf(fieldString);
                if (ObjUtil.isNull(aBoolean)) {
                    return false;
                }
                return Boolean.valueOf(true).equals(aBoolean) || Boolean.valueOf(false).equals(aBoolean);
            default:
                return false;
        }

    }

}
