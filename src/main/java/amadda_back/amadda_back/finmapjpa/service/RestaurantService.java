package amadda_back.amadda_back.finmapjpa.service;


import amadda_back.amadda_back.finmapjpa.dao.RestaurantDAO;
import amadda_back.amadda_back.finmapjpa.domain.entity.RestaurantEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantDAO restaurantDao;

    // 모든 레스토랑 정보 반환
    public List<RestaurantEntity> getAllRestaurants() {
        return restaurantDao.findAll();
    }

    // total_post 수에 따라 핀 색상을 반환하는 메서드
    public String getPinColorByPostCount(int totalPost) {
        if (totalPost < 50) return "black";
        if (totalPost < 100) return "red";
        if (totalPost < 200) return "orange";
        if (totalPost < 300) return "blue";
        if (totalPost < 400) return "yellow";
        return "purple";  // 400 이상은 purple
    }
}