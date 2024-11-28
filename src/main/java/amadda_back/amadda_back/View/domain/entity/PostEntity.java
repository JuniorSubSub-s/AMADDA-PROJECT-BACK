package amadda_back.amadda_back.View.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "post")
@Data
@Entity
@DynamicUpdate
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer postId;

    @Column(name = "post_title")
    private String postTitle;

    @Column(name = "post_content")
    private String postContent;

    @Column(name = "post_date")
    private LocalDateTime postDate = LocalDateTime.now();

    @Column(name = "privacy")
    private String privacy;

    @Column(name = "food_category")
    private String foodCategory;

    @Column(name = "mood")
    private String mood;

    @Column(name = "weather")
    private String weather;

    @Column(name = "receipt_verification")
    private Boolean receiptVerification = false;

    @Column(name = "daily_views")
    private Integer dailyViews = 0;

    @Column(name = "theme_diary_img")
    private String themeDiaryImg;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    private ThemeEntity theme;

}
