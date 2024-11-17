package amadda_back.amadda_back.PaymentPage.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import amadda_back.amadda_back.PaymentPage.dao.OrderRepository;
import amadda_back.amadda_back.PaymentPage.dao.PaymentRepository;
import amadda_back.amadda_back.PaymentPage.domain.entity.Order;

@Service
public class PaymentsService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentsService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void validateAndUpdatePayment(String merchantUid, Map<String, Object> paymentData) {
        BigDecimal paidAmount = new BigDecimal((Integer) paymentData.get("amount"));
        String status = (String) paymentData.get("status");

        // 주문 데이터 확인
        Order order = orderRepository.findById(Integer.parseInt(merchantUid))
                .orElseThrow(() -> new RuntimeException("주문 데이터 없음"));

        if (!order.getAmount().equals(paidAmount)) {
            throw new RuntimeException("결제 금액 불일치");
        }

        switch (status) {
            case "paid":
                orderRepository.updateOrderStatus(order.getId(), "completed"); // Integer로 직접 호출
                break;
            case "ready":
                // 가상계좌 발급 처리
                break;
            default:
                throw new RuntimeException("결제 상태 처리 불가");
        }
    }
}
