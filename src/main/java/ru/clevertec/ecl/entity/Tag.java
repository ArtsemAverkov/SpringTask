package ru.clevertec.ecl.entity;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    @OneToMany(mappedBy ="tag", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GiftCertificates> giftCertificatesList = new ArrayList<>();

    public Tag(String name) {
        this.name = name;
    }
}
