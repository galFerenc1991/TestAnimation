package com.example.hm.myanimation;

import android.animation.TypeEvaluator;
import android.graphics.Point;

/**
 * Created by Ferenc on 2016.06.06..
 */
public class MyTypeEvaluator implements TypeEvaluator<Point> {

    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        float dX = endValue.x - startValue.x;
        float dY = endValue.y - startValue.y;
        dX *= fraction;
        dY *= fraction;

        float endX = startValue.x + dX;
        float endY = startValue.y + dY;

        Point point = new Point(((int) endX) , ((int) endY));
        return point;
    }
}