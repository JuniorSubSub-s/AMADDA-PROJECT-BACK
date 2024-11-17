package amadda_back.amadda_back.PaymentPage.ctrl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import amadda_back.amadda_back.PaymentPage.domain.entity.PaymentRequest;
import amadda_back.amadda_back.PaymentPage.service.IamportService;
import amadda_back.amadda_back.PaymentPage.service.PaymentsService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentsService paymentService;
    private final IamportService iamportService; // 포트원 서비스 추가

    public PaymentController(PaymentsService paymentService, IamportService iamportService) {
        this.paymentService = paymentService;
        this.iamportService = iamportService;
    }

    @PostMapping("/complete")
    public ResponseEntity<String> completePayment(@RequestBody Map<String, String> request) {
        try {
            String impUid = request.get("impUid");
            String merchantUid = request.get("merchantUid");

            // 1. 포트원 API를 통해 결제 내역 확인
            Map<String, Object> paymentData = iamportService.getPaymentData(impUid);

            // 2. 내부 주문 데이터와 결제 정보 비교 및 상태 업데이트
            paymentService.validateAndUpdatePayment(merchantUid, paymentData);

            return ResponseEntity.ok("결제가 성공적으로 처리되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("결제 검증 실패: " + e.getMessage());
        }
    }
}
