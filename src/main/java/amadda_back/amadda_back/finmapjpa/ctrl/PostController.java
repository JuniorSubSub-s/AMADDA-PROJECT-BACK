package amadda_back.amadda_back.finmapjpa.ctrl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import amadda_back.amadda_back.finmapjpa.domain.entity.CommentEntity;
import amadda_back.amadda_back.finmapjpa.domain.entity.PostResponseMapDTO;
import amadda_back.amadda_back.finmapjpa.domain.entity.ReplyCommentEntity;
import amadda_back.amadda_back.finmapjpa.domain.entity.RestaurantMapEntity;
import amadda_back.amadda_back.finmapjpa.service.BadgeService;
import amadda_back.amadda_back.finmapjpa.service.FoodModalImageService;
import amadda_back.amadda_back.finmapjpa.service.PostCommentService;
import amadda_back.amadda_back.finmapjpa.service.PostMapService;
import amadda_back.amadda_back.finmapjpa.service.ReplyCommentService;
import amadda_back.amadda_back.finmapjpa.service.RestaurantMapService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final RestaurantMapService restaurantMapService;
    private final PostMapService postMapService;
    private final PostCommentService postCommentService;
    private final ReplyCommentService replyCommentService;
    private final FoodModalImageService foodModalImageService;
    private final BadgeService badgeService;

    // 사용자 ID에 해당하는 배지 이미지 조회
    @GetMapping("/{userId}/badges")
    public List<String> getUserBadgeImages(@PathVariable("userId") Long userId) {
        return badgeService.getUserBadgeImages(userId);
    }

    // 모든 배지 이미지 조회
    @GetMapping("/badges")
    public List<String> getAllBadges() {
        return badgeService.getAllBadges();
    }

    // 모든 레스토랑을 가져오는 API
    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantMapEntity>> getAllRestaurants() {
        List<RestaurantMapEntity> result = restaurantMapService.getAllRestaurants();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 레스토랑의 total_post에 맞는 핀 색상을 가져오는 API
    @GetMapping("/restaurants/pins")
    public ResponseEntity<List<String>> getPinColors() {
        List<RestaurantMapEntity> restaurants = restaurantMapService.getAllRestaurants();
        List<String> pinColors = restaurants.stream()
                .map(restaurant -> restaurantMapService.getPinColorByPostCount(restaurant.getTotalPost()))
                .toList();
        return new ResponseEntity<>(pinColors, HttpStatus.OK);
    }

    // 특정 사용자의 게시글 가져오기
    @GetMapping("/restaurants/mapPost/{userId}/posts")
    public ResponseEntity<?> getPostsByUserId(@PathVariable("userId") Integer userId) {
        try {
            List<PostResponseMapDTO> posts = postMapService.getPostsByUserId(userId);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // 사용자 데이터 없음
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // 기타 예외 처리
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류가 발생했습니다.");
        }
    }

    // 특정 레스토랑의 포스트 정보를 가져오는 API
    @GetMapping("/restaurants/{restaurantId}/posts")
    public ResponseEntity<List<PostResponseMapDTO>> getPostsByRestaurantId(
            @PathVariable("restaurantId") Integer restaurantId) {
        // PostMapService에서 포스트와 관련된 토픽 이름들과 태그 이름들을 포함하여 가져옴
        List<PostResponseMapDTO> posts = postMapService.getPostsByRestaurantId(restaurantId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // 댓글 추가 API
    @PostMapping("/restaurants/comments")
    public ResponseEntity<CommentEntity> addComment(
            @RequestParam(name = "userId") Integer userId,
            @RequestParam(name = "postId") Integer postId,
            @RequestParam(name = "commentContent") String commentContent) {

        try {
            // 댓글 추가 서비스 호출
            CommentEntity savedComment = postCommentService.addComment(userId, postId, commentContent);
            return ResponseEntity.ok(savedComment);
        } catch (Exception e) {
            // 예외 메시지 로깅
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    // 댓글 조회 API - 특정 포스트의 댓글 목록
    @GetMapping("/restaurants/posts/{postId}/comments")
    public ResponseEntity<List<CommentEntity>> getCommentsByPostId(@PathVariable("postId") Integer postId) {
        try {
            List<CommentEntity> comments = postCommentService.getCommentsByPostId(postId);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    // 댓글 삭제 API
    @DeleteMapping("/restaurants/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Integer commentId) {
        try {
            // 댓글과 답글 삭제 서비스 호출
            postCommentService.deleteComment(commentId);
            return ResponseEntity.ok("댓글과 답글이 삭제되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("오류가 발생했습니다.");
        }
    }

    // 댓글에 답글 추가
    @PostMapping("/restaurants/comments/{commentId}/replies")
    public ResponseEntity<ReplyCommentEntity> addReplyToComment(
            @PathVariable("commentId") Integer commentId,
            @RequestParam("userId") Integer userId,
            @RequestParam("replyContent") String replyContent) {
        try {
            // 답글 추가 서비스 호출
            ReplyCommentEntity savedReply = replyCommentService.addReplyToComment(userId, commentId, replyContent);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReply); // 생성된 답글 반환
        } catch (Exception e) {
            // 예외 메시지 로깅
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    // 답글 삭제
    @DeleteMapping("/restaurants/replies/{replyId}")
    public ResponseEntity<String> deleteReply(@PathVariable("replyId") Integer replyId) {
        try {
            replyCommentService.deleteReply(replyId);
            return ResponseEntity.ok("답글이 삭제되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("오류가 발생했습니다.");
        }
    }

    // 특정 댓글에 대한 답글 목록 조회
    @GetMapping("/restaurants/comments/{commentId}/replies")
    public ResponseEntity<List<ReplyCommentEntity>> getRepliesByCommentId(
            @PathVariable("commentId") Integer commentId) {
        try {
            List<ReplyCommentEntity> replies = replyCommentService.getRepliesByCommentId(commentId);
            return new ResponseEntity<>(replies, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/posts/{postId}/food-images")
    public List<String> getFoodImages(@PathVariable Long postId) {
        return foodModalImageService.getFoodImageUrls(postId);
    }

}
