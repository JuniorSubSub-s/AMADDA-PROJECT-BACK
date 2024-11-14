package amadda_back.amadda_back.mypage.domain.entity;

import amadda_back.amadda_back.View.domain.entity.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "user_badge")
public class UserBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_badge_id")
    private int userBadgeId;

    @Column(name = "user_id", nullable = false)
    private int userId;  // 사용자 ID

    @Column(name = "badge_id", nullable = false)
    private int badgeId;  // 뱃지 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private UserEntity user;  // 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_id", referencedColumnName = "badge_id", insertable = false, updatable = false)
    private Badge badge;  // 뱃지

    // 기본 생성자
    public UserBadge() {
    }

    // 생성자
    public UserBadge(int userId, int badgeId) {
        this.userId = userId;
        this.badgeId = badgeId;
    }

    // Getters and Setters
    public int getUserBadgeId() {
        return userBadgeId;
    }

    public void setUserBadgeId(int userBadgeId) {
        this.userBadgeId = userBadgeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(int badgeId) {
        this.badgeId = badgeId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }
}
