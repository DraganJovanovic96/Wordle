package com.wordle.repository;

import com.wordle.enumeration.GameStatus;
import com.wordle.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * The GameRepository interface extends JpaRepository to inherit JPA-based CRUD methods and custom
 * methods for accessing and modifying Game entities.
 *
 * @author Dragan Jovanovic
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {
    /**
     * Find a game by player id and game status.
     *
     * @param playerId   the id of the player
     * @param gameStatus the current status of the game
     * @return an Optional containing the game if found, or empty if not
     */
    Optional<Game> findOneByPlayerIdAndGameStatus(UUID playerId, GameStatus gameStatus);

    /**
     * Count the number of games by player ID and game status.
     *
     * @param playerId   the ID of the player
     * @param gameStatus the status of the game (WIN or LOSE)
     * @return the number of games matching the criteria
     */
    long countByPlayerIdAndGameStatus(UUID playerId, GameStatus gameStatus);
}
