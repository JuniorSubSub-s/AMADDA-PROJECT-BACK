package amadda_back.amadda_back.View.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponseDTO {

    private double temp;
    private double feelsLike;
    private double tempMin;
    private double tempMax;
    private String main;
    private String description;
    private String detailedDescription;
    private String icon;
}
