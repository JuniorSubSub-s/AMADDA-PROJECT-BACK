package amadda_back.amadda_back.finmapjpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import amadda_back.amadda_back.finmapjpa.domain.entity.FoodTagEntity;

import java.util.List;

public interface FoodTagDAO extends JpaRepository<FoodTagEntity, Integer> {
    List<FoodTagEntity> findByPost_PostId(Integer postId);  // 포스트 ID로 태그들을 조회
}
