package amadda_back.amadda_back.View.domain.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PostResponseDTO {

    private Integer postId;
    private String postTitle;
    private String postContent;
    private LocalDateTime postDate;
    private String privacy;
    private String foodCategory;
    private String mood;
    private String weather;
    private Boolean receiptVerification;
    private Integer dailyViews;
    private RestaurantEntity restaurant;
    private UserEntity user;
    private ThemeEntity theme;
    private String themeDiaryImg;

    // PostEntity로부터 PostResponseDTO를 생성하는 생성자 추가
    public PostResponseDTO(PostEntity entity) {
        this.postId = entity.getPostId();
        this.postTitle = entity.getPostTitle();
        this.postContent = entity.getPostContent();
        this.postDate = entity.getPostDate();
        this.privacy = entity.getPrivacy();
        this.foodCategory = entity.getFoodCategory();
        this.mood = entity.getMood();
        this.weather = entity.getWeather();
        this.receiptVerification = entity.getReceiptVerification();
        this.dailyViews = entity.getDailyViews();
        this.restaurant = entity.getRestaurant();
        this.user = entity.getUser();
        this.theme = entity.getTheme();
        this.themeDiaryImg = entity.getThemeDiaryImg();
    }

    // PostEntity의 Privacy 값을 PostResponseDTO의 Privacy 값으로 변환
    // private Privacy mapPrivacy(PostEntity.Privacy entityPrivacy) {
    //     switch (entityPrivacy) {
    //         case PUBLIC:
    //             return Privacy.PUBLIC;
    //         case PRIVATE:
    //             return Privacy.PRIVATE;
    //         case ONLY_ME:
    //             return Privacy.ONLY_ME;
    //         default:
    //             return Privacy.PRIVATE; // 기본값 설정
    //     }
    // }
    // PostEntity의 FoodCategory 값을 PostResponseDTO의 FoodCategory 값으로 변환
    // private FoodCategory mapFoodCategory(PostEntity.FoodCategory entityFoodCategory) {
    //     switch (entityFoodCategory) {
    //         case 한식:
    //             return FoodCategory.한식;
    //         case 중식:
    //             return FoodCategory.중식;
    //         case 양식:
    //             return FoodCategory.양식;
    //         case 일식:
    //             return FoodCategory.일식;
    //         case 아시아요리:
    //             return FoodCategory.아시아요리;
    //         case 패스트푸드:
    //             return FoodCategory.패스트푸드;
    //         case 디저트:
    //             return FoodCategory.디저트;
    //         default:
    //             return FoodCategory.한식; // 기본값 설정
    //     }
    // }
    // PostEntity의 Mood 값을 PostResponseDTO의 Mood 값으로 변환
    // private Mood mapMood(PostEntity.Mood entityMood) {
    //     switch (entityMood) {
    //         case 평온:
    //             return Mood.평온;
    //         case 행복:
    //             return Mood.행복;
    //         case 사랑:
    //             return Mood.사랑;
    //         case 호기심:
    //             return Mood.호기심;
    //         case 스트레스:
    //             return Mood.스트레스;
    //         case 귀찮음:
    //             return Mood.귀찮음;
    //         default:
    //             return Mood.평온; // 기본값 설정
    //     }
    // }
}
