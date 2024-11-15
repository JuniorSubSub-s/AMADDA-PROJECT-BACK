package amadda_back.amadda_back.PaymentPage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import amadda_back.amadda_back.PaymentPage.domain.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{
    
    @Modifying
    @Query("UPDATE Payment p SET p.status = :status, p.modified = CURRENT_TIMESTAMP WHERE p.id = :paymentId AND p.status = 'pending'")
    int updatePaymentStatus(@Param("paymentId") Long paymentId, @Param("status") String status);
}