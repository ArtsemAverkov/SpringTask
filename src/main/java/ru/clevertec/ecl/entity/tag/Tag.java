package ru.clevertec.ecl.entity.tag;

import lombok.*;
import ru.clevertec.ecl.entity.giftCertificates.GiftCertificates;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
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
    @Builder.Default
    private List<GiftCertificates> giftCertificatesList = new ArrayList<>();

    public Tag(String name) {
        this.name = name;
    }
}
