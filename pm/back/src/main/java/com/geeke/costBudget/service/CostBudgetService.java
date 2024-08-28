package com.geeke.costBudget.service;

import camundajar.com.google.gson.Gson;
import camundajar.com.google.gson.reflect.TypeToken;
import com.geeke.common.controller.SearchParams;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.costBudget.dao.CostBudgetDao;
import com.geeke.costBudget.entity.CostBudget;
import com.geeke.costBudget.entity.vo.CostBudgetVo;
import com.geeke.projectManage.entity.ProjectList;
import com.geeke.projectManage.entity.ReportManage;
import com.geeke.projectManage.service.ProjectListService;
import com.geeke.projectManage.service.ReportManageService;
import com.geeke.team.entity.TeamMember;
import com.geeke.team.service.TeamMemberService;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 成本预算Service
 * @author
 * @version
 */

@Service("costBudgetService")
@Transactional(readOnly = true)
public class CostBudgetService extends CrudService<CostBudgetDao, CostBudget> {

    @Autowired
    private CostBudgetDao costBudgetDao;

    @Autowired
    private ProjectListService projectListService;

    @Autowired
    private ReportManageService reportManageService;

    @Autowired
    private TeamMemberService teamMemberService;

    public CostBudgetVo getByProjectIdWithCheck(String projectId) throws ParseException {
        CostBudgetVo costBudgetVo = new CostBudgetVo();
        List<CostBudget> costBudget = costBudgetDao.getByProjectId(projectId);
        if(!CollectionUtils.isEmpty(costBudget)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "该项目已经存在成本预算表，请勿重复添加。"));
        } else {
            String startDateString = "";
            String endDateString = "";
            List<CostBudget> costBudgetList = new ArrayList<>();
            ProjectList projectList = projectListService.get(projectId);
            if(null == projectList) {
                return null;
            }
            if(StringUtils.isNotBlank(projectList.getPlanBeginDate()) && StringUtils.isNotBlank(projectList.getPlanEndDate())){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(sdf.parse(projectList.getPlanBeginDate()));
                int startYear = calendar.get(Calendar.YEAR);
                int startMonth = calendar.get(Calendar.MONTH) + 1;
                int startDay = calendar.get(Calendar.DAY_OF_MONTH);
                startDateString = startYear+"."+startMonth+"."+startDay;
                calendar.setTime(sdf.parse(projectList.getPlanEndDate()));
                int endYear = calendar.get(Calendar.YEAR);
                int endMonth = calendar.get(Calendar.MONTH) + 1;
                int endDay = calendar.get(Calendar.DAY_OF_MONTH);
                endDateString = endYear+"."+endMonth+"."+endDay;

                LocalDate start = LocalDate.of(startYear, startMonth, startDay);
                LocalDate end = LocalDate.of(endYear, endMonth, endDay);
                // 创建一个 LocalDate 对象的迭代器
                YearMonth currentMonth = YearMonth.from(start);
                // 判断当前月份是否在结束时间之前
                while (currentMonth.isBefore(YearMonth.from(end)) || currentMonth.equals(YearMonth.from(end))) {
                    CostBudget cost = new CostBudget();
                    cost.setProjectId(projectId);
                    cost.setBudgetDate(currentMonth.toString());
                    costBudgetList.add(cost);
                    // 将当前月份+1
                    currentMonth = currentMonth.plusMonths(1);
                }
                List<Map<String, Object>> mapList = new ArrayList<>();
                if(StringUtils.isNotBlank(projectList.getPlanPersonMonthly())) {
                    Gson gson = new Gson();
                    mapList = gson.fromJson(projectList.getPlanPersonMonthly(), new TypeToken<List<Map<String, Object>>>() {
                    }.getType());
                }
                for (CostBudget cost: costBudgetList) {
                    double perInvestNumber =  0;
                    List<ReportManage> reportManages = reportManageService.getListByProjectIdAndDate(projectId, cost.getBudgetDate());
                    if(!CollectionUtils.isEmpty(reportManages)) {
                        // 统计总的加班时长
                        double totalOvertimeHour = reportManages.stream().filter(item -> null != item.getOvertimeHour()).mapToDouble(ReportManage::getOvertimeHour).sum();
                        // 项目团队成员当月日报天数+加班
                        perInvestNumber = totalOvertimeHour + reportManages.size();
                    }
                    // 设置人员投入-实际
                    cost.setPerInvestReal(BigDecimal.valueOf(perInvestNumber/21.75).setScale(2, RoundingMode.HALF_UP));
                    // 设置计划投入人月-预算
                    mapList.forEach(i -> {
                        if(i.keySet().equals(cost.getBudgetDate())){
                            cost.setPerInvestBudget(((BigDecimal) i.get(cost.getBudgetDate())).setScale(2, RoundingMode.HALF_UP));
                        }
                    });
                    //设置人员成本-实际
                    BigDecimal totalPerCost = new BigDecimal(0);
                    Map<Long,List<ReportManage>> reportListMap = reportManages.stream().collect(Collectors.groupingBy(ReportManage::getReportUser));
                    for(Map.Entry<Long, List<ReportManage>> entry : reportListMap.entrySet()) {
                        List<ReportManage> list = reportListMap.get(entry.getKey());
                        double totalOvertimeHour = list.stream().filter(item -> null != item.getOvertimeHour()).mapToDouble(ReportManage::getOvertimeHour).sum();
                        SearchParams searchParams = new SearchParams();
                        List<Parameter> params = new ArrayList<>();
                        searchParams.setParams(params);
                        searchParams.getParams().add(new Parameter("user_id", "=", entry.getKey()));
                        List<TeamMember> result = teamMemberService.listAll(searchParams.getParams(), searchParams.getOrderby());
                        BigDecimal costPrice = new BigDecimal(0);
                        if(!CollectionUtils.isEmpty(result)){
                            costPrice = null != result.get(0).getUnitPriceCost() ? result.get(0).getUnitPriceCost() : new BigDecimal(0);
                        }
                        totalPerCost = totalPerCost.add(BigDecimal.valueOf((totalOvertimeHour+list.size())/8).multiply(costPrice));
                    }
                    cost.setPerCostReal(totalPerCost.setScale(2, RoundingMode.HALF_UP));
                    cost.setTotalPaidAmount(totalPerCost.setScale(2, RoundingMode.HALF_UP));
                    double diffScale = 0;
                    if(totalPerCost.compareTo(new BigDecimal(0)) > 0) {
                        diffScale = 1;
                    }
                    cost.setDiffScale(diffScale + "%");
                }
            }
            costBudgetVo.setProjectName(projectList.getProjectName());
            costBudgetVo.setStartAndEndDate(startDateString + "-" + endDateString);
            costBudgetVo.setProjectBudget(projectList.getProjectBudget());
            costBudgetVo.setCostBudgetList(costBudgetList);
            BigDecimal allBudgetAmount = costBudgetList.stream().map(CostBudget::getTotalBudgetAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal allPaidAmount  = costBudgetList.stream().map(CostBudget::getTotalPaidAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            costBudgetVo.setAllBudgetAmount(allBudgetAmount);
            costBudgetVo.setAllPaidAmount(allPaidAmount);
        }
        return costBudgetVo;
    }

    public CostBudgetVo getByProjectId(String projectId) throws ParseException {
        CostBudgetVo costBudgetVo = new CostBudgetVo();
        List<CostBudget> costBudget = costBudgetDao.getByProjectId(projectId);
        ProjectList projectList = projectListService.get(projectId);
        costBudgetVo.setProjectName(projectList.getProjectName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(projectList.getPlanBeginDate()));
        int startYear = calendar.get(Calendar.YEAR);
        int startMonth = calendar.get(Calendar.MONTH) + 1;
        int startDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(sdf.parse(projectList.getPlanEndDate()));
        int endYear = calendar.get(Calendar.YEAR);
        int endMonth = calendar.get(Calendar.MONTH) + 1;
        int endDay = calendar.get(Calendar.DAY_OF_MONTH);
        costBudgetVo.setStartAndEndDate(startYear+"."+startMonth+"."+startDay + "-" + endYear+"."+endMonth+"."+endDay);
        costBudgetVo.setProjectBudget(projectList.getProjectBudget());
        for (CostBudget cost: costBudget) {
            BigDecimal shopPaidBudget = null != cost.getShopPaidBudget() ? cost.getShopPaidBudget() : new BigDecimal(0);
            BigDecimal otherPaidBudget = null != cost.getOtherPaidBudget() ? cost.getOtherPaidBudget() : new BigDecimal(0);
            cost.setTotalBudgetAmount((null != cost.getPerCostBudget() ? cost.getPerCostBudget() : new BigDecimal(0).add(shopPaidBudget).add(otherPaidBudget)).setScale(2, RoundingMode.HALF_UP));
            BigDecimal shopPaidReal = null != cost.getShopPaidBudget() ? cost.getShopPaidBudget() : new BigDecimal(0);
            BigDecimal otherPaidReal = null != cost.getOtherPaidBudget() ? cost.getOtherPaidBudget() : new BigDecimal(0);
            cost.setTotalPaidAmount((null != cost.getPerCostReal() ? cost.getPerCostReal() : new BigDecimal(0).add(shopPaidReal).add(otherPaidReal)).setScale(2, RoundingMode.HALF_UP));
            double diffScale = 0;
            if(cost.getTotalPaidAmount().compareTo(new BigDecimal(0)) > 0) {
                diffScale = (cost.getTotalPaidAmount().subtract(cost.getTotalBudgetAmount())).divide(cost.getTotalPaidAmount(), RoundingMode.HALF_UP)
                        .setScale(2, RoundingMode.HALF_UP).doubleValue();
            }
            cost.setDiffScale(diffScale + "%");
            }
        costBudgetVo.setCostBudgetList(costBudget);
        BigDecimal allBudgetAmount = costBudget.stream().map(CostBudget::getTotalBudgetAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal allPaidAmount  = costBudget.stream().map(CostBudget::getTotalPaidAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        costBudgetVo.setAllBudgetAmount(allBudgetAmount);
        costBudgetVo.setAllPaidAmount(allPaidAmount);
        return costBudgetVo;
    }

}
