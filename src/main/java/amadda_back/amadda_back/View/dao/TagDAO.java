package amadda_back.amadda_back.View.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import amadda_back.amadda_back.View.domain.entity.TagEntity;

@Repository
public interface TagDAO extends JpaRepository<TagEntity, Integer> {

    @Query("SELECT t.tagName FROM TagEntity t WHERE t.post.postId = :postId")
    List<String> findTagNamesByPostId(@Param("postId") Integer postId);
}
