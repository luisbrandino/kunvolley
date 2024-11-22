package com.game.controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.game.entities.Player;

public final class PlayerController implements KeyListener {
    private int _horizontalSpeed = 0;
    private int _verticalSpeed = 0;

    private final int WALKING_SPEED = 7;

    private final Player _player;

    public PlayerController(Player player)
    {
        _player = player;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int pressedKey = e.getKeyCode();

        if (pressedKey == KeyEvent.VK_UP)
            _horizontalSpeed = -WALKING_SPEED;
        else if (pressedKey == KeyEvent.VK_DOWN)
            _horizontalSpeed = WALKING_SPEED;
        else if (pressedKey == KeyEvent.VK_RIGHT)
            _verticalSpeed = WALKING_SPEED;
        else if (pressedKey == KeyEvent.VK_LEFT)
            _verticalSpeed = -WALKING_SPEED;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int pressedKey = e.getKeyCode();

        boolean isMovingHorizontally = pressedKey == KeyEvent.VK_UP || pressedKey == KeyEvent.VK_DOWN;
        boolean isMovingVertically = pressedKey == KeyEvent.VK_RIGHT || pressedKey == KeyEvent.VK_LEFT;

        _horizontalSpeed = isMovingHorizontally ? 0 : _horizontalSpeed;
        _verticalSpeed = isMovingVertically ? 0 : _verticalSpeed;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    public void update() {
        _player.position.x +=_verticalSpeed;
        _player.position.y += _horizontalSpeed; 
    }
}
