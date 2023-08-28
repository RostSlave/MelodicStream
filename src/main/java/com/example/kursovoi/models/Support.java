package com.example.kursovoi.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="support")
public class Support implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String theme;

}
