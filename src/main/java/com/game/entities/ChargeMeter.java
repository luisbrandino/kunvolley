package com.game.entities;

import com.game.contracts.IEntity;
import com.game.utils.Vector2;

public final class ChargeMeter implements IEntity {
    public final int MAX_WIDTH = 50;
    private final int HEIGHT = 8;

    public Vector2 position = new Vector2(0, 0);
    public Vector2 size = new Vector2(0, HEIGHT);

    private boolean _isCharging = false;
    
    public long MAX_CHARGE_DURATION = 1000;
    private long _startedChargingAt = 0;
    private long _lastChargeDuration = 0;

    private final Player _belongsTo;

    public ChargeMeter(Player player) {
        _belongsTo = player;
        updatePosition();
    }

    public void startCharging() {
        if (_isCharging) {
            stopCharging();
        }

        _isCharging = true;
        _startedChargingAt = System.currentTimeMillis();
    }

    public void stopCharging() {
        _lastChargeDuration = getTimeCharging();
        _isCharging = false;
        _startedChargingAt = 0;
    }

    public boolean isCharging() {
        return _isCharging;
    }

    public long getTimeCharging() {
        if (!isCharging())
            return 0;

        return System.currentTimeMillis() - _startedChargingAt;
    }

    public long getLastChargeDuration() {
        return _lastChargeDuration;
    }

    public void updatePosition() {
        position.x = _belongsTo.position.x - 3;
        position.y = _belongsTo.position.y + 65;
    }

    public void update() {
        updatePosition();

        if (isCharging())
            size.x = Math.min(((double)getTimeCharging() / (double)MAX_CHARGE_DURATION) * MAX_WIDTH, MAX_WIDTH);
        else
            size.x = 0;
    }   

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public Vector2 getSize() {
        return size;
    }
    
}
