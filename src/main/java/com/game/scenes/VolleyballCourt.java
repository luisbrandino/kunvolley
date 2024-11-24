package com.game.scenes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public final class VolleyballCourt extends BaseScene {
    private final int GROUND_WIDTH = 800;
    private final int GROUND_HEIGHT = 450;

    public VolleyballCourt() {
        super();
        setPreferredSize(new Dimension(GROUND_WIDTH, GROUND_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;

        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, GROUND_WIDTH, GROUND_HEIGHT);

        g2d.setStroke(new BasicStroke(7.5f));
        g2d.setColor(Color.BLACK);

        g2d.drawRect(208, 60, GROUND_WIDTH - 425, GROUND_HEIGHT - 100); 
        
        super.paintComponent(graphics);
    }
}