package edu.wat.tim.lab1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="pozycja_w_koszyku")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PozycjaWKoszyku {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "iloscProduktu")
    private Integer iloscProduktu;

    @ManyToOne
    @JoinColumn(name = "koszyk_id",nullable = false)
    @JsonIgnore
    private KoszykEntity koszykEntity;

    @ManyToOne
    @JoinColumn(name="artykul_id", nullable=false)
    @JsonIgnore
    private ArtykulEntity artykulEntity;

}

