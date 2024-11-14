package amadda_back.amadda_back.mypage.domain.entity;

import amadda_back.amadda_back.View.domain.entity.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "user_follower")
public class UserFollower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private int followId;

    @Column(name = "user_id", nullable = false)
    private int userId; // 팔로잉하는 사용자 ID

    @Column(name = "follower_user_id", nullable = false)
    private int followerUserId; // 팔로워 사용자 ID

    // User와의 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private UserEntity user; // 팔로잉하는 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private UserEntity followerUser; // 팔로워 사용자

    // 기본 생성자
    public UserFollower() {
    }

    // 생성자
    public UserFollower(int userId, int followerUserId) {
        this.userId = userId;
        this.followerUserId = followerUserId;
    }

    // Getters and Setters
    public int getFollowId() {
        return followId;
    }

    public void setFollowId(int followId) {
        this.followId = followId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFollowerUserId() {
        return followerUserId;
    }

    public void setFollowerUserId(int followerUserId) {
        this.followerUserId = followerUserId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public UserEntity getFollowerUser() {
        return followerUser;
    }

    public void setFollowerUser(UserEntity followerUser) {
        this.followerUser = followerUser;
    }
}
