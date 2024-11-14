package amadda_back.amadda_back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 모든 경로에 대해 CORS 허용
                .allowedOrigins("http://localhost:3000")  // 리액트가 실행되는 포트를 정확히 명시 (예: localhost:3000)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 허용할 HTTP 메서드
                .allowedHeaders("*")  // 모든 헤더 허용
                .allowCredentials(true);  // 인증 정보 허용 여부
    }

     @Override
     public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /assets/** 경로 요청이 C:/finalproject/AMADDA-PROJECT-BACK/src/main/resources/static/img/profile/로 매핑되도록 설정
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("file:///C:/finalproject/AMADDA-PROJECT-BACK/src/main/resources/static/img/profile/");
    }


}
