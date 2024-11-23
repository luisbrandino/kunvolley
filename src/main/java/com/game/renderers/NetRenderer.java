package com.game.renderers;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import com.game.Main;
import com.game.contracts.IEntity;
import com.game.contracts.IRenderer;
import com.game.entities.Net;

public final class NetRenderer implements IRenderer {
    private Image _image = new ImageIcon(Main.class.getResource("images/net.png")).getImage(); 

    @Override
    public void render(IEntity entity, Graphics graphics) {
        Net net = (Net) entity;

        if (net == null)
            throw new Error("NetRenderer.render : Given entity is not a net");

        render(net, graphics);
    }
    
    public void render(Net net, Graphics graphics) {
        graphics.drawImage(_image, (int)net.getPosition().x, (int)net.getPosition().y, (int)net.getSize().x, (int)net.getSize().y, null);
    }
}
