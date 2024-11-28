package com.game.controllers;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;

import com.game.entities.Ball;
import com.game.entities.ChargeMeter;
import com.game.entities.Net;
import com.game.entities.Player;
import com.game.enums.GameState;
import com.game.enums.PlayerState;
import com.game.managers.SoundManager;
import com.game.managers.TurnManager;
import com.game.renderers.BallRenderer;
import com.game.renderers.ChargeMeterRenderer;
import com.game.renderers.NetRenderer;
import com.game.renderers.PlayerRenderer;
import com.game.renderers.Renderers;
import com.game.scenes.Sky;
import com.game.scenes.VolleyballCourt;
import com.game.settings.PlayerSettings;
import com.game.utils.Cooldown;
import com.game.utils.Field;
import com.game.utils.Positions;
import com.game.utils.Vector2;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
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

    private int _firstPlayerPoints = 0;
    private int _secondPlayerPoints = 0;

    private final Cooldown _intermissionCooldown;

    private final VolleyballCourt _volleyballCourt;
    private final Sky _sky;
    private PlayerController _firstPlayerController;
    private PlayerController _secondPlayerController;
    private ChargeMeter _firstPlayerChargeMeter;
    private ChargeMeter _secondPlayerChargeMeter;

    private GameState _currentGameState;

    private TurnManager _turnManager;

    private final SoundManager gameSoundManager;

    public GameController() {
        _intermissionCooldown = new Cooldown(3000);

        Renderers.addRenderer(Player.class.getName(), new PlayerRenderer());
        Renderers.addRenderer(Ball.class.getName(), new BallRenderer());
        Renderers.addRenderer(Net.class.getName(), new NetRenderer());
        Renderers.addRenderer(ChargeMeter.class.getName(), new ChargeMeterRenderer());

        _frame = new JFrame();

        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setSize(WIDTH, HEIGHT);
        _frame.setResizable(false);
        _frame.setLayout(new BorderLayout());

        _volleyballCourt = new VolleyballCourt();
        _sky = new Sky(this);

        _ball = new Ball(new Vector2(Positions.BALL_FIRST_PLAYER_SERVE_POSITION));

        _frame.add(_volleyballCourt, BorderLayout.SOUTH);
        _frame.add(_sky, BorderLayout.NORTH);

        setupPlayers();

        Net net = new Net(new Vector2(Positions.NET_POSITION));

        _volleyballCourt.addEntity(_secondPlayer);
        _volleyballCourt.addEntity(_secondPlayerChargeMeter);
        _volleyballCourt.addEntity(net);
        _volleyballCourt.addEntity(_ball);
        _volleyballCourt.addEntity(_firstPlayer);
        _volleyballCourt.addEntity(_firstPlayerChargeMeter);

        setGameState(GameState.FIRST_PLAYER_SERVE);

        gameSoundManager = new SoundManager(Arrays.asList("sounds/song.wav"));

        gameSoundManager.playSound(0, Clip.LOOP_CONTINUOUSLY);
        gameSoundManager.setVolume(0.01f);

        _frame.setVisible(true);

        _thread = new Timer(1000 / FRAMES_PER_SECOND, e -> update());
        _thread.start();

        startRound();
    }

    private void setupPlayers() {
        PlayerSettings firstPlayerSettings = new PlayerSettings(
                KeyEvent.VK_UP,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_RIGHT,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_ENTER,
                -1);

        PlayerSettings secondPlayerSettings = new PlayerSettings(
                KeyEvent.VK_W,
                KeyEvent.VK_S,
                KeyEvent.VK_D,
                KeyEvent.VK_A,
                KeyEvent.VK_SPACE,
                1);

        Field firstPlayerField = new Field(
                new Vector2(175, 370),
                new Vector2(580, 245),
                true);

        Field secondPlayerField = new Field(
                new Vector2(175, 5),
                new Vector2(580, 160));

        _firstPlayer = new Player(new Vector2(Positions.FIRST_PLAYER_SERVE_POSITION));
        _secondPlayer = new Player(new Vector2(Positions.SECOND_PLAYER_SERVE_POSITION));

        _firstPlayer.setState(PlayerState.IDLE_BACK);
        _secondPlayer.setState(PlayerState.IDLE_FRONT);

        _firstPlayerController = new PlayerController(this, _firstPlayer, firstPlayerSettings, firstPlayerField);
        _secondPlayerController = new PlayerController(this, _secondPlayer, secondPlayerSettings, secondPlayerField);

        _firstPlayerChargeMeter = new ChargeMeter(_firstPlayer);
        _secondPlayerChargeMeter = new ChargeMeter(_secondPlayer);

        _firstPlayer.setChargeMeter(_firstPlayerChargeMeter);
        _secondPlayer.setChargeMeter(_secondPlayerChargeMeter);

        _frame.addKeyListener(_firstPlayerController);
        _frame.addKeyListener(_secondPlayerController);

        List<Player> players = new ArrayList<Player>();

        players.add(_firstPlayer);
        players.add(_secondPlayer);

        _turnManager = new TurnManager(players);
    }

    private void startRound() {
        _firstPlayer.position = new Vector2(Positions.FIRST_PLAYER_SERVE_POSITION);
        _secondPlayer.position = new Vector2(Positions.SECOND_PLAYER_SERVE_POSITION);

        if (_currentGameState == GameState.FIRST_PLAYER_SERVE)
            _ball.position = new Vector2(Positions.BALL_FIRST_PLAYER_SERVE_POSITION);
        else if (_currentGameState == GameState.SECOND_PLAYER_SERVE)
            _ball.position = new Vector2(Positions.BALL_SECOND_PLAYER_SERVE_POSITION);
    }

    public GameState getGameState() {
        return _currentGameState;
    }

    public void setGameState(GameState gameState) {
        _currentGameState = gameState;
    }

    public Ball getBall() {
        return _ball;
    }

    public TurnManager getTurnManager() {
        return _turnManager;
    }

    private Player findWinner() {
        boolean hasBallFallenOnFirstPlayerField = _firstPlayerController.getPlayerField()
                .isWithinBounds(_ball.position);

        if (hasBallFallenOnFirstPlayerField)
            return _secondPlayer;

        boolean hasBallFallenOnSecondPlayerField = _secondPlayerController.getPlayerField()
                .isWithinBounds(_ball.position);

        if (hasBallFallenOnSecondPlayerField)
            return _firstPlayer;

        return _turnManager.getCurrentPlayer();
    }

    private void handleWhenRoundIsOver() {
        if (!_intermissionCooldown.ready())
            return;

        Player winner = findWinner();

        GameState serveStartsWith;
        int playerServingIndex;

        serveStartsWith = winner == _secondPlayer ? GameState.SECOND_PLAYER_SERVE : GameState.FIRST_PLAYER_SERVE;
        playerServingIndex = winner == _secondPlayer ? 1 : 0;

        setGameState(serveStartsWith);
        _turnManager.setNextTurnTo(playerServingIndex);
        _firstPlayer.setState(PlayerState.IDLE_BACK);
        _secondPlayer.setState(PlayerState.IDLE_FRONT);

        startRound();
    }

    public int getFirstPlayerPoints() {
        return _firstPlayerPoints;
    }

    public int getSecondPlayerPoints() {
        return _secondPlayerPoints;
    }

    public void update() {
        if (_currentGameState == GameState.ROUND_IS_OVER)
            handleWhenRoundIsOver();

        _firstPlayerController.update();
        _secondPlayerController.update();
        _firstPlayerChargeMeter.update();
        _secondPlayerChargeMeter.update();
        _ball.update();
        _frame.repaint();

        if (!_ball.isMoving() && _currentGameState == GameState.PLAYING) {
            setGameState(GameState.ROUND_IS_OVER);

            Player winner = findWinner();

            _firstPlayerPoints = winner == _firstPlayer ? _firstPlayerPoints + 1 : _firstPlayerPoints;
            _secondPlayerPoints = winner == _secondPlayer ? _secondPlayerPoints + 1 : _secondPlayerPoints;

            _firstPlayerController.updateIdleState();
            _secondPlayerController.updateIdleState();

            _intermissionCooldown.start();
        }
    }
}