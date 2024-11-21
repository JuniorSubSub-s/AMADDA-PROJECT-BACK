package amadda_back.amadda_back.mypage.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    // 클라우드 스토리지 URL 설정 (예시: Naver Cloud Storage URL)
    private static final String CLOUD_STORAGE_URL = "https://kr.object.ncloudstorage.com/amadda.post.image/";

    // 파일을 클라우드에 업로드하는 메소드
    public String saveFile(MultipartFile file) throws IOException {
        // 파일 이름 가져오기
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename(); // UUID를 이용해 고유한 파일 이름 생성

        // 파일을 클라우드에 업로드하고, 클라우드 스토리지 URL을 생성하여 반환
        String cloudFilePath = uploadFileToCloud(file, fileName);

        // 클라우드에서의 최종 URL 반환
        return cloudFilePath;
    }

    // 파일을 클라우드에 업로드하는 메소드
    private String uploadFileToCloud(MultipartFile file, String fileName) throws IOException {
        // 예시: Naver Cloud Object Storage에 파일을 업로드하는 로직

        // 파일을 클라우드에 업로드하는 코드를 구현
        // (예: Naver Cloud SDK 또는 REST API 사용)

        // 업로드된 파일의 URL 반환 (클라우드 스토리지의 파일 URL)
        String cloudFilePath = CLOUD_STORAGE_URL + fileName;

        // 실제 클라우드에 업로드 작업을 해야 합니다.
        // 업로드가 완료된 후 클라우드 URL을 반환하도록 해야 합니다.

        return cloudFilePath;
    }
}
