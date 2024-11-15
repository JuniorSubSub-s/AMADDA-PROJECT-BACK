package amadda_back.amadda_back.PaymentPage.domain.entity;

public class PaymentRequest {
    private String impUid;
    private int amount;

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
}
