package amadda_back.amadda_back.finmapjpa.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import amadda_back.amadda_back.finmapjpa.domain.entity.UserRequestDTO;


@Repository
public interface UserDAO extends JpaRepository<UserRequestDTO, Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO user (user_name, user_password, user_email, user_nickname, user_phonenumber, user_gender) "
            + "VALUES (:userName, :userPassword, :userEmail, :userNickname, :userPhonenumber, :userGender)", nativeQuery = true)
    void insertUser(@Param("userName") String userName,
            @Param("userPassword") String userPassword,
            @Param("userEmail") String userEmail,
            @Param("userNickname") String userNickname,
            @Param("userPhonenumber") String userPhonenumber,
            @Param("userGender") String userGender);
}