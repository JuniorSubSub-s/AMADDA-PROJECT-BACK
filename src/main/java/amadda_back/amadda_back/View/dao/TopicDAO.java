package amadda_back.amadda_back.View.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import amadda_back.amadda_back.View.domain.entity.TopicEntity;

@Repository
public interface TopicDAO extends JpaRepository<TopicEntity, Integer> {
}
