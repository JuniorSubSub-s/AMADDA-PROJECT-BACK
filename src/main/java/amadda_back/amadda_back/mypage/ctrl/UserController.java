package amadda_back.amadda_back.mypage.ctrl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
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

import amadda_back.amadda_back.View.service.ImageService;
import amadda_back.amadda_back.mypage.domain.entity.Badge;
import amadda_back.amadda_back.mypage.domain.entity.UserInfoDTO;
import amadda_back.amadda_back.mypage.exception.ResourceNotFoundException;
import amadda_back.amadda_back.mypage.service.UserService;

@RestController
@RequestMapping("/api/amadda/user")
public class UserController {

    private final UserService userService;
    private final ImageService imageService;

    public UserController(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
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
    public ResponseEntity<UserInfoDTO> updateUserInfo(@PathVariable("userId") int userId,
            @RequestBody UserInfoDTO userInfoDTO) {
        try {
            UserInfoDTO updatedUserInfo = userService.updateUserInfo(userId, userInfoDTO);
            return ResponseEntity.ok(updatedUserInfo);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/upload-profile-image/{userId}")
    public ResponseEntity<?> uploadProfileImage(@RequestParam("file") List<MultipartFile> files, // 다중 파일 업로드 지원
            @RequestParam("userId") Integer userId) {

        if (files.isEmpty() || files.stream().anyMatch(MultipartFile::isEmpty)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("파일이 비어 있습니다.");
        }

        try {
            // 이미지 파일 업로드
            List<String> imageUrls = imageService.uploadFile(files);

            // 첫 번째 이미지를 사용자 프로필 이미지로 저장 (필요 시 변경 가능)
            if (!imageUrls.isEmpty()) {
                userService.updateProfileImage(userId, imageUrls.get(0));
            }

            return ResponseEntity.ok(imageUrls); // 업로드된 이미지 URLs 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("파일 업로드 실패: " + e.getMessage());
        }
    }

    @GetMapping("/badge/{userId}")
    public ResponseEntity<List<Badge>> getBadgesByUserId(@PathVariable(name = "userId") Integer userId) {
        List<Badge> badges = userService.getBadgesByUserId(userId);
        return ResponseEntity.ok(badges);
    }

}