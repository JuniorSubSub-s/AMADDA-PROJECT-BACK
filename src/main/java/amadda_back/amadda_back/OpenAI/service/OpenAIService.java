package amadda_back.amadda_back.OpenAI.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import amadda_back.amadda_back.OpenAI.dto.OpenAIRequest;
import amadda_back.amadda_back.OpenAI.dto.OpenAIResponse;
import jakarta.annotation.PostConstruct;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        // apiKey가 제대로 로드되었는지 확인
        if (apiKey != null && !apiKey.isEmpty()) {
            // WebClient 초기화: apiKey가 올바르게 로드된 후에 WebClient 생성
            this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + apiKey)  // API 키 적용
                .build();
        } else {
            System.out.println("API Key is not loaded correctly.");
        }
    }

    // Jackson ObjectMapper 객체 생성
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateContent(OpenAIRequest request) {
        try {

            // 요청 객체를 JSON 형식으로 변환하여 로그에 출력
            String requestJson = objectMapper.writeValueAsString(request);
            System.out.println("Request Body: " + requestJson);

            // OpenAI API 호출
            OpenAIResponse response = webClient.post()
                    .header("Content-Type", "application/json")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(OpenAIResponse.class)
                    .block();

            // 응답이 유효한지 확인
            if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
                return response.getChoices().get(0).getMessage().getContent();
            } else {
                throw new RuntimeException("Invalid response from OpenAI API");
            }
        } catch (Exception e) {
            // 예외 발생 시 오류 메시지 출력
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException("Error calling OpenAI API: " + e.getMessage(), e);
        }
    }
}
