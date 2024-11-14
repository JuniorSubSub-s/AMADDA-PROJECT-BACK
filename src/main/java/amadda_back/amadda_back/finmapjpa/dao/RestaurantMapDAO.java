package amadda_back.amadda_back.finmapjpa.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import amadda_back.amadda_back.finmapjpa.domain.entity.RestaurantMapEntity;

import java.util.List;

@Repository
public interface RestaurantMapDAO extends JpaRepository<RestaurantMapEntity, Integer> {

    
    List<RestaurantMapEntity> findAll();
}