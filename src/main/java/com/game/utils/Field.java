package com.game.utils;

public final class Field {
    private final Vector2 _min;
    private final Vector2 _max;
    private boolean _isReversed = false;

    public Field(Vector2 min, Vector2 max)
    {
        _min = min;
        _max = max;
    }

    public Field(Vector2 min, Vector2 max, boolean isReversed)
    {
        _min = min;
        _max = max;
        _isReversed = isReversed;
    }

    public boolean isWithinBounds(Vector2 vector) {
        if (vector.x < _min.x || vector.x > _max.x)
            return false;

        if (_isReversed)
            return vector.y <= _min.y && vector.y >= _max.y;

        return vector.y >= _min.y && vector.y <= _max.y;
    }
}
