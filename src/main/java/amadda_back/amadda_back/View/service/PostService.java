package amadda_back.amadda_back.View.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import amadda_back.amadda_back.View.dao.FoodImageDAO;
import amadda_back.amadda_back.View.dao.PostDAO;
import amadda_back.amadda_back.View.dao.TagDAO;
import amadda_back.amadda_back.View.domain.entity.PostEntity;
import amadda_back.amadda_back.View.domain.entity.PostResponseDTO;
import amadda_back.amadda_back.finmapjpa.dao.FinmapPostDAO;

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

}
