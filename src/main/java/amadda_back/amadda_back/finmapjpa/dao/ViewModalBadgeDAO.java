package amadda_back.amadda_back.finmapjpa.dao;

import amadda_back.amadda_back.finmapjpa.domain.entity.ModalBadgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewModalBadgeDAO extends JpaRepository<ModalBadgeEntity, Integer> {
    
}
