package amadda_back.amadda_back.loginpage.ctrl.SignupContoller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/login")
public class KakaoLoginPageController {
    @Value("${kakao.client_id}")
    private String client_id ;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri ;

    @GetMapping("/page")
    public ResponseEntity<String> getKakaoLoginUrl(HttpServletRequest request) {
        String redirectUri = request.getParameter("redirectUri"); // 원하는 최종 리디렉션 URL
        if (redirectUri != null) {
            request.getSession().setAttribute("redirectUri", redirectUri); 
        }
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + client_id + "&redirect_uri=" + redirect_uri;
        return ResponseEntity.ok(location); // React로 인증 URL 반환
    }
}
