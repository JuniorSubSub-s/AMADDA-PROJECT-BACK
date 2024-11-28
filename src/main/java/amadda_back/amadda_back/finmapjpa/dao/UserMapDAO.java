package amadda_back.amadda_back.finmapjpa.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import amadda_back.amadda_back.finmapjpa.domain.entity.UserRequestMapDTO;




@Repository
public interface UserMapDAO extends JpaRepository<UserRequestMapDTO, Integer> {

   
}