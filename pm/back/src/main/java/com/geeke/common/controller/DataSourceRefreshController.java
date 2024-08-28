package com.geeke.common.controller;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.geeke.utils.AppVersionUtils;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SpringUtils;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author TBH
 *
 */
@Slf4j
@RestController
public class DataSourceRefreshController {

    @Autowired
    AppVersionUtils appVersionUtils;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DefaultDataSourceCreator dataSourceCreator;

    @PostMapping("/refresh/dataSource")
    public ResponseEntity<JSONObject> refresh() throws Exception{
        log.info(">>>>>>>>>>>>>>>执行刷新数据源 <<<<<<<<<<<<<");
        JSONObject version = appVersionUtils.getCurrentAppVersion();

        String driver = version.getJSONObject("dbType").getString("driver");

        JSONArray tenantApps = appVersionUtils.getTenatApps(version.getString("id"));

        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        for(int i = 0; i < tenantApps.size(); i++) {
            JSONObject tenantApp = tenantApps.getJSONObject(i);
            String tenantId = tenantApp.getJSONObject("tenant").getString("id");

            DataSourceProperty dataSourceProperty = new DataSourceProperty();
            dataSourceProperty.setDriverClassName(driver);
            dataSourceProperty.setUrl(tenantApp.getString("dbUrl"));
            dataSourceProperty.setUsername(tenantApp.getString("dbUsername"));
            dataSourceProperty.setPassword(tenantApp.getString("dbPassword"));
            dataSourceProperty.setType(HikariDataSource.class);
            dataSourceProperty.setPoolName(tenantApp.getJSONObject("tenant").getString("id"));

            DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);

            // 通过Flyway对升级（创建）所有动态数据源的数据库
            Flyway flywayInSpring = SpringUtils.getBean("flyway");
            ClassicConfiguration conf = (ClassicConfiguration)flywayInSpring.getConfiguration();
            conf.setDataSource(dataSource);
            conf.getPlaceholders().put("tenant", tenantId); // 租户参数
            Flyway flyway = new Flyway(conf);
            flyway.migrate();

            // PoolName就是我们yaml配置中说的数据源名称
            ds.addDataSource(tenantId, dataSource);

        }
        log.info(">>>>>>>>>>>>>>>结束刷新数据源 <<<<<<<<<<<<<");
        return ResponseEntity.ok(ResultUtil.successJson());
    }
}
