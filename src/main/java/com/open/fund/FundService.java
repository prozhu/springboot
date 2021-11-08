package com.open.fund;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.open.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ：zc
 * @createTime ：2021/2/2 15:23
 */
@Slf4j
public class FundService {


    /**
     * 获取今日基金预估收益汇总（通过传递基金code和总投资额）
     * @param paramList
     * @return
     */
    public BigDecimal getFundIncome(List<FundModel> paramList) {
        BigDecimal totalMoney = BigDecimal.ZERO;
        if (paramList.isEmpty()) {
            return totalMoney;
        }
        //存储基金的数据，格式为：code，基金详情模型
        HashMap<String, FundVO> resultMap = new HashMap<String, FundVO>();
        //存储基金的数据，格式为：code，投资总额
        HashMap<String, String> fundMap = new HashMap<>();
        //1. 遍历调用paramList
        for (FundModel temp : paramList) {
            String code = temp.getCode();
            FundVO fund  = calculateAndSend(code);
            resultMap.put(code, fund);
            totalMoney = totalMoney.add(new BigDecimal(temp.getInvestMoney()));
            fundMap.put(code, temp.getInvestMoney());
        }
        //2. 计算
        BigDecimal makeMoney = calculateFund(resultMap, fundMap);
        log.info("今日利润：{}", makeMoney);
        log.info("投资总额：{}", totalMoney);
        return makeMoney;
    }


    public List<FundModel> getFundIncome2(List<FundModel> paramList) {
        BigDecimal totalMoney = BigDecimal.ZERO;
        BigDecimal todayMoney = BigDecimal.ZERO;

        if (paramList.isEmpty()) {
            return paramList;
        }
        //存储基金的数据，格式为：code，基金详情模型
        HashMap<String, FundVO> resultMap = new HashMap<String, FundVO>();
        //1. 遍历调用paramList
        for (FundModel temp : paramList) {
            String code = temp.getCode();
            FundVO fund  = calculateAndSend(code);
            temp.setGszzl(fund.getGszzl());
            temp.setName(fund.getName());
            totalMoney = totalMoney.add(new BigDecimal(temp.getInvestMoney()));
            todayMoney = todayMoney.add(temp.getMoney());
        }
        //2. 计算
        log.info("今日利润：{}", todayMoney);
        log.info("投资总额：{}", totalMoney);
        return paramList;
    }


    /**
     * 获取基金预估收益率
     * @param fundCode 基金code
     * @return
     */
    private String sendGet(String fundCode) {
        String url = "http://fundgz.1234567.com.cn/js/" + fundCode + ".js?rt=1463558676006";
        return HttpUtils.sendGet(url);
    }

    /**
     * 获取预估值
     * @param fundCode
     * @return
     */
    private FundVO calculateAndSend(String fundCode) {
        String sendResult = sendGet(fundCode);
        String replaceResult = sendResult.replace("jsonpgz(", "").replace(");", "");
        JSONObject jsonObject = JSON.parseObject(replaceResult);
        FundVO fund = new FundVO();
        fund.setCode(fundCode);
        if (!ObjectUtils.isEmpty(jsonObject)) {
            fund.setGszzl(jsonObject.getBigDecimal("gszzl"));
            fund.setName(jsonObject.getString("name"));
        } else{
            log.info("查询基金信息异常{}", fundCode);
        }
        return fund;
    }


    /**
     * 计算基金今日累计收益
     * @param param 存储基金的数据，格式为：code，基金详情模型
     * @param fundMap 存储基金的数据，格式为：code，投资总额
     * @return
     */
    private BigDecimal calculateFund(HashMap<String, FundVO> param, HashMap<String, String> fundMap) {
        BigDecimal money = BigDecimal.ZERO;
        Iterator<Map.Entry<String, FundVO>> iterator = param.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, FundVO> next = iterator.next();
            String code = next.getKey();
            FundVO fund = next.getValue();
            //从fund 库里获取money，在乘以 预估值
            BigDecimal initialMoney = new BigDecimal(fundMap.get(code));
            //计算利润
            BigDecimal multiply = initialMoney.multiply((fund.getGszzl().divide(new BigDecimal("100"))));
            log.info("{}({}) , 利润为：{}, 利率：{}", fund.getName(), fund.getCode(), multiply.setScale(2).toPlainString(), fund.getGszzl());
            //累加利润
            money = money.add(multiply);
        }
        //默认四舍五入，保留2位小数
        return money.setScale(2);
    }


}
