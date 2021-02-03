package com.open.fund;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.open.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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
        HashMap<String, BigDecimal> resultMap = new HashMap<String, BigDecimal>();
        //存储基金的数据，格式为：code，投资总额
        HashMap<String, String> fundMap = new HashMap<>();
        //1. 遍历调用paramList
        for (FundModel temp : paramList) {
            String code = temp.getCode();
            BigDecimal ygz = calculateAndSend(code);
            resultMap.put(code, ygz);
            totalMoney = totalMoney.add(new BigDecimal(temp.getInvestMoney()));
            fundMap.put(code, temp.getInvestMoney());
        }
        //2. 计算
        BigDecimal makeMoney = calculateFund(resultMap, fundMap);
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
    private BigDecimal calculateAndSend(String fundCode) {
        String sendResult = sendGet(fundCode);
        String replaceResult = sendResult.replace("jsonpgz(", "").replace(");", "");
        JSONObject jsonObject = JSON.parseObject(replaceResult);
        return jsonObject.getBigDecimal("gszzl");
    }


    /**
     * 计算基金今日累计收益
     * @param param
     * @param fundMap 存储基金的数据，格式为：code，投资总额
     * @return
     */
    private BigDecimal calculateFund(HashMap<String, BigDecimal> param, HashMap<String, String> fundMap) {
        BigDecimal money = BigDecimal.ZERO;
        Iterator<Map.Entry<String, BigDecimal>> iterator = param.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, BigDecimal> next = iterator.next();
            String code = next.getKey();
            BigDecimal ygz = next.getValue();
            //从fund 库里获取money，在乘以 预估值
            BigDecimal initialMoney = new BigDecimal(fundMap.get(code));
            //计算利润
            BigDecimal multiply = initialMoney.multiply((ygz.divide(new BigDecimal("100"))));
            log.info("基金code：{} , 本次利润为：{}", code, multiply.setScale(2).toPlainString());
            //累加利润
            money = money.add(multiply);
        }
        //默认四舍五入，保留2位小数
        return money.setScale(2);
    }


}
