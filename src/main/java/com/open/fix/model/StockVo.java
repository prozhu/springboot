package com.open.fix.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 库存详情模型
 * @author ：zc
 * @createTime ：2021/3/7 14:52
 */
@NoArgsConstructor
@Data
public class StockVo {


    /**
     * orderId : DGRB202101161854841519
     * amountInStock : 3000
     * amountOutStock : 1
     * inventoryAmount : 2999
     * amountNotInto : 0
     * amountAnswer : 3000
     * warehouseList : [{"id":"a0ee9f297e8746a3b4f7a9008872af8d","orderId":"DGRB202101161854841519","warehouseId":"WH01","warehouseName":"丰收纸板仓库","areaList":[{"id":"a0ee9f297e8746a3b4f7a9008872af8d","orderId":"DGRB202101161854841519","warehouseAreaId":"WA01","warehouseAreaName":"浦江内","warehouseId":"WH01","warehouseName":"丰收纸板仓库","locationList":[{"partnerId":null,"partnerName":null,"partnerArea":null,"operatorId":null,"operator":null,"warehouseLocInfoId":"81e71ebd-4363-11eb-9ea1-00163e145df5","amount":2999,"warehouseLocId":"WL001","warehouseLocName":"测试库位","warehouseAreaId":"WA01","warehouseAreaName":"浦江内","warehouseId":"WH01","warehouseName":"丰收纸板仓库"}]}]}]
     * orderInfo : {"productionNo":"XP2101O208","productName":"B4C","materialName":"B4C","materialLength":"88.00","materialWidth":"42.20","orderType":1,"orderAmount":3000,"fluteType":"单B瓦","isUrgency":0,"boxLength":"21.00","boxWidth":"21.00","boxHeight":"21.00","longitude":"120.195828","latitude":"29.282607","codeTown":330783004,"discountArea":1114.08,"orderId":"DGRB202101161854841519","customerName":"李龙滔","consignee":"李龙滔","contactWay":"13516951192","addressDetail":"黄桥路99号","addressRemark":"东阳市白云街道黄桥路70号","address":null,"unit":"片","deliveryTime":"2021-01-18 00:00:00","productArea":1114.08,"essentialStatus":0,"vline":"10+21+10","lngLat":"120.195828,29.282607","streetCode":330783004,"materialSize":"88.00*42.20","productSize":"21.00*21.00*21.00"}
     * deliveryTime : 2021-01-18 00:00:00
     */

    private String orderId;
    private int amountInStock;
    private int amountOutStock;
    private int inventoryAmount;
    private int amountNotInto;
    private int amountAnswer;
    private OrderInfoBean orderInfo;
    private String deliveryTime;
    private List<WarehouseListBean> warehouseList;

    @NoArgsConstructor
    @Data
    public static class OrderInfoBean {
        /**
         * productionNo : XP2101O208
         * productName : B4C
         * materialName : B4C
         * materialLength : 88.00
         * materialWidth : 42.20
         * orderType : 1
         * orderAmount : 3000
         * fluteType : 单B瓦
         * isUrgency : 0
         * boxLength : 21.00
         * boxWidth : 21.00
         * boxHeight : 21.00
         * longitude : 120.195828
         * latitude : 29.282607
         * codeTown : 330783004
         * discountArea : 1114.08
         * orderId : DGRB202101161854841519
         * customerName : 李龙滔
         * consignee : 李龙滔
         * contactWay : 13516951192
         * addressDetail : 黄桥路99号
         * addressRemark : 东阳市白云街道黄桥路70号
         * address : null
         * unit : 片
         * deliveryTime : 2021-01-18 00:00:00
         * productArea : 1114.08
         * essentialStatus : 0
         * vline : 10+21+10
         * lngLat : 120.195828,29.282607
         * streetCode : 330783004
         * materialSize : 88.00*42.20
         * productSize : 21.00*21.00*21.00
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
        private Object address;
        private String unit;
        private String deliveryTime;
        private double productArea;
        private int essentialStatus;
        private String vline;
        private String lngLat;
        private int streetCode;
        private String materialSize;
        private String productSize;
    }

    @NoArgsConstructor
    @Data
    public static class WarehouseListBean {
        /**
         * id : a0ee9f297e8746a3b4f7a9008872af8d
         * orderId : DGRB202101161854841519
         * warehouseId : WH01
         * warehouseName : 丰收纸板仓库
         * areaList : [{"id":"a0ee9f297e8746a3b4f7a9008872af8d","orderId":"DGRB202101161854841519","warehouseAreaId":"WA01","warehouseAreaName":"浦江内","warehouseId":"WH01","warehouseName":"丰收纸板仓库","locationList":[{"partnerId":null,"partnerName":null,"partnerArea":null,"operatorId":null,"operator":null,"warehouseLocInfoId":"81e71ebd-4363-11eb-9ea1-00163e145df5","amount":2999,"warehouseLocId":"WL001","warehouseLocName":"测试库位","warehouseAreaId":"WA01","warehouseAreaName":"浦江内","warehouseId":"WH01","warehouseName":"丰收纸板仓库"}]}]
         */

        private String id;
        private String orderId;
        private String warehouseId;
        private String warehouseName;
        private List<AreaListBean> areaList;

        @NoArgsConstructor
        @Data
        public static class AreaListBean {
            /**
             * id : a0ee9f297e8746a3b4f7a9008872af8d
             * orderId : DGRB202101161854841519
             * warehouseAreaId : WA01
             * warehouseAreaName : 浦江内
             * warehouseId : WH01
             * warehouseName : 丰收纸板仓库
             * locationList : [{"partnerId":null,"partnerName":null,"partnerArea":null,"operatorId":null,"operator":null,"warehouseLocInfoId":"81e71ebd-4363-11eb-9ea1-00163e145df5","amount":2999,"warehouseLocId":"WL001","warehouseLocName":"测试库位","warehouseAreaId":"WA01","warehouseAreaName":"浦江内","warehouseId":"WH01","warehouseName":"丰收纸板仓库"}]
             */

            private String id;
            private String orderId;
            private String warehouseAreaId;
            private String warehouseAreaName;
            private String warehouseId;
            private String warehouseName;
            private List<LocationListBean> locationList;

            @NoArgsConstructor
            @Data
            public static class LocationListBean {
                /**
                 * partnerId : null
                 * partnerName : null
                 * partnerArea : null
                 * operatorId : null
                 * operator : null
                 * warehouseLocInfoId : 81e71ebd-4363-11eb-9ea1-00163e145df5
                 * amount : 2999
                 * warehouseLocId : WL001
                 * warehouseLocName : 测试库位
                 * warehouseAreaId : WA01
                 * warehouseAreaName : 浦江内
                 * warehouseId : WH01
                 * warehouseName : 丰收纸板仓库
                 */

                private Object partnerId;
                private Object partnerName;
                private Object partnerArea;
                private Object operatorId;
                private Object operator;
                private String warehouseLocInfoId;
                private int amount;
                private String warehouseLocId;
                private String warehouseLocName;
                private String warehouseAreaId;
                private String warehouseAreaName;
                private String warehouseId;
                private String warehouseName;
            }
        }
    }
}
