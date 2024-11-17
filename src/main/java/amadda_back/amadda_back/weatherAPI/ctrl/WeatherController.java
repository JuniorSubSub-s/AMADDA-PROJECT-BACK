package amadda_back.amadda_back.weatherAPI.ctrl;

import jxl.Sheet;
import jxl.Workbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import amadda_back.amadda_back.weatherAPI.domain.entity.WeatherDTO;
import amadda_back.amadda_back.weatherAPI.service.GetWeatherService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
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

    // 날씨 데이터를 지역 이름 기반으로 조회
    @GetMapping("/weatherImageChange")
    public Object getWeatherByLocation(
            @RequestParam("nx") String nx,
            @RequestParam("ny") String ny,
            @RequestParam("base_date") String baseDate,
            @RequestParam("base_time") String baseTime) {

        return getWeatherData(baseDate, baseTime, nx, ny);
    }

    // API 호출 및 데이터 반환
    private Object getWeatherData(String baseDate, String baseTime, String nx, String ny) {
        try {
            StringBuilder urlBuilder = new StringBuilder(weatherApiUrl);
            urlBuilder.append("?").append(URLEncoder.encode("ServiceKey", "UTF-8")).append("=").append(serviceKey);
            urlBuilder.append("&").append(URLEncoder.encode("nx", "UTF-8")).append("=")
                    .append(URLEncoder.encode(nx, "UTF-8"));
            urlBuilder.append("&").append(URLEncoder.encode("ny", "UTF-8")).append("=")
                    .append(URLEncoder.encode(ny, "UTF-8"));
            urlBuilder.append("&").append(URLEncoder.encode("base_date", "UTF-8")).append("=")
                    .append(URLEncoder.encode(baseDate, "UTF-8"));
            urlBuilder.append("&").append(URLEncoder.encode("base_time", "UTF-8")).append("=")
                    .append(URLEncoder.encode(baseTime, "UTF-8"));
            urlBuilder.append("&").append(URLEncoder.encode("dataType", "UTF-8")).append("=").append("JSON");
    
            String apiUrl = urlBuilder.toString();
            System.out.println("API URL: " + apiUrl);
    
            RestTemplate restTemplate = new RestTemplate();
            String jsonResponse = restTemplate.getForObject(new URI(apiUrl), String.class);
    
            // 응답이 올바른 JSON인지 확인
            if (jsonResponse == null || !jsonResponse.trim().startsWith("{")) {
                System.err.println("Invalid JSON response: " + jsonResponse);
                return "Error: Invalid response from weather API.";
            }
    
            System.out.println("API Response: " + jsonResponse);
    
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(jsonResponse); // JSON 데이터로 변환하여 반환
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching weather data: " + e.getMessage();
        }
    }

    // 엑셀 파일에서 지역 이름에 해당하는 격자값 조회
    public Map<String, String> readExcel(String localName) {
        Map<String, String> result = new HashMap<>();

        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("local_name.xls");
            if (is == null) {
                throw new IllegalArgumentException("엑셀 파일을 찾을 수 없습니다.");
            }

            Workbook wb = Workbook.getWorkbook(is);
            if (wb != null) {
                Sheet sheet = wb.getSheet(0);
                if (sheet != null) {
                    int rowTotal = sheet.getRows();

                    for (int row = 1; row < rowTotal; row++) {
                        String contents = sheet.getCell(0, row).getContents();
                        if (contents.contains(localName)) {
                            String x = sheet.getCell(1, row).getContents();
                            String y = sheet.getCell(2, row).getContents();
                            result.put("x", x);
                            result.put("y", y);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("엑셀 파일 읽기 중 오류 발생: " + e.getMessage());
        }

        return result;
    }
}
