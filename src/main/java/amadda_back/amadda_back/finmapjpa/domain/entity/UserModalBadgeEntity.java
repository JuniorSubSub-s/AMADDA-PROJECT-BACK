package amadda_back.amadda_back.finmapjpa.domain.entity;

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


@Table(name = "user_badge")
@Data
@Entity
@DynamicUpdate
public class UserModalBadgeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_badge_id")
    private Integer userBadgeId;

    @Column(name = "badge_earned_date")
    private LocalDateTime badgeEarnedDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserRequestMapDTO user;

    @ManyToOne
    @JoinColumn(name = "badge_id")
    private ModalBadgeEntity badge;

}