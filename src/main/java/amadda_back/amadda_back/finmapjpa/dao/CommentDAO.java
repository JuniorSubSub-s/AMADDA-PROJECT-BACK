package amadda_back.amadda_back.finmapjpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import amadda_back.amadda_back.finmapjpa.domain.entity.CommentEntity;

@Repository
public interface CommentDAO extends JpaRepository<CommentEntity, Integer> {


}
