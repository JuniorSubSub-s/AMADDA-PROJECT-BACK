package amadda_back.amadda_back.finmapjpa.domain.entity;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;




@Data
@Entity(name = "post")
@DynamicUpdate
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

    @Column(name ="receipt_verification")
    private Boolean receiptVerification;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantMapEntity restaurant;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserRequestMapDTO user;

    @Transient
    private List<String> foodImageUrls;  // foodImageUrls로 수정 (여러 이미지 URL을 담을 수 있도록 수정)

    // userNickname을 반환하는 메서드 추가
    public String getUserNickname() {
        return this.user != null ? this.user.getUserNickname() : null;
    }

    // userName을 반환하는 메서드 추가
    public String getUserName() {
        return this.user != null ? this.user.getUserName() : null;
    }
}
