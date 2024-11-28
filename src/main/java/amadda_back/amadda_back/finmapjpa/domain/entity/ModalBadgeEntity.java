package amadda_back.amadda_back.finmapjpa.domain.entity;

import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "badge")
@Data
@Entity
@DynamicUpdate
public class ModalBadgeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Integer badgeId;

    @Column(name = "badge_name")
    private String badgeName;

    @Column(name = "badge_description")
    private String badgeDescription;

    @Column(name = "badge_image")
    private String badgeImage;
}
