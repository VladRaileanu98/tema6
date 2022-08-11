package com.db.bookstore.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //contor functie de fiecare clasa in parte
    private int id;
    @Column(unique = true) //in loc de uniqueConstreaint
    private String username;
    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    private String role; //user sau admin

}
