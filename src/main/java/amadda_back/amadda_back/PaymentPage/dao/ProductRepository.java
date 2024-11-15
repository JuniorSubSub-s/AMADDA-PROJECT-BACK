package amadda_back.amadda_back.PaymentPage.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import amadda_back.amadda_back.PaymentPage.domain.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
    
}
