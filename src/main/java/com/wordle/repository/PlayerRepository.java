package com.wordle.repository;

import com.wordle.model.Game;
import com.wordle.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {
    /**
     * Find a player by their id.
     *
     * @param playerId the id of the player
     * @return an Optional containing the player if found, or empty if not
     */
    Optional<Player> findOneById(UUID playerId);
}
