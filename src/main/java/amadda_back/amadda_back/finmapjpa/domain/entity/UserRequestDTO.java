package amaddaback.amadda.finmapjpa.domain.entity;
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

@Table(name = "user")
@Entity
@Data
@DynamicUpdate
public class UserRequestDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId; // 자동 증가 ID 필드

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_nickname")
    private String userNickname;

    @Column(name = "user_phonenumber")
    private String userPhoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_gender")
    private Gender userGender;

    @Column(name = "user_createat")
    private LocalDateTime userCreateAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription")
    private Subscription subscription = Subscription.N;

    @Column(name = "subscription_date")
    private LocalDate subscriptionDate;

    @Column(name = "user_currency_balance")
    private Integer userCurrencyBalance = 0;

    public enum Gender {
        M, F
    }

    public enum Subscription {
        Y, N
    }
}