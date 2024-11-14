package amadda_back.amadda_back.View.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import amadda_back.amadda_back.View.dao.FoodImageDAO;
import amadda_back.amadda_back.View.dao.PostDAO;
import amadda_back.amadda_back.View.dao.TagDAO;
import amadda_back.amadda_back.View.domain.entity.FoodImageEntity;
import amadda_back.amadda_back.View.domain.entity.PostEntity;
import amadda_back.amadda_back.View.domain.entity.PostResponseDTO;
import amadda_back.amadda_back.View.domain.entity.RestaurantEntity;
import amadda_back.amadda_back.finmapjpa.dao.FinmapPostDAO;
import amadda_back.amadda_back.finmapjpa.dao.RestaurantDAO;

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

    public List<PostResponseDTO> getPostsByPrivacy(PostResponseDTO.Privacy privacy) {
        // Privacy 타입을 PostEntity.Privacy로 변환
        PostEntity.Privacy entityPrivacy = PostEntity.Privacy.valueOf(privacy.name());
        List<PostEntity> postEntities = postDAO.findPostsByPrivacy(entityPrivacy);
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
    // public PostEntity savePost(String title, String content, Privacy privacy, FoodCategory foodCategory, Mood mood,
    //                            String weather, Boolean receiptVerification, Integer restaurantId, Integer userId, Integer themeId) {
    //     PostEntity post = new PostEntity();
    //     post.setPostTitle(title);
    //     post.setPostContent(content);
    //     post.setPrivacy(privacy);
    //     post.setFoodCategory(foodCategory);
    //     post.setMood(mood);
    //     post.setWeather(weather);
    //     post.setReceiptVerification(receiptVerification);
    //     // 각 엔티티를 ID로 찾아서 매핑
    //     post.setRestaurant(restaurantRepository.findById(restaurantId).orElse(null));
    //     post.setUser(userRepository.findById(userId).orElse(null));
    //     post.setTheme(themeRepository.findById(themeId).orElse(null));
    //     return postRepository.save(post);
    // }
    // 이미지 저장
    public List<String> saveImages(List<MultipartFile> images, Integer postId) {
        List<String> imagePaths = new ArrayList<>();

        for (MultipartFile image : images) {
            String imageUrl = saveImageFile(image);
            if (imageUrl != null) {
                imagePaths.add(imageUrl);

                // FoodImageEntity 생성 및 저장
                FoodImageEntity foodImage = new FoodImageEntity();
                foodImage.setFoodImageUrl(imageUrl);
                PostEntity post = postDAO.findById(postId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid postId: " + postId));
                foodImage.setPost(post);
                foodImageDAO.save(foodImage);
            }
        }
        return imagePaths;
    }

    private String saveImageFile(MultipartFile image) {
        String directory = "src/main/resources/static/post/images/";
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        String filePath = directory + fileName;

        try {
            File file = new File(filePath);
            image.transferTo(file);
            return "/post/images/" + fileName; // 클라이언트에서 접근 가능한 URL 반환
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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

}
