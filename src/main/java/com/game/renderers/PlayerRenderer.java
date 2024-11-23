package com.game.renderers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.game.contracts.IEntity;
import com.game.contracts.IRenderer;
import com.game.entities.Player;

public final class PlayerRenderer implements IRenderer {

    @Override
    public void render(IEntity entity, Graphics graphics) {
        Player player = (Player) entity;

        if (player == null)
            throw new Error("PlayerRenderer.render : Given entity is not a player");

        render(player, graphics);
    }

    public void render(Player player, Graphics graphics) {
        String currentAnimation = player.getCurrentAnimation();

        BufferedImage frame = player.getAnimation().getCurrentFrame(currentAnimation);

        graphics.drawImage(frame, (int) player.getPosition().x, (int) player.getPosition().y, (int) player.getSize().x, (int) player.getSize().y, null);
    }
}
