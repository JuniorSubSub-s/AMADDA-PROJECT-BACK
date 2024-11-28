package amadda_back.amadda_back.finmapjpa.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "post")
public class PostResponseMapDTO {

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

    @Column(name = "receipt_verification")
    private Boolean receiptVerification;

    @Column(name = "food_category")
    private String foodCategory;

    @Column(name = "mood")
    private String mood;

    @Column(name = "weather")
    private String weather;

    @Column(name = "theme_diary_img")
    private String themeDiaryImg;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantMapEntity restaurant;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserRequestMapDTO user;

    @Transient
    private List<String> foodImageUrls;

    @Transient
    private List<String> topicNames;

    @Transient
    private List<String> tagNames;

    @Transient
    private List<String> badgeNames;

    @Transient
    private List<String> badgeImages;

    public String getUserNickname() {
        return this.user != null ? this.user.getUserNickname() : null;
    }

    public String getUserName() {
        return this.user != null ? this.user.getUserName() : null;
    }
}
