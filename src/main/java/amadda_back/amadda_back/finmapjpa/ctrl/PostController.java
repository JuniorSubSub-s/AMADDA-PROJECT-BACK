package amadda_back.amadda_back.finmapjpa.ctrl;

import amadda_back.amadda_back.finmapjpa.domain.entity.PostResponseDTO;
import amadda_back.amadda_back.finmapjpa.domain.entity.RestaurantEntity;
import amadda_back.amadda_back.finmapjpa.service.PostService;
import amadda_back.amadda_back.finmapjpa.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    @Autowired
    private final RestaurantService restaurantService;
    @Autowired
    private final PostService postService;

    // 모든 레스토랑을 가져오는 API
    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantEntity>> getAllRestaurants() {
        List<RestaurantEntity> result = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 레스토랑의 total_post에 맞는 핀 색상을 가져오는 API
    @GetMapping("/restaurants/pins")
    public ResponseEntity<List<String>> getPinColors() {
        List<RestaurantEntity> restaurants = restaurantService.getAllRestaurants();
        List<String> pinColors = restaurants.stream()
                                            .map(restaurant -> restaurantService.getPinColorByPostCount(restaurant.getTotalPost()))
                                            .toList();
        return new ResponseEntity<>(pinColors, HttpStatus.OK);
    }

    // 특정 레스토랑의 포스트 정보를 가져오는 API
    @GetMapping("/restaurants/{restaurantId}/posts")
    public ResponseEntity<List<PostResponseDTO>> getPostsByRestaurantId(@PathVariable Integer restaurantId) {
        List<PostResponseDTO> posts = postService.getPostsByRestaurantId(restaurantId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}