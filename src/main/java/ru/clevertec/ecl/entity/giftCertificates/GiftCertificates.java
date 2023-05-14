package ru.clevertec.ecl.entity.giftCertificates;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import ru.clevertec.ecl.entity.tag.Tag;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "tag")
@EqualsAndHashCode(exclude = "tag")
@OptimisticLocking(type = OptimisticLockType.VERSION)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "tag"})
public class GiftCertificates {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Long duration;
    private String create_date;
    private String last_update_date;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Tag tag;

    public GiftCertificates(Long id, String name, String description, Double price,
                            Long duration, String create_date, String last_update_date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.create_date = create_date;
        this.last_update_date = last_update_date;
    }
}
