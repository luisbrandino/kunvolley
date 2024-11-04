package com.game.scenes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public final class VolleyballCourt extends BaseScene {
    private final int GROUND_WIDTH = 800;
    private final int GROUND_HEIGHT = 300;

    public VolleyballCourt() {
        super();
        setPreferredSize(new Dimension(GROUND_WIDTH, GROUND_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.setColor(Color.GRAY);
        graphics.fillRect(0, 0, GROUND_WIDTH, GROUND_HEIGHT);

        super.paintComponent(graphics);
    }
}