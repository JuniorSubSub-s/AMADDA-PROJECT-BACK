package amadda_back.amadda_back.View.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import amadda_back.amadda_back.View.domain.entity.ThemeEntity;

@Repository
public interface ThemeDAO extends JpaRepository<ThemeEntity, Integer> {

}
