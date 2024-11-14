package amadda_back.amadda_back.loginpage.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import amadda_back.amadda_back.loginpage.entity.Users;


public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUserEmail(String userEmail);
    Optional<Users> findByUserPwd(String userPwd);
    Optional<Users> findByUserRefreshToken(String userRefreshToken);
}
