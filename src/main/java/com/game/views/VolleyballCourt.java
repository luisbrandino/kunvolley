package com.game.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public final class VolleyballCourt extends JPanel {
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
    }

}