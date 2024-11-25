package com.game.renderers;

import java.awt.Color;
import java.awt.Graphics;

import com.game.contracts.IEntity;
import com.game.contracts.IRenderer;
import com.game.entities.ChargeMeter;

public final class ChargeMeterRenderer implements IRenderer {
    @Override
    public void render(IEntity entity, Graphics graphics) {
        ChargeMeter chargeMeter = (ChargeMeter) entity;

        if (chargeMeter == null)
            throw new Error("ChargeMeterRenderer.render : Given entity is not a charge meter");

        render(chargeMeter, graphics);
    }
    
    public void render(ChargeMeter chargeMeter, Graphics graphics) {
        float chargePercentage = (float) chargeMeter.getSize().x / (float) chargeMeter.MAX_WIDTH;

        int red = 0;
        int green = 0;

        if (chargePercentage <= 0.5f) {
            green = 255;
            red = (int) (255 * (chargePercentage / 0.5f)); 
        } else {
            red = 255; 
            green = (int) (255 * (1 - (chargePercentage - 0.5f) / 0.5f));
        }

        Color chargeColor = new Color(red, green, 0);

        graphics.setColor(chargeColor);
        graphics.fillRect((int)chargeMeter.getPosition().x, (int)chargeMeter.getPosition().y, (int)chargeMeter.getSize().x, (int)chargeMeter.getSize().y);
    }
}
