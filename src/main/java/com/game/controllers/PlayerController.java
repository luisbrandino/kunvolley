package com.game.controllers;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.game.entities.Ball;
import com.game.entities.Player;
import com.game.enums.GameState;
import com.game.utils.RandomGenerator;

public final class PlayerController implements KeyListener {
    private final GameController _game;

    private int _horizontalSpeed = 0;
    private int _verticalSpeed = 0;

    private final int WALKING_SPEED = 7;

    private final Player _player;

    public PlayerController(GameController game, Player player)
    {
        _game = game;
        _player = player;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int pressedKey = e.getKeyCode();

        move(pressedKey);

        pass(pressedKey);
        
        serve(pressedKey);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        stopMoving(e.getKeyCode());        
    }

    public void move(int pressedKey)
    {
        boolean isServing = _game.currentGameState == GameState.PLAYER_SERVE || _game.currentGameState == GameState.AI_SERVE;

        if (isServing)
            return;

        if (pressedKey == KeyEvent.VK_UP)
            _verticalSpeed = -WALKING_SPEED;
        else if (pressedKey == KeyEvent.VK_DOWN)
            _verticalSpeed = WALKING_SPEED;
        else if (pressedKey == KeyEvent.VK_RIGHT)
            _horizontalSpeed = WALKING_SPEED;
        else if (pressedKey == KeyEvent.VK_LEFT)
            _horizontalSpeed = -WALKING_SPEED;
    }

    public void stopMoving(int pressedKey)
    {
        boolean isMovingHorizontally = pressedKey == KeyEvent.VK_RIGHT || pressedKey == KeyEvent.VK_LEFT;
        boolean isMovingVertically = pressedKey == KeyEvent.VK_UP || pressedKey == KeyEvent.VK_DOWN;

        _horizontalSpeed = isMovingHorizontally ? 0 : _horizontalSpeed;
        _verticalSpeed = isMovingVertically ? 0 : _verticalSpeed;
    }

    public void serve(int pressedKey)
    {
        boolean canServe = pressedKey == KeyEvent.VK_SPACE && _game.currentGameState == GameState.PLAYER_SERVE;

        if (!canServe)
            return;

        float direction = RandomGenerator.nextFloat(-0.5f, 0.5f);

        _game.getBall().serve(direction, -1.0f);
        _game.setGameState(GameState.PLAYING);
    }

    public void pass(int pressedKey)
    {
        boolean canPass = 
            (pressedKey == KeyEvent.VK_SPACE || pressedKey == KeyEvent.VK_Z) && 
            _game.currentGameState == GameState.PLAYING &&
            getPlayerDistanceFromBall(_game.getPlayer(), _game.getBall()) <= 45;

        if (!canPass)
            return;

        float direction = _game.getPlayer().position.x >= 400 ? RandomGenerator.nextFloat(-0.5f, 0f) : RandomGenerator.nextFloat(0f, 0.5f);

        _game.getBall().serve(direction, pressedKey == KeyEvent.VK_SPACE ? -1.0f : 1);
    }

    public double getPlayerDistanceFromBall(Player player, Ball ball)
    {
        Rectangle playerRect = new Rectangle((int)player.position.x, (int)player.position.y, (int)player.size.x, (int)player.size.y);
        Rectangle ballRect = new Rectangle((int)ball.position.x, (int)ball.position.y, (int)ball.size.x, (int)ball.size.y);

        double playerCenterX = playerRect.getCenterX();
        double playerCenterY = playerRect.getCenterY();
        double ballCenterX = ballRect.getCenterX();
        double ballCenterY = ballRect.getCenterY();

        return Math.sqrt(Math.pow(ballCenterX - playerCenterX, 2) + Math.pow(ballCenterY - playerCenterY, 2));
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    public void update() {
        _player.position.x += _horizontalSpeed; 
        _player.position.y +=_verticalSpeed;
    }
}
