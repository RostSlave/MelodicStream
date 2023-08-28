package com.example.kursovoi.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="contracts")

public class Contract implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "contract",
            cascade = CascadeType.ALL)
    private List<User> user;

    private int price;

    private String royalty;

    private int days;




}
