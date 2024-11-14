package amadda_back.amadda_back.mypage.domain.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "badge")
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer badgeId;

    @Column(name = "badge_name")
    private String badgeName;

    @Column(name = "badge_description")
    private String badgeDescription;

    @Column(name = "badge_image")
    private String badgeImage;
}
