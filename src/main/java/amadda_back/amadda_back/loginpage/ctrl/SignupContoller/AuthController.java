package amadda_back.amadda_back.loginpage.ctrl.SignupContoller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import amadda_back.amadda_back.loginpage.dto.SignupDto.KakaoTokenResponseDto;
import amadda_back.amadda_back.loginpage.dto.SignupDto.KakaoUserInfoResponseDto;
import amadda_back.amadda_back.loginpage.dto.SignupDto.UserLogin;
import amadda_back.amadda_back.loginpage.dto.SignupDto.UsersFormDto;
import amadda_back.amadda_back.loginpage.service.SignupService.KakaoService;
import amadda_back.amadda_back.loginpage.service.SignupService.TokensService;
import amadda_back.amadda_back.loginpage.service.SignupService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final KakaoService kakaoService;
    private final TokensService tokensService;
    private final UserService userService;

    @Value("${kakao.client_id}")
    private String clientId ;

    @PostMapping("/kakao/login")
    public ResponseEntity<?> getKakaoLoginUrl() {
        String kakaoLoginUrl = "https://kauth.kakao.com/oauth/authorize"
            + "?client_id=" + clientId
            + "&redirect_uri=" + "http://localhost:7777/auth/kakao/callback"
            + "&response_type=code";

        return ResponseEntity.ok(Map.of("kakaoLoginUrl", kakaoLoginUrl));
    }
    
    @GetMapping("/kakao/callback")
    public ResponseEntity<?> handleCallback(@RequestParam("code")  String code) {
        if (code == null || code.isEmpty()) {
            return ResponseEntity.badRequest().body("Authorization code is missing");
        }
        try {
            //1.Athorization Code로 Access Token 요청
            KakaoTokenResponseDto tokens = kakaoService.getTokensForLogin(code) ;
            log.info("Authorization code: {}", code);
            log.info("Client ID: {}", clientId);
            log.info("Redirect URI: {}", "http://localhost:7777/auth/kakao/callback");
            //2. Access Token으로 사용자 정보 요청
            KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(tokens.getAccessToken());
            String userEmail = userInfo.getKakaoAccount().getEmail() ;

            //3. 사용자 존재 여부 확인
            boolean userExists = userService.checkDuplicate(userEmail) ;

            if ( !userExists ) {
                //3.1 회원가입 처리
                UsersFormDto usersFormDto = kakaoService.mapToUsersFormDto(userInfo) ;
                usersFormDto.setUser_refresh_token(tokens.getRefreshToken());
                usersFormDto.setUser_access_token(tokens.getAccessToken());
                userService.createUsers(usersFormDto);
                log.info("[SignUp Wanryo] for email: {}", userEmail);
            } else {
                //3.2 로그인 처리(refresh Token 갱신)
                tokensService.updateRefreshToken(userEmail, tokens.getRefreshToken(), tokens.refreshTokenExpiresIn) ;
                log.info("[SignIn Wanryo] {}", userEmail);
            }

            //4. JWT 생성 및 반환
            String jwt = tokensService.createJwt(userEmail) ;

            //5. 프론트엔드로 리다이렉트
            String redirectUrl = "http://localhost:3000/auth/kakao/callback";
            redirectUrl += "?jwt=" + jwt;
            redirectUrl += "&accessToken=" + tokens.getAccessToken();
            redirectUrl += "&refreshToken=" + tokens.getRefreshToken();
            return ResponseEntity.status(HttpStatus.FOUND) // HTTP 302 상태 코드
                             .header(HttpHeaders.LOCATION, redirectUrl)
                             .build();
        } catch (Exception e) {
            log.error("카카오가 callback 동안 {}", e.getMessage());
            return ResponseEntity.status(500).body("카카오 로그인 처리 실패(500)");
        }
    }

    

    //액세스 토큰 재생성
    @PostMapping("/kakao/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestParam String refreshToken) {
        try {
            String newAccessToken = tokensService.createNewAccessToken(refreshToken);
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("토큰 갱신 실패: " + e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin userLogin) {
        try {
            // 1. 사용자 인증
            String email = userLogin.getUserEmail(); //프론트에서 받아온 userLoginDto에서 get해옴
            String password = userLogin.getUserPwd();

            if (!userService.authenticate(email, password)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 또는 비밀번호가 잘못되었습니다.");
            }

            // 2. JWT 생성
            String jwt = tokensService.createJwt(email);

            log.info("로그인 요청: email={}, password={}", userLogin.getUserEmail(), userLogin.getUserPwd());
            
            return ResponseEntity.ok(Map.of(
                "message", "로그인 성공",
                "jwt", jwt
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 처리 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> backendLogout(@RequestHeader("Authorization") String refreshToken) { //헤더로 받기
        log.info("logout받았음");
        try{
            //앞에 bearer 지우기
            if (refreshToken.startsWith("Bearer ")) {
                refreshToken = refreshToken.substring(7);
            }
            //refreshToken으로 로그아웃 
            tokensService.logout(refreshToken);
            return ResponseEntity.ok("로그아웃 성공");

        } catch (Exception e) {
            e.getStackTrace() ;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그아웃 실패: " + e.getMessage());
        }
    }
}
