package amadda_back.amadda_back.PaymentPage.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import amadda_back.amadda_back.PaymentPage.domain.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>{
    Optional<Payment> findByMerchantUid(String merchantUid); // 혹은 다른 검색 조건을 사용합니다.
}
