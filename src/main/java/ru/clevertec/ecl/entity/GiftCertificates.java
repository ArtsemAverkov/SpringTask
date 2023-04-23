package ru.clevertec.ecl.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "tag")
@EqualsAndHashCode(exclude = "tag")
public class GiftCertificates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String duration;
    private LocalDate create_date;
    private LocalDate last_update_date;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Tag tag;


}
