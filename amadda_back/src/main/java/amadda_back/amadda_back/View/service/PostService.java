package amadda_back.amadda_back.View.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import amadda_back.amadda_back.View.dao.FoodImageDAO;
import amadda_back.amadda_back.View.dao.PostDAO;
import amadda_back.amadda_back.View.domain.entity.PostEntity;
import amadda_back.amadda_back.View.domain.entity.PostResponseDTO;

@Service
public class PostService {

    @Autowired
    private PostDAO postDAO;

    public List<PostResponseDTO> getPostsByWeather(String weather) {
        return postDAO.findPostsByWeather(weather);
    }

    public List<PostResponseDTO> getPostsByMood(List<String> moods) {
        return postDAO.findByMoodIn(moods);
    }

    public List<PostResponseDTO> getPostsByIds(List<Integer> postIds) {
        return postDAO.findAllById(postIds);
    }

    public List<PostResponseDTO> getPostsByPrivacy(PostResponseDTO.Privacy privacy) {
        return postDAO.findPostsByPrivacy(privacy);
    }

    public List<PostResponseDTO> getPostsByColor(String color) {
        // 'Total'일 경우 모든 게시물을 반환
        if ("Total".equals(color)) {
            return postDAO.findAllByOrderByPostDateAsc(); // 모든 게시물을 반환하는 DAO 메소드 필요
        }

        // 'Black'일 경우 totalPost가 50 미만인 게시물 검색
        if ("Black".equals(color)) {
            return postDAO.findPostsByLessThan50(); // totalPost가 50 미만인 게시물 반환하는 DAO 메소드 필요
        }

        // 그 외의 색상에 대해서는 최소 게시물 수를 기준으로 검색
        int minPosts = getMinPostsByColor(color);
        return postDAO.findPostsByColor(minPosts);
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
            default:  // "Black"
                return 0; // Black일 경우는 별도로 처리
        }
    }

    public List<PostResponseDTO> getPostsBySearchText(String searchText) {
        List<PostResponseDTO> postsByRestaurant = postDAO.findByRestaurantName(searchText);
        List<PostResponseDTO> postsByTag = postDAO.findByTagTagName(searchText);

        Set<PostResponseDTO> combinedPosts = new HashSet<>();
        combinedPosts.addAll(postsByRestaurant);
        combinedPosts.addAll(postsByTag);

        return new ArrayList<>(combinedPosts);
    }

    public List<PostEntity> getPostsByTags(List<String> tagNames) {
        return postDAO.findPostsByTagNames(tagNames);
    }

    public List<PostEntity> getPostsByTopics(List<String> topicNames) {
        return postDAO.findPostsByTopicNames(topicNames);
    }

    public List<PostResponseDTO> getLatestPosts() {
        return postDAO.findAllByOrderByPostDateAsc();
    }

    public List<PostResponseDTO> findPostsByReceiptVerification(Boolean receiptVerification) {
        return postDAO.findByReceiptVerification(receiptVerification);
    }

    @Autowired
    private FoodImageDAO foodImageDAO;

    public List<String> getFirstFoodImageUrl(Integer postId) {
        return foodImageDAO.findFirstFoodImageUrlByPostId(postId);
    }

    public Map<Integer, String> getFirstFoodImagesByPostIds(List<Integer> postIds) {
        List<String> imageUrls = foodImageDAO.findFoodImagesByPostIds(postIds);

        Map<Integer, String> postImageMap = new HashMap<>();
        for (Integer postId : postIds) {
            String imageUrl = imageUrls.stream()
                    .filter(url -> url != null) // 각 postId에 대한 첫 번째 이미지 URL만 추가
                    .findFirst().orElse("Image not found");
            postImageMap.put(postId, imageUrl);
        }

        return postImageMap;
    }

    public List<PostResponseDTO> getPostsSortedByDailyViews() {
        return postDAO.findAllOrderByDailyViewsDesc();
    }

}
