package amadda_back.amadda_back.finmapjpa.dao;

import amadda_back.amadda_back.finmapjpa.domain.entity.FoodMapImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FoodModalImageDAO extends JpaRepository<FoodMapImageEntity, Long> {

    // 특정 postId에 관련된 모든 음식 이미지 URL을 가져오는 메서드
    List<FoodMapImageEntity> findByPostPostId(Long postId);
}
