package amadda_back.amadda_back.subscriptionPage.ctrl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import amadda_back.amadda_back.subscriptionPage.service.UserSubscribeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class SubscriptionController {
    @Autowired
    private UserSubscribeService userService;

    // 구독 확인
    @GetMapping("/subscription/status")
    public ResponseEntity<Map<String, Boolean>> checkSubscription(@RequestParam(name = "userId") Integer userId) {
        boolean isSubscribed = userService.checkSubscription(userId);
        return ResponseEntity.ok(Map.of("isSubscribed", isSubscribed));
    }

    // 구독 요청
    @PostMapping("/subscription")
    public ResponseEntity<String> subscription(@RequestParam(name = "userId") Integer userId) {
        try {
            String result = userService.subscribe(userId);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("서버 내부 오류: " + e.getMessage());
        }
    }

    // 구독 취소
    @PostMapping("/unsubscription")
    public ResponseEntity<String> unsubscription(@RequestParam(name = "userId") Integer userId) {
        try {
            String result = userService.unsubscribe(userId);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("서버 내부 오류: " + e.getMessage());
        }
    }
}
