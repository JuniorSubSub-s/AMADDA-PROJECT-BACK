package amadda_back.amadda_back.finmapjpa.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import amadda_back.amadda_back.finmapjpa.dao.FoodImageMapDAO;
import amadda_back.amadda_back.finmapjpa.dao.PostMapDAO;
import amadda_back.amadda_back.finmapjpa.domain.entity.FoodMapImageEntity;
import amadda_back.amadda_back.finmapjpa.domain.entity.PostResponseMapDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostMapService {

    private final PostMapDAO postDao;
    private final FoodImageMapDAO foodImageDao;

    // 레스토랑 ID에 해당하는 포스트와 이미지 데이터를 가져오는 메서드
    public List<PostResponseMapDTO> getPostsByRestaurantId(Integer restaurantId) {
        List<PostResponseMapDTO> posts = postDao.findByRestaurant_RestaurantId(restaurantId);

        // 각 포스트에 대해 FoodImageEntity에서 이미지 URL들을 조회하고, 포스트에 추가
        for (PostResponseMapDTO post : posts) {
            List<FoodMapImageEntity> foodImages = foodImageDao.findByPost_PostId(Long.valueOf(post.getPostId()));
            if (foodImages != null && !foodImages.isEmpty()) {
                List<String> imageUrls = foodImages.stream()
                                                   .map(FoodMapImageEntity::getFoodImageUrl)
                                                   .toList();
                post.setFoodImageUrls(imageUrls);  // 여러 이미지 URL을 설정
            }
        }
        return posts;
    }
}