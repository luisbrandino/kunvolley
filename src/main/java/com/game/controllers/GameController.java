package com.game.controllers;

import javax.swing.JFrame;

import com.game.entities.Player;
import com.game.renderers.PlayerRenderer;
import com.game.renderers.Renderers;
import com.game.scenes.VolleyballCourt;

import java.awt.BorderLayout;

import javax.swing.Timer;

public final class GameController {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int FRAMES_PER_SECOND = 60;

    private final JFrame _frame;
    private final Timer _thread;

    private final VolleyballCourt _volleyballCourt;

    public GameController() {
        Renderers.addRenderer(Player.class.getName(), new PlayerRenderer());

        _frame = new JFrame();
    
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setSize(WIDTH, HEIGHT);
        _frame.setResizable(false);
        _frame.setLayout(new BorderLayout());

        _volleyballCourt = new VolleyballCourt();

        _frame.add(_volleyballCourt, BorderLayout.SOUTH);

        _volleyballCourt.addEntity(new Player());

        _frame.setVisible(true);

        _thread = new Timer(1000 / FRAMES_PER_SECOND, e -> update());
        _thread.start();
    }

    // called once per frame
    public void update() {
        _frame.repaint();
    }
}