package amadda_back.amadda_back.loginpage.ctrl.SignupContoller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import amadda_back.amadda_back.loginpage.dto.SignupDto.KakaoTokenResponseDto;
import amadda_back.amadda_back.loginpage.dto.SignupDto.KakaoUserInfoResponseDto;
import amadda_back.amadda_back.loginpage.dto.SignupDto.UsersFormDto;
import amadda_back.amadda_back.loginpage.service.SignupService.KakaoService;
import amadda_back.amadda_back.loginpage.service.SignupService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class KakaoLoginController {

    @Autowired
    private KakaoService kakaoService;
    @Autowired
    private UserService userService;
    
    @GetMapping("/callback")
    public ModelAndView callback(@RequestParam("code") String code) throws IOException {
        

        // 1. 카카오 서버로부터 액세스 토큰 받기
        KakaoTokenResponseDto tokens = kakaoService.getTokens(code);
        log.info("Token Response: {}", tokens);
        System.out.println("Access Token: " + tokens.getAccessToken());
        System.out.println("Refresh Token: " + tokens.getRefreshToken());

        // 2. 액세스 토큰으로 사용자 정보 가져오기
        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(tokens.getAccessToken());
        log.info("User Info: {}", userInfo);

        // 2.5.kakaoDto를 userForm으로  바꾸기
        UsersFormDto usersFormDto = kakaoService.mapToUsersFormDto(userInfo);
        usersFormDto.setUser_refresh_token(tokens.getRefreshToken());
        usersFormDto.setUser_access_token(tokens.getAccessToken());
        
        try {
            // 3. UsersFormDto를 DB에 저장
            userService.createUsers(usersFormDto);
        } catch (Exception e) {
            log.error("error : ", e);
            // 이메일 중복 에러로 가정하고 예외 처리
            return new ModelAndView("redirect:http://localhost:3000/amadda/signUpPage")
                            .addObject("error", "email-duplicate");
        }

        // 4. 메인페이지로 이동
        return new ModelAndView("redirect:http://localhost:3000/amadda?jwt=" + tokens.getAccessToken() +
        "&refreshToken=" + tokens.getRefreshToken() +
        "&accessToken=" + tokens.getAccessToken()); 
    }
}