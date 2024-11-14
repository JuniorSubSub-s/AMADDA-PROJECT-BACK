package amadda_back.amadda_back.View.ctrl;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import amadda_back.amadda_back.View.domain.entity.PostEntity;
import amadda_back.amadda_back.View.domain.entity.PostResponseDTO;
import amadda_back.amadda_back.View.domain.entity.WeatherResponseDTO;
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

    @GetMapping("/postsByWeather")
    public ResponseEntity<List<PostResponseDTO>> getPostsByWeather(@RequestParam String weather) {
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
    public ResponseEntity<List<PostResponseDTO>> getPostsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(postService.getPostsByUserId(userId));
    }
    @GetMapping("/posts/{postId}")
    public ResponseEntity<List<PostResponseDTO>> getPostsByIds(@PathVariable List<Integer> postId) {
        List<PostResponseDTO> posts = postService.getPostsByIds(postId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/mood")
    public ResponseEntity<List<PostResponseDTO>> getPostsByMood(@RequestParam List<String> moods) {
        return ResponseEntity.ok(postService.getPostsByMood(moods));
    }

    @GetMapping("/posts/privacy")
    public ResponseEntity<List<PostResponseDTO>> getPostsByPrivacy(@RequestParam PostResponseDTO.Privacy privacy) {
        return ResponseEntity.ok(postService.getPostsByPrivacy(privacy));
    }

    @GetMapping("/posts/pinColor")
    public ResponseEntity<List<PostResponseDTO>> getPostsByColor(@RequestParam String color) {
        return ResponseEntity.ok(postService.getPostsByColor(color));
    }

    @GetMapping("/posts/searchText")
    public ResponseEntity<List<PostResponseDTO>> searchPosts(@RequestParam String searchText) {
        return ResponseEntity.ok(postService.getPostsBySearchText(searchText));
    }

    @GetMapping("/posts/tags")
    public ResponseEntity<List<PostEntity>> getPostsByTags(@RequestParam List<String> tagNames) {
        return ResponseEntity.ok(postService.getPostsByTags(tagNames));
    }

    @GetMapping("/posts/topics")
    public ResponseEntity<List<PostEntity>> getPostsByTopics(@RequestParam List<String> topicNames) {
        return ResponseEntity.ok(postService.getPostsByTopics(topicNames));
    }

    @GetMapping("/posts/latest")
    public ResponseEntity<List<PostResponseDTO>> getLatestPosts() {
        return ResponseEntity.ok(postService.getLatestPosts());
    }

    @GetMapping("/posts/verification")
    public ResponseEntity<List<PostResponseDTO>> getPostsByReceiptVerification(@RequestParam Boolean receiptVerification) {
        return ResponseEntity.ok(postService.findPostsByReceiptVerification(receiptVerification));
    }

    @GetMapping("/weatherByLocation")
    public ResponseEntity<WeatherResponseDTO> getWeatherByLocation(@RequestParam double lat, @RequestParam double lon) {
        try {
            return ResponseEntity.ok(weatherService.getWeatherByLocation(lat, lon));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/foodImage")
    public ResponseEntity<List<String>> getFirstFoodImage(@RequestParam Integer postId) {
        return ResponseEntity.ok(postService.getFirstFoodImageUrl(postId));
    }

    @GetMapping("/foodImages")
    public ResponseEntity<Map<Integer, String>> getFoodImagesByPostIds(@RequestParam List<Integer> postIds) {
        return ResponseEntity.ok(postService.getFirstFoodImagesByPostIds(postIds));
    }

    @GetMapping("/posts/dailyViews")
    public ResponseEntity<List<PostResponseDTO>> getPostsSortedByViews() {
        return ResponseEntity.ok(postService.getPostsSortedByDailyViews());
    }

    @GetMapping("/tags")
    public ResponseEntity<List<String>> getTagsByPostId(@RequestParam Integer postId) {
        return ResponseEntity.ok(postService.getTagsByPostId(postId));
    }
}
