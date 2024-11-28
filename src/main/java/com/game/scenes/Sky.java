package com.game.scenes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.game.Main;
import com.game.controllers.GameController;
import com.game.utils.Cooldown;
import com.game.utils.RandomGenerator;
import com.game.utils.Vector2;

public final class Sky extends BaseScene {
    private class Plane {
        public Vector2 position = new Vector2(0, 0);
        public int horizontalSpeed = 0;
        public boolean isFlipped = false;
    }

    private final GameController _game;
    private final int WIDTH = 800;
    private final int HEIGHT = 150;

    private Image _planeImage = new ImageIcon(Main.class.getResource("images/plane.png")).getImage();
    private Font _font;

    private final int PLANE_WIDTH = 80;
    private final int PLANE_HEIGHT = 20;

    private final int MAX_PLANES_ON_SCREEN = 4;

    private final List<Plane> _planes = new ArrayList<>();

    private Cooldown _cooldown = new Cooldown(RandomGenerator.nextInt(3000, 4000));

    public Sky(GameController game) {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        _game = game;

        URL fontUrl = Main.class.getResource("fonts/score.ttf");

        if (fontUrl != null) {
            try {
                File fontFile = new File(fontUrl.toURI());
                _font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
                _font = _font.deriveFont(Font.PLAIN, 50);
            } catch (Exception e) {
            }
        } else {
            _font = new Font("Serif", Font.PLAIN, 50);
        }
    }

    private void removeOldPlanes() {
        List<Plane> planesToRemove = new ArrayList<>();

        _planes.forEach(plane -> {
            if (plane.position.x >= WIDTH + PLANE_WIDTH || plane.position.x <= 0 - PLANE_WIDTH)
                planesToRemove.add(plane);
        });

        _planes.removeAll(planesToRemove);
    }

    private void generatePlane() {
        if (_planes.size() >= MAX_PLANES_ON_SCREEN || !_cooldown.ready())
            return;

        _cooldown = new Cooldown(RandomGenerator.nextInt(3000, 4000));

        Plane plane = new Plane();

        int random = RandomGenerator.nextInt(1, 10);
        boolean isFlipped = random <= 7;

        int x = isFlipped ? 0 - PLANE_WIDTH : WIDTH + PLANE_WIDTH;

        plane.position = new Vector2(x, RandomGenerator.nextInt(0, HEIGHT - PLANE_HEIGHT - 50));
        plane.horizontalSpeed = isFlipped ? 3 : -3;
        plane.isFlipped = isFlipped;

        _planes.add(plane);
    }

    private void updatePlanes() {
        _planes.forEach(plane -> {
            plane.position.x += plane.horizontalSpeed;
        });
    }

    private void paintPlanes(Graphics graphics) {
        _planes.forEach(plane -> {
            if (plane.isFlipped) {
                Graphics2D g2d = (Graphics2D) graphics;

                g2d.drawImage(_planeImage, (int) plane.position.x + PLANE_WIDTH, (int) plane.position.y, -PLANE_WIDTH,
                        PLANE_HEIGHT, null);

                return;
            }

            graphics.drawImage(_planeImage, (int) plane.position.x, (int) plane.position.y, PLANE_WIDTH, PLANE_HEIGHT,
                    null);
        });
    }

    private void showScore(Graphics graphics) {
        int firstPlayerPoints = _game.getFirstPlayerPoints();
        int secondPlayerPoints = _game.getSecondPlayerPoints();

        graphics.setFont(_font);
        graphics.setColor(Color.WHITE);

        graphics.drawString(firstPlayerPoints + " : " + secondPlayerPoints, WIDTH / 2 - 50, HEIGHT / 2);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.setColor(Color.decode("#87CEEB"));
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        removeOldPlanes();
        generatePlane();
        updatePlanes();
        paintPlanes(graphics);
        showScore(graphics);

        super.paintComponent(graphics);
    }
}