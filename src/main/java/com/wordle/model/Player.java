package com.wordle.model;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This class represents the User entity.
 * and update timestamps as well as a boolean flag for deletion status.
 *
 * @author Dragan Jovanovic
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "players")
public class Player extends BaseEntity {
    /**
     * The games associated with the player.
     */
    @OneToMany(mappedBy = "player")
    private List<Game> games;

}
