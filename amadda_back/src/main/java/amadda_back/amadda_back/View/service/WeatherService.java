package amadda_back.amadda_back.View.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import amadda_back.amadda_back.View.domain.entity.WeatherResponseDTO;
import amadda_back.amadda_back.View.mapper.WeatherDescriptionMapper;

@Service
public class WeatherService {

    private static final String API_KEY = "5cc85d245b489f947f813431ec416a0d";
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";

    public WeatherResponseDTO getWeatherByLocation(double lat, double lon) {
        RestTemplate restTemplate = new RestTemplate();
        String url = WEATHER_URL + "?lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY + "&units=metric";

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        System.out.println(response); // 응답 로그 출력

        Map<String, Object> mainData = (Map<String, Object>) response.get("main");
        List<Map<String, Object>> weatherData = (List<Map<String, Object>>) response.get("weather");

        WeatherResponseDTO weatherResponse = new WeatherResponseDTO();

        // 숫자 타입을 Number로 받고 double로 변환
        weatherResponse.setTemp(((Number) mainData.get("temp")).doubleValue());
        weatherResponse.setFeelsLike(((Number) mainData.get("feels_like")).doubleValue());
        weatherResponse.setTempMin(((Number) mainData.get("temp_min")).doubleValue());
        weatherResponse.setTempMax(((Number) mainData.get("temp_max")).doubleValue());

        if (!weatherData.isEmpty()) {
            weatherResponse.setMain((String) weatherData.get(0).get("main"));
            weatherResponse.setIcon((String) weatherData.get(0).get("icon"));

            int weatherId = (int) weatherData.get(0).get("id");
            List<String> descriptions = WeatherDescriptionMapper.getWeatherDescription(weatherId);

            weatherResponse.setDescription(descriptions.get(0)); // 간단 설명 설정
            weatherResponse.setDetailedDescription(descriptions.get(1)); // 상세 설명 설정
        }

        return weatherResponse;
    }

}
