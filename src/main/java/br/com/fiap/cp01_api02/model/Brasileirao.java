package br.com.fiap.cp01_api02.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "brasileiroes")
public class Brasileirao {
    @Id
    private Long id;

    private String campeao;

    private String artilheiro;
    @Column(nullable = true)
    private String revelacao;

    private String goleada;
}