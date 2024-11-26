package amadda_back.amadda_back.loginpage.service.SignupService;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import amadda_back.amadda_back.loginpage.dto.SignupDto.UsersFormDto;
import amadda_back.amadda_back.loginpage.entity.Users;
import amadda_back.amadda_back.loginpage.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@Service("signupUserService")
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public Users createUsers(UsersFormDto usersFormDto) { // userForkDto 형식으로 받아서
        if (usersFormDto.getUser_email() == null || usersFormDto.getUser_email().isEmpty()) {
            throw new IllegalArgumentException("User email cannot be null or empty");
        }
        Users users = null;
        if (usersFormDto.getUser_refresh_token() == null) { // refreshToken이 없다면(일반 로그인이라면)
            users = Users.builder()
                    .userEmail(usersFormDto.getUser_email())
                    .userNickname(usersFormDto.getUser_nickname())
                    .userPwd(passwordEncoder.encode(usersFormDto.getUser_pwd()))
                    .userPhonenumber(usersFormDto.getUser_phonenumber())
                    .userGender(usersFormDto.getUser_gender())
                    .userName(usersFormDto.getUser_name())
                    .userNation(usersFormDto.getUser_nation())
                    .userBirth(usersFormDto.getUser_birth())

                    .build();
        } else {
            users = Users.builder() // 카카오 로그인이라면
                    .userEmail(usersFormDto.getUser_email())
                    .userNickname(usersFormDto.getUser_nickname())
                    .userPwd(passwordEncoder.encode(usersFormDto.getUser_pwd()))
                    .userPhonenumber(usersFormDto.getUser_phonenumber())
                    .userGender(usersFormDto.getUser_gender())
                    .userName(usersFormDto.getUser_name())
                    .userNation(usersFormDto.getUser_nation())
                    .userBirth(usersFormDto.getUser_birth())
                    .userRefreshToken(usersFormDto.getUser_refresh_token()) // 리프레쉬
                    .userAccessToken(usersFormDto.getUser_access_token()) // 엑세스
                    .userExpiresIn(usersFormDto.getUser_expires_in()) // 리프레쉬 만료일까지 받음
                    .build();
        }

        usersRepository.save(users);

        return users; // Users Entity 형식으로 반환
    }

    public boolean checkDuplicate(String email) {
        return usersRepository.findByUserEmail(email).isPresent();
    }

    // 이메일 비밀번호 맞는지 검증
    public boolean authenticate(String email, String password) {
        Optional<Users> optionalUser = usersRepository.findByUserEmail(email);
        Users user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new IllegalArgumentException("이메일 없음");
        }

        // 암호화 된 비밀번호 맞는지 검증
        return passwordEncoder.matches(password, user.getUserPwd());
    }
}
