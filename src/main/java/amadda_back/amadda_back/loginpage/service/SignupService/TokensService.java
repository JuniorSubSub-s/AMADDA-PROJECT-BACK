package amadda_back.amadda_back.loginpage.service.SignupService;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import amadda_back.amadda_back.loginpage.dto.SignupDto.KakaoTokenResponseDto;
import amadda_back.amadda_back.loginpage.entity.Users;
import amadda_back.amadda_back.loginpage.repository.UsersRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokensService {
    
    @Value("${jwt.secret}")
    private String secretKey ;

    @Value("${kakao.client_id}")
    private String clientId ;

    WebClient webClient = WebClient.builder()
    .baseUrl("https://kauth.kakao.com") //기본 도메인 추가
    .build();

    

    private final UsersRepository usersRepository ;

    public TokensService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository ;
    }

    //1. Refresh Token 저장 및 갱신
    public void updateRefreshToken(String userEmail, String refreshToken, Integer refreshTokenExpiresIn) {
        Users user = usersRepository.findByUserEmail(userEmail)
            .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        // 현재 시간에 카카오가 제공한 만료 기간(초)을 더해 만료 시간 계산(1000(LONG)는 밀리초로 변환하기 위한 상수)
        Date refreshTokenExpireDate = new Date(System.currentTimeMillis() + (refreshTokenExpiresIn * 1000L));
        
        user.setUserRefreshToken(refreshToken);
        user.setUserExpiresIn(refreshTokenExpireDate); 
        usersRepository.save(user) ;
    }

    //JWT 생성
    public String createJwt(String userEmail) {
        Users user = usersRepository.findByUserEmail(userEmail)
        .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        Integer userId = user.getUserId();
        long now = System.currentTimeMillis() ;
        Date expiryDate = new Date(now + (60 * 60 * 1000)) ; //1시간

        return Jwts.builder()
            .setSubject(userEmail)
            .claim("userId", userId)
            .setIssuedAt(new Date())
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact() ;
    }

    //oauth/token 에서 refresh토큰으로 accessToken 받기
    public String refreshKakaoAccessToken(String refreshToken) {
    
        KakaoTokenResponseDto response = webClient.post()
            .uri(uriBuilder -> uriBuilder
                .path("/oauth/token")
                .queryParam("grant_type", "refresh_token")
                .queryParam("client_id", clientId)
                .queryParam("refresh_token", refreshToken)
                .build()) //URI 빌드 함수
            .retrieve() //받은 응답 디코딩(body를 받아 디코딩하는 메서드)
            .bodyToMono(KakaoTokenResponseDto.class) //DTO로 매핑해서 받고
            .block(); //동기형식으로 받기

        // 새 Access Token 반환
        return response.getAccessToken();
    }
    
    //4. Refresh 로 Access 갱신
    public String createNewAccessToken(String refreshToken) {
        Users user = usersRepository.findByUserRefreshToken(refreshToken)
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 리프레쉬 토큰"));

        if (user.getUserExpiresIn().before(new Date())) {
            throw new IllegalArgumentException("리프레쉬 토큰 만료");
        }

        // 새 카카오 Access Token 요청
        String newAccessToken = refreshKakaoAccessToken(refreshToken);

        // 사용자 엔티티 업데이트
        user.setUserAccessToken(newAccessToken);
        usersRepository.save(user);
        log.info("[newAccessToken] {}", newAccessToken);
        return newAccessToken;
    }




}
