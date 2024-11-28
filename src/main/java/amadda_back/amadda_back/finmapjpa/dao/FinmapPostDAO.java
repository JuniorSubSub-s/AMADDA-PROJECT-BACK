package amadda_back.amadda_back.finmapjpa.dao;

import amadda_back.amadda_back.View.domain.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinmapPostDAO extends JpaRepository<PostEntity, Integer> {

    // 레스토랑 ID에 해당하는 포스트를 조회하는 메서드
    List<PostEntity> findByRestaurant_RestaurantId(Integer restaurantId);
}
