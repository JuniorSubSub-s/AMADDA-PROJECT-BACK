package amadda_back.amadda_back.PaymentPage.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class IamportService {

    private final String API_KEY = "5014340734058557";
    private final String API_SECRET = "YzKqiChf7CfHtXBv1aQnBGf50AhVneecqUAXX6oPwYicbTQRJXRaxNJvjMT9ExEAqMX0lPiIHFZL9c6g";
    private final String IAMPORT_TOKEN_URL = "https://api.iamport.kr/users/getToken";
    private final String IAMPORT_PAYMENT_URL = "https://api.iamport.kr/payments/";

    private String getToken() {
        RestTemplate restTemplate = new RestTemplate();

        // 요청 본문
        Map<String, String> body = new HashMap<>();
        body.put("imp_key", API_KEY);
        body.put("imp_secret", API_SECRET);

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 엔티티 생성
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        try {
            // POST 요청 실행
            ResponseEntity<Map> response = restTemplate.postForEntity(IAMPORT_TOKEN_URL, entity, Map.class);
            if (response.getStatusCodeValue() == 200) {
                Map<String, Object> responseData = (Map<String, Object>) response.getBody().get("response");
                return (String) responseData.get("access_token"); // 토큰 반환
            } else {
                System.err.println("Token request failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Token request error: " + e.getMessage());
        }
        throw new RuntimeException("Failed to get Iamport API token");
    }

    // 결제 검증 메서드
    public boolean verifyPayment(String impUid, int amount) {
        String token = getToken(); // 토큰 생성
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        String url = IAMPORT_PAYMENT_URL + impUid;

        try {
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

            if (response.getStatusCodeValue() == 200) {
                Map<String, Object> responseData = (Map<String, Object>) response.getBody().get("response");
                int responseAmount = (int) responseData.get("amount");

                // 결제 금액 검증
                return responseAmount == amount;
            } else {
                System.err.println("Payment verification failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Payment verification error: " + e.getMessage());
        }
        return false;
    }
}
