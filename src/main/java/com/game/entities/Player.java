package com.game.entities;

import com.game.contracts.IEntity;
import com.game.enums.PlayerState;
import com.game.graphics.PlayerAnimation;
import com.game.utils.Vector2;

public final class Player implements IEntity {
    private final int WIDTH = 43;
    private final int HEIGHT = 56;

    public Vector2 position;
    public Vector2 size = new Vector2(WIDTH, HEIGHT);

    private PlayerState _state;

    private PlayerAnimation _animation;

    public Player() {
        position = new Vector2(0 ,0);
        _animation = new PlayerAnimation();
    }

    public Player(Vector2 position) {
        this.position = position;
        _animation = new PlayerAnimation();
    }

    public String getCurrentAnimation()
    {
        switch (_state) {
            case IDLE_FRONT:
                return "idle_front";
            case IDLE_BACK:
                return "idle_back";
            case IDLE_RIGHT:
                return "idle_right";
            case IDLE_LEFT:
                return "idle_left";
            case WALKING_FRONT:
                return "walk_front";
            case WALKING_BACK:
                return "walk_back";
            case WALKING_RIGHT:
                return "walk_right";
            case WALKING_LEFT:
                return "walk_left";
        }

        return "idle_front";
    }

    public void setState(PlayerState state)
    {
        _state = state;
    }

    public PlayerState getState()
    {
        return _state;
    }

    public PlayerAnimation getAnimation()
    {
        return _animation;
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