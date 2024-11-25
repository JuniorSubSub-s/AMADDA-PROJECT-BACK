package amadda_back.amadda_back.subscriptionPage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import amadda_back.amadda_back.subscriptionPage.domain.entity.SubscribeUser;

@Repository
public interface UserSubscribeRepository extends JpaRepository<SubscribeUser, Integer>{} 
