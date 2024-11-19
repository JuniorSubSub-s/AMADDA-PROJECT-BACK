package amadda_back.amadda_back.finmapjpa.domain.entity;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "topic")
@Data
@DynamicUpdate
public class TopicMapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Long topicId; 

    @Column(name = "topic_name")
    private String topicName;

    @ManyToOne
    @JoinColumn(name = "post_id")  // 외래키 설정
    private PostResponseMapDTO post;  // PostEntity와 연결된 필드
}