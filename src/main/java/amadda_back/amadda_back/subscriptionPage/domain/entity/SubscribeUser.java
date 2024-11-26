package amadda_back.amadda_back.subscriptionPage.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class SubscribeUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "subscription", nullable = false)
    private String subscription = "N";

    @Column(name = "subscription_date")
    private LocalDateTime subscriptionDate;

    @Column(name = "user_currency_balance", nullable = false)
    private Integer currencyBalance = 0;
}
