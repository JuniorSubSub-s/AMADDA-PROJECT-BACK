package amadda_back.amadda_back.finmapjpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import amadda_back.amadda_back.finmapjpa.dao.FoodImageMapDAO;
import amadda_back.amadda_back.finmapjpa.dao.FoodTagDAO;
import amadda_back.amadda_back.finmapjpa.dao.PostMapDAO;
import amadda_back.amadda_back.finmapjpa.dao.TopicMapDAO;
import amadda_back.amadda_back.finmapjpa.dao.UserModalBadgeDAO;
import amadda_back.amadda_back.finmapjpa.domain.entity.FoodMapImageEntity;
import amadda_back.amadda_back.finmapjpa.domain.entity.FoodTagEntity;
import amadda_back.amadda_back.finmapjpa.domain.entity.PostResponseMapDTO;
import amadda_back.amadda_back.finmapjpa.domain.entity.TopicMapEntity;
import amadda_back.amadda_back.finmapjpa.domain.entity.UserModalBadgeEntity;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostMapService {

    private final PostMapDAO postDao;
    private final FoodImageMapDAO foodImageDao;
    private final TopicMapDAO topicMapDao;
    private final FoodTagDAO foodTagDao;
    private final UserModalBadgeDAO userModalBadgeDao;

    public List<PostResponseMapDTO> getPostsByRestaurantId(Integer restaurantId) {
        // 1. 레스토랑 ID에 해당하는 포스트 리스트 가져오기
        List<PostResponseMapDTO> posts = postDao.findByRestaurant_RestaurantId(restaurantId);

        for (PostResponseMapDTO post : posts) {
            // 2. 각 포스트에 대한 관련 정보 추가

            // 이미지 URL 처리
            List<FoodMapImageEntity> foodImages = foodImageDao.findByPost_PostId(Long.valueOf(post.getPostId()));
            if (foodImages != null && !foodImages.isEmpty()) {
                List<String> imageUrls = foodImages.stream()
                                                   .map(FoodMapImageEntity::getFoodImageUrl)
                                                   .collect(Collectors.toList());
                post.setFoodImageUrls(imageUrls);
            }

            // 토픽 이름 처리
            List<TopicMapEntity> topics = topicMapDao.findByPost_PostId(post.getPostId());
            List<String> topicNames = topics.stream()
                                           .map(TopicMapEntity::getTopicName)
                                           .collect(Collectors.toList());
            post.setTopicNames(topicNames);

            // 태그 이름 처리
            List<FoodTagEntity> tags = foodTagDao.findByPost_PostId(post.getPostId());
            List<String> tagNames = tags.stream()
                                        .map(FoodTagEntity::getTagName)
                                        .collect(Collectors.toList());
            post.setTagNames(tagNames);

            // 배지 이름과 이미지 처리
            List<UserModalBadgeEntity> userBadges = userModalBadgeDao.findByUser_UserId(post.getUser().getUserId());
            if (userBadges != null && !userBadges.isEmpty()) {
                List<String> badgeNames = userBadges.stream()
                                                    .map(userModalBadge -> userModalBadge.getBadge().getBadgeName())
                                                    .collect(Collectors.toList());

                List<String> badgeImages = userBadges.stream()
                                                     .map(userModalBadge -> userModalBadge.getBadge().getBadgeImage())
                                                     .collect(Collectors.toList());

                post.setBadgeNames(badgeNames);  // 배지 이름 리스트 설정
                post.setBadgeImages(badgeImages);  // 배지 이미지 리스트 설정
            }
        }

        return posts;
    }
}
