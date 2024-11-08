package amadda_back.amadda_back.mypage.domain.entity;

import java.time.LocalDateTime;

public class UserInfoDTO {
    private int userId;                
    private String name;                // userName과 매칭
    private String introduceText;
    private String nickname;      
    private String phoneNumber;        
    private String email;              
    private LocalDateTime birthDate;   
    private User.Gender gender;        
    private int currencyBalance;       
    private User.Subscription subscription; 
    private String profileImage;
    private int followingCount; // 추가된 필드
    private int followerCount; // 추가된 필드


    // 기본 생성자
    public UserInfoDTO() {}

    // 모든 필드를 초기화하는 생성자
    public UserInfoDTO(int userId, String name, String introduceText, String nickname, String phoneNumber, String email, String profileImage, 
                       LocalDateTime birthDate, User.Gender gender, int currencyBalance, 
                       User.Subscription subscription,  int followingCount, int followerCount) {
        this.userId = userId;
        this.name = name; // userName
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

    public LocalDateTime getBirthDate() {
        return birthDate; 
    }

    public User.Gender getGender() {
        return gender;
    }

    public int getCurrencyBalance() {
        return currencyBalance;
    }

    public User.Subscription getSubscription() {
        return subscription;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public int getFollowingCount() { // 추가된 필드의 Getter
        return followingCount;
    }

    public int getFollowerCount() { // 추가된 필드의 Getter
        return followerCount;
    }


    // Setters
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

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(User.Gender gender) {
        this.gender = gender;
    }

    public void setCurrencyBalance(int currencyBalance) {
        this.currencyBalance = currencyBalance;
    }

    public void setSubscription(User.Subscription subscription) {
        this.subscription = subscription;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setFollowingCount(int followingCount) { // 추가된 필드의 Setter
        this.followingCount = followingCount;
    }

    public void setFollowerCount(int followerCount) { // 추가된 필드의 Setter
        this.followerCount = followerCount;
    }

}
