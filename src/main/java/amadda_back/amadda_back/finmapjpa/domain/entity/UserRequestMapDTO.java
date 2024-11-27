package amadda_back.amadda_back.finmapjpa.domain.entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Table(name = "user")
@Entity
@Data
@DynamicUpdate
public class UserRequestMapDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")  // 컬럼 명칭을 일관되게 수정
    private Integer userId;

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

    @Column(name = "user_createat")
    private LocalDateTime userCreateAt = LocalDateTime.now();

    @Column(name = "subscription_date")
    private LocalDate subscriptionDate;

    @Column(name = "user_currency_balance")
    private Integer userCurrencyBalance = 0;

    @Column(name = "profile_image")
    private String profileImage;
}