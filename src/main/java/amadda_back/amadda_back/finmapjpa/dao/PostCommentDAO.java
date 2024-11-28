package amadda_back.amadda_back.finmapjpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import amadda_back.amadda_back.finmapjpa.domain.entity.CommentEntity;

import java.util.List;

@Repository
public interface PostCommentDAO extends JpaRepository<CommentEntity, Integer> {
    // 포스트 ID에 해당하는 댓글 목록 조회
    List<CommentEntity> findByPostPostId(Integer postId);
}
