package amadda_back.amadda_back.View.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "food_image")
@Data
@Entity
public class FoodImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_image_id")
    private Integer foodImageId;

    @Column(name = "food_image_url")
    private String foodImageUrl;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

}
