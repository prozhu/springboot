package com.zc.fund;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.protocol.x.Notice;
import com.open.fund.FundModel;
import com.open.fund.FundService;
import com.open.utils.http.HttpUtils;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
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
        service.getFundIncome(paramList);
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
            FundModel temp = new FundModel();
            temp.setCode(code);
            temp.setInvestMoney(investMoney);
            paramList.add(temp);
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
    }


}
