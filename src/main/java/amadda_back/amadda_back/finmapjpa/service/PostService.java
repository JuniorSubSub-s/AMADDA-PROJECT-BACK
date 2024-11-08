package amadda_back.amadda_back.finmapjpa.service;


import amadda_back.amadda_back.finmapjpa.dao.PostDAO;
import amadda_back.amadda_back.finmapjpa.domain.entity.PostResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostDAO postDao;

    // 레스토랑 ID에 해당하는 포스트를 가져오는 메서드
    public List<PostResponseDTO> getPostsByRestaurantId(Integer restaurantId) {
        return postDao.findByRestaurant_RestaurantId(restaurantId);  // 레스토랑 ID에 해당하는 포스트 조회
    }
}