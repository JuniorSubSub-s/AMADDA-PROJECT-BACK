package amadda_back.amadda_back.View.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import amadda_back.amadda_back.View.domain.entity.PostEntity;

@Repository
public interface PostDAO extends JpaRepository<PostEntity, Integer> {

    @Query("SELECT p FROM PostEntity p WHERE p.weather = :weather")
    List<PostEntity> findPostsByWeather(@Param("weather") String weather);

    @Query("SELECT p FROM PostEntity p WHERE p.mood = :mood")
    List<PostEntity> findPostsByMood(@Param("mood") PostEntity.Mood mood);

    @Query("SELECT p FROM PostEntity p WHERE p.privacy = :privacy")
    List<PostEntity> findPostsByPrivacy(@Param("privacy") PostEntity.Privacy privacy);

    @Query("SELECT p FROM PostEntity p JOIN p.restaurant r WHERE r.totalPost >= :minPosts")
    List<PostEntity> findPostsByColor(@Param("minPosts") int minPosts);

    @Query("SELECT p FROM PostEntity p JOIN p.restaurant r WHERE r.totalPost < 50")
    List<PostEntity> findPostsByLessThan50();

    @Query("SELECT p FROM PostEntity p JOIN p.restaurant r WHERE r.restaurantName LIKE %:searchText%")
    List<PostEntity> findByRestaurantName(@Param("searchText") String searchText);

    @Query("SELECT p FROM TagEntity t JOIN PostEntity p ON t.post.postId = p.postId WHERE t.tagName = :searchText")
    List<PostEntity> findByTagTagName(@Param("searchText") String searchText);

    @Query("SELECT DISTINCT p FROM TagEntity t JOIN t.post p WHERE t.tagName IN :tagNames")
    List<PostEntity> findPostsByTagNames(@Param("tagNames") List<String> tagNames);

    @Query("SELECT DISTINCT p FROM TopicEntity t JOIN t.post p WHERE t.topicName IN :topicNames")
    List<PostEntity> findPostsByTopicNames(@Param("topicNames") List<String> topicNames);

    List<PostEntity> findByMoodIn(List<String> moods);

    @Query("SELECT p FROM PostEntity p WHERE p.postId IN :postIds")
    List<PostEntity> getPostsByIds(@Param("postIds") List<Integer> postIds);

    List<PostEntity> findAllByOrderByPostDateAsc();

    List<PostEntity> findByReceiptVerification(Boolean receiptVerification);

    @Query("SELECT p FROM PostEntity p ORDER BY p.dailyViews DESC")
    List<PostEntity> findAllOrderByDailyViewsDesc();

}

