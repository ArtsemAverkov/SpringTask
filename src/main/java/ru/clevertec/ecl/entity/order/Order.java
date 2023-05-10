package ru.clevertec.ecl.entity.order;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;
import ru.clevertec.ecl.entity.user.User;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double cost;

    private LocalDateTime purchaseTime;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "gift_certificates_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GiftCertificates giftCertificates;
    public Order(Double cost, LocalDateTime purchaseTime, User user, GiftCertificates giftCertificates) {
        this.cost = cost;
        this.purchaseTime = purchaseTime;
        this.user = user;
        this.giftCertificates = giftCertificates;
    }
}
