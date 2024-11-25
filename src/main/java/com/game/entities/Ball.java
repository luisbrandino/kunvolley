package com.game.entities;

import java.util.Arrays;

import com.game.contracts.IEntity;
import com.game.managers.SoundManager;
import com.game.utils.Vector2;

public class Ball implements IEntity { 
    private final int WIDTH = 35;
    private final int HEIGHT = 35;

    public Vector2 position;
    public Vector2 size = new Vector2(WIDTH, HEIGHT);
    private Vector2 _originalSize = new Vector2(WIDTH, HEIGHT);

    private float _horizontalSpeed = 0;
    private float _verticalSpeed = 0;

    private final float BALL_SPEED = 10;
    private final float GRAVITY = 0.25f;
    private boolean _movingUp = false; 

    private final SoundManager _ballSoundManager = new SoundManager(Arrays.asList(
        "sounds/ballhit1.wav",
        "sounds/ballhit2.wav",
        "sounds/ballhit3.wav",
        "sounds/ballhit4.wav",
        "sounds/ballhit5.wav"
    ));

    public double angle;

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

    public void serve(float force, float horizontalDirection, float verticalDirection)
    {
        _horizontalSpeed = (BALL_SPEED * (1 + force)) * horizontalDirection;
        _verticalSpeed = BALL_SPEED * verticalDirection;

        _movingUp = _verticalSpeed > 0;
    }

    public void update()
    {
        if (!isMoving())
            return;
        
        position.x += _horizontalSpeed;

        _movingUp = _verticalSpeed > 0;

        if (_movingUp) {
            _verticalSpeed -= GRAVITY;
            if (_verticalSpeed <= 0)
                _movingUp = false;
        } else {
            _verticalSpeed += GRAVITY;
        }

        angle += 10;
        if (angle >= 360)
            angle = 0;

        position.y += _verticalSpeed;
    }

    public SoundManager getBallSoundManager()
    {
        return _ballSoundManager;
    }

    public boolean isMoving()
    {
        return _verticalSpeed != 0;
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public Vector2 getSize() {
        return size;
    }

    public Vector2 getOriginalSize() {
        return _originalSize;
    }
}