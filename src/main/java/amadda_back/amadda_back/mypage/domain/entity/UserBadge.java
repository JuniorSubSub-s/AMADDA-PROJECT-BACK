package amadda_back.amadda_back.mypage.domain.entity;

import amadda_back.amadda_back.View.domain.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_badge")
public class UserBadge {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userBadgeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "badge_id", nullable = false)
    private Badge badge;

}
