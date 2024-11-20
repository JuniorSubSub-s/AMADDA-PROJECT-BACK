package amadda_back.amadda_back.weatherAPI.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import amadda_back.amadda_back.weatherAPI.domain.entity.WeatherDTO;

@Service
public class GetWeatherService {

    // 한글 설명 매핑
    private static final Map<Integer, String> WEATHER_DESC_KO = new HashMap<>();
    private static final Map<String, String> WEATHER_MAIN_KO = new HashMap<>();

    static {
        WEATHER_DESC_KO.put(201, "가벼운 비를 동반한 천둥구름");
        WEATHER_DESC_KO.put(200, "비를 동반한 천둥구름");
        WEATHER_DESC_KO.put(202, "폭우를 동반한 천둥구름");
        WEATHER_DESC_KO.put(210, "약한 천둥구름");
        WEATHER_DESC_KO.put(211, "천둥구름");
        WEATHER_DESC_KO.put(212, "강한 천둥구름");
        WEATHER_DESC_KO.put(221, "불규칙적 천둥구름");
        WEATHER_DESC_KO.put(230, "약한 연무를 동반한 천둥구름");
        WEATHER_DESC_KO.put(231, "연무를 동반한 천둥구름");
        WEATHER_DESC_KO.put(232, "강한 안개비를 동반한 천둥구름");
        WEATHER_DESC_KO.put(300, "가벼운 안개비");
        WEATHER_DESC_KO.put(301, "안개비");
        WEATHER_DESC_KO.put(302, "강한 안개비");
        WEATHER_DESC_KO.put(310, "가벼운 적은비");
        WEATHER_DESC_KO.put(311, "적은비");
        WEATHER_DESC_KO.put(312, "강한 적은비");
        WEATHER_DESC_KO.put(313, "소나기와 안개비");
        WEATHER_DESC_KO.put(314, "강한 소나기와 안개비");
        WEATHER_DESC_KO.put(321, "소나기");
        WEATHER_DESC_KO.put(500, "악한 비");
        WEATHER_DESC_KO.put(501, "중간 비");
        WEATHER_DESC_KO.put(502, "강한 비");
        WEATHER_DESC_KO.put(503, "매우 강한 비");
        WEATHER_DESC_KO.put(504, "극심한 비");
        WEATHER_DESC_KO.put(511, "우박");
        WEATHER_DESC_KO.put(520, "약한 소나기 비");
        WEATHER_DESC_KO.put(521, "소나기 비");
        WEATHER_DESC_KO.put(522, "강한 소나기 비");
        WEATHER_DESC_KO.put(531, "불규칙적 소나기 비");
        WEATHER_DESC_KO.put(600, "가벼운 눈");
        WEATHER_DESC_KO.put(601, "눈");
        WEATHER_DESC_KO.put(602, "강한 눈");
        WEATHER_DESC_KO.put(611, "진눈깨비");
        WEATHER_DESC_KO.put(612, "소나기 진눈깨비");
        WEATHER_DESC_KO.put(615, "약한 비와 눈");
        WEATHER_DESC_KO.put(616, "비와 눈");
        WEATHER_DESC_KO.put(620, "약한 소나기 눈");
        WEATHER_DESC_KO.put(621, "소나기 눈");
        WEATHER_DESC_KO.put(622, "강한 소나기 눈");
        WEATHER_DESC_KO.put(701, "박무");
        WEATHER_DESC_KO.put(711, "연기");
        WEATHER_DESC_KO.put(721, "연무");
        WEATHER_DESC_KO.put(731, "모래 먼지");
        WEATHER_DESC_KO.put(741, "안개");
        WEATHER_DESC_KO.put(751, "모래");
        WEATHER_DESC_KO.put(761, "먼지");
        WEATHER_DESC_KO.put(762, "화산재");
        WEATHER_DESC_KO.put(771, "돌풍");
        WEATHER_DESC_KO.put(781, "토네이도");
        WEATHER_DESC_KO.put(800, "구름 한 점 없는 맑은 하늘");
        WEATHER_DESC_KO.put(801, "약간의 구름이 낀 하늘");
        WEATHER_DESC_KO.put(802, "드문드문 구름이 낀 하늘");
        WEATHER_DESC_KO.put(803, "구름이 거의 없는 하늘");
        WEATHER_DESC_KO.put(804, "구름으로 뒤덮인 흐린 하늘");
        WEATHER_DESC_KO.put(900, "토네이도");
        WEATHER_DESC_KO.put(901, "태풍");
        WEATHER_DESC_KO.put(902, "허리케인");
        WEATHER_DESC_KO.put(903, "한랭");
        WEATHER_DESC_KO.put(904, "고온");
        WEATHER_DESC_KO.put(905, "바람부는");
        WEATHER_DESC_KO.put(906, "우박");
        WEATHER_DESC_KO.put(951, "바람이 거의 없는");
        WEATHER_DESC_KO.put(952, "약한 바람");
        WEATHER_DESC_KO.put(953, "부드러운 바람");
        WEATHER_DESC_KO.put(954, "중간 세기 바람");
        WEATHER_DESC_KO.put(955, "신선한 바람");
        WEATHER_DESC_KO.put(956, "센 바람");
        WEATHER_DESC_KO.put(957, "돌풍에 가까운 센 바람");
        WEATHER_DESC_KO.put(958, "돌풍");
        WEATHER_DESC_KO.put(959, "심각한 돌풍");
        WEATHER_DESC_KO.put(960, "폭풍");
        WEATHER_DESC_KO.put(961, "강한 폭풍");
        WEATHER_DESC_KO.put(962, "허리케인");

        WEATHER_MAIN_KO.put("Thunderstorm", "천둥구름");
        WEATHER_MAIN_KO.put("Drizzle", "이슬비");
        WEATHER_MAIN_KO.put("Rain", "비");
        WEATHER_MAIN_KO.put("Snow", "눈");
        WEATHER_MAIN_KO.put("Clear", "맑음");
        WEATHER_MAIN_KO.put("Clouds", "구름");
        WEATHER_MAIN_KO.put("Mist", "안개");
        WEATHER_MAIN_KO.put("Smoke", "연기");
        WEATHER_MAIN_KO.put("Haze", "연무");
        WEATHER_MAIN_KO.put("Dust", "먼지");
        WEATHER_MAIN_KO.put("Fog", "안개");
        WEATHER_MAIN_KO.put("Sand", "모래");
        WEATHER_MAIN_KO.put("Ash", "화산재");
        WEATHER_MAIN_KO.put("Squall", "돌풍");
        WEATHER_MAIN_KO.put("Tornado", "토네이도");
    }

    // 바람 방향을 동서남북으로 변환하는 메서드
    private String convertWindDirection(double degree) {
        if (degree >= 0 && degree < 45)
            return "북";
        if (degree >= 45 && degree < 90)
            return "동북";
        if (degree >= 90 && degree < 135)
            return "동";
        if (degree >= 135 && degree < 180)
            return "동남";
        if (degree >= 180 && degree < 225)
            return "남";
        if (degree >= 225 && degree < 270)
            return "서남";
        if (degree >= 270 && degree < 315)
            return "서";
        if (degree >= 315 && degree < 360)
            return "북서";
        return "알 수 없는 방향";
    }

    public List<WeatherDTO> parseJson(String jsonData) {
        List<WeatherDTO> weatherList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        String day = "";
        String cityName = "";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonData);

            cityName = rootNode.path("city").path("name").asText();
            JsonNode weatherNodes = rootNode.path("list");

            for (JsonNode weatherNode : weatherNodes) {
                WeatherDTO weatherDTO = new WeatherDTO();

                String dateTime = weatherNode.path("dt_txt").asText();
                String[] dateTimeSplit = dateTime.split(" ");
                String date = dateTimeSplit[0];
                String time = dateTimeSplit[1];

                if (LocalDate.parse(date).equals(today) || !date.equals(day)) {
                    int weatherId = weatherNode.path("weather").get(0).path("id").asInt();
                    String descriptionKo = WEATHER_DESC_KO.getOrDefault(weatherId, "알 수 없는 날씨");

                    String icon = weatherNode.path("weather").get(0).path("icon").asText();
                    String main = weatherNode.path("weather").get(0).path("main").asText();
                    String mainKo = WEATHER_MAIN_KO.getOrDefault(main, "알 수 없는 상태");

                    double temp = weatherNode.path("main").path("temp").asDouble();
                    double tempMin = weatherNode.path("main").path("temp_min").asDouble();
                    double tempMax = weatherNode.path("main").path("temp_max").asDouble();
                    double humidity = weatherNode.path("main").path("humidity").asDouble(); // 습도
                    double feelsLike = weatherNode.path("main").path("feels_like").asDouble(); // 체감온도
                    double rain = weatherNode.path("rain").path("3h").asDouble(); // 강수량
                    double windSpeed = weatherNode.path("wind").path("speed").asDouble(); // 바람의 속도
                    double windDeg = weatherNode.path("wind").path("deg").asDouble(); // 바람의 방향

                    String windDirection = convertWindDirection(windDeg); // 바람 방향 변환

                    weatherDTO.setCityName(cityName);
                    weatherDTO.setDate(date);
                    weatherDTO.setTime(time);
                    weatherDTO.setIcon(icon);
                    weatherDTO.setMain(main);
                    weatherDTO.setMainKo(mainKo); // 한글 설명 추가
                    weatherDTO.setTemp(temp);
                    weatherDTO.setTempMin(tempMin);
                    weatherDTO.setTempMax(tempMax);
                    weatherDTO.setHumidity(humidity); // 습도
                    weatherDTO.setFeelsLike(feelsLike); // 체감온도
                    weatherDTO.setRain(rain); // 강수량
                    weatherDTO.setWindSpeed(windSpeed); // 바람속도
                    weatherDTO.setWindDirection(windDirection); // 바람 방향 추가
                    weatherDTO.setWeatherId(weatherId); // 날씨 ID 추가
                    weatherDTO.setDescriptionKo(descriptionKo); // 한글 설명 추가

                    weatherList.add(weatherDTO);
                    day = date;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();;
        }

        return weatherList;
    }

}
