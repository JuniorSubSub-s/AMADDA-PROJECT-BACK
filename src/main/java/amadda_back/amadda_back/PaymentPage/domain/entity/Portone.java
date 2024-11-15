package amadda_back.amadda_back.PaymentPage.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Portone {
    
    @JsonProperty("tx_id")
    private String txId;

    @JsonProperty("payment_id")
    private String paymentId;

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private String status;

    @JsonProperty("order_name")
    private String orderName;

    @JsonProperty("is_escrow")
    private Boolean isEscrow;

    @JsonProperty("amount")
    private Long amount;

    @JsonProperty("vat")
    private Long vat;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("buyer_email")
    private String buyerEmail;

    @JsonProperty("buyer_phone")
    private String buyerPhone;

    @JsonProperty("buyer_name")
    private String buyerName;

    @JsonProperty("custom_data")
    private String customData;

    @JsonProperty("pay_method")
    private String payMethod;

    @JsonProperty("pg_provider")
    private String pgProvider;

    @JsonProperty("paid_at")
    private String paidAt;

    @JsonProperty("failed_at")
    private String failedAt;

    @JsonProperty("fail_reason")
    private String failReason;

    @JsonProperty("approval_number")
    private String approvalNumber;

    @JsonProperty("card_name")
    private String cardName;

    @JsonProperty("card_quota")
    private Long cardQuota;

    @JsonProperty("pg_tx_id")
    private String pgTxId; // 추가된 필드
}
