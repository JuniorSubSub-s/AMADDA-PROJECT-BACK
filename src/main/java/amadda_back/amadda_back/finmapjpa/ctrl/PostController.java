package amadda_back.amadda_back.finmapjpa.ctrl;


import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import amadda_back.amadda_back.finmapjpa.domain.entity.PostResponseMapDTO;
import amadda_back.amadda_back.finmapjpa.domain.entity.RestaurantMapEntity;
import amadda_back.amadda_back.finmapjpa.service.PostMapService;
import amadda_back.amadda_back.finmapjpa.service.RestaurantMapService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final RestaurantMapService restaurantMapService;
    private final PostMapService PostMapService;

    // 모든 레스토랑을 가져오는 API
    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantMapEntity>> getAllRestaurants() {
        List<RestaurantMapEntity> result = restaurantMapService.getAllRestaurants();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 레스토랑의 total_post에 맞는 핀 색상을 가져오는 API
    @GetMapping("/restaurants/pins")
    public ResponseEntity<List<String>> getPinColors() {
        List<RestaurantMapEntity> restaurants = restaurantMapService.getAllRestaurants();
        List<String> pinColors = restaurants.stream()
                                            .map(restaurant -> restaurantMapService.getPinColorByPostCount(restaurant.getTotalPost()))
                                            .toList();
        return new ResponseEntity<>(pinColors, HttpStatus.OK);
    }

     // 특정 레스토랑의 포스트 정보를 가져오는 API
     @GetMapping("/restaurants/{restaurantId}/posts")
     public ResponseEntity<List<PostResponseMapDTO>> getPostsByRestaurantId(@PathVariable("restaurantId") Integer restaurantId) {
         List<PostResponseMapDTO> posts = PostMapService.getPostsByRestaurantId(restaurantId);
         return new ResponseEntity<>(posts, HttpStatus.OK);
     }
}
