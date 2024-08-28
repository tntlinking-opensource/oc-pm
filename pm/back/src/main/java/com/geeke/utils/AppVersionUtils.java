package com.geeke.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Parameter;
import com.google.common.collect.Lists;
import com.newtouch.cloud.common.dependency.remote.rest.DynamicRestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * 应用工具类
 */
@Component
@Slf4j
public class AppVersionUtils {
     // 当前应用appKey
     @Value(value = "${spring.application.app-key}")
     private String currentAppKey;
 
     // 单前应用版本号
     @Value(value = "${spring.application.version}")
     private String currentVersionNo;

     @Autowired
     private DynamicRestTemplate restTemplate;

     /**
      * 根据appkey获取应用Id
      * @param appKey
      * @return
      * @throws Exception
      */
     public String getAppId(String appKey) throws Exception {
        log.info("===== 根据appKey获取appId。 appKey：{}", appKey);
        //准备参数
        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter("app_key", "=", appKey));
        
        SearchParams searchParams = new SearchParams();
        searchParams.setParams(params);
        JSONObject jsonObject;
        try{
            jsonObject = restTemplate.post(
                "cloud-geeke-devtool", 
                "/micro/microApp/listAll", 
                JSONObject.class, searchParams, null);
        }catch(Exception e) {
            throw new Exception("访问开发者平台cloud-geeke-devtool微服务的/micro/microApp/listAll接口异常："+ e.getMessage());
        }
        // 远程服务访问失败
        if(!"100".equals(jsonObject.getString("code"))) {
            throw new Exception("访问开发者平台cloud-geeke-devtool微服务的/micro/microApp/listAll接口返回错误：" + jsonObject.getString("msg"));
        }
        
        JSONArray apps = jsonObject.getJSONArray("data");
        if(apps.size() == 0) {
            throw new Exception("开发者平台没有找到appKey为" + appKey + "的应用");
        }
        if(apps.size() > 1) {
            throw new Exception("开发者平台appKey为" + appKey + "存在多个应用");
        }
        JSONObject app = apps.getJSONObject(0);
        return app.getString("id");
    }
    
    /**
     *  获取应用版本信息
     * @param appId
     * @param appKey
     * @param versionNo
     * @return
     * @throws Exception
     */
    public JSONObject getAppVersion(String appId, String appKey, String versionNo) throws Exception {
        log.info("===== 获取应用版本。应用Id：{} 应用版本：{}", appId, versionNo);
        //准备参数
        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter("app_id", "=", appId));
        params.add(new Parameter("name", "=", versionNo));
        
        SearchParams searchParams = new SearchParams();
        searchParams.setParams(params);
        JSONObject jsonObject;
        try{
            jsonObject = restTemplate.post(
                "cloud-geeke-devtool", 
                "/micro/microVersion/listAll", 
                JSONObject.class, searchParams, null);
        }catch(Exception e) {
            throw new Exception("访问开发者平台cloud-geeke-devtool微服务的/micro/microVersion/listAll接口异常："+ e.getMessage());
        }
        // 远程服务访问失败
        if(!"100".equals(jsonObject.getString("code"))) {
            throw new Exception("访问开发者平台cloud-geeke-devtool微服务的/micro/microVersion/listAll接口返回错误：" + jsonObject.getString("msg"));
        }
        
        JSONArray versions = jsonObject.getJSONArray("data");
        if(versions.size() == 0) {
            throw new Exception("开发者平台appKey为" + appKey + "的应用没有找到的版本号" + versionNo);
        }
        if(versions.size() > 1) {
            throw new Exception("开发者平台appKey为" + appKey + "的应用版本号" + versionNo + "存在多个");
        }

        return versions.getJSONObject(0);
    }
    
    /**
     * 获取应用版本的租户应用列表
     * @param versionId  应用版本Id
     * @return
     * @throws Exception
     */
    public JSONArray getTenatApps(String versionId) throws Exception {
        log.info("===== 获取住户应用列表。应用版本id： {}", versionId);
        //准备参数
        List<Parameter> params = Lists.newArrayList();
        params.add(new Parameter("app_version_id", "=", versionId));
        
        SearchParams searchParams = new SearchParams();
        searchParams.setParams(params);

        JSONObject jsonObject;
        try{
            jsonObject = restTemplate.post(
                "cloud-geeke-organisation", 
                "/ten/tenantApp/listAll", 
                JSONObject.class, searchParams, null);
        }catch(Exception e) {
            throw new Exception("访问组织架构cloud-geeke-organisation微服务的/ten/tenantApp/listAll接口异常："+ e.getMessage());
        }
        // 远程服务访问失败
        if(!"100".equals(jsonObject.getString("code"))) {
            throw new Exception("访问组织架构cloud-geeke-organisation微服务的/ten/tenantApp/listAll接口返回错误：" + jsonObject.getString("msg"));
        }
        
        return jsonObject.getJSONArray("data");
    }

    /**
     * 获取当前应用Id
     * @return
     * @throws Exception
     */
    public String getCurrentAppId()  throws Exception {
        return getAppId(currentAppKey);
    }

    /**
     * 获取当前应用版本信息
     * @return
     * @throws Exception
     */
    public JSONObject getCurrentAppVersion() throws Exception {
        String appId = getCurrentAppId();
        return getAppVersion(appId, currentAppKey, currentVersionNo);
    }

    /**
     * 获取当前应用的租户应用列表
     * @return
     * @throws Exception
     */
    public JSONArray getCurrentTenatApps() throws Exception {
        JSONObject version = getCurrentAppVersion();
        return getTenatApps(version.getString("id"));
    }
}
