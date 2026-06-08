package br.ufms.facom.progWeb.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "baixa")
public class Baixa{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;

    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

}
