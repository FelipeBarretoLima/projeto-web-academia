package br.uel.RegistroTreinos.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "treino")
@Setter
@Getter

public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id",nullable = false)
    private Usuario usuario;

    private LocalDate date;
    private String nomeTreino;

    public Treino(){
    }

    public Treino(Usuario usuario, LocalDate date, String nomeTreino){
        this.usuario = usuario;
        this.date = date;
        this.nomeTreino = nomeTreino;
    }
}
