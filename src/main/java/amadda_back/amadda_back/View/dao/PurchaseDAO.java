package amadda_back.amadda_back.View.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import amadda_back.amadda_back.View.domain.entity.PurchaseEntity;

@Repository
public interface PurchaseDAO extends JpaRepository<PurchaseEntity, Integer> {

    List<PurchaseEntity> findByUser_UserId(Integer userId);
}
