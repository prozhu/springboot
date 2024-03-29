package com.open.fund;

import com.open.utils.email.EmailManage;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author ：zc
 * @createTime ：2021/2/2 14:00
 */
@Slf4j
public class FundServiceTest {

    private static HashMap<String, String> fundMap = new HashMap<>();
    private static HashMap<String, String> otherFundMap = new HashMap<>();
    private static HashMap<String, String> wxFundMap = new HashMap<>();


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
        BigDecimal money2 = service.getFundIncome(paramList);

        log.info("微信账号情况");
        service = new FundService();
        paramList = mapToList(wxFundMap);
        BigDecimal money3 = service.getFundIncome(paramList);
        log.info("make money: {} ", money.add(money2).add(money3));
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


    static {
        fundMap.put("003304", "2000");
        fundMap.put("160633", "3500");
        fundMap.put("003096", "6500");
        fundMap.put("005312", "5500");
        fundMap.put("001102", "6000");
        fundMap.put("004854", "6000");
        fundMap.put("002132", "2000");
        fundMap.put("004997", "3000");
        fundMap.put("005063", "1000");
        fundMap.put("004856", "1000");


        //其他账号情况
        otherFundMap.put("167301", "6000");
        otherFundMap.put("005063", "17000");
        otherFundMap.put("008949", "4000");
        otherFundMap.put("001102", "6000");

        //微信
        wxFundMap.put("005063", "7000");


    }


}
