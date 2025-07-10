package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Rating")
@Data
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Integer id;
    private String moodysRating;
    private String sandPRating;
    private String fitchRating;
    private Integer orderNumber;
}
