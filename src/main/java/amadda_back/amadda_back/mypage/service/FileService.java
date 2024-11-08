package amadda_back.amadda_back.mypage.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileService {

    // 파일을 저장할 경로
    private static final String UPLOAD_DIR = "C:/finalproject/AMADDA-PROJECT-BACK/src/main/resources/static/img/profile";  // 업로드 디렉토리 경로 수정

    // 생성자에서 디렉토리 존재 여부 확인 후 생성
    public FileService() {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // 디렉토리 생성
        }
    }

    // 파일 저장 메소드
    public String saveFile(MultipartFile file) throws IOException {
        // 파일 이름 가져오기
        String fileName = file.getOriginalFilename();
        
        // 저장할 파일 경로 생성
        File targetFile = new File(UPLOAD_DIR + "/" + fileName);
        
        // 파일을 지정된 경로에 저장
        file.transferTo(targetFile);

        // 저장된 파일의 경로 반환
        return targetFile.getAbsolutePath();
    }
}
