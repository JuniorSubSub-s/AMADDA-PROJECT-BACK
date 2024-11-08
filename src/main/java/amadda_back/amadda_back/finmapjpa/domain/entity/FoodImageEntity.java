package amaddaback.amadda.finmapjpa.domain.entity;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Entity(name = "food_image")
@Data
@DynamicUpdate
public class FoodImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_image_id")
    private Long foodImageId; // 이미지 ID

    @Column(name = "food_image_url", nullable = false)
    private String foodImageUrl; // 음식 이미지 URL

    @Column(name = "post_id")
    private Long post; // 해당 음식 이미지를 가진 게시물 (PostResponseDTO)

}