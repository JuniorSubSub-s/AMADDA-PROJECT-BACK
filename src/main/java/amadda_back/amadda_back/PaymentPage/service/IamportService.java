package amadda_back.amadda_back.PaymentPage.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Map;

@Service
public class IamportService {

    private static final String API_URL = "https://api.iamport.kr";
    private static final String API_KEY = "5014340734058557";
    private static final String API_SECRET = "YzKqiChf7CfHtXBv1aQnBGf50AhVneecqUAXX6oPwYicbTQRJXRaxNJvjMT9ExEAqMX0lPiIHFZL9c6g";

    public String getAccessToken() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = Map.of(
            "imp_key", API_KEY,
            "imp_secret", API_SECRET
        );

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
            API_URL + "/users/getToken", entity, Map.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("토큰 발급 실패");
        }

        Map<String, Object> responseBody = response.getBody();
        return (String) ((Map<String, Object>) responseBody.get("response")).get("access_token");
    }

    public Map<String, Object> getPaymentData(String impUid) throws Exception {
        String accessToken = getAccessToken();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(
            API_URL + "/payments/" + impUid, HttpMethod.GET, entity, Map.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new Exception("결제 정보 조회 실패");
        }

        return (Map<String, Object>) response.getBody().get("response");
    }
}
