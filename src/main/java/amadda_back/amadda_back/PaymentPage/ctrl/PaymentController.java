package amadda_back.amadda_back.PaymentPage.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import amadda_back.amadda_back.PaymentPage.service.IamportService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private IamportService iamportService;

    // 결제 검증 엔드포인트
    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(@RequestParam String impUid, @RequestParam int amount) {
        boolean isValid = iamportService.verifyPayment(impUid, amount);

        if (isValid) {
            return ResponseEntity.ok("결제가 성공적으로 검증되었습니다.");
        } else {
            return ResponseEntity.status(400).body("결제 검증에 실패했습니다.");
        }
    }
}
