package com.open.fix;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

import java.util.*;

/**
 *
 * @author ：zc
 * @createTime ：2021/3/7 13:49
 */
@Slf4j
public class StockTest {


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

   public void adjustStock(String prodctionNo) {
        //无库存信息
       List<String> noStock = new ArrayList<>();
       //入库数量为0
       List<String> noStock2 = new ArrayList<>();
       //库存数量不为0
       List<String> noStock3 = new ArrayList<>();

       String[] split = prodctionNo.split(",");
       for (int i = 0; i < split.length ; i++) {
           String productNo = split[i];
           //1. 通过生产单号查询库存信息
           JSONObject result = selectOrderStock(productNo);
           OrderStockVo orderStockVo = (OrderStockVo) analyzeResult(result, OrderStockVo.class);
           if (ObjectUtils.isEmpty(orderStockVo)) {
               noStock.add(productNo);
               log.error("查询不到该笔库存信息:{}", productNo);
               return;
           }

           //已入库数量为：0的
           if (orderStockVo.getAmountInStock() == 0) {
               noStock2.add(productNo);
           }

           //如果：库存数量不等于0，就进行下面的操作
           if (orderStockVo.getInventoryAmount() == 0) {
               log.error("库存数量为 0 ", productNo);
               return;
           } else {
               noStock3.add(productNo);
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
       }

       //打印有异常的单号
       log.info("无库存信息：{}", JSON.toJSONString(noStock));
       log.info("入库数量为0：{}", JSON.toJSONString(noStock2));
       log.info("库存数量不为0 :{}", JSON.toJSONString(noStock3));
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
        //https://supplier.djcps.com/djsupplier/djwmsservice/inventory/outStock.do
        String url = "https://supplier.djcps.com/djsupplier/djwmsservice/inventory/outStock.do";
        //传参：  OutStockBo
        OutStockBo param = new OutStockBo();
        param.setOrderId(stockVo.getOrderId());
        param.setOutStockAmount(waitingNum);
        List<StockVo.WarehouseListBean> warehouseList = stockVo.getWarehouseList();
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
        requestHeaders.add("Cookie", "appName=djsupplier; token=83f1a929626d454e8917e03ad234ad48");
        HttpEntity<Map> requestEntity = new HttpEntity<Map>(params, requestHeaders);
        return client.exchange(url, HttpMethod.POST, requestEntity, JSONObject.class).getBody();
    }

    public static JSONObject sendGetRequest(String url) {
        RestTemplate client = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "appName=djsupplier; token=83f1a929626d454e8917e03ad234ad48");
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
