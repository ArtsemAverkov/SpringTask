package ru.clevertec.ecl.entity;

import lombok.*;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @OneToMany(mappedBy ="tag", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GiftCertificates> giftCertificatesList = new ArrayList<>();

    public Tag(String name) {
        this.name = name;
    }
}
