package amadda_back.amadda_back.mypage.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "badge")  // 테이블 이름 지정
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 증가 설정
    @Column(name = "badge_id")
    private Integer badgeId;  // Integer로 변경, 자동 증가 시 적합

    @Column(name = "badge_name")
    private String badgeName;

    @Column(name = "badge_description")
    private String badgeDescription;

    @Column(name = "badge_image")
    private String badgeImage;

    // Getters and Setters
    public Integer getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Integer badgeId) {
        this.badgeId = badgeId;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    public String getBadgeDescription() {
        return badgeDescription;
    }

    public void setBadgeDescription(String badgeDescription) {
        this.badgeDescription = badgeDescription;
    }

    public String getBadgeImage() {
        return badgeImage;
    }

    public void setBadgeImage(String badgeImage) {
        this.badgeImage = badgeImage;
    }
}
