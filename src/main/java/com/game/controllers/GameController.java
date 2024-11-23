package com.game.controllers;

import javax.swing.JFrame;

import com.game.entities.Ball;
import com.game.entities.Player;
import com.game.enums.GameState;
import com.game.renderers.BallRenderer;
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
    private final Ball _ball;

    private final VolleyballCourt _volleyballCourt;
    private final PlayerController _playerController;

    public GameState currentGameState;

    public GameController() {
        Renderers.addRenderer(Player.class.getName(), new PlayerRenderer());
        Renderers.addRenderer(Ball.class.getName(), new BallRenderer());

        _frame = new JFrame();
    
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setSize(WIDTH, HEIGHT);
        _frame.setResizable(false);
        _frame.setLayout(new BorderLayout());

        _volleyballCourt = new VolleyballCourt();

        _player = new Player(Positions.PLAYER_START_POSITION);
        _ai = new Player(Positions.AI_START_POSITION);
        _ball = new Ball(Positions.BALL_PLAYER_SERVE_POSITION);

        _frame.add(_volleyballCourt, BorderLayout.SOUTH);

        _volleyballCourt.addEntity(_player);
        _volleyballCourt.addEntity(_ai);
        _volleyballCourt.addEntity(_ball);

        currentGameState = GameState.PLAYER_SERVE;

        _playerController = new PlayerController(this, _player);

        _frame.addKeyListener(_playerController);

        _frame.setVisible(true);

        _thread = new Timer(1000 / FRAMES_PER_SECOND, e -> update());
        _thread.start();

        startRound();
    }

    public void startRound()
    {
        _player.position = Positions.PLAYER_START_POSITION;
        _ai.position = Positions.AI_START_POSITION;
        
        if (currentGameState == GameState.PLAYER_SERVE)
            _ball.position = Positions.BALL_PLAYER_SERVE_POSITION;
        else if (currentGameState == GameState.AI_SERVE)
            _ball.position = Positions.BALL_AI_SERVE_POSITION;
    }

    public void setGameState(GameState gameState)
    {
        currentGameState = gameState;
    }

    public Ball getBall()
    {
        return _ball;
    }

    public Player getPlayer()
    {
        return _player;
    }

    // called once per frame
    public void update() {
        _playerController.update();
        _ball.update();
        _frame.repaint();
    }
}