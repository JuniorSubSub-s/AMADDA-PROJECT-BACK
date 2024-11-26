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
    private String themeDiaryImg;
    private RestaurantEntity restaurant;
    private UserEntity user;
    private ThemeEntity theme;

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
        this.themeDiaryImg = entity.getThemeDiaryImg();
        this.restaurant = entity.getRestaurant();
        this.user = entity.getUser();
        this.theme = entity.getTheme();
    }

}
