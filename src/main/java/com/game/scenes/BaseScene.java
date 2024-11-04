package com.game.scenes;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.game.contracts.IEntity;
import com.game.renderers.Renderers;

public abstract class BaseScene extends JPanel {
    protected List<IEntity> _entities = new ArrayList<IEntity>();

    public void addEntity(IEntity entity) {
        _entities.add(entity);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        _entities.forEach(entity -> {
            Renderers.getRenderer(entity.getClass().getName()).render(entity, graphics);
        });
    }
}