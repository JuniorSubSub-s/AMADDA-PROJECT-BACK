package amadda_back.amadda_back.finmapjpa.dao;

import amadda_back.amadda_back.finmapjpa.domain.entity.UserModalBadgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserViewModalBadgeDAO extends JpaRepository<UserModalBadgeEntity, Integer> {

    // 사용자 ID에 해당하는 배지 이미지들만 가져오기 (JPQL을 사용하지 않고 메서드 이름으로 해결)
    List<UserModalBadgeEntity> findByUser_UserId(Long userId);  
}
