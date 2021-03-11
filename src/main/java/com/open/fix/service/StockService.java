package com.open.fix.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.open.fix.model.AdjustStockVo;
import com.open.fix.model.OrderStockVo;
import com.open.fix.model.OutStockBo;
import com.open.fix.model.StockVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库存业务层
 * @author ：zc
 * @createTime ：2021/3/7 15:33
 */
@Slf4j
public class StockService {


    @Test
    public void main() {
        String productNo = "XP21023774";
        //1. 通过生产单号查询库存信息
        JSONObject result = selectOrderStock(productNo);
        OrderStockVo orderStockVo = (OrderStockVo) analyzeResult(result, OrderStockVo.class);
        if (ObjectUtils.isEmpty(orderStockVo)) {
            log.error("查询不到该笔库存信息:{}", productNo);
            return;
        }

        //如果：库存数量不等于0，就进行下面的操作
        if (orderStockVo.getInventoryAmount() == 0) {
            log.error("库存数量为 0 ", productNo);
            return;
        }
        //计算"待出库数量" : 待出库数量 = 订单数量 - 已出数量
        int waitingNum = orderStockVo.getAmountAnswer() - orderStockVo.getAmountOutStock();
        if (waitingNum < 0) {
            log.error("待出库数量 小于 0 ", productNo);
            return;
        }

        //查询库存详情
        StockVo stockVo = selectStockDetail(orderStockVo.getOrderId());
        //构建 出库接口的入参
        // boolean outStockSuccess = requestOutStock(stockVo, waitingNum);

        //第二部分： 应配调整， 关闭订单


        System.out.println(result.toJSONString());
    }

    public AdjustStockVo adjustStock(String[] prodctionNo) throws  Exception{
        //无库存信息
        List<String> noStock = new ArrayList<>();
        //入库数量为0
        List<String> noStock2 = new ArrayList<>();
        //库存数量不为0
        List<String> noStock3 = new ArrayList<>();
        //库存数量为：0
        List<String> noStock4 = new ArrayList<>();

        for (int i = 0; i < prodctionNo.length; i++) {
            log.info("正在处理第{}条数据，请稍后~", i + 1);
            String productNo = prodctionNo[i];
            //1. 通过生产单号查询库存信息
            JSONObject result = selectOrderStock(productNo);
            OrderStockVo orderStockVo = (OrderStockVo) analyzeResult(result, OrderStockVo.class);
            if (ObjectUtils.isEmpty(orderStockVo)) {
                noStock.add(productNo);
                log.error("查询不到该笔库存信息:{}", productNo);
                continue;
            }

            //已入库数量为：0的
            if (orderStockVo.getAmountInStock() == 0) {
                noStock2.add(productNo);
            }

            //如果：库存数量不等于0，就进行下面的操作
            if (orderStockVo.getInventoryAmount() == 0) {
                log.error("库存数量为 0 ", productNo);
                noStock4.add(productNo);
                continue;
            } else {
                noStock3.add(productNo);
            }
            //计算"待出库数量" : 待出库数量 = 订单数量 - 已出数量
            int waitingNum = orderStockVo.getAmountAnswer() - orderStockVo.getAmountOutStock();
            if (waitingNum <= 0 && orderStockVo.getInventoryAmount() == 0) {
                log.error("待出库数量 小于 0 ", productNo);
                continue;
            } else {
                waitingNum = orderStockVo.getInventoryAmount();
            }

            //查询库存详情
            StockVo stockVo = selectStockDetail(orderStockVo.getOrderId());
            //构建 出库接口的入参
            boolean outStockSuccess = requestOutStock(stockVo, waitingNum);
            if (!outStockSuccess) {
                log.error("出库失败：{}", JSON.toJSONString(stockVo));
                continue;
            }
            Thread.sleep(100);

            //第二部分： 应配调整， 关闭订单
            if (orderStockVo.getAmountInStock() != orderStockVo.getAmountAnswer()) {
                continue;
            }
            int adjustAmout = - orderStockVo.getAmountAnswer();
            boolean adjustSuccess = changeDistribution(orderStockVo.getOrderId(), adjustAmout);
            if (!adjustSuccess) {
                log.error("应配调整， 关闭订单失败：{}", JSON.toJSONString(stockVo));
                continue;
            }
        }

        //打印有异常的单号
        AdjustStockVo result = new AdjustStockVo();
        log.info("无库存信息：{}", JSON.toJSONString(noStock));
        result.setNoStock(noStock);
        log.info("入库数量为0：{}", JSON.toJSONString(noStock2));
        result.setNoStock2(noStock2);
        log.info("库存数量不为0 :{}", JSON.toJSONString(noStock3));
        result.setNoStock3(noStock3);
        log.info("库存数量为0 :{}", JSON.toJSONString(noStock4));
        result.setNoStock3(noStock4);
        return result;
    }

    @Test
    public void test2() {
        String orderId = "DGRB202103061878446504";
        int amout = -400;
        boolean ss = changeDistribution(  orderId,   amout);
    }


    /**
     * 应配调整
     * @param orderId
     * @param amout
     * @return
     */
    private boolean changeDistribution(String orderId, int amout) {
        //https://supplier.djcps.com/djsupplier/djwmsservice/allocation/changeDistribution.do
        String url = "https://supplier.djcps.com/djsupplier/djwmsservice/allocation/changeDistribution.do";
        //传参: {"orderId":"DGRB202101091474275424","amount":-100,"adjustCause":3}
        HashMap<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("amount", amout);
        params.put("adjustCause", 3);
        //订单数量和应配数量相等的情况下，才允许关闭订单
        JSONObject result = sendPostRequest(url, params);
        log.info("调用应配调整接口的请求结果：{}", result.toJSONString());
        return result.getBoolean("success");
    }


    /**
     * 查询库存的详情
     * @param orderNo
     * @return
     */
    private StockVo selectStockDetail(String orderNo) {
        //https://supplier.djcps.com/djsupplier/djwmsservice/inventory/detail.do?orderId=DGRB202101161854841519   get请求
        String url = "https://supplier.djcps.com/djsupplier/djwmsservice/inventory/detail.do?orderId=" + orderNo;
        JSONObject result = sendGetRequest(url);
        log.info(result.toJSONString());
        return result.getJSONObject("data").toJavaObject(StockVo.class);
    }

    /**
     * 调用出库接口
     * @param stockVo 库存详情
     * @param waitingNum  待出库数量
     * @return
     */
    private boolean requestOutStock(StockVo stockVo, int waitingNum) {
        //如果待出库数量为 0，直接返回，不进行出库操作
        if (waitingNum == 0 ) {
            return true;
        }
        //https://supplier.djcps.com/djsupplier/djwmsservice/inventory/outStock.do
        String url = "https://supplier.djcps.com/djsupplier/djwmsservice/inventory/outStock.do";
        //传参：  OutStockBo
        OutStockBo param = new OutStockBo();
        param.setOrderId(stockVo.getOrderId());
        param.setOutStockAmount(waitingNum);
        List<StockVo.WarehouseListBean> warehouseList = stockVo.getWarehouseList();
        if (ObjectUtils.isEmpty(warehouseList)) {
            log.error("该笔订单无库区信息：{}", stockVo);
            return true;
        }
        StockVo.WarehouseListBean warehouseListBean = warehouseList.get(0);
        StockVo.WarehouseListBean.AreaListBean areaListBean = warehouseListBean.getAreaList().get(0);

        param.setWarehouseAreaId(areaListBean.getWarehouseAreaId());
        param.setWarehouseId(warehouseListBean.getWarehouseId());
        param.setWarehouseLocId(areaListBean.getLocationList().get(0).getWarehouseLocId());

        //参数转换
        Map map = JSONObject.parseObject(JSONObject.toJSONString(param), Map.class);

        JSONObject result = sendPostRequest(url, map);
        log.info("调用出库接口的请求结果：{}", result.toJSONString());
        return result.getBoolean("success");
    }


    public JSONObject adjustStock() {
        //https://supplier.djcps.com/djsupplier/djwmsservice/order/pageInventory.do
        //{"pageNo":1,"pageSize":15,"productionNo":"XP21020615"}
        //1. 通过生产单号查询库存信息
        String url = "https://supplier.djcps.com/djsupplier/djwmsservice/order/pageInventory.do";
        Map<String, Object> params = new HashMap<>();
        params.put("pageNo", "1");
        params.put("pageSize", "15");
        params.put("productionNo", "XP21020615");
        JSONObject result = sendPostRequest(url, params);
        //2. 判断库存是否出库
        //3. 执行出库操作，对库存进行清零
        //4. 应配调整，关闭订单
        return result;
    }

    /**
     * 根据生产单号查询库存信息
     * @param productNo
     * @return
     */
    private JSONObject selectOrderStock(String productNo) {
        String url = "https://supplier.djcps.com/djsupplier/djwmsservice/order/pageInventory.do";
        Map<String, Object> params = new HashMap<>();
        params.put("pageNo", "1");
        params.put("pageSize", "15");
        params.put("productionNo", productNo);
        JSONObject result = sendPostRequest(url, params);
        log.info("根据生产单号查询库存信息, 结果为：{}", result.toJSONString());
        return result;
    }

    public static JSONObject sendPostRequest(String url, Map params) {
        RestTemplate client = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "appName=djsupplier; token=e674c69f788744e7ab116479ef7d4208");
        HttpEntity<Map> requestEntity = new HttpEntity<Map>(params, requestHeaders);
        return client.exchange(url, HttpMethod.POST, requestEntity, JSONObject.class).getBody();
    }

    public static JSONObject sendGetRequest(String url) {
        RestTemplate client = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "appName=djsupplier; token=e674c69f788744e7ab116479ef7d4208");
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(null, requestHeaders);
        return client.exchange(url, HttpMethod.GET, requestEntity, JSONObject.class).getBody();
    }

    public Object analyzeResult(JSONObject result, Class cls) {
        JSONArray jsonArray = result.getJSONObject("data").getJSONArray("list");
        if (jsonArray.isEmpty()) {
            return null;
        }
        return jsonArray.getJSONObject(0).toJavaObject(cls);
    }
}
