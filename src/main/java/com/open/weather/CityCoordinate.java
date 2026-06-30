package com.open.weather;

import java.util.HashMap;
import java.util.Map;

/**
 * 城市经纬度常量
 * @author ：zc
 * @createTime ：2026/6/29
 */
public class CityCoordinate {

    private static final Map<String, double[]> CITY_MAP = new HashMap<>();

    static {
        CITY_MAP.put("北京", new double[]{39.9042, 116.4074});
        CITY_MAP.put("上海", new double[]{31.2304, 121.4737});
        CITY_MAP.put("广州", new double[]{23.1291, 113.2644});
        CITY_MAP.put("深圳", new double[]{22.5431, 114.0579});
        CITY_MAP.put("杭州", new double[]{30.2741, 120.1551});
        CITY_MAP.put("成都", new double[]{30.5728, 104.0668});
        CITY_MAP.put("武汉", new double[]{30.5928, 114.3055});
        CITY_MAP.put("南京", new double[]{32.0603, 118.7969});
        CITY_MAP.put("西安", new double[]{34.3416, 108.9398});
        CITY_MAP.put("重庆", new double[]{29.5630, 106.5516});
    }

    /**
     * 根据城市名称获取经纬度
     * @param cityName 城市名称
     * @return [纬度, 经度]，未找到返回null
     */
    public static double[] getCoordinate(String cityName) {
        return CITY_MAP.get(cityName);
    }

    /**
     * 获取所有支持的城市
     * @return 城市名称数组
     */
    public static String[] getSupportedCities() {
        return CITY_MAP.keySet().toArray(new String[0]);
    }
}