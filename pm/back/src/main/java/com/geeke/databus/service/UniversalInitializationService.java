package com.geeke.databus.service;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.databus.dao.AppMasterDataTypeDao;
import com.geeke.databus.dao.AppStandardFieldDao;
import com.geeke.databus.dao.MasterDataBatchRecordsDao;
import com.geeke.databus.dao.MasterDataJsonDao;
import com.geeke.databus.dao.MasterDataRecordsDao;
import com.geeke.databus.dao.StandardFieldDao;
import com.geeke.databus.entity.AppMasterDataType;
import com.geeke.databus.entity.MasterDataBatchRecords;
import com.geeke.databus.entity.MasterDataDetail;
import com.geeke.databus.entity.MasterDataJson;
import com.geeke.databus.entity.MasterDataRecords;
import com.geeke.databus.entity.StandardField;
import com.geeke.databus.enums.FieldType;
import com.geeke.utils.StringUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("universalInitializationService")
@Transactional(readOnly = true)
public class UniversalInitializationService extends CrudService<StandardFieldDao, StandardField> {

    @Autowired
    private MasterDataBatchRecordsDao masterDataBatchRecordsDao;
    @Autowired
    private MasterDataRecordsDao masterDataRecordsDao;
    @Autowired
    private AppMasterDataTypeDao appMasterDataTypeDao;
    @Autowired
    private MasterDataJsonDao masterDataJsonDao;
    @Autowired
    private MasterDataJsonService masterDataJsonService;
    @Autowired
    private MasterDataRecordsService masterDataRecordsService;
    @Autowired
    private MasterDataDetailService masterDataDetailService;
    @Autowired
    private AppStandardFieldDao appStandardFieldDao;
    @Autowired
    private StandardFieldDao standardFieldDao;
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public List<MasterDataDetail> getInitData(String tenantId, String sourceAppId){
        //先拿着过来的应用key+租户去数据库查询数据

        //第一步 获取应用所有定义的数据 通过应用id
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("app_id", "=", sourceAppId));
        PageRequest pageRequest = new PageRequest(params);
        List<AppMasterDataType> appMasterDataTypes = appMasterDataTypeDao.listAll(pageRequest);
        List<MasterDataDetail> masterDataRecords= new ArrayList<>();
        if (appMasterDataTypes.size() > 0){

            for (AppMasterDataType appMasterDataType : appMasterDataTypes) {
                //获取到了应用主数据
                String id = appMasterDataType.getMasterDataType().getId();
                //获取最新的数据集合
                masterDataRecords.addAll(masterDataRecordsDao.getNewData(tenantId,id)) ;
            }
        }
        //获取最新的数据集合
        return masterDataRecords;
        //处理最新的集合
    }

    //todo 决定好如何顶用后取消事务注释
//    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public boolean parsingJsonData(){
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("analysis", "=", 0));
        PageRequest pageRequest = new PageRequest(params);
        //先查找是否被解析了 没有被解析 进行解析
        List<MasterDataJson> masterDataJsons = masterDataJsonDao.listAll(pageRequest);

        for (int i = 0; i < masterDataJsons.size(); i++) {

            //这里拿着数据进行遍历读取数据
            MasterDataJson masterDataJson = masterDataJsons.get(i);
            String dataJson = masterDataJson.getDataJson();

            JSONObject jsonObject = JSONObject.parseObject(dataJson);
            MasterDataBatchRecords masterDataBatchRecords = masterDataJson.getMasterDataBatchRecords();

            String masterDataTypeId = masterDataBatchRecords.getMasterDataTypeId();

            //解析json
            String data_type = jsonObject.getString("data_type");
            JSONArray data = jsonObject.getJSONArray("data");

            //通过data_type 获取 数据key
            List<Parameter> params2 = Lists.newArrayList();
            params2.add(new Parameter("master_data_type_id", "=", data_type));
            params2.add(new Parameter("data_key", "=", 1));
            PageRequest pageRequest2 = new PageRequest(params2);
            List<StandardField> standardFields = standardFieldDao.listAll(pageRequest2);
            if (standardFields.size() ==0){
                throw new RuntimeException("主数据类型主键不存在");
            }

            String code = standardFields.get(0).getFiledCode();

            for (int i1 = 0; i1 < data.size(); i1++) {
                JSONObject data1 = data.getJSONObject(i1);



                //通过这个id获取值
                Object o1 = data1.get(code);

                //这里是为了判断传来的key是否有值  没有的话这里就是无效数据 直接当次不进行了
                if (!ObjectUtil.isNotNull(o1)){
                    continue;
                }

                //这里判断值key是否符合配置的参数
                if (!fieldType(o1,  standardFields.get(0))){
                    continue;
                };
                //判断是否有必填的没填  如果是的话，直接不进行这条保存了
                List<Parameter> params3 = Lists.newArrayList();
                params3.add(new Parameter("master_data_type_id", "=", data_type));
                params3.add(new Parameter("required", "=", 1));
                PageRequest pageRequest3 = new PageRequest(params3);
                List<StandardField> standardFields3 = standardFieldDao.listAll(pageRequest3);
                Boolean required =false;
                for (int i2 = 0; i2 < standardFields3.size(); i2++) {
                    StandardField standardField = standardFields3.get(i2);
                    String filedCode = standardField.getFiledCode();
                    if (!StringUtils.isNotBlank(data1.getString(filedCode))){
                        required=true;
                    }
                }
                if (required){
                    continue;
                }

                List<MasterDataDetail> master_data_detail =  new ArrayList<>();
                String DataVersion = String.valueOf(new Date().getTime());
                //先插入数据 获取id
                MasterDataRecords masterDataRecords = new MasterDataRecords();
                masterDataRecords.setMasterDataBatchRecords(new MasterDataBatchRecords(masterDataBatchRecords.getId()));
                masterDataRecords.setOrderNumber(i1+1);
                masterDataRecords.setTenantId(masterDataBatchRecords.getTenantId());
                masterDataRecords.setMasterDataTypeId(data_type);
                masterDataRecords.setDataKey(String.valueOf(o1));
                masterDataRecords.setDataVersion(DataVersion);
                masterDataRecords.setCreateBy("system");
                masterDataRecords.setUpdateBy("system");
                masterDataRecords.preInsert();
                masterDataRecords.setId(null);

                //先保存然后获取id
                MasterDataRecords save = masterDataRecordsService.save(masterDataRecords);
                String id = save.getId();

                for(String key:data1.keySet()){
                    //这里判断字段是否符合配置的参数

                    Object o = data1.get(key);
                    //获取当前key的值的id
                    List<Parameter> params1 = Lists.newArrayList();

                    params1.add(new Parameter("master_data_type_id", "=", data_type));
                    params1.add(new Parameter("filed_code", "=", key));
                    PageRequest pageRequest1 = new PageRequest(params1);
                    List<StandardField> standardFields1 = standardFieldDao.listAll(pageRequest1);

                    if (standardFields1.size() ==0){
                        continue;
                    }
                    if (!fieldType(o,  standardFields1.get(0))){
                        if (standardFields1.get(0).getRequired().equals(1)){
                            masterDataRecordsService.delete(save);
                            master_data_detail=new ArrayList<>();
                            break;
                        }
                        continue;
                    };
                    //这里进行存储到MasterDataDetail表中
                    MasterDataDetail masterDataDetail = new MasterDataDetail();
                    masterDataDetail.setDataKey(String.valueOf(o1));
                    masterDataDetail.setDataVersion(DataVersion);
                    masterDataDetail.setMasterDataTypeId(data_type);
                    masterDataDetail.setTenantId(masterDataBatchRecords.getTenantId());
                    masterDataDetail.setStandardFieldId(standardFields1.get(0).getId());
                    masterDataDetail.setMasterDataInfoFiled(key);
                    masterDataDetail.setMasterDataInfoValue(String.valueOf(o));
                    masterDataDetail.setMasterDataRecordsId(id);
                    masterDataDetail.setCreateBy("system");
                    masterDataDetail.setUpdateBy("system");
                    master_data_detail.add(masterDataDetail);
                }
                if (master_data_detail.size() !=0){
                    masterDataDetailService.bulkInsert(master_data_detail);
                }

            }
            masterDataJson.setAnalysis("1");
            masterDataJsonService.save(masterDataJson);
        }
        return true;
    }
    public boolean fieldType(Object fieldType,StandardField standardField) {
        String filedType = standardField.getFiledType();
//        Map<FieldType, Runnable>
        if (filedType.equals(FieldType.FIELD_STRING.getCode())){
            try {
                String ffieldType = (String) fieldType;
                if (ffieldType.length()>Long.parseLong(standardField.getFiledSize())){
                    return false;
                }
            }catch (Exception e){
                return false;
            }
        }else if (filedType.equals(FieldType.FIELD_DOUBLE.getCode())){
            try {
                Double ffieldType =Double.valueOf(fieldType.toString());
                String s = String.valueOf(ffieldType);
                String[] split = s.split("\\.");
                if (split.length>=2){

                    if (split[0].length()> Long.parseLong(standardField.getFiledSize())){
                        return false;
                    }
                    if (split[1].length()>Long.parseLong(standardField.getFiledPrecision())){
                        return false;
                    }
                }else {
                    return false;
                }

            }catch (Exception e){
                return false;
            }
        }else if (filedType.equals(FieldType.FIELD_LONG.getCode())){
            try {

                Long ffieldType = Long.parseLong(fieldType.toString());
                if (String.valueOf(ffieldType).length()>Long.parseLong(standardField.getFiledSize())){
                    return false;
                }
            }catch (Exception e){
                return false;
            }
        }

        return true;
    }

}
