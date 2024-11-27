package amadda_back.amadda_back.finmapjpa.service;

import amadda_back.amadda_back.finmapjpa.domain.entity.CommentEntity;
import amadda_back.amadda_back.finmapjpa.domain.entity.PostResponseMapDTO;
import amadda_back.amadda_back.finmapjpa.domain.entity.UserRequestMapDTO;
import amadda_back.amadda_back.finmapjpa.dao.CommentDAO;
import amadda_back.amadda_back.finmapjpa.dao.PostCommentDAO;
import amadda_back.amadda_back.finmapjpa.dao.PostMapDAO;
import amadda_back.amadda_back.finmapjpa.dao.UserMapDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostCommentService {

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private PostMapDAO postMapDAO;

    @Autowired
    private UserMapDAO userMapDAO;

    @Autowired
    private PostCommentDAO postCommentDAO;

    @Autowired
    private ReplyCommentService replyCommentService;

    // 댓글 추가
    public CommentEntity addComment(Integer userId, Integer postId, String commentContent) {
        PostResponseMapDTO post = postMapDAO.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        UserRequestMapDTO user = userMapDAO.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CommentEntity newComment = new CommentEntity();
        newComment.setCommentContent(commentContent);
        newComment.setPost(post);
        newComment.setUser(user);

        return commentDAO.save(newComment);
    }

    // 댓글 삭제
    public void deleteComment(Integer commentId) {
        // 댓글이 존재하는지 확인
        CommentEntity comment = commentDAO.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다."));

        // 해당 댓글에 달린 답글들을 먼저 삭제
        replyCommentService.deleteRepliesByCommentId(commentId);

        // 댓글 삭제
        commentDAO.delete(comment);
    }

    // 특정 포스트에 대한 댓글 조회
    public List<CommentEntity> getCommentsByPostId(Integer postId) {
        // 포스트 ID에 해당하는 댓글 목록을 조회
        return postCommentDAO.findByPostPostId(postId);
    }
}
