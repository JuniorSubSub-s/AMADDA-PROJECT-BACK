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
@Table(name = "reply_comment")
public class ReplyCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Integer replyId;

    @Column(name = "reply_create_time")
    private LocalDateTime replyCreateTime = LocalDateTime.now();

    @Column(name = "reply_content")
    private String replyContent;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity postComment;

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
