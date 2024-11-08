package amadda_back.amadda_back.View.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import amadda_back.amadda_back.View.domain.entity.PostEntity;
import amadda_back.amadda_back.View.domain.entity.PostResponseDTO;

@Repository
public interface PostDAO extends JpaRepository<PostResponseDTO, Integer> {

    @Query("SELECT p FROM PostResponseDTO p WHERE p.weather = :weather")
    List<PostResponseDTO> findPostsByWeather(@Param("weather") String weather);

    @Query("SELECT p FROM PostResponseDTO p WHERE p.mood = :mood")
    List<PostResponseDTO> findPostsByMood(@Param("mood") PostResponseDTO.Mood mood);

    @Query("SELECT p FROM PostResponseDTO p WHERE p.privacy = :privacy")
    List<PostResponseDTO> findPostsByPrivacy(@Param("privacy") PostResponseDTO.Privacy privacy);

    @Query("SELECT p FROM PostResponseDTO p JOIN p.restaurant r WHERE r.totalPost >= :minPosts")
    List<PostResponseDTO> findPostsByColor(@Param("minPosts") int minPosts);

    @Query("SELECT p FROM PostResponseDTO p JOIN p.restaurant r WHERE r.totalPost < 50")
    List<PostResponseDTO> findPostsByLessThan50();

    @Query("SELECT p FROM PostResponseDTO p JOIN p.restaurant r WHERE r.restaurantName LIKE %:searchText%")
    List<PostResponseDTO> findByRestaurantName(@Param("searchText") String searchText);

    @Query("SELECT p FROM TagEntity t JOIN PostResponseDTO p ON t.post.postId = p.postId WHERE t.tagName = :searchText")
    List<PostResponseDTO> findByTagTagName(@Param("searchText") String searchText);

    @Query("SELECT DISTINCT p FROM TagEntity t JOIN t.post p WHERE t.tagName IN :tagNames")
    List<PostEntity> findPostsByTagNames(@Param("tagNames") List<String> tagNames);

    @Query("SELECT DISTINCT p FROM TopicEntity t JOIN t.post p WHERE t.topicName IN :topicNames")
    List<PostEntity> findPostsByTopicNames(@Param("topicNames") List<String> topicNames);

    List<PostResponseDTO> findByMoodIn(List<String> moods);

    @Query("SELECT p FROM PostResponseDTO p WHERE p.postId IN :postIds")
    List<PostResponseDTO> getPostsByIds(@Param("postIds") List<Integer> postIds);

    List<PostResponseDTO> findAllByOrderByPostDateAsc();

    List<PostResponseDTO> findByReceiptVerification(Boolean receiptVerification);

    @Query("SELECT p FROM PostResponseDTO p ORDER BY p.dailyViews DESC")
    List<PostResponseDTO> findAllOrderByDailyViewsDesc();

}
