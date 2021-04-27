package com.open.fund;

import com.open.utils.email.EmailManage;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

/**
 *
 * @author ：zc
 * @createTime ：2021/2/2 14:00
 */
@Slf4j
public class FundServiceTest {

    /**
     * 获取今日基金收益
     */
    @Test
    public void getFundIncomeTest() {
        log.info("zc账号情况");
        FundService service = new FundService();
        List<FundModel> paramList = mapToList(fundMap);
        BigDecimal money = service.getFundIncome(paramList);
//        EmailManage.sendSimpleEmail("fund remind通知", "这个邮件的详情" +money.toPlainString(), "496659989@qq.com");
        log.info("other账号情况");
        service = new FundService();
        paramList = mapToList(otherFundMap);
        money = service.getFundIncome(paramList);
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
    private static HashMap<String, String> otherFundMap = new HashMap<>();

    static {
        fundMap.put("001102", "2300");
        fundMap.put("002190", "4600");
        fundMap.put("160633", "5000");
        fundMap.put("004854", "2500");
        fundMap.put("160643", "5500");
        fundMap.put("001552", "3100");
       // fundMap.put("003096", "200");
        fundMap.put("001513", "1400");
        fundMap.put("320007", "3100");
        fundMap.put("004856", "0");

        //其他账号情况
        otherFundMap.put("160643", "2700");
        otherFundMap.put("008949", "2500");
        otherFundMap.put("005063", "0");
        otherFundMap.put("004997", "1000");

    }


}