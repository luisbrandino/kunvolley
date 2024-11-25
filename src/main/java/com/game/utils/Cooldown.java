package com.game.utils;

public class Cooldown {
    private final long _delayInMilliseconds;
    private long _lastTick = 0;

    public Cooldown(long delayInMilliseconds) {
        _delayInMilliseconds = delayInMilliseconds;
    }

    public void start() {
        _lastTick = System.currentTimeMillis();
    }
    
    public boolean ready() {
        if (_lastTick == 0) {
            start();
            
            return false;
        }
        
        return System.currentTimeMillis() - _lastTick >= _delayInMilliseconds;
    }
}