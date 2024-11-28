package amadda_back.amadda_back.weatherAPI.domain.entity;

import lombok.Data;

@Data
public class WeatherDTO {
    
    // private field
    private String cityName;
    private String date;
    private String time;
    private String icon;
    private String main;
    private String mainKo;
    private double temp;
    private double tempMin;
    private double tempMax;

    private int weatherId;          // weather.id 추가
    private String descriptionKo;   // 한글 설명 추가

    // 추가된 field
    private double rain;            // 강수량
    private double feelsLike;       // 체감 온도
    private double humidity;        // 습도
    private double windSpeed;       // 바람 속도
    private String windDirection;   // 바람의 방향(동서남북 등)

    // getter, setter
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
