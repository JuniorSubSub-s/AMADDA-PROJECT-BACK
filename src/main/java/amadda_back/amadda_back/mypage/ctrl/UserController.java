package amadda_back.amadda_back.mypage.ctrl;

import java.io.File;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import amadda_back.amadda_back.mypage.domain.entity.UserInfoDTO;
import amadda_back.amadda_back.mypage.exception.ResourceNotFoundException;
import amadda_back.amadda_back.mypage.service.UserService;
@RestController
@RequestMapping("/api/amadda/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable("userId") int userId) {
        try {
            UserInfoDTO userInfo = userService.getUserInfo(userId);
            return ResponseEntity.ok(userInfo);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserInfoDTO> updateUserInfo(@PathVariable("userId") int userId, @RequestBody UserInfoDTO userInfoDTO) {
        try {
            UserInfoDTO updatedUserInfo = userService.updateUserInfo(userId, userInfoDTO);
            return ResponseEntity.ok(updatedUserInfo);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{userId}/upload-image")
    public ResponseEntity<String> uploadProfileImage(@PathVariable("userId") int userId, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일이 비어 있습니다.");
        }

        try {
            String imageUrl = saveFile(file);
            userService.updateProfileImage(userId, imageUrl);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 실패: " + e.getMessage());
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        // 업로드 디렉토리 경로를 "C:/uploads/assets"로 변경
        String uploadDir = "C:/finalproject/AMADDA-PROJECT-BACK/src/main/resources/static/img/profile";
        File destinationDir = new File(uploadDir);
        if (!destinationDir.exists()) {
            destinationDir.mkdirs(); 
        }
    
        // 파일 이름 중복 방지 처리
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new IOException("파일 이름을 가져올 수 없습니다.");
        }
    
        String filePath = uploadDir + "/" + fileName;
        File destinationFile = new File(filePath);
        
        int count = 1;
        while (destinationFile.exists()) {
            String newFileName = fileName.substring(0, fileName.lastIndexOf('.')) + "_" + count++ + fileName.substring(fileName.lastIndexOf('.'));
            destinationFile = new File(uploadDir, newFileName);
        }
    
        file.transferTo(destinationFile);
    
        // 반환할 URL 형식으로 수정
        return "/assets/" + destinationFile.getName();  // 반환 경로를 "/assets/"로 수정
    }
}
