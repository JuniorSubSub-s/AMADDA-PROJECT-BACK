package amadda_back.amadda_back.mypage.domain.entity;

import java.time.LocalDate;

import amadda_back.amadda_back.View.domain.entity.UserEntity;

public class UserInfoDTO {

    private Integer userId;
    private String name;
    private String introduceText;
    private String nickname;
    private String phoneNumber;
    private String email;
    private String profileImage;
    private LocalDate birthDate;
    private UserEntity.Gender gender;
    private Integer currencyBalance;
    private UserEntity.Subscription subscription;
    private Integer followingCount;
    private Integer followerCount;

    // 생성자
    public UserInfoDTO(Integer userId, String name, String introduceText, String nickname, String phoneNumber,
            String email, String profileImage, LocalDate birthDate, UserEntity.Gender gender,
            Integer currencyBalance, UserEntity.Subscription subscription, Integer followingCount, Integer followerCount) {
        this.userId = userId;
        this.name = name;
        this.introduceText = introduceText;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.profileImage = profileImage;
        this.birthDate = birthDate;
        this.gender = gender;
        this.currencyBalance = currencyBalance;
        this.subscription = subscription;
        this.followingCount = followingCount;
        this.followerCount = followerCount;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getIntroduceText() {
        return introduceText;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate; // LocalDateTime 반환
    }

    public UserEntity.Gender getGender() {
        return gender; // UserEntity.Gender 반환
    }

    public int getCurrencyBalance() {
        return currencyBalance;
    }

    public UserEntity.Subscription getSubscription() {
        return subscription; // UserEntity.Subscription 반환
    }

    public String getProfileImage() {
        return profileImage;
    }

    public Integer getFollowingCount() {
        return followingCount;
    }

    public Integer getFollowerCount() {
        return followerCount;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntroduceText(String introduceText) {
        this.introduceText = introduceText;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(UserEntity.Gender gender) {
        this.gender = gender; // UserEntity.Gender 설정
    }

    public void setCurrencyBalance(int currencyBalance) {
        this.currencyBalance = currencyBalance;
    }

    public void setSubscription(UserEntity.Subscription subscription) {
        this.subscription = subscription; // UserEntity.Subscription 설정
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setFollowingCount(Integer followingCount) {
        this.followingCount = followingCount;
    }

    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }
    
    // 내부 클래스 Gender 및 Subscription 정의
    public enum Gender {
        M, F
    }

    public enum Subscription {
        Y, N
    }
}
