package amadda_back.amadda_back.View.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
@DynamicUpdate
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId; // 자동 증가 ID 필드

    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    @Column(name = "user_email", unique = true, length = 50)
    private String userEmail;

    @Column(name = "user_nickname", nullable = false, length = 50)
    private String userNickname;

    @Column(name = "user_phonenumber", nullable = false, length = 50)
    private String userPhoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_gender", nullable = false)
    private Gender userGender;

    @Column(name = "user_createat", nullable = false)
    private LocalDateTime userCreateAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription", nullable = false)
    private Subscription subscription = Subscription.N;

    @Column(name = "subscription_date")
    private LocalDate subscriptionDate;

    @Column(name = "user_currency_balance", nullable = false)
    private Integer userCurrencyBalance = 0;

    @Column(name = "introduce_text", columnDefinition = "TEXT")
    private String introduceText;

    @Column(name = "user_birth")
    private LocalDate birthDate;

    @Column(name = "profile_image", nullable = true)
    private String profileImage;

    public enum Gender {
        M, F
    }

    public enum Subscription {
        Y, N
    }
}
