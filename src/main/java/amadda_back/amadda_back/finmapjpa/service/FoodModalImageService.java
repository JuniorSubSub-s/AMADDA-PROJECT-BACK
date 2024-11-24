package amadda_back.amadda_back.finmapjpa.service;

import amadda_back.amadda_back.finmapjpa.dao.FoodModalImageDAO;
import amadda_back.amadda_back.finmapjpa.domain.entity.FoodMapImageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodModalImageService {

    @Autowired
    private FoodModalImageDAO foodModalImageDAO;

    // 특정 postId에 대한 음식 이미지 URL들을 배열로 가져오는 메서드
    public List<String> getFoodImageUrls(Long postId) {
        List<FoodMapImageEntity> foodMapImages = foodModalImageDAO.findByPostPostId(postId);

        // foodImageUrl을 배열 형태로 가공
        return foodMapImages.stream()
                            .map(FoodMapImageEntity::getFoodImageUrl)
                            .collect(Collectors.toList());
    }
}
