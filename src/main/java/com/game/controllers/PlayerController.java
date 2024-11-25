package com.game.controllers;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import com.game.entities.Ball;
import com.game.entities.Player;
import com.game.enums.GameState;
import com.game.enums.PlayerState;
import com.game.settings.PlayerSettings;
import com.game.utils.Field;
import com.game.utils.RandomGenerator;

public final class PlayerController implements KeyListener {
    private final GameController _game;

    private int _horizontalSpeed = 0;
    private int _verticalSpeed = 0;

    private final PlayerSettings _settings;
    private final int WALKING_SPEED = 7;

    private final Player _player;
    private final Field _playerField;
    
    private static final Map<PlayerState, PlayerState> _movementToIdleStateMap = new HashMap<>();
    
    static {
        _movementToIdleStateMap.put(PlayerState.WALKING_FRONT, PlayerState.IDLE_FRONT);
        _movementToIdleStateMap.put(PlayerState.WALKING_BACK, PlayerState.IDLE_BACK);
        _movementToIdleStateMap.put(PlayerState.WALKING_RIGHT, PlayerState.IDLE_RIGHT);
        _movementToIdleStateMap.put(PlayerState.WALKING_LEFT, PlayerState.IDLE_LEFT);
    }
    
    private final Map<Integer, Movement> _movementMap;

    private static class Movement {
        final int verticalSpeed;
        final int horizontalSpeed;
        final PlayerState playerState;
    
        Movement(int verticalSpeed, int horizontalSpeed, PlayerState playerState) {
            this.verticalSpeed = verticalSpeed;
            this.horizontalSpeed = horizontalSpeed;
            this.playerState = playerState;
        }
    }

    public PlayerController(GameController game, Player player, PlayerSettings settings, Field playerField)
    {
        _game = game;
        _player = player;
        _settings = settings;
        _playerField = playerField;

        _movementMap = Map.of(
            _settings.MOVE_FORWARD, new Movement(-WALKING_SPEED, 0, PlayerState.WALKING_BACK),
            _settings.MOVE_BACKWARD, new Movement(WALKING_SPEED, 0, PlayerState.WALKING_FRONT),
            _settings.MOVE_RIGHT, new Movement(0, WALKING_SPEED, PlayerState.WALKING_RIGHT),
            _settings.MOVE_LEFT, new Movement(0, -WALKING_SPEED, PlayerState.WALKING_LEFT)
        );
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
        if (isServing())
            return;

        Movement movement = _movementMap.get(pressedKey);

        if (movement != null)
            updateMovement(movement);
    }

    private boolean isServing() {
        GameState currentGameState = _game.getGameState();
        return currentGameState == GameState.FIRST_PLAYER_SERVE || currentGameState == GameState.SECOND_PLAYER_SERVE;
    }

    private void updateMovement(Movement movement) {
        _verticalSpeed = movement.verticalSpeed == 0 ? _verticalSpeed : movement.verticalSpeed;
        _horizontalSpeed = movement.horizontalSpeed == 0 ? _horizontalSpeed : movement.horizontalSpeed;

        _player.setState(movement.playerState);
    }

    public void stopMoving(int pressedKey) {
        boolean isMovingHorizontally = pressedKey == _settings.MOVE_RIGHT || pressedKey == _settings.MOVE_LEFT;
        boolean isMovingVertically = pressedKey == _settings.MOVE_FORWARD || pressedKey == _settings.MOVE_BACKWARD;

        _horizontalSpeed = isMovingHorizontally ? 0 : _horizontalSpeed;
        _verticalSpeed = isMovingVertically ? 0 : _verticalSpeed;
    }

    public void serve(int pressedKey)
    {
        if (!canPerformServe(pressedKey))
            return;

        float direction = RandomGenerator.nextFloat(-0.5f, 0.5f);

        _game.getBall().serve(direction, _settings.SERVE_DIRECTION);
        _game.getBall().getBallSoundManager().playRandom();
        _game.getTurnManager().nextTurn();
        _game.setGameState(GameState.PLAYING);
    }

    private boolean canPerformServe(int pressedKey)
    {
        GameState currentGameState = _game.getGameState();

        return pressedKey == _settings.SERVE && 
            (currentGameState == GameState.FIRST_PLAYER_SERVE || currentGameState == GameState.SECOND_PLAYER_SERVE) &&
            isPlayerTurn();
    }

    public void pass(int pressedKey)
    {
        if (!canPerformPass(pressedKey))
            return;

        float direction = _player.position.x >= 400 ? RandomGenerator.nextFloat(-0.5f, 0f) : RandomGenerator.nextFloat(0f, 0.5f);

        _game.getBall().serve(direction, _settings.SERVE_DIRECTION);
        _game.getBall().getBallSoundManager().playRandom();
        _game.getTurnManager().nextTurn();
    }

    private boolean canPerformPass(int pressedKey)
    {
        return (pressedKey == _settings.SERVE || pressedKey == KeyEvent.VK_Z) && 
            _game.getGameState() == GameState.PLAYING &&
            isPlayerTurn() &&
            getPlayerDistanceFromBall(_player, _game.getBall()) <= 45;
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

    public void updateIdleState()
    {
        PlayerState state = _player.getState();
        PlayerState idleState = _movementToIdleStateMap.get(state);

        if (idleState != null)
            _player.setState(idleState);
    }

    public Field getPlayerField() {
        return _playerField;
    }

    public void update() {
        boolean isMoving = _horizontalSpeed != 0 || _verticalSpeed != 0;

        if (!isMoving) {
            updateIdleState();
            return;
        }

        if (isServing())
            return;

        _player.position.x += _horizontalSpeed; 
        _player.position.y +=_verticalSpeed;

        if (!_playerField.isWithinBounds(_player.position)) {
            _player.position.x -= _horizontalSpeed;
            _player.position.y -= _verticalSpeed;

            return;
        }
    }
}