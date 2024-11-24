package amadda_back.amadda_back.finmapjpa.service;

import amadda_back.amadda_back.finmapjpa.dao.UserViewModalBadgeDAO;
import amadda_back.amadda_back.finmapjpa.dao.ViewModalBadgeDAO;
import amadda_back.amadda_back.finmapjpa.domain.entity.ModalBadgeEntity;
import amadda_back.amadda_back.finmapjpa.domain.entity.UserModalBadgeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BadgeService {

    @Autowired
    private UserViewModalBadgeDAO userViewModalBadgeDAO;

    @Autowired
    private ViewModalBadgeDAO viewModalBadgeDAO;

    // 사용자 배지 이미지 조회
    public List<String> getUserBadgeImages(Long userId) {
        // UserModalBadgeEntity 목록을 가져온 후 배지 이미지만 추출
        List<UserModalBadgeEntity> userBadges = userViewModalBadgeDAO.findByUser_UserId(userId);
        return userBadges.stream()
                         .map(umb -> umb.getBadge().getBadgeImage())  // 배지 이미지만 추출
                         .collect(Collectors.toList());
    }

    // 모든 배지 이미지 조회
    public List<String> getAllBadges() {
        // ModalBadgeEntity 목록을 가져온 후 배지 이미지만 추출
        List<ModalBadgeEntity> allBadges = viewModalBadgeDAO.findAll();
        return allBadges.stream()
                        .map(ModalBadgeEntity::getBadgeImage)  // 배지 이미지만 추출
                        .collect(Collectors.toList());
    }
}
