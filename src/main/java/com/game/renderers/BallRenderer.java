package com.game.renderers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

import com.game.Main;
import com.game.contracts.IEntity;
import com.game.contracts.IRenderer;
import com.game.entities.Ball;

public final class BallRenderer implements IRenderer {
    private Image _image = new ImageIcon(Main.class.getResource("images/volleyball.png")).getImage(); 
    
    @Override
    public void render(IEntity entity, Graphics graphics) {
        Ball ball = (Ball) entity;

        if (ball == null)
            throw new Error("BallRenderer.render : Given entity is not a ball");

        render(ball, graphics);
    }

    public void render(Ball ball, Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;

        AffineTransform originalTransform = g2d.getTransform();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.translate((int)ball.getPosition().x + (int)ball.getSize().x / 2, (int)ball.getPosition().y + (int)ball.getSize().y / 2);

        double scaleX = ball.getSize().x / ball.getOriginalSize().x; 
        double scaleY = ball.getSize().y / ball.getOriginalSize().y;  
        g2d.scale(scaleX, scaleY); 

        g2d.rotate(Math.toRadians(ball.angle));

        g2d.drawImage(_image, -(int)ball.getSize().x / 2, -(int)ball.getSize().y / 2, null);

        g2d.setTransform(originalTransform);
    }
}