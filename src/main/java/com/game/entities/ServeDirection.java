package com.game.entities;

import com.game.contracts.IEntity;
import com.game.utils.Vector2;

public final class ServeDirection implements IEntity {
    private final int WIDTH = 2;
    private final int HEIGHT = 10;

    public Vector2 position = new Vector2(0, 0);
    public Vector2 size = new Vector2(WIDTH, HEIGHT);

    public boolean isVisible = false;
    public boolean isInverted = false;
    public float rotation = 0;

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public Vector2 getSize() {
        return size;
    }
}
