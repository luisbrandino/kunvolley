package com.game.entities;

import com.game.contracts.IEntity;
import com.game.utils.Vector2;

public final class Player implements IEntity {
    private final int WIDTH = 50;
    private final int HEIGHT = 50;

    public Vector2 position;
    public Vector2 size = new Vector2(WIDTH, HEIGHT);

    public Player() {
        position = new Vector2(0 ,0);
    }

    public Player(Vector2 position) {
        this.position = position;
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