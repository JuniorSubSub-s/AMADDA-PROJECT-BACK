package amadda_back.amadda_back.finmapjpa.dao;

import amadda_back.amadda_back.finmapjpa.domain.entity.PostResponseMapDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostUserBadgeDAO extends JpaRepository<PostResponseMapDTO, Integer> {
    PostResponseMapDTO findByPostId(Integer postId);
    List<String> findBadgeImagesByUser_UserId(Integer userId);
}

