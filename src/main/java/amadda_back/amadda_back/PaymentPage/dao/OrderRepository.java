package amadda_back.amadda_back.PaymentPage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import amadda_back.amadda_back.PaymentPage.domain.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
    @Modifying
    @Query("UPDATE Order o SET o.status = :status, o.modified = CURRENT_TIMESTAMP WHERE o.id = :orderId AND o.status = 'pending'")
    int updateOrderStatus(@Param("orderId") Integer orderId, @Param("status") String status); // Long -> Integer
}