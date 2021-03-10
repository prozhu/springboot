package com.open.fix.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 库存列表的数据
 * @author ：zc
 * @createTime ：2021/3/7 14:30
 */
@NoArgsConstructor
@Data
public class OrderStockVo {
    /**
     * orderId : DGRB202103051876542564
     * amountInStock : 2600
     * amountOutStock : 2600
     * inventoryAmount : 0
     * amountNotInto : 0
     * amountAnswer : 2600
     * orderInfo : {"productionNo":"XP21020615","productName":"B262C","materialName":"B262C","materialLength":"176.00","materialWidth":"81.40","orderType":1,"orderAmount":2600,"fluteType":"AB瓦","isUrgency":0,"boxLength":"49.00","boxWidth":"37.00","boxHeight":"44.00","longitude":"120.179377","latitude":"29.393768","codeTown":330782105,"discountArea":3724.86,"orderId":"DGRB202103051876542564","customerName":"熊剑","consignee":"熊剑","contactWay":"15257966091","addressDetail":"无名路","addressRemark":"义乌市苏溪镇范家村3号","unit":"片","deliveryTime":"2021-02-24 00:00:00.0","productArea":3724.86,"essentialStatus":0,"vline":"18+44+18","lngLat":"120.179377,29.393768","streetCode":330782105,"productSize":"49.00*37.00*44.00","materialSize":"176.00*81.40"}
     * deliveryTime : 2021-02-24 00:00:00.0
     */

    private String orderId;
    private int amountInStock;
    private int amountOutStock;
    private int inventoryAmount;
    private int amountNotInto;
    private int amountAnswer;
    private OrderInfoBean orderInfo;
    private String deliveryTime;

    @NoArgsConstructor
    @Data
    public static class OrderInfoBean {
        /**
         * productionNo : XP21020615
         * productName : B262C
         * materialName : B262C
         * materialLength : 176.00
         * materialWidth : 81.40
         * orderType : 1
         * orderAmount : 2600
         * fluteType : AB瓦
         * isUrgency : 0
         * boxLength : 49.00
         * boxWidth : 37.00
         * boxHeight : 44.00
         * longitude : 120.179377
         * latitude : 29.393768
         * codeTown : 330782105
         * discountArea : 3724.86
         * orderId : DGRB202103051876542564
         * customerName : 熊剑
         * consignee : 熊剑
         * contactWay : 15257966091
         * addressDetail : 无名路
         * addressRemark : 义乌市苏溪镇范家村3号
         * unit : 片
         * deliveryTime : 2021-02-24 00:00:00.0
         * productArea : 3724.86
         * essentialStatus : 0
         * vline : 18+44+18
         * lngLat : 120.179377,29.393768
         * streetCode : 330782105
         * productSize : 49.00*37.00*44.00
         * materialSize : 176.00*81.40
         */

        private String productionNo;
        private String productName;
        private String materialName;
        private String materialLength;
        private String materialWidth;
        private int orderType;
        private int orderAmount;
        private String fluteType;
        private int isUrgency;
        private String boxLength;
        private String boxWidth;
        private String boxHeight;
        private String longitude;
        private String latitude;
        private int codeTown;
        private double discountArea;
        private String orderId;
        private String customerName;
        private String consignee;
        private String contactWay;
        private String addressDetail;
        private String addressRemark;
        private String unit;
        private String deliveryTime;
        private double productArea;
        private int essentialStatus;
        private String vline;
        private String lngLat;
        private int streetCode;
        private String productSize;
        private String materialSize;
    }
}
