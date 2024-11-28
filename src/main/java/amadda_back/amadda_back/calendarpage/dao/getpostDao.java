package amadda_back.amadda_back.calendarpage.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import amadda_back.amadda_back.calendarpage.domain.GetPostEntity;

public interface getpostDao extends JpaRepository<GetPostEntity, Integer> {
    // @EntityGraph(attributePaths = "foodImages") // 연관된 foodImages를 함께 가져옴
    // List<GetPostEntity> findByUserAndPostDateBetween(String user, LocalDate startDate, LocalDate endDate);

    @Query("SELECT p FROM post p LEFT JOIN FETCH p.foodImages f WHERE p.user = :userId AND p.postDate BETWEEN :startDate AND :endDate")
    List<GetPostEntity> findPostsByUserIdAndDateRange(String userId, LocalDate startDate, LocalDate endDate);
    
}
