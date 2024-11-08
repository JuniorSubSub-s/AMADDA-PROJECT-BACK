package amadda_back.amadda_back.mypage.service;

import org.springframework.stereotype.Service;

import amadda_back.amadda_back.mypage.dao.UserRepository;
import amadda_back.amadda_back.mypage.domain.entity.User;
import amadda_back.amadda_back.mypage.domain.entity.UserInfoDTO;
import amadda_back.amadda_back.mypage.exception.ResourceNotFoundException;

import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final FileService fileService; // 파일 서비스 추가

    // 생성자에서 FileService 주입
    public UserService(UserRepository userRepository, FileService fileService) {
        this.userRepository = userRepository;
        this.fileService = fileService;
    }

    public UserInfoDTO getUserInfo(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다. ID: " + userId));
        
        int followingCount = userRepository.countFollowingByUserId(userId);
        int followerCount = userRepository.countFollowerByUserId(userId);

        return new UserInfoDTO(
            user.getUserId(),                
            user.getUserName(), 
            user.getIntroduceText(),
            user.getUserNickname(),
            user.getUserPhoneNumber(),
            user.getUserEmail(),
            user.getProfileImage(),
            user.getBirthDate(),
            user.getUserGender(),
            user.getUserCurrencyBalance(),
            user.getSubscription(),
            followingCount,
            followerCount
        );
    }

    public int getUserCurrencyBalance(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다. ID: " + userId));
        return user.getUserCurrencyBalance();
    }

    public void setUserCurrencyBalance(int userId, int userCurrencyBalance) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다. ID: " + userId));
        user.setUserCurrencyBalance(userCurrencyBalance);
        userRepository.save(user);
    }

    // 사용자 정보를 업데이트하는 메서드 추가
    public UserInfoDTO updateUserInfo(int userId, UserInfoDTO userInfoDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다. ID: " + userId));
        
        user.setUserNickname(userInfoDTO.getNickname());
        user.setIntroduceText(userInfoDTO.getIntroduceText());
        
        // 프로필 이미지가 null이 아닐 경우에만 업데이트
        if (userInfoDTO.getProfileImage() != null) {
            user.setProfileImage(userInfoDTO.getProfileImage());
        }

        userRepository.save(user);

        return new UserInfoDTO(
            user.getUserId(),
            user.getUserName(),
            user.getIntroduceText(),
            user.getUserNickname(),
            user.getUserPhoneNumber(),
            user.getUserEmail(),
            user.getProfileImage(),
            user.getBirthDate(),
            user.getUserGender(),
            user.getUserCurrencyBalance(),
            user.getSubscription(),
            0, // followingCount
            0  // followerCount
        );
    }

    // 프로필 이미지를 업데이트하는 메서드 추가
    public void updateProfileImage(int userId, String profileImage) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다. ID: " + userId));
        
        user.setProfileImage(profileImage); // 프로필 이미지 업데이트
        userRepository.save(user); // 변경 사항 저장
    }

    // 파일 업로드 처리 메서드 추가
    public void uploadProfileImage(int userId, MultipartFile file) throws Exception {
        // 파일을 저장하고 경로를 얻기
        String filePath = fileService.saveFile(file);

        // 파일 경로를 사용자 프로필 이미지에 저장
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다. ID: " + userId));
        
        user.setProfileImage(filePath); // 저장된 파일 경로를 프로필 이미지로 설정
        userRepository.save(user); // 변경 사항 저장
    }
}
