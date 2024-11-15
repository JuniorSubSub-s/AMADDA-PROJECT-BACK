package amadda_back.amadda_back.PaymentPage.domain.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor // Hibernate에서 사용
@AllArgsConstructor
@Builder
@Entity
@Table(name="user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "user_id", unique = true)
    private Integer userId;

    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail ;

    @Column(name = "user_nickname",nullable = false, unique = true)
    private String userNickname ;

    @Column(name = "user_password",nullable = false)
    private String userPwd ;

    @Column(name = "user_phonenumber",nullable = false)
    private String userPhonenumber ;

    @Column(name = "user_name",nullable = false)
    private String userName ;

    @Column (name = "user_nation", nullable = false)
    private Integer userNation ;

    @Temporal(TemporalType.DATE)
    @Column (name = "user_birth", nullable = false)
    private Date userBirth ;

    @Column (name = "user_refresh_token", nullable = true)
    private String userRefreshToken ;

    @Column (name = "user_access_token", nullable = true)
    private String userAccessToken;

    @Column (name = "user_expires_in", nullable = true)
    private Date userExpiresIn;
}


