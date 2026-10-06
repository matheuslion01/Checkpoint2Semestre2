package br.com.fiap.cp01_api02.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "olimpiadas")
public class Olimpiadas {
    @Id
    private Long id;

    private String ano;

    private String sede;
    @Column(nullable = true)
    private String maiorMedalhista;

    private String maiorVencedor;
}
