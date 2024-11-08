package amadda_back.amadda_back.View.domain.entity;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "topic")
@Data
@Entity
@DynamicUpdate
public class TopicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Integer topicId;

    @Column(name = "topic_name", nullable = false, length = 50)
    private String topicName;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;
}
