package amadda_back.amadda_back.calendarpage.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "food_image")
public class GetFoodImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_image_id")
    private Integer foodImageId;

    @Column(name = "food_image_url")
    private String foodImageUrl;

    @Column(name = "post_id")
    private Integer post;

}
