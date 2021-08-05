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
        fundMap.put("160633", "8000");
        fundMap.put("001552", "8000");
        fundMap.put("004856", "4000");
        fundMap.put("001027", "4500");
        fundMap.put("260101", "500");
//        fundMap.put("000522", "500");
        fundMap.put("161005", "1500");


        //其他账号情况
        otherFundMap.put("167301", "9500");
        otherFundMap.put("004997", "3000");
        otherFundMap.put("001552", "4000");
        otherFundMap.put("005063", "8500");

        //微信
        wxFundMap.put("005063", "4000");
        wxFundMap.put("001027", "3000");


    }


}
