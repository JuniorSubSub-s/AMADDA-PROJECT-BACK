package amadda_back.amadda_back.View.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "privacy")
    private Privacy privacy;

    @Enumerated(EnumType.STRING)
    @Column(name = "food_category")
    private FoodCategory foodCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "mood")
    private Mood mood;

    @Column(name = "weather")
    private String weather;

    @Column(name = "receipt_verification")
    private Boolean receiptVerification = false;

    @Column(name = "daily_views")
    private Integer dailyViews = 0;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    private ThemeEntity theme;

    public enum Privacy {
        PUBLIC, PRIVATE, ONLY_ME
    }

    public enum FoodCategory {
        한식, 중식, 양식, 일식, 아시아요리, 패스트푸드, 디저트
    }

    public enum Mood {
        평온, 행복, 사랑, 호기심, 스트레스, 귀찮음
    }

}
