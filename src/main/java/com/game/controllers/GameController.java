package com.game.controllers;

import javax.swing.JFrame;

import com.game.entities.Ball;
import com.game.entities.Player;
import com.game.enums.GameState;
import com.game.managers.TurnManager;
import com.game.renderers.BallRenderer;
import com.game.renderers.PlayerRenderer;
import com.game.renderers.Renderers;
import com.game.scenes.VolleyballCourt;
import com.game.settings.PlayerSettings;
import com.game.utils.Positions;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

public final class GameController {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int FRAMES_PER_SECOND = 60;

    private final JFrame _frame;
    private final Timer _thread;

    private Player _firstPlayer;
    private Player _secondPlayer;
    private Ball _ball;

    private final VolleyballCourt _volleyballCourt;
    private PlayerController _firstPlayerController;
    private PlayerController _secondPlayerController;

    private GameState _currentGameState;

    private TurnManager _turnManager;

    public GameController() {
        Renderers.addRenderer(Player.class.getName(), new PlayerRenderer());
        Renderers.addRenderer(Ball.class.getName(), new BallRenderer());

        _frame = new JFrame();
    
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setSize(WIDTH, HEIGHT);
        _frame.setResizable(false);
        _frame.setLayout(new BorderLayout());

        _volleyballCourt = new VolleyballCourt();

        _ball = new Ball(Positions.BALL_FIRST_PLAYER_SERVE_POSITION);

        _frame.add(_volleyballCourt, BorderLayout.SOUTH);

        setupPlayers();

        _volleyballCourt.addEntity(_ball);

        setGameState(GameState.FIRST_PLAYER_SERVE);

        _frame.setVisible(true);

        _thread = new Timer(1000 / FRAMES_PER_SECOND, e -> update());
        _thread.start();

        startRound();
    }

    private void setupPlayers()
    {
        PlayerSettings firstPlayerControlSettings = new PlayerSettings(
            KeyEvent.VK_UP,
            KeyEvent.VK_DOWN,
            KeyEvent.VK_RIGHT,
            KeyEvent.VK_LEFT,
            KeyEvent.VK_ENTER,
            -1
        );

        PlayerSettings secondPlayerControlSettings = new PlayerSettings(
            KeyEvent.VK_W,
            KeyEvent.VK_S,
            KeyEvent.VK_D,
            KeyEvent.VK_A,
            KeyEvent.VK_SPACE,
            1
        );

        _firstPlayer = new Player(Positions.FIRST_PLAYER_SERVE_POSITION);
        _secondPlayer = new Player(Positions.SECOND_PLAYER_SERVE_POSITION);

        _firstPlayerController = new PlayerController(this, _firstPlayer, firstPlayerControlSettings);
        _secondPlayerController = new PlayerController(this, _secondPlayer, secondPlayerControlSettings);

        _frame.addKeyListener(_firstPlayerController);
        _frame.addKeyListener(_secondPlayerController);

        _volleyballCourt.addEntity(_firstPlayer);
        _volleyballCourt.addEntity(_secondPlayer);

        List<Player> players = new ArrayList<Player>();

        players.add(_firstPlayer);
        players.add(_secondPlayer);

        _turnManager = new TurnManager(players);
    }

    private void startRound()
    {
        _firstPlayer.position = Positions.FIRST_PLAYER_SERVE_POSITION;
        _secondPlayer.position = Positions.SECOND_PLAYER_SERVE_POSITION;
        
        if (_currentGameState == GameState.FIRST_PLAYER_SERVE)
            _ball.position = Positions.BALL_FIRST_PLAYER_SERVE_POSITION;
        else if (_currentGameState == GameState.SECOND_PLAYER_SERVE)
            _ball.position = Positions.BALL_SECOND_PLAYER_SERVE_POSITION;
    }

    public GameState getGameState()
    {
        return _currentGameState;
    }

    public void setGameState(GameState gameState)
    {
        _currentGameState = gameState;
    }

    public Ball getBall()
    {
        return _ball;
    }

    public TurnManager getTurnManager()
    {
        return _turnManager;
    }

    // called once per frame
    public void update() {
        _firstPlayerController.update();
        _secondPlayerController.update();
        _ball.update();
        _frame.repaint();
    }
}