package com.geeke.databus.response;

import cn.hutool.core.bean.BeanUtil;
import com.geeke.databus.entity.AppMasterDataType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author yi.luo
 * @date 2023-05-23 16:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppMasterDataTypeResponse extends AppMasterDataType {
    private String appName;

    public static AppMasterDataTypeResponse appMasterDataTypeConvert(AppMasterDataType appMasterDataType) {
        AppMasterDataTypeResponse appMasterDataTypeResponse = new AppMasterDataTypeResponse();
        BeanUtil.copyProperties(appMasterDataType, appMasterDataTypeResponse);
        return appMasterDataTypeResponse;
    }
}
