package com.wordle.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "secondary_words")
public class SecondaryWord extends BaseEntity {

    @Column
    private String stringOfWord;

    @OneToMany(mappedBy = "guessedWord")
    private List<GuessedWord> guessedWords;
}
