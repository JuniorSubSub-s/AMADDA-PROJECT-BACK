package amadda_back.amadda_back.finmapjpa.domain.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "post_comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer commentId;

    @Column(name = "create_time")
    private LocalDateTime createTime = LocalDateTime.now();

    @Column(name = "comment_content")
    private String commentContent;

    // Post와의 관계 설정
    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostResponseMapDTO post;

    // User와의 관계 설정
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserRequestMapDTO user;

    // User의 이름 가져오기
    public String getUserName() {
        return user != null ? user.getUserName() : null;
    }

    // User의 프로필 이미지 가져오기
    public String getProfileImage() {
        return user != null ? user.getProfileImage() : null;
    }
}
