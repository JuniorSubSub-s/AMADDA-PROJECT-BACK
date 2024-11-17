package amadda_back.amadda_back.finmapjpa.dao;

import amadda_back.amadda_back.finmapjpa.domain.entity.TopicMapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicMapDAO extends JpaRepository<TopicMapEntity, Long> {

    // 주어진 postId에 해당하는 TopicMapEntity를 찾는 메서드
    List<TopicMapEntity> findByPost_PostId(Integer postId);
}