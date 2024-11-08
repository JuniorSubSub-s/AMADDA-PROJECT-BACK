package amadda_back.amadda_back.mypage.domain.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    @Column(name = "user_email", unique = true, length = 50)
    private String userEmail;

    @Column(name = "user_nickname", nullable = false, length = 50)
    private String userNickname;

    @Column(name = "user_phonenumber", nullable = false, length = 50)
    private String userPhoneNumber;

    @Column(name = "user_gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender userGender;

    @Column(name = "user_createat", nullable = false)
    private LocalDateTime userCreateAt;

    @Column(name = "subscription", nullable = false)
    @Enumerated(EnumType.STRING)
    private Subscription subscription;

    @Column(name = "user_currency_balance", nullable = false)
    private int userCurrencyBalance;

    @Column(name = "introduce_text", columnDefinition = "TEXT")
    private String introduceText;

    @Column(name = "user_birth")  // 추가된 생년월일 필드
    private LocalDateTime birthDate;

    @Column(name ="profile_image", nullable = true) // 프로필 이미지 필드 추가
    private String profileImage;

    public enum Gender {
        M, F
    }

    public enum Subscription {
        Y, N
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public Gender getUserGender() {
        return userGender;
    }

    public void setUserGender(Gender userGender) {
        this.userGender = userGender;
    }

    public LocalDateTime getUserCreateAt() {
        return userCreateAt;
    }

    public void setUserCreateAt(LocalDateTime userCreateAt) {
        this.userCreateAt = userCreateAt;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public int getUserCurrencyBalance() {
        return userCurrencyBalance;
    }

    public void setUserCurrencyBalance(int userCurrencyBalance) {
        this.userCurrencyBalance = userCurrencyBalance;
    }

    public String getIntroduceText() {
        return introduceText;
    }

    public void setIntroduceText(String introduceText) {
        this.introduceText = introduceText;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
