package amadda_back.amadda_back.View.ctrl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import amadda_back.amadda_back.View.domain.entity.PostEntity;
import amadda_back.amadda_back.View.domain.entity.PostResponseDTO;
import amadda_back.amadda_back.View.domain.entity.RestaurantEntity;
import amadda_back.amadda_back.View.domain.entity.WeatherResponseDTO;
import amadda_back.amadda_back.View.service.OCRService;
import amadda_back.amadda_back.View.service.PostService;
import amadda_back.amadda_back.View.service.WeatherService;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/amadda")
@RequiredArgsConstructor
public class Controller {

    private final PostService postService;
    private final WeatherService weatherService;
    private final OCRService ocrService;

    @GetMapping("/postsByWeather")
    public ResponseEntity<List<PostResponseDTO>> getPostsByWeather(@RequestParam(name = "weather") String weather) {
        try {
            System.out.println("params = " + weather);
            List<PostResponseDTO> posts = postService.getPostsByWeather(weather);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            System.err.println("Error fetching posts by weather: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/posts/user/{userId}")
    public ResponseEntity<List<PostResponseDTO>> getPostsByUserId(@PathVariable(name = "userId") Integer userId) {
        return ResponseEntity.ok(postService.getPostsByUserId(userId));
    }
    @GetMapping("/posts/{postId}")
    public ResponseEntity<List<PostResponseDTO>> getPostsByIds(@PathVariable(name = "postId") List<Integer> postId) {
        List<PostResponseDTO> posts = postService.getPostsByIds(postId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/mood")
    public ResponseEntity<List<PostResponseDTO>> getPostsByMood(@RequestParam(name = "moods") List<String> moods) {
        return ResponseEntity.ok(postService.getPostsByMood(moods));
    }

    // @GetMapping("/posts/privacy")
    // public ResponseEntity<List<PostResponseDTO>> getPostsByPrivacy(@RequestParam PostResponseDTO.Privacy privacy) {
    //     return ResponseEntity.ok(postService.getPostsByPrivacy(privacy));
    // }
    @GetMapping("/posts/pinColor")
    public ResponseEntity<List<PostResponseDTO>> getPostsByColor(@RequestParam(name = "color") String color) {
        return ResponseEntity.ok(postService.getPostsByColor(color));
    }

    @GetMapping("/posts/searchText")
    public ResponseEntity<List<PostResponseDTO>> searchPosts(@RequestParam(name = "searchText") String searchText) {
        return ResponseEntity.ok(postService.getPostsBySearchText(searchText));
    }

    @GetMapping("/posts/tags")
    public ResponseEntity<List<PostEntity>> getPostsByTags(@RequestParam(name = "tagNames") List<String> tagNames) {
        return ResponseEntity.ok(postService.getPostsByTags(tagNames));
    }

    @GetMapping("/posts/topics")
    public ResponseEntity<List<PostEntity>> getPostsByTopics(@RequestParam(name = "topicNames") List<String> topicNames) {
        return ResponseEntity.ok(postService.getPostsByTopics(topicNames));
    }

    @GetMapping("/posts/latest")
    public ResponseEntity<List<PostResponseDTO>> getLatestPosts() {
        return ResponseEntity.ok(postService.getLatestPosts());
    }

    @GetMapping("/posts/verification")
    public ResponseEntity<List<PostResponseDTO>> getPostsByReceiptVerification(@RequestParam(name = "receiptVerification") Boolean receiptVerification) {
        return ResponseEntity.ok(postService.findPostsByReceiptVerification(receiptVerification));
    }

    @GetMapping("/weatherByLocation")
    public ResponseEntity<WeatherResponseDTO> getWeatherByLocation(@RequestParam(name = "lat") double lat, @RequestParam(name = "lon") double lon) {
        try {
            return ResponseEntity.ok(weatherService.getWeatherByLocation(lat, lon));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/foodImage")
    public ResponseEntity<List<String>> getFirstFoodImage(@RequestParam(name = "postId") Integer postId) {
        return ResponseEntity.ok(postService.getFirstFoodImageUrl(postId));
    }

    @GetMapping("/foodImages")
    public ResponseEntity<Map<Integer, String>> getFoodImagesByPostIds(@RequestParam(name = "postIds") List<Integer> postIds) {
        return ResponseEntity.ok(postService.getFirstFoodImagesByPostIds(postIds));
    }

    @GetMapping("/posts/dailyViews")
    public ResponseEntity<List<PostResponseDTO>> getPostsSortedByViews() {
        return ResponseEntity.ok(postService.getPostsSortedByDailyViews());
    }

    @GetMapping("/tags")
    public ResponseEntity<List<String>> getTagsByPostId(@RequestParam(name = "postId") Integer postId) {
        return ResponseEntity.ok(postService.getTagsByPostId(postId));
    }

    //영수증 인증
    @PostMapping("/process")
    public ResponseEntity<Boolean> processOcr(@RequestParam("file") MultipartFile file,
            @RequestParam("storeName") String storeName,
            @RequestParam("storeAddress") String storeAddress) {

        try {
            boolean isStoreInfoFound = ocrService.checkStoreInfoInOcr(file, storeName, storeAddress);
            return ResponseEntity.ok(isStoreInfoFound);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(false);  // 오류 발생 시 false 반환
        }
    }

    //이미지 저장
    @PostMapping("/saveFoodImages")
    public List<String> uploadImages(
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam("postId") Integer postId) {

        // 서비스로 전달하여 이미지 저장 및 경로 반환
        return postService.saveImages(images, postId);
    }

    //레스토랑 저장
    @PostMapping("/saveRestaurant")
    public ResponseEntity<?> saveRestaurant(@RequestParam String restaurantName,
            @RequestParam String restaurantAddress,
            @RequestParam Double locationLatitude,
            @RequestParam Double locationLongitude) {
        try {
            RestaurantEntity restaurant = postService.addRestaurantIfNotExists(restaurantName, restaurantAddress, locationLatitude, locationLongitude);
            if (restaurant != null) {
                // 레스토랑 ID 반환
                return ResponseEntity.ok(restaurant.getRestaurantId());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("레스토랑 추가에 실패했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }

    //게시물 저장
    @PostMapping("/savePost")
    public ResponseEntity<?> savePost(@RequestBody Map<String, Object> postData) {
        try {
            // 프론트에서 전달한 데이터 파싱
            String title = (String) postData.get("post_title");
            String content = (String) postData.get("post_content");
            String privacy = (String) postData.get("privacy");
            String foodCategory = (String) postData.get("food_category");
            String mood = (String) postData.get("mood");
            String weather = (String) postData.get("weather");
            Boolean receiptVerification = (Boolean) postData.get("receipt_verification");
            Integer restaurantId = (Integer) postData.get("restaurant_id");
            Integer userId = (Integer) postData.get("user_id");
            Integer themeId = (Integer) postData.get("theme_id");

            // 포스트 저장
            PostEntity savedPost = postService.savePost(title, content, privacy, foodCategory, mood, weather, receiptVerification, restaurantId, userId, themeId);

            return ResponseEntity.ok(savedPost.getPostId()); // 저장된 게시물의 ID 반환

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시물 저장 중 오류가 발생했습니다.");
        }
    }
}
