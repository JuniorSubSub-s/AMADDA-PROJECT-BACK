package amadda_back.amadda_back.View.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import amadda_back.amadda_back.View.domain.entity.PostEntity;

@Repository
public interface PostDAO extends JpaRepository<PostEntity, Integer> {

    // 사용자 ID로 포스트 조회
    @Query("SELECT p FROM PostEntity p WHERE p.user.userId = :userId")
    List<PostEntity> findByUser_UserId(@Param("userId") Integer userId);

    // 날씨에 해당하는 포스트 조회
    @Query("SELECT p FROM PostEntity p WHERE p.weather = :weather")
    List<PostEntity> findPostsByWeather(@Param("weather") String weather);

    // 감정에 해당하는 포스트 조회
    @Query("SELECT p FROM PostEntity p WHERE p.mood = :mood")
    List<PostEntity> findPostsByMood(@Param("mood") PostEntity.Mood mood);

    // 프라이버시 설정에 해당하는 포스트 조회
    @Query("SELECT p FROM PostEntity p WHERE p.privacy = :privacy")
    List<PostEntity> findPostsByPrivacy(@Param("privacy") PostEntity.Privacy privacy);

    // 특정 레스토랑에서 포스트 색깔 기준으로 조회
    @Query("SELECT p FROM PostEntity p JOIN p.restaurant r WHERE r.totalPost >= :minPosts")
    List<PostEntity> findPostsByColor(@Param("minPosts") int minPosts);

    // 레스토랑이 50개 미만인 포스트 조회
    @Query("SELECT p FROM PostEntity p JOIN p.restaurant r WHERE r.totalPost < 50")
    List<PostEntity> findPostsByLessThan50();

    // 레스토랑 이름으로 포스트 검색
    @Query("SELECT p FROM PostEntity p JOIN p.restaurant r WHERE r.restaurantName LIKE %:searchText%")
    List<PostEntity> findByRestaurantName(@Param("searchText") String searchText);

    // 태그 이름으로 포스트 검색
    @Query("SELECT p FROM TagEntity t JOIN t.post p WHERE t.tagName = :searchText")
    List<PostEntity> findByTagTagName(@Param("searchText") String searchText);

    // 여러 태그 이름에 해당하는 포스트 조회
    @Query("SELECT DISTINCT p FROM TagEntity t JOIN t.post p WHERE t.tagName IN :tagNames")
    List<PostEntity> findPostsByTagNames(@Param("tagNames") List<String> tagNames);

    // 여러 토픽 이름에 해당하는 포스트 조회
    @Query("SELECT DISTINCT p FROM TopicEntity t JOIN t.post p WHERE t.topicName IN :topicNames")
    List<PostEntity> findPostsByTopicNames(@Param("topicNames") List<String> topicNames);

    // 여러 감정에 해당하는 포스트 조회
    List<PostEntity> findByMoodIn(List<String> moods);

    // 여러 포스트 ID로 포스트 조회
    @Query("SELECT p FROM PostEntity p WHERE p.postId IN :postIds")
    List<PostEntity> getPostsByIds(@Param("postIds") List<Integer> postIds);

    // 최신 포스트 조회 (날짜 기준)
    List<PostEntity> findAllByOrderByPostDateAsc();

    // 영수증 인증 여부에 따른 포스트 조회
    List<PostEntity> findByReceiptVerification(Boolean receiptVerification);

    // 조회수가 높은 순으로 포스트 조회
    @Query("SELECT p FROM PostEntity p ORDER BY p.dailyViews DESC")
    List<PostEntity> findAllOrderByDailyViewsDesc();
}
