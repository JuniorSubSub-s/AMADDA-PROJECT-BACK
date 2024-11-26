package amadda_back.amadda_back.finmapjpa.service;

import amadda_back.amadda_back.finmapjpa.domain.entity.ReplyCommentEntity;
import amadda_back.amadda_back.finmapjpa.domain.entity.UserRequestMapDTO;
import amadda_back.amadda_back.finmapjpa.domain.entity.CommentEntity;
import amadda_back.amadda_back.finmapjpa.dao.ReplyCommentDAO;
import amadda_back.amadda_back.finmapjpa.dao.CommentDAO;
import amadda_back.amadda_back.finmapjpa.dao.UserMapDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReplyCommentService {

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private ReplyCommentDAO replyCommentDAO;

    @Autowired
    private UserMapDAO userMapDAO;

    // 댓글에 답글 추가
    public ReplyCommentEntity addReplyToComment(Integer userId, Integer commentId, String replyContent) {
        // 댓글이 존재하는지 확인
        CommentEntity comment = commentDAO.findById(commentId)
            .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));

        // 사용자 확인
        UserRequestMapDTO user = userMapDAO.findById(userId)
            .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));

        // 새로운 답글 생성
        ReplyCommentEntity reply = new ReplyCommentEntity();
        reply.setReplyContent(replyContent);
        reply.setPostComment(comment);
        reply.setUser(user);

        return replyCommentDAO.save(reply);
    }

    // 답글 삭제
    public void deleteReply(Integer replyId) {
        // 답글이 존재하는지 확인
        ReplyCommentEntity reply = replyCommentDAO.findById(replyId)
            .orElseThrow(() -> new RuntimeException("답글이 존재하지 않습니다."));

        // 답글 삭제
        replyCommentDAO.delete(reply);
    }

    // 특정 댓글에 대한 답글 목록 조회
    public List<ReplyCommentEntity> getRepliesByCommentId(Integer commentId) {
        // 댓글 ID에 해당하는 답글 목록 조회
        return replyCommentDAO.findByPostCommentCommentId(commentId);
    }

    public void deleteRepliesByCommentId(Integer commentId) {
        // 해당 댓글에 달린 모든 답글 조회
        List<ReplyCommentEntity> replies = replyCommentDAO.findByPostCommentCommentId(commentId);
    
        // 답글들 삭제
        replyCommentDAO.deleteAll(replies);
    }
}
