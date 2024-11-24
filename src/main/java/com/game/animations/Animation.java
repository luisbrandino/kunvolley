package com.game.animations;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Animation {
    private Map<String, BufferedImage[]> _animations;
    private int _currentFrame;
    private long _lastFrameTime;
    private long _frameDelay;
    private String _currentAnimation;

    public Animation(long frameDelay) {
        this._animations = new HashMap<>();
        this._currentFrame = 0;
        this._frameDelay = frameDelay;
        this._lastFrameTime = System.currentTimeMillis();
    }

    public void addAnimation(String name, BufferedImage[] frames) {
        _animations.put(name, frames);
    }

    public BufferedImage getCurrentFrame(String animationName) {
        BufferedImage[] frames = _animations.get(animationName);

        if (_currentAnimation != null && !_currentAnimation.equals(animationName))
            _currentFrame = 0;

        if (frames == null)
            throw new IllegalArgumentException("No animation found with name: " + animationName);

        if (System.currentTimeMillis() - _lastFrameTime >= _frameDelay) {
            _currentFrame = (_currentFrame + 1) % frames.length;
            _lastFrameTime = System.currentTimeMillis();
        }

        _currentAnimation = animationName;

        return frames[_currentFrame];
    }
}
