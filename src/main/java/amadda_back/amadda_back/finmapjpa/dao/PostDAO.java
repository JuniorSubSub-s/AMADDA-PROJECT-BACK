package amadda_back.amadda_back.finmapjpa.dao;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import amadda_back.amadda_back.finmapjpa.domain.entity.PostResponseDTO;

import java.util.List;

@Repository
public interface PostDAO extends JpaRepository<PostResponseDTO, Integer> {

    // 레스토랑 ID에 해당하는 포스트를 조회하는 메서드
    List<PostResponseDTO> findByRestaurant_RestaurantId(Integer restaurantId);
}