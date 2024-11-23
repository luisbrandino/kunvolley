package com.game.managers;

import java.util.List;

import com.game.entities.Player;

public final class TurnManager {
    private final List<Player> _players;
    private int _currentPlayerIndex;

    public TurnManager(List<Player> players)
    {
        _players = players;
        _currentPlayerIndex = 0;
    }

    public Player getCurrentPlayer()
    {
        return _players.get(_currentPlayerIndex);
    }

    public void nextTurn() {
        _currentPlayerIndex = (_currentPlayerIndex + 1) % _players.size();
    }
}
