package com.game.controllers;

import javax.swing.JFrame;

import com.game.entities.Player;
import com.game.renderers.PlayerRenderer;
import com.game.renderers.Renderers;
import com.game.scenes.VolleyballCourt;
import com.game.utils.Positions;

import java.awt.BorderLayout;

import javax.swing.Timer;

public final class GameController {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int FRAMES_PER_SECOND = 60;

    private final JFrame _frame;
    private final Timer _thread;

    private final Player _player;
    private final Player _ai;

    private final VolleyballCourt _volleyballCourt;

    private final PlayerController _playerController;

    public GameController() {
        Renderers.addRenderer(Player.class.getName(), new PlayerRenderer());

        _frame = new JFrame();
    
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setSize(WIDTH, HEIGHT);
        _frame.setResizable(false);
        _frame.setLayout(new BorderLayout());

        _volleyballCourt = new VolleyballCourt();

        _player = new Player(Positions.PLAYER_START_POSITION);
        _ai = new Player(Positions.AI_START_POSITION);

        _frame.add(_volleyballCourt, BorderLayout.SOUTH);

        _volleyballCourt.addEntity(_player);
        _volleyballCourt.addEntity(_ai);

        _playerController = new PlayerController(_player);

        _frame.addKeyListener(_playerController);

        _frame.setVisible(true);

        _thread = new Timer(1000 / FRAMES_PER_SECOND, e -> update());
        _thread.start();
    }

    // called once per frame
    public void update() {
        _playerController.update();
        _frame.repaint();
    }
}