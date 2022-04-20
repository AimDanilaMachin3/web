package com.example.web.conf.ent;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "carss")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    private int price;
    private String description;
    @Column(name="imagelink")
    private String imageLink;

}
