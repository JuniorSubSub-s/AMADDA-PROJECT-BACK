package amadda_back.amadda_back.mypage.domain.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import amadda_back.amadda_back.View.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

    private Integer userId;
    private String name;
    private String introduceText;
    private String nickname;
    private String phoneNumber;
    private String email;
    private String profileImage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private UserEntity.Gender gender;
    private Integer currencyBalance;
    private UserEntity.Subscription subscription;
    private Integer followingCount;
    private Integer followerCount;
    private Integer badgeCount; // 유저가 받은 뱃지 수 추가

    @JsonFormat(shape = JsonFormat.Shape.STRING) // JSON 문자열로 처리
    public enum Gender {
        M, F
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING) // JSON 문자열로 처리
    public enum Subscription {
        Y, N
    }
}
