package com.open.weather;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 天气查询服务测试类
 * 调用Open-Meteo第三方API查询天气
 * @author ：zc
 * @createTime ：2026/6/29
 */
@Slf4j
public class WeatherServiceTest {

    private WeatherService weatherService = new WeatherService();

    /**
     * 测试根据城市名称查询天气
     */
    @Test
    public void testQueryWeatherByCity() {
        String city = "武汉";
        WeatherVO weather = weatherService.queryWeatherByCity(city);
        if (weather != null) {
            log.info("====== 查询结果 ======");
            log.info("城市：{}", weather.getCityName());
            log.info("经纬度：{}, {}", weather.getLatitude(), weather.getLongitude());
            log.info("温度：{}℃", weather.getTemperature());
            log.info("天气：{}", weather.getWeatherDescription());
            log.info("风速：{}km/h", weather.getWindSpeed());
            log.info("风向：{}°", weather.getWindDirection());
            log.info("天气代码：{}", weather.getWeatherCode());
            log.info("查询时间：{}", weather.getQueryTime());
        } else {
            log.error("查询天气失败，城市：{}", city);
        }
    }

    /**
     * 测试批量查询多个城市的天气
     */
    @Test
    public void testQueryMultipleCities() {
        String[] cities = CityCoordinate.getSupportedCities();
        log.info("====== 开始查询{}个城市的天气 ======", cities.length);

        for (String city : cities) {
            WeatherVO weather = weatherService.queryWeatherByCity(city);
            if (weather != null) {
                log.info("{}：{}℃，{}", weather.getCityName(),
                        weather.getTemperature(), weather.getWeatherDescription());
            } else {
                log.warn("{}：查询失败", city);
            }
        }
        log.info("====== 查询完毕 ======");
    }

    /**
     * 测试根据经纬度查询天气（自定义坐标）
     */
    @Test
    public void testQueryWeatherByCoordinate() {
        // 查询拉萨的天气（使用经纬度：29.65, 91.10）
        double latitude = 29.65;
        double longitude = 91.10;
        String cityName = "拉萨";

        WeatherVO weather = weatherService.queryWeather(cityName, latitude, longitude);
        if (weather != null) {
            log.info("{}：{}℃，{}", weather.getCityName(),
                    weather.getTemperature(), weather.getWeatherDescription());
        } else {
            log.error("查询天气失败，坐标：{}, {}", latitude, longitude);
        }
    }

    /**
     * 测试查询不存在的城市
     */
    @Test
    public void testQueryUnsupportedCity() {
        String city = "不存在的城市";
        WeatherVO weather = weatherService.queryWeatherByCity(city);
        if (weather == null) {
            log.info("查询不存在的城市返回null，符合预期");
        } else {
            log.warn("查询不存在的城市返回了结果，不符合预期");
        }
    }
}