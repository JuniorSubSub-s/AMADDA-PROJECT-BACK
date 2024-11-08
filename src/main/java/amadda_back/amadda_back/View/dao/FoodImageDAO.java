package amadda_back.amadda_back.View.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import amadda_back.amadda_back.View.domain.entity.FoodImageEntity;

@Repository
public interface FoodImageDAO extends JpaRepository<FoodImageEntity, Integer> {

    @Query("SELECT f.foodImageUrl FROM FoodImageEntity f WHERE f.post.postId = :postId")
    List<String> findFirstFoodImageUrlByPostId(@Param("postId") Integer postId);

    @Query("SELECT f.foodImageUrl FROM FoodImageEntity f WHERE f.post.postId IN :postIds")
    List<String> findFoodImagesByPostIds(@Param("postIds") List<Integer> postIds);

}
