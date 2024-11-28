package amadda_back.amadda_back.loginpage.dto.SignupDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KakaoTokenResponseDto {
    //토큰타입
    @JsonProperty("token_type")
    public String tokenType;

    //사용자 엑세스 토큰 값
    @JsonProperty("access_token")
    public String accessToken;

    //ID 토큰 값(추가항복동의받기 요청을 거친 경우)
    @JsonProperty("id_token")
    public String idToken;

    //액세스 만료되는 시간(초)
    @JsonProperty("expires_in")
    public Integer expiresIn;

    //사용자 리프레시 토큰 값 유효기간이 1개월 미만 일 경후 갱신
    @JsonProperty("refresh_token")
    public String refreshToken;
    
    //리프레시 만료 시간(초)
    @JsonProperty("refresh_token_expires_in")
    public Integer refreshTokenExpiresIn;
    
    //정보 조회 권한 범위
    @JsonProperty("scope")
    public String scope;
}
