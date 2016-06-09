package com.example.hm.myanimation.states;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.MotionEvent;

/**
 * Created by Ferenc on 2016.06.07..
 */
public interface State extends ValueAnimator.AnimatorUpdateListener{

    void initCoordinatesForSmallCircles(int _angleForFirstCircle,
                                        int _angleForSecondCircle,
                                        int _angleForThirdCircle);

    void doFocus();
    void setEvent(MotionEvent event);
    void stopAnimation();
    boolean animationIsRunning();
}
