package com.example.jokes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "jokes")
@Data
@EqualsAndHashCode(callSuper=false)
public class Joke extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "setup", nullable = false)
    private String setup;

    @Column(name = "punchline", nullable = false)
    private String punchline;

    public Joke(String setup, String punchline) {
        this.setup = setup;
        this.punchline = punchline;
    }

    @Override
    public String toString() {
        return "Joke{" +
                "id=" + id +
                ", setup='" + setup + '\'' +
                ", punchline='" + punchline + '\'' +
                '}';
    }
}