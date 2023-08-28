package com.example.kursovoi.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="account", uniqueConstraints={@UniqueConstraint(columnNames={"email"})})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String email;
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dateBegin")
    private LocalDate dateBegin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dateEnd")
    private LocalDate dateEnd;

    private int sum;

    private String royalty;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "roles_users_mus",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles_;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_contract", referencedColumnName = "id")
    private Contract contract;

//    @OneToMany(fetch = FetchType.EAGER,
//            mappedBy = "account",
//            cascade = CascadeType.ALL)
//    private List<Song> songs;

    public User(String name, String surname, String email, String password, Collection<Role> roles) {
        super();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.roles_ = roles;
    }
    public User(String name, String surname, String email, String password, Collection<Role> roles, Contract contract) {
        super();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.roles_ = roles;
        this.contract=contract;
    }
    public User(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public Collection<Role> getRoles() { return roles_;}

    public Contract getContract(){return contract;}

}
