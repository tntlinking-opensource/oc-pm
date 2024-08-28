package com.geeke.databus.service;

import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.databus.dao.ProductionRelationDao;
import com.geeke.databus.entity.AppMasterDataType;
import com.geeke.databus.entity.ProductionRelation;
import com.geeke.utils.StringUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 生产消费关系Service
 * @author
 * @version
 */

@Service("productionRelationService")
@Transactional(readOnly = false)
public class ProductionRelationService extends CrudService<ProductionRelationDao, ProductionRelation> {
    @Autowired
    private AppMasterDataTypeService appMasterDataTypeService;
    public ProductionRelation getByMasterIdAndtenantId(String tenantId,String masterDataTypeId) {
        return dao.getByMasterIdAndtenantId(tenantId,masterDataTypeId);
    }
    public ProductionRelation save(ProductionRelation entity) {
        if (StringUtils.isBlank(entity.getId())){
            entity.preInsert();
            doInsert(entity);
            this.saveAction(this.createAction(ActionConstants.ACTION_CREATED, entity));
        }else{
            entity.preUpdate();
            doUpdate(entity);
            this.saveAction(this.createAction(ActionConstants.ACTION_UPDATED, entity));
        }
        return entity;
    }
    public void saveall(ProductionRelation entity){
        //进行校验操作 先查找应用主数据这个应用是否已经有了
        String appId = entity.getAppId();
        String[] split = entity.getDataModel().split(",");
        ArrayList<ProductionRelation> objects = new ArrayList<>();
        for (String s : split) {
            ProductionRelation productionRelation = new ProductionRelation();
            BeanUtils.copyProperties( entity, productionRelation);
            productionRelation.setAppId(s);
            productionRelation.setDataModel("0");
            objects.add(productionRelation);
        }
        ProductionRelation productionRelation = new ProductionRelation();
        BeanUtils.copyProperties( entity, productionRelation);
        productionRelation.setAppId(appId);
        productionRelation.setDataModel("1");
        objects.add(productionRelation);
        dao.deleteByMasterIdAndtenantId(entity);
        for (ProductionRelation object : objects) {
            List<Parameter> params2 = Lists.newArrayList();

            params2.add(new Parameter("app_id", "=", object.getAppId()));
            if (object.getDataModel().equals("0")){
                params2.add(new Parameter("consumer_work_model", "=", 1));
            }else {
                params2.add(new Parameter("produce_work_model", "=", 1));
            }

            List<AppMasterDataType> update_date = appMasterDataTypeService.listAll(params2, "a.update_date");
            if (update_date.size()==0){
                throw new RuntimeException("当前选中的应用模式不支持！请前往应用主数据添加！");
//                return ResponseEntity.ok(ResultUtil.warningJson(ErrorEnum.E_50002, "当前选中的应用模式不支持！请前往应用主数据添加！"));
            }
            params2.add(new Parameter("master_data_type_id", "=", object.getMasterDataTypeId()));
            List<AppMasterDataType> listsize = appMasterDataTypeService.listAll(params2, "a.update_date");
            if (listsize.size()==0){
                throw new RuntimeException("当前选择的应用模式主数据类型不支持！请前往应用主数据添加！");
//                return ResponseEntity.ok(ResultUtil.warningJson(ErrorEnum.E_50002, "当前选择的应用模式主数据类型不支持！请前往应用主数据添加！"));
            }
            params2.add(new Parameter("audit_status", "=", 1));
            listsize = appMasterDataTypeService.listAll(params2, "a.update_date");
            if (listsize.size()==0){
                throw new RuntimeException("当前选中的数据未审核通过，请先审核！");
//                return ResponseEntity.ok(ResultUtil.warningJson(ErrorEnum.E_50002, "当前选中的数据未审核通过，请先审核！"));
            }
            object.setId("");
            save(object);
        }
    }
    public int deleteByMasterIdAndtenantId(ProductionRelation entity){
        return dao.deleteByMasterIdAndtenantId(entity);
    };
}
