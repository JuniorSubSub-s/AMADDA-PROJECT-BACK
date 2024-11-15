package amadda_back.amadda_back.PaymentPage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;

@Service
public class IamportService {
    @Value("${iamport.api_key}")
    private String apiKey;

    @Value("${iamport.api_secret}")
    private String apiSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    // 아임포트 서버에서 액세스 토큰 가져오기
    public String getIamportAccessToken() {
        String url = "https://api.iamport.kr/users/getToken";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 바디에 API 키와 시크릿 키를 JSON 형식으로 포함
        Map<String, String> body = Map.of("imp_key", apiKey, "imp_secret", apiSecret);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        // 아임포트 API에 POST 요청
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
        Map<String, Object> responseBody = response.getBody();

        // 액세스 토큰 반환
        Map<String, Object> responseData = (Map<String, Object>) responseBody.get("response");
        return (String) responseData.get("access_token");
    }

    // 결제 검증 메서드
    public boolean verifyPayment(String impUid, int amount) {
        String accessToken = getIamportAccessToken();
        String url = "https://api.iamport.kr/payments/" + impUid;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

        Map<String, Object> responseBody = response.getBody();
        Map<String, Object> responseData = (Map<String, Object>) responseBody.get("response");

        // 결제 금액 확인
        int responseAmount = (int) responseData.get("amount");
        return responseAmount == amount;
    }
}
