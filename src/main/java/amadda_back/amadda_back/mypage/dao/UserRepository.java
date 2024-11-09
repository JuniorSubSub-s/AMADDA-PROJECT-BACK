package amadda_back.amadda_back.mypage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import amadda_back.amadda_back.View.domain.entity.UserEntity;
import amadda_back.amadda_back.mypage.domain.entity.UserInfoDTO;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT new amadda_back.amadda_back.mypage.domain.entity.UserInfoDTO(" +
       "u.userId, u.userName, u.introduceText, u.userNickname, u.userPhoneNumber, u.userEmail, u.profileImage, " +
       "u.birthDate, u.userGender, u.userCurrencyBalance, u.subscription, " +
       "(SELECT COUNT(f1) FROM UserFollower f1 WHERE f1.userId = u.userId), " + // 팔로잉 카운트
       "(SELECT COUNT(f2) FROM UserFollower f2 WHERE f2.followerUserId = u.userId)) " + // 팔로워 카운트
       "FROM UserEntity u " + // 엔티티 이름을 UserEntity로 변경
       "WHERE u.userId = :userId")
    UserInfoDTO findUserInfoByUserId(@Param("userId") int userId);
    
    @Query("SELECT COUNT(f) FROM UserFollower f WHERE f.userId = :userId")
    int countFollowingByUserId(@Param("userId") int userId);

    @Query("SELECT COUNT(f) FROM UserFollower f WHERE f.followerUserId = :userId")
    int countFollowerByUserId(@Param("userId") int userId);
}