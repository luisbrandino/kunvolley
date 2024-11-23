package com.game.entities;

import com.game.contracts.IEntity;
import com.game.utils.Vector2;

public final class Net implements IEntity {
    private final int WIDTH = 390;
    private final int HEIGHT = 139;

    public Vector2 position;
    public Vector2 size = new Vector2(WIDTH, HEIGHT);

    public Net() {
        position = new Vector2(0, 0);
    }

    public Net(Vector2 position) {
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
