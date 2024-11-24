package amadda_back.amadda_back.finmapjpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import amadda_back.amadda_back.finmapjpa.domain.entity.ReplyCommentEntity;
import java.util.List;

@Repository
public interface ReplyCommentDAO extends JpaRepository<ReplyCommentEntity, Integer> {
    // 특정 댓글에 대한 답글 목록 조회
    List<ReplyCommentEntity> findByPostCommentCommentId(Integer commentId);

    // 여러 답글을 한 번에 삭제
    void deleteAllInBatch(Iterable<ReplyCommentEntity> entities);
}