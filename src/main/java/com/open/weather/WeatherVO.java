package com.open.weather;

import lombok.Data;

/**
 * 天气信息模型
 * @author ：zc
 * @createTime ：2026/6/29
 */
@Data
public class WeatherVO {

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 当前温度（摄氏度）
     */
    private Double temperature;

    /**
     * 风速（km/h）
     */
    private Double windSpeed;

    /**
     * 风向（角度）
     */
    private Integer windDirection;

    /**
     * 天气代码
     */
    private Integer weatherCode;

    /**
     * 天气描述
     */
    private String weatherDescription;

    /**
     * 查询时间
     */
    private String queryTime;
}