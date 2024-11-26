package amadda_back.amadda_back.View.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import amadda_back.amadda_back.View.domain.entity.OCRResponseDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OCRService {

    private static final Logger logger = LoggerFactory.getLogger(OCRService.class);

    public boolean checkStoreInfoInOcr(MultipartFile file, String storeName, String storeAddress) throws IOException {
        // OCR 요청 후 결과 받기
        String ocrResult = sendOcrRequest(file);
        logger.info("OCR Result: {}", ocrResult);  // OCR 결과 로깅

        // OCR 결과를 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        OCRResponseDTO ocrResponse = null;
        try {
            ocrResponse = objectMapper.readValue(ocrResult, OCRResponseDTO.class);
        } catch (IOException e) {
            logger.error("OCR 결과 파싱 오류", e);
            throw new RuntimeException("OCR 결과 파싱 오류: " + e.getMessage());
        }

        logger.info("OCRResponseDTO: {}", ocrResponse);  // OCRResponseDTO 객체 로깅
        System.out.println("==================================");

        // OCR에서 추출한 텍스트를 하나의 문자열로 합침
        StringBuilder ocrText = new StringBuilder();
        for (OCRResponseDTO.OCRImage image : ocrResponse.getImages()) {
            for (OCRResponseDTO.Field field : image.getFields()) {
                // 현재 ocrText 상태와 field의 inferText를 로그로 출력
                logger.debug("Current ocrText: {}", ocrText.toString()); // ocrText의 상태 로그
                logger.debug("Appending OCR Field Text: {}", field.getInferText()); // field.getInferText() 값 로그

                ocrText.append(field.getInferText()).append(" "); // 텍스트를 공백으로 구분하여 합침

                // 합친 후의 ocrText 상태도 로그로 출력
                logger.debug("Updated ocrText: {}", ocrText.toString()); // 업데이트된 ocrText 상태
            }
        }

        // 모든 OCR 필드를 합친 후 최종 ocrText 출력
        logger.debug("Final OCR Text: {}", ocrText.toString());
        System.out.println("Final OCR Text: " + ocrText.toString()); // 콘솔에 최종 결과 출력

        // 가게 이름과 주소가 OCR 결과에 포함되어 있는지 확인
        //boolean isStoreNameFound = countOccurrences(ocrText.toString(), storeName) >= 2;  // 2회 이상 포함되어야 true
        boolean isStoreAddressFound = countOccurrences(ocrText.toString(), storeAddress);  // 2회 이상 포함되어야 true

        //logger.info("Is Store Name Found: {}", isStoreNameFound);
        logger.info("Is Store Address Found: {}", isStoreAddressFound);

        // 두 조건이 모두 만족하면 true 반환
        return isStoreAddressFound;
    }

    public String sendOcrRequest(MultipartFile file) throws IOException {
        String url = "https://raoo9hcr3v.apigw.ntruss.com/custom/v1/34349/b2f74932e73b4c9f958ff7e7d180b4e00f548f56521a7b0beb0c8402148931a3/general";

        // 파일을 Base64 인코딩
        String base64Image = Base64Utils.encodeToString(file.getBytes());
        logger.info("Base64 encoded image (first 100 chars): {}", base64Image.substring(0, 100));  // 처음 100글자만 로깅

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("X-OCR-SECRET", "UllsRHl1a05MY0tTbU9Wa0FrVGtIZHpHdUlZUEdxdVk=");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("version", "V2");
        requestBody.put("requestId", "1234");
        requestBody.put("timestamp", System.currentTimeMillis());
        requestBody.put("lang", "ko");
        requestBody.put("images", List.of(Map.of(
                "format", "jpg",
                "name", "uploaded_image",
                "data", base64Image
        )));
        requestBody.put("enableTableDetection", false);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            String responseBody = response.getBody();
            logger.debug("OCR API Response: {}", responseBody);
            System.out.println("==================================");
            return responseBody;
        } catch (Exception e) {
            logger.error("OCR 요청 중 오류 발생", e);
            throw new RuntimeException("OCR 요청 중 오류 발생: " + e.getMessage());
        }
    }

    private Boolean countOccurrences(String text, String keyword) {
        // OCR 텍스트와 키워드에서 공백 및 특수문자 제거
        String normalizedText = text.replaceAll("[^a-zA-Z가-힣0-9]", "").toLowerCase();
        String normalizedKeyword = keyword.replaceAll("[^a-zA-Z가-힣0-9]", "").toLowerCase();

        // 서울특별시를 서울로 변경 (키워드에서만)
        normalizedKeyword = normalizedKeyword.replace("서울특별시", "서울");

        logger.info("Normalized Text: {}", normalizedText);
        logger.info("Normalized Keyword: {}", normalizedKeyword);

        // 키워드 배열로 나눔
        String[] keywords = normalizedKeyword.split("(?<=\\G.{1,2})"); // 각 한글/영어 단위로 나눔
        int keywordIndex = 0;

        for (int i = 0; i < normalizedText.length(); i++) {
            if (keywordIndex == keywords.length) {
                logger.info("All keywords matched in sequence.");
                return true; // 모든 키워드 매칭 완료
            }

            if (i + keywords[keywordIndex].length() <= normalizedText.length()
                    && normalizedText.substring(i, i + keywords[keywordIndex].length())
                            .equals(keywords[keywordIndex])) {
                logger.debug("Matched keyword '{}' at index: {}", keywords[keywordIndex], i);
                i += keywords[keywordIndex].length() - 1;
                keywordIndex++;
            } else if (keywordIndex > 0) {
                logger.debug("Keyword match sequence interrupted. Resetting index.");
                keywordIndex = 0;
            }
        }

        logger.info("Keywords match sequence failed.");
        return false; // 키워드 매칭 실패
    }

}
