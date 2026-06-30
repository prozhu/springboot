package com.open.weather;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.open.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

/**
 * 天气查询服务 —— 调用 Open-Meteo 第三方API查询天气
 * API文档: https://open-meteo.com/en/docs
 * @author ：zc
 * @createTime ：2026/6/29
 */
@Slf4j
public class WeatherService {

    /**
     * 根据城市名称查询当前天气
     * @param cityName 城市名称（如：北京、上海）
     * @return 天气信息
     */
    public WeatherVO queryWeatherByCity(String cityName) {
        double[] coordinate = CityCoordinate.getCoordinate(cityName);
        if (ObjectUtils.isEmpty(coordinate)) {
            log.warn("未找到城市【{}】的经纬度信息", cityName);
            return null;
        }
        return queryWeather(cityName, coordinate[0], coordinate[1]);
    }

    /**
     * 根据经纬度查询当前天气
     * @param cityName   城市名称（用于展示）
     * @param latitude   纬度
     * @param longitude  经度
     * @return 天气信息
     */
    public WeatherVO queryWeather(String cityName, double latitude, double longitude) {
        String url = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current_weather=true",
                latitude, longitude
        );
        log.info("请求天气API: {}", url);

        String result = HttpUtils.sendGet(url);
        log.info("天气API返回: {}", result);

        return parseWeatherResult(cityName, latitude, longitude, result);
    }

    /**
     * 解析天气API返回结果
     * @param cityName  城市名称
     * @param latitude  纬度
     * @param longitude 经度
     * @param jsonStr   API返回的JSON字符串
     * @return 天气信息VO
     */
    private WeatherVO parseWeatherResult(String cityName, double latitude, double longitude, String jsonStr) {
        if (ObjectUtils.isEmpty(jsonStr)) {
            log.error("天气API返回为空");
            return null;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            JSONObject currentWeather = jsonObject.getJSONObject("current_weather");
            if (ObjectUtils.isEmpty(currentWeather)) {
                log.error("天气API返回数据中没有current_weather字段: {}", jsonStr);
                return null;
            }

            WeatherVO vo = new WeatherVO();
            vo.setCityName(cityName);
            vo.setLatitude(latitude);
            vo.setLongitude(longitude);
            vo.setTemperature(currentWeather.getDouble("temperature"));
            vo.setWindSpeed(currentWeather.getDouble("windspeed"));
            vo.setWindDirection(currentWeather.getInteger("winddirection"));
            vo.setWeatherCode(currentWeather.getInteger("weathercode"));
            vo.setWeatherDescription(describeWeatherCode(currentWeather.getInteger("weathercode")));
            vo.setQueryTime(currentWeather.getString("time"));

            log.info("城市：{}，温度：{}℃，天气：{}，风速：{}km/h",
                    vo.getCityName(), vo.getTemperature(),
                    vo.getWeatherDescription(), vo.getWindSpeed());
            return vo;
        } catch (Exception e) {
            log.error("解析天气数据异常: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据WMO天气代码返回中文描述
     * @param code WMO天气代码
     * @return 天气描述
     */
    private String describeWeatherCode(Integer code) {
        if (code == null) {
            return "未知";
        }
        switch (code) {
            case 0: return "晴";
            case 1: return "大部晴朗";
            case 2: return "多云";
            case 3: return "阴天";
            case 45: case 48: return "雾";
            case 51: case 53: case 55: return "毛毛雨";
            case 56: case 57: return "冻毛毛雨";
            case 61: case 63: case 65: return "雨";
            case 66: case 67: return "冻雨";
            case 71: case 73: case 75: return "雪";
            case 77: return "雪粒";
            case 80: case 81: case 82: return "阵雨";
            case 85: case 86: return "阵雪";
            case 95: return "雷暴";
            case 96: case 99: return "雷暴伴冰雹";
            default: return "其他";
        }
    }
}