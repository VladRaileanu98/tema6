package com.db.bookstore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @ManyToMany //o carte poate avea mai multi autori dar si invers
    private Set<Author> authorList; //in loc de lista avem 'Set', care permite elemente unice
    private int pageNo;
    private String publisher;
}
