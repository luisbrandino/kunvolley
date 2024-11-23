package com.game.renderers;

import java.awt.Color;
import java.awt.Graphics;

import com.game.contracts.IEntity;
import com.game.contracts.IRenderer;
import com.game.entities.Ball;

public final class BallRenderer implements IRenderer {
    @Override
    public void render(IEntity entity, Graphics graphics) {
        Ball ball = (Ball) entity;

        if (ball == null)
            throw new Error("BallRenderer.render : Given entity is not a ball");

        render(ball, graphics);
    }

    public void render(Ball ball, Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillOval((int)ball.getPosition().x, (int)ball.getPosition().y, (int)ball.getSize().x, (int)ball.getSize().y);
    }
}