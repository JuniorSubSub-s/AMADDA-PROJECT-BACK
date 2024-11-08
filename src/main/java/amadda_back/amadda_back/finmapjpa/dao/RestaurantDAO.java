package amadda_back.amadda_back.finmapjpa.dao;


import amadda_back.amadda_back.finmapjpa.domain.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantDAO extends JpaRepository<RestaurantEntity, Integer> {

    
    List<RestaurantEntity> findAll();
}