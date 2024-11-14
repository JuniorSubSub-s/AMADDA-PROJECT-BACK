package amadda_back.amadda_back.PaymentPage.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import amadda_back.amadda_back.PaymentPage.domain.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{
    Optional<Order> findByMerchantUid(String merchantUid);
} 
