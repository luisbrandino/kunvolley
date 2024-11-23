package com.game.controllers;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.game.entities.Ball;
import com.game.entities.Player;
import com.game.enums.GameState;
import com.game.enums.PlayerState;
import com.game.settings.PlayerSettings;
import com.game.utils.RandomGenerator;

public final class PlayerController implements KeyListener {
    private final GameController _game;

    private int _horizontalSpeed = 0;
    private int _verticalSpeed = 0;

    private final PlayerSettings _settings;
    private final int WALKING_SPEED = 7;

    private final Player _player;

    public PlayerController(GameController game, Player player, PlayerSettings settings)
    {
        _game = game;
        _player = player;
        _settings = settings;
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
        GameState currentGameState = _game.getGameState();

        boolean isServing = currentGameState == GameState.FIRST_PLAYER_SERVE || currentGameState == GameState.SECOND_PLAYER_SERVE;

        if (isServing)
            return;

        if (pressedKey == _settings.MOVE_FORWARD) {
            _verticalSpeed = -WALKING_SPEED;
            _player.setState(PlayerState.WALKING_BACK);
        } else if (pressedKey == _settings.MOVE_BACKWARD) {
            _verticalSpeed = WALKING_SPEED;
            _player.setState(PlayerState.WALKING_FRONT);
        } else if (pressedKey == _settings.MOVE_RIGHT) {
            _horizontalSpeed = WALKING_SPEED;
            _player.setState(PlayerState.WALKING_RIGHT);
        } else if (pressedKey == _settings.MOVE_LEFT) {
            _horizontalSpeed = -WALKING_SPEED;
            _player.setState(PlayerState.WALKING_LEFT);
        }
    }

    public void stopMoving(int pressedKey)
    {
        boolean isMovingHorizontally = pressedKey == _settings.MOVE_RIGHT || pressedKey == _settings.MOVE_LEFT;
        boolean isMovingVertically = pressedKey == _settings.MOVE_FORWARD || pressedKey == _settings.MOVE_BACKWARD;

        _horizontalSpeed = isMovingHorizontally ? 0 : _horizontalSpeed;
        _verticalSpeed = isMovingVertically ? 0 : _verticalSpeed;
    }

    public void serve(int pressedKey)
    {
        GameState currentGameState = _game.getGameState();

        boolean canServe = 
            pressedKey == _settings.SERVE && 
            (currentGameState == GameState.FIRST_PLAYER_SERVE || currentGameState == GameState.SECOND_PLAYER_SERVE) &&
            isPlayerTurn();

        if (!canServe)
            return;

        float direction = RandomGenerator.nextFloat(-0.5f, 0.5f);

        _game.getBall().serve(direction, _settings.SERVE_DIRECTION);
        _game.getBall().getBallSoundManager().playRandom();
        _game.getTurnManager().nextTurn();
        _game.setGameState(GameState.PLAYING);
    }

    public void pass(int pressedKey)
    {
        boolean canPass = 
            (pressedKey == _settings.SERVE || pressedKey == KeyEvent.VK_Z) && 
            _game.getGameState() == GameState.PLAYING &&
            isPlayerTurn() &&
            getPlayerDistanceFromBall(_player, _game.getBall()) <= 45;

        if (!canPass)
            return;

        float direction = _player.position.x >= 400 ? RandomGenerator.nextFloat(-0.5f, 0f) : RandomGenerator.nextFloat(0f, 0.5f);

        _game.getBall().serve(direction, _settings.SERVE_DIRECTION);
        _game.getBall().getBallSoundManager().playRandom();
        _game.getTurnManager().nextTurn();
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

    public boolean isPlayerTurn()
    {
        return _game.getTurnManager().getCurrentPlayer() == _player;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    private void updateIdleState()
    {
        PlayerState state = _player.getState();

        if (state == PlayerState.WALKING_FRONT)
            _player.setState(PlayerState.IDLE_FRONT);
        else if (state == PlayerState.WALKING_BACK)
            _player.setState(PlayerState.IDLE_BACK);
        else if (state == PlayerState.WALKING_RIGHT)
            _player.setState(PlayerState.IDLE_RIGHT);
        else if (state == PlayerState.WALKING_LEFT)
            _player.setState(PlayerState.IDLE_LEFT);
    }

    public void update() {
        boolean isMoving = _horizontalSpeed != 0 || _verticalSpeed != 0;

        if (!isMoving) {
            updateIdleState();
            return;
        }

        _player.position.x += _horizontalSpeed; 
        _player.position.y +=_verticalSpeed;
    }
}
