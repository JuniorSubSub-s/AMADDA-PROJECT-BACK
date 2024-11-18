package amadda_back.amadda_back.loginpage.service.SignupService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import amadda_back.amadda_back.loginpage.dto.SignupDto.KakaoTokenResponseDto;
import amadda_back.amadda_back.loginpage.dto.SignupDto.KakaoUserInfoResponseDto;
import amadda_back.amadda_back.loginpage.dto.SignupDto.UsersFormDto;
import amadda_back.amadda_back.loginpage.enums.Gender;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {

    

    private String clientId ;
    private final String KAUTH_TOKEN_URL_HOST ;
    private final String KAUTH_USER_URL_HOST ;

    @Autowired
    public KakaoService(@Value("${kakao.client_id}")  String clientId) {
        this.clientId = clientId ;
        KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
        KAUTH_USER_URL_HOST = "https://kapi.kakao.com";
    }

    //코드를 이용해 토큰 생성(토큰으로 액세스 토큰, 리프레쉬 토큰 만드는데 쓰이는 메서드)
    public KakaoTokenResponseDto getTokens(String code) {
        WebClient webClient = WebClient.create(KAUTH_TOKEN_URL_HOST);
    
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("redirect_uri", "http://localhost:7777/callback")
                        .queryParam("code", code)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    log.error("4xx error occurred while fetching tokens");
                    return Mono.error(new RuntimeException("Invalid Parameter: " + clientResponse.statusCode()));
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    log.error("5xx error occurred while fetching tokens");
                    return Mono.error(new RuntimeException("Internal Server Error: " + clientResponse.statusCode()));
                })
                .bodyToMono(KakaoTokenResponseDto.class)
                .block();
    }

    //auth/kakao/callback 로그인을 이용한 토큰 생성 메서드
    public KakaoTokenResponseDto getTokensForLogin(String code) {
        WebClient webClient = WebClient.create(KAUTH_TOKEN_URL_HOST); //웹으로 API를 호출하기 위해 사용되는 Http Client 모듈 중 하나
        
        //카카오 API를 호출해 kakaoTokenResponseDto Json으로 반환된
        /*
         {
            "access_token": "new_access_token_value",
            "expires_in": 3600,
            "refresh_token": "new_refresh_token_value",
            "refresh_token_expires_in": 2592000
        }
         */
        return webClient.post() //JSON 형식으로 반환
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("redirect_uri", "http://localhost:7777/auth/kakao/callback")
                        .queryParam("code", code)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve() //받은 응답 디코딩(body를 받아 디코딩하는 메서드)
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> { //예외 상태 커스텀
                    log.error("4xx error occurred while fetching tokens");
                    return Mono.error(new RuntimeException("Invalid Parameter: " + clientResponse.statusCode()));
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> { //예외 상태 커스텀
                    log.error("5xx error occurred while fetching tokens");
                    return Mono.error(new RuntimeException("Internal Server Error: " + clientResponse.statusCode()));
                })
                .bodyToMono(KakaoTokenResponseDto.class) //body의 데이터로만 받고싶다면 사용하는 메서드
                //.toEntity status, headers, body포함하는 ResponseEntity 타입으로 받을 수 있음
                .block(); //동기 코드처럼 작동하도록 바꿈(코드가 간단해짐, 예외처리하기 더 간단함)
    }

    //엑세스 토큰으로 user정보 가져오는 함수
    public KakaoUserInfoResponseDto getUserInfo(String accessToken) {

        KakaoUserInfoResponseDto userInfo = WebClient.create(KAUTH_USER_URL_HOST)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/v2/user/me") //이 엔드포인트로 토큰을 통해 사용자 정보를 조회
                        .build(true))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // access token 인가
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                //TODO : Custom Exception
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoUserInfoResponseDto.class)
                .block();

        log.info("[ Kakao Service ] Auth ID ---> {} ", userInfo.getId());
        log.info("[ Kakao Service ] NickName ---> {} ", userInfo.getKakaoAccount().getProfile().getNickName());
        log.info("[ Kakao Service ] ProfileImageUrl ---> {} ", userInfo.getKakaoAccount().getProfile().getProfileImageUrl());
        //로그로 출력
        return userInfo; //사용자정보 객체 반환
    }

    //카카오에서 유저dto로
   public UsersFormDto mapToUsersFormDto(KakaoUserInfoResponseDto userInfo) {
        return UsersFormDto     .builder()
                                .user_email(userInfo.getKakaoAccount().getEmail())
                                .user_nickname(userInfo.getKakaoAccount().getProfile().getNickName())
                                .user_phonenumber(normalPhoneNum(userInfo.getKakaoAccount().getPhoneNumber())) //82+ 010으로 정상화
                                .user_gender(enumGender(userInfo.getKakaoAccount().getGender())) //enum값으로 바꾸기
                                .user_name(userInfo.getKakaoAccount().getName())
                                .user_nation(1) // 카카오톡 쓰면 내국인이겠지
                                .user_birth(sumBirthDate(userInfo.getKakaoAccount().getBirthYear(), userInfo.getKakaoAccount().getBirthDay()))
                                .user_pwd(generateRandomCode()) //일단은 더미데이터로(카카오톡 쓰면 비번이 필요가 없음)
                                .build();
    }

    // 성별을 Gender Enum으로 변환
    private Gender enumGender(String gender) {
        if ("male".equalsIgnoreCase(gender)) {
            return Gender.M;
        } else if ("female".equalsIgnoreCase(gender)) {
            return Gender.F;
        }
        return null; // 성별이 없거나 알 수 없는 경우 null
    }

    // 생년월일 변환 (YYYY + MMDD 조합)
    private Date sumBirthDate(String birthYear, String birthDay) {
        if (birthYear == null || birthDay == null) {
            return null;
        }
        try {
            String fullBirthDate = birthYear + birthDay;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            return sdf.parse(fullBirthDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    //폰넘버 정상화
    private String normalPhoneNum(String phoneNumber) {
            return phoneNumber.replace("+82 10-", "010-");
    }

    //비밀번호 랜덤생성
    private String generateRandomCode() {
        StringBuilder randomCode = new StringBuilder();
        Random random = new Random();
    
        for (int i = 0; i < 20; i++) {
            int digit = random.nextInt(10); // 0부터 9까지의 랜덤 숫자
            randomCode.append(digit);
        }
    
        return randomCode.toString();
    }

    
}