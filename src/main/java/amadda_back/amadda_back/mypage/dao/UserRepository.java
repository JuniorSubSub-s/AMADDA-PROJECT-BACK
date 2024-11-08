package amadda_back.amadda_back.mypage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import amadda_back.amadda_back.mypage.domain.entity.User;
import amadda_back.amadda_back.mypage.domain.entity.UserInfoDTO;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * 주어진 userId에 대한 사용자 정보를 UserInfoDTO로 반환합니다.
     * @param userId 사용자 ID
     * @return 사용자 정보 DTO
     */
    @Query("SELECT new amadda.userprofileedit.amadda_user_profile_edit_backend.dto.UserInfoDTO(" +
       "u.userId, u.userName, u.introduceText, u.userNickname, u.userPhoneNumber, u.userEmail, u.profileImage, " +
       "u.birthDate, u.userGender, u.userCurrencyBalance, u.subscription, " +
       "COUNT(DISTINCT f1), COUNT(DISTINCT f2)) " + // COUNT를 JOIN으로 변경
       "FROM User u " +
       "LEFT JOIN UserFollower f1 ON f1.userId = u.userId " + // 팔로잉 카운트
       "LEFT JOIN UserFollower f2 ON f2.followerUserId = u.userId " + // 팔로워 카운트
       "WHERE u.userId = :userId " +
       "GROUP BY u.userId")
       UserInfoDTO findUserInfoByUserId(@Param("userId") int userId);

    @Query("SELECT COUNT(f) FROM UserFollower f WHERE f.userId = :userId")
    int countFollowingByUserId(@Param("userId") int userId);

    @Query("SELECT COUNT(f) FROM UserFollower f WHERE f.followerUserId = :userId")
    int countFollowerByUserId(@Param("userId") int userId);
}
