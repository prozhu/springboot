package com.open.fund;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.open.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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
    public Double getFundIncome(List<FundModel> paramList) {
        Double totalMoney = 0.0;
        if (paramList.isEmpty()) {
            return totalMoney;
        }
        HashMap<String, Double> resultMap = new HashMap<String, Double>();
        //存储基金的数据，格式为：code，投资总额
        HashMap<String, String> fundMap = new HashMap<>();
        //1. 遍历调用paramList
        for (FundModel temp : paramList) {
            String code = temp.getCode();
            Double ygz = calculateAndSend(code);
            resultMap.put(code, ygz);
            totalMoney = totalMoney + Double.parseDouble(temp.getInvestMoney());
            fundMap.put(code, temp.getInvestMoney());
        }
        //2. 计算
        Double makeMoney = calculateFund(resultMap, fundMap);
        log.info("今日利润：{}", makeMoney);
        log.info("投资总额：{}", totalMoney);
        return makeMoney;
    }


    /**
     * 获取基金预估收益率
     * @param fundCode 基金code
     * @return
     */
    private String sendGet(String fundCode) {
        String url = "http://fundgz.1234567.com.cn/js/" + fundCode + ".js?rt=1463558676006";
        return HttpUtils.sendGet(url).toString();
    }

    /**
     * 获取预估值
     * @param fundCode
     * @return
     */
    private Double calculateAndSend(String fundCode) {
        String sendResult = sendGet(fundCode);
        String replaceResult = sendResult.replace("jsonpgz(", "").replace(");", "");
        JSONObject jsonObject = JSON.parseObject(replaceResult);
        return jsonObject.getDouble("gszzl");
    }


    /**
     * 计算基金今日累计收益
     * @param param
     * @param fundMap 存储基金的数据，格式为：code，投资总额
     * @return
     */
    private Double calculateFund(HashMap<String, Double> param, HashMap<String, String> fundMap) {
        Double money = 0.0;
        Iterator<Map.Entry<String, Double>> iterator = param.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Double> next = iterator.next();
            String code = next.getKey();
            Double ygz = next.getValue();
            //从fund 库里获取money，在乘以 预估值
            Double initialMoney = Double.parseDouble(fundMap.get(code));
            money = money + (initialMoney * (ygz / 100));
        }
        return money;
    }


}
