package amadda_back.amadda_back.weatherAPI.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import amadda_back.amadda_back.weatherAPI.domain.entity.WeatherDTO;
import amadda_back.amadda_back.weatherAPI.service.GetWeatherService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class WeatherController {

    // 원준 키
    @Value("${weather.service.key}")
    private String serviceKey;

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    // 윤성 키
    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.callBackUrl}")
    private String callBackUrl;

    @Autowired
    GetWeatherService getWeatherService;

    @GetMapping("/weatherDetails")
    public ResponseEntity<Object> getWeather(
            @RequestParam(name="lat") double lat, 
            @RequestParam(name="lon") double lon) {
        
        System.out.println("client end point : /api/weather1");
        System.out.println("serviceKey : " + apiKey);
        System.out.println("params: " + lat + ", " + lon);

        // 날씨 API URL 생성
        String requestURL = callBackUrl + 
                "?lat=" + lat +
                "&lon=" + lon +
                "&appid=" + apiKey +
                "&units=metric";
        
        System.out.println("url check : " + requestURL);

        HttpURLConnection http = null;
        InputStream stream = null;
        String result = null;
        List<WeatherDTO> list = null;

        try {
            URL url = new URL(requestURL);
            http = (HttpURLConnection) url.openConnection(); // HTTP 연결 설정
            System.out.println("http connection : " + http);
            int code = http.getResponseCode(); // HTTP 응답 코드 확인
            System.out.println("http response code : " + code); //200이 나와야 정상

            if(code == 200) {
                stream = http.getInputStream(); //응답데이터를 InputStream으로 읽음
                result = readString(stream);

                List<WeatherDTO> weatherList = getWeatherService.parseJson(result);
                System.out.println(weatherList);;

                if(weatherList != null && !weatherList.isEmpty()) {
                    // 날씨 데이터가 있으면 리스트 초기화
                    return new ResponseEntity<>(weatherList, HttpStatus.OK);
                } else {
                    System.out.println("No data to save. list is empty");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(http != null) {
                http.disconnect();
            }
        }

        if(list == null || list.isEmpty()) {
            Map<String, String> map = new HashMap<>();
            map.put("info", "저장된 데이터가 존재하지 않습니다.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            System.out.println("client list size : " + list.size());
            System.out.println(list.get(0));
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    // InputStream을 문자열로 변환하는 메서드
    public String readString(InputStream stream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String input = null;
        StringBuilder result = new StringBuilder();
        while((input = br.readLine()) != null) {
            result.append(input).append("\n\r");
        }
        br.close();
        
        return result.toString();
    }
}
