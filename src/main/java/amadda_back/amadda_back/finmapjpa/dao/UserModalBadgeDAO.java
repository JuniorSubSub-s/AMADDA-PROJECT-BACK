package amadda_back.amadda_back.finmapjpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import amadda_back.amadda_back.finmapjpa.domain.entity.UserModalBadgeEntity;

import java.util.List;

public interface UserModalBadgeDAO extends JpaRepository<UserModalBadgeEntity, Integer> {
    List<UserModalBadgeEntity> findByUser_UserId(Integer userId);
}