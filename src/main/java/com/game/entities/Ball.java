package com.game.entities;

import com.game.contracts.IEntity;
import com.game.utils.Vector2;

public class Ball implements IEntity { 
    private final int WIDTH = 35;
    private final int HEIGHT = 35;

    public Vector2 position;
    public Vector2 size = new Vector2(WIDTH, HEIGHT);

    private float _horizontalSpeed = 0;
    private float _verticalSpeed = 0;

    private final float BALL_SPEED = 10;
    private final float GRAVITY = 0.25f; 
    private boolean _movingUp = false; 

    public Ball() {
        position = new Vector2(0, 0);
    }

    public Ball(Vector2 position) {
        this.position = position;
    }

    public void serve(float horizontalDirection, float verticalDirection)
    {
        _horizontalSpeed = BALL_SPEED * horizontalDirection;
        _verticalSpeed = BALL_SPEED * verticalDirection;

        _movingUp = _verticalSpeed > 0;
    }

    public void update()
    {
        boolean isMoving = _horizontalSpeed != 0 && _verticalSpeed != 0;

        if (!isMoving)
            return;

        position.x += _horizontalSpeed;

        if (_movingUp) {
            _verticalSpeed -= GRAVITY;
            if (_verticalSpeed <= 0)
                _movingUp = false;
        } else {
            _verticalSpeed += GRAVITY; 
        }

        position.y += _verticalSpeed;
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public Vector2 getSize() {
        return size;
    }
}