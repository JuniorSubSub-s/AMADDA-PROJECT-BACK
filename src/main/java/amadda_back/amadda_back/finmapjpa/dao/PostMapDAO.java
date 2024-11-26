package amadda_back.amadda_back.finmapjpa.dao;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import amadda_back.amadda_back.finmapjpa.domain.entity.PostResponseMapDTO;

import java.util.List;

@Repository
public interface PostMapDAO extends JpaRepository<PostResponseMapDTO, Integer> {

    // 레스토랑 ID에 해당하는 포스트를 조회하는 메서드
    List<PostResponseMapDTO> findByRestaurant_RestaurantId(Integer restaurantId);

    // 특정 사용자의 게시글 조회
    List<PostResponseMapDTO> findByUser_UserId(Integer userId);
}