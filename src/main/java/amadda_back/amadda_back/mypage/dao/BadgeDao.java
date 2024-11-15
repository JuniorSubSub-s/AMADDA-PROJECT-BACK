package amadda_back.amadda_back.mypage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import amadda_back.amadda_back.mypage.domain.entity.Badge;
import amadda_back.amadda_back.mypage.domain.entity.UserBadge;

public interface BadgeDao extends JpaRepository<UserBadge, Integer> {
    
    @Query("SELECT ub FROM UserBadge ub JOIN ub.user u WHERE u.userId = :userId")
    List<Badge> findBadgesByUserId(@Param("userId") Integer userId);
}
