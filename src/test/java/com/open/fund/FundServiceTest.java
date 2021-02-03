package com.open.fund;

import com.open.utils.email.EmailManage;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

/**
 *
 * @author ：zc
 * @createTime ：2021/2/2 14:00
 */
public class FundServiceTest {

    /**
     * 获取今日基金收益
     */
    @Test
    public void getFundIncomeTest() {
        FundService service = new FundService();
        List<FundModel> paramList = mapToList(fundMap);
        BigDecimal money = service.getFundIncome(paramList);
//        EmailManage.sendSimpleEmail("fund remind通知", "这个邮件的详情" +money.toPlainString(), "496659989@qq.com");
    }

    /**
     * map 转list
     * @param fundMap
     * @return
     */
    private static List<FundModel> mapToList(HashMap<String, String> fundMap) {
        List<FundModel> paramList = new ArrayList<>();
        Iterator<Map.Entry<String, String>> iterator = fundMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String code = next.getKey();
            String investMoney = next.getValue();
            paramList.add(new FundModel(code, investMoney));
        }
        return paramList;
    }

    private static HashMap<String, String> fundMap = new HashMap<>();
    static {
        fundMap.put("160643", "2500");
        fundMap.put("002190", "1400");
        fundMap.put("003304", "2000");
        fundMap.put("001027", "900");
        fundMap.put("160633", "2100");
        fundMap.put("001552", "2800");
        fundMap.put("003096", "200");
        fundMap.put("001631", "1000");
        fundMap.put("004854", "1300");
        fundMap.put("161032", "1800");
        fundMap.put("004856", "1500");
        fundMap.put("320007", "500");
    }


}
