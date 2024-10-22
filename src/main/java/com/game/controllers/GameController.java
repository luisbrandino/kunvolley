package com.game.controllers;

import javax.swing.JFrame;

import com.game.views.VolleyballCourt;

import java.awt.BorderLayout;

import javax.swing.Timer;

public final class GameController {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int FRAMES_PER_SECOND = 60;

    private final JFrame _frame;
    private final Timer _thread;

    public GameController() {
        _frame = new JFrame();
    
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setSize(WIDTH, HEIGHT);
        _frame.setResizable(false);
        _frame.setLayout(new BorderLayout());
        _frame.add(new VolleyballCourt(), BorderLayout.SOUTH);
        _frame.setVisible(true);

        _thread = new Timer(1000 / FRAMES_PER_SECOND, e -> update());
        _thread.start();
    }

    // called once per frame
    public void update() {
        _frame.repaint();
    }
}