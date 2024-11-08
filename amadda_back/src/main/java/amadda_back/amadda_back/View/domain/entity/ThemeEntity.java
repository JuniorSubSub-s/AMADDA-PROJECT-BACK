package amadda_back.amadda_back.View.domain.entity;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "theme")
@Data
@Entity
@DynamicUpdate
public class ThemeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theme_id")
    private Integer themeId;

    @Column(name = "theme_name")
    private String themeName;

    @Column(name = "theme_description")
    private String themeDescription;

    @Column(name = "theme_price")
    private Integer themePrice;

    @Column(name = "theme_image")
    private String themeImage;
}
