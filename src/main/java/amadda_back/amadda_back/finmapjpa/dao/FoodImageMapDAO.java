package amadda_back.amadda_back.finmapjpa.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import amadda_back.amadda_back.finmapjpa.domain.entity.FoodMapImageEntity;

public interface FoodImageMapDAO extends JpaRepository<FoodMapImageEntity, Long> {

    // postId에 해당하는 여러 FoodImageEntity를 찾는 메서드
    List<FoodMapImageEntity> findByPost_PostId(Long postId);  // List로 반환
}