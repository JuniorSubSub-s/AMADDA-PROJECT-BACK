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
        boolean isStoreNameFound = countOccurrences(ocrText.toString(), storeName) >= 2;  // 2회 이상 포함되어야 true
        boolean isStoreAddressFound = countOccurrences(ocrText.toString(), storeAddress) >= 2;  // 2회 이상 포함되어야 true

        logger.info("Is Store Name Found: {}", isStoreNameFound);
        logger.info("Is Store Address Found: {}", isStoreAddressFound);

        // 두 조건이 모두 만족하면 true 반환
        return isStoreNameFound || isStoreAddressFound;
    }

    public String sendOcrRequest(MultipartFile file) throws IOException {
        String url = "https://raoo9hcr3v.apigw.ntruss.com/custom/v1/34349/b2f74932e73b4c9f958ff7e7d180b4e00f548f56521a7b0beb0c8402148931a3/general";

        // 파일을 Base64 인코딩
        String base64Image = Base64Utils.encodeToString(file.getBytes());
        logger.info("Base64 encoded image (first 100 chars): {}", base64Image.substring(0, 100));  // 처음 100글자만 로깅

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("X-OCR-SECRET", "c3RlZG1lZHdoUFl2cXZSaVN6ZXBmbVJlUmhGU0pSSmY=");

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
            logger.info("OCR API Response: {}", responseBody);
            System.out.println("==================================");
            return responseBody;
        } catch (Exception e) {
            logger.error("OCR 요청 중 오류 발생", e);
            throw new RuntimeException("OCR 요청 중 오류 발생: " + e.getMessage());
        }
    }

    private int countOccurrences(String text, String keyword) {
        int count = 0;
        String[] keywords = keyword.split(" "); // 공백을 기준으로 키워드를 나눔

        for (String word : keywords) {
            int index = 0;
            // 각 단어가 text에서 몇 번 등장하는지 확인
            while ((index = text.indexOf(word, index)) != -1) {
                count++;
                logger.debug("Keyword '{}' found at index: {}", word, index); // 키워드 발견 위치 로깅
                index++;
            }
        }
        return count;
    }

}
