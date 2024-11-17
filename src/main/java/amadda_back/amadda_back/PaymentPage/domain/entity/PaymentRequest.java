package amadda_back.amadda_back.PaymentPage.domain.entity;

public class PaymentRequest {
    private String impUid;
    private int amount;
    private Long paymentId;
    private Long orderId;

    // Getters and Setters
    public String getImpUid() {
        return impUid;
    }

    public void setImpUid(String impUid) {
        this.impUid = impUid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Long getPaymentId(){
        return paymentId;
    }

    public void setPaymentId(Long paymentId){
        this.paymentId = paymentId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
