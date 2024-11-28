package amadda_back.amadda_back.finmapjpa.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import amadda_back.amadda_back.View.domain.entity.RestaurantEntity;

@Repository
public interface RestaurantDAO extends JpaRepository<RestaurantEntity, Integer> {

    List<RestaurantEntity> findAll();

    boolean existsByRestaurantNameOrRestaurantAddress(String restaurantName, String restaurantAddress);

    Optional<RestaurantEntity> findByRestaurantNameAndRestaurantAddress(String restaurantName,
            String restaurantAddress);

}
