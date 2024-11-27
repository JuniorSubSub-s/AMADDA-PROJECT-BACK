package amadda_back.amadda_back.View.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import amadda_back.amadda_back.View.dao.FoodImageDAO;
import amadda_back.amadda_back.View.dao.PostDAO;
import amadda_back.amadda_back.View.dao.TagDAO;
import amadda_back.amadda_back.View.dao.ThemeDAO;
import amadda_back.amadda_back.View.dao.TopicDAO;
import amadda_back.amadda_back.View.domain.entity.FoodImageEntity;
import amadda_back.amadda_back.View.domain.entity.PostEntity;
import amadda_back.amadda_back.View.domain.entity.PostResponseDTO;
import amadda_back.amadda_back.View.domain.entity.RestaurantEntity;
import amadda_back.amadda_back.View.domain.entity.TagEntity;
import amadda_back.amadda_back.View.domain.entity.ThemeEntity;
import amadda_back.amadda_back.View.domain.entity.TopicEntity;
import amadda_back.amadda_back.finmapjpa.dao.FinmapPostDAO;
import amadda_back.amadda_back.finmapjpa.dao.RestaurantDAO;
import amadda_back.amadda_back.mypage.dao.UserRepository;

@Service
public class PostService {

    @Autowired
    private PostDAO postDAO;

    @Autowired
    private FinmapPostDAO finmapPostDAO;

    @Autowired
    private FoodImageDAO foodImageDAO;

    @Autowired
    private TagDAO tagDAO;

    @Autowired
    private RestaurantDAO restaurantDAO;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThemeDAO themeDAO;

    @Autowired
    private TopicDAO topicDAO;

    // 레스토랑 ID에 해당하는 포스트를 가져오는 메서드
    public List<PostResponseDTO> getPostsByRestaurantId(Integer restaurantId) {
        List<PostEntity> postEntities = finmapPostDAO.findByRestaurant_RestaurantId(restaurantId);
        return postEntities.stream()
                .map(PostResponseDTO::new) // PostEntity -> PostResponseDTO 변환
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPostsByWeather(String weather) {
        List<PostEntity> postEntities = postDAO.findPostsByWeather(weather);
        return convertToPostResponseDTO(postEntities);
    }

    public List<PostResponseDTO> getPostsByMood(List<String> moods) {
        List<PostEntity> postEntities = postDAO.findByMoodIn(moods);
        return convertToPostResponseDTO(postEntities);
    }

    public List<PostResponseDTO> getPostsByIds(List<Integer> postIds) {
        List<PostEntity> postEntities = postDAO.findAllById(postIds);
        return convertToPostResponseDTO(postEntities);
    }

    public List<PostResponseDTO> getPostsByColor(String color) {
        if ("Total".equals(color)) {
            List<PostEntity> postEntities = postDAO.findAllByOrderByPostDateAsc();
            return convertToPostResponseDTO(postEntities);
        }

        if ("Black".equals(color)) {
            List<PostEntity> postEntities = postDAO.findPostsByLessThan50();
            return convertToPostResponseDTO(postEntities);
        }

        int minPosts = getMinPostsByColor(color);
        List<PostEntity> postEntities = postDAO.findPostsByColor(minPosts);
        return convertToPostResponseDTO(postEntities);
    }

    private int getMinPostsByColor(String color) {
        switch (color) {
            case "Purple":
                return 400;
            case "Yellow":
                return 300;
            case "Blue":
                return 200;
            case "Orange":
                return 100;
            case "Red":
                return 50;
            default:
                return 0;
        }
    }

    public List<PostResponseDTO> getPostsBySearchText(String searchText) {
        List<PostEntity> postsByRestaurant = postDAO.findByRestaurantName(searchText);
        List<PostEntity> postsByTag = postDAO.findByTagTagName(searchText);

        List<PostEntity> combinedPosts = new ArrayList<>();
        combinedPosts.addAll(postsByRestaurant);
        combinedPosts.addAll(postsByTag);

        return convertToPostResponseDTO(combinedPosts);
    }

    public List<PostEntity> getPostsByTags(List<String> tagNames) {
        return postDAO.findPostsByTagNames(tagNames);
    }

    public List<PostEntity> getPostsByTopics(List<String> topicNames) {
        return postDAO.findPostsByTopicNames(topicNames);
    }

    public List<PostResponseDTO> getLatestPosts() {
        List<PostEntity> postEntities = postDAO.findAllByOrderByPostDateAsc();
        return convertToPostResponseDTO(postEntities);
    }

    public List<PostResponseDTO> findPostsByReceiptVerification(Boolean receiptVerification) {
        List<PostEntity> postEntities = postDAO.findByReceiptVerification(receiptVerification);
        return convertToPostResponseDTO(postEntities);
    }

    public List<String> getFirstFoodImageUrl(Integer postId) {
        return foodImageDAO.findFirstFoodImageUrlByPostId(postId);
    }

    public Map<Integer, String> getFirstFoodImagesByPostIds(List<Integer> postIds) {
        List<String> imageUrls = foodImageDAO.findFoodImagesByPostIds(postIds);
        Map<Integer, String> postImageMap = new HashMap<>();

        for (int i = 0; i < postIds.size(); i++) {
            postImageMap.put(postIds.get(i), imageUrls.size() > i ? imageUrls.get(i) : "Image not found");
        }

        return postImageMap;
    }

    public List<PostResponseDTO> getPostsSortedByDailyViews() {
        List<PostEntity> postEntities = postDAO.findAllOrderByDailyViewsDesc();
        return convertToPostResponseDTO(postEntities);
    }

    // Entity -> DTO 변환
    private List<PostResponseDTO> convertToPostResponseDTO(List<PostEntity> postEntities) {
        List<PostResponseDTO> postResponseDTOs = new ArrayList<>();
        for (PostEntity entity : postEntities) {
            postResponseDTOs.add(new PostResponseDTO(entity)); // PostEntity를 PostResponseDTO로 변환
        }
        return postResponseDTOs;
    }

    public List<String> getTagsByPostId(Integer postId) {
        return tagDAO.findTagNamesByPostId(postId);
    }

    // 레스토랑 중복 검사 후 추가 또는 기존 레스토랑 반환
    public RestaurantEntity addRestaurantIfNotExists(String restaurantName, String restaurantAddress, Double locationLatitude, Double locationLongitude) {
        // 중복 레스토랑 확인
        Optional<RestaurantEntity> existingRestaurant = restaurantDAO.findByRestaurantNameAndRestaurantAddress(restaurantName, restaurantAddress);

        if (existingRestaurant.isPresent()) {
            // 중복이 있을 경우 기존 레스토랑 반환
            return existingRestaurant.get();
        } else {
            // 중복이 없을 경우 새로운 레스토랑 저장 후 반환
            RestaurantEntity newRestaurant = new RestaurantEntity(restaurantName, restaurantAddress, locationLatitude, locationLongitude);
            return restaurantDAO.save(newRestaurant);
        }
    }

    // 게시물 저장
    public PostEntity savePost(String title, String content, String privacy, String foodCategory, String mood,
            String weather, Boolean receiptVerification, Integer restaurantId, Integer userId, Integer themeId) {
        PostEntity post = new PostEntity();
        post.setPostTitle(title);
        post.setPostContent(content);
        post.setPrivacy(privacy);
        post.setFoodCategory(foodCategory);
        post.setMood(mood);
        post.setWeather(weather);
        post.setReceiptVerification(receiptVerification);
        // 각 엔티티를 ID로 찾아서 매핑
        post.setRestaurant(restaurantDAO.findById(restaurantId).orElse(null));
        post.setUser(userRepository.findById(userId).orElse(null));
        post.setTheme(themeDAO.findById(themeId).orElse(null));
        return postDAO.save(post);
    }

    // 주제 저장
    public void saveTopics(List<String> topics, Integer postId) {
        // PostEntity 조회
        PostEntity post = postDAO.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid postId: " + postId));

        // TopicEntity 저장
        for (String topicName : topics) {
            TopicEntity topic = new TopicEntity();
            topic.setTopicName(topicName);
            topic.setPost(post); // PostEntity와 연결
            topicDAO.save(topic);
        }
    }

    // 태그 저장
    public void savetags(List<String> tags, Integer postId) {
        // PostEntity 조회
        PostEntity post = postDAO.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid postId: " + postId));

        // TopicEntity 저장
        for (String tagName : tags) {
            TagEntity tag = new TagEntity();
            tag.setTagName(tagName);
            tag.setPost(post); // PostEntity와 연결
            tagDAO.save(tag);
        }
    }

    // 사용자 ID로 포스트를 가져오는 메서드
    public List<PostResponseDTO> getPostsByUserId(Integer userId) {
        // userId로 Post 조회
        List<PostEntity> posts = postDAO.findByUser_UserId(userId);
        return posts.stream()
                .map(PostResponseDTO::new) // PostEntity -> PostResponseDTO 변환
                .collect(Collectors.toList());
    }

    public boolean deletePost(Integer postId) {
        // 게시물이 존재하는지 확인
        if (postDAO.existsById(postId)) {
            postDAO.deleteById(postId); // 삭제
            return true;
        }
        return false; // 게시물이 존재하지 않으면 false 반환
    }

    // 이미지 저장
    public void saveImage(List<String> imageUrls, Integer postId, Integer restaurantId) {

        for (String imageUrl : imageUrls) {
            FoodImageEntity foodImage = new FoodImageEntity();
            foodImage.setFoodImageUrl(imageUrl);
            foodImage.setRestaurant(restaurantDAO.findById(restaurantId).orElse(null));
            foodImage.setPost(postDAO.findById(postId).orElse(null));
            foodImageDAO.save(foodImage);
        }

    }

    // 테마 불러오기
    public List<ThemeEntity> getAllThemes() {
        return themeDAO.findAll();
    }

}
