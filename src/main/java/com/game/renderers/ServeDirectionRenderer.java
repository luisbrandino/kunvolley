package com.game.renderers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import com.game.contracts.IEntity;
import com.game.contracts.IRenderer;
import com.game.entities.ServeDirection;

public final class ServeDirectionRenderer implements IRenderer {
    @Override
    public void render(IEntity entity, Graphics graphics) {
        ServeDirection serveDirection = (ServeDirection) entity;

        if (serveDirection == null)
            throw new Error("ServeDirectionRenderer.render : Given entity is not a serve direction");

        render(serveDirection, graphics);
    }
    
    public void render(ServeDirection serveDirection, Graphics graphics) {
        if (!serveDirection.isVisible)
            return;

        Graphics2D g2d = (Graphics2D) graphics;
        
        float angle = serveDirection.rotation * 180; 

        int x = (int)serveDirection.position.x;
        int y = (int)serveDirection.position.y;

        if (serveDirection.isInverted)
            angle += 180; 
        else
            angle = -angle;

        g2d.rotate(Math.toRadians(angle), x, y);
        
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(x, y, x, y - 50);
        
        g2d.setTransform(new AffineTransform());
    }
}