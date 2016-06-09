package com.example.hm.myanimation.states.three;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

import com.example.hm.myanimation.CustomBackground;
import com.example.hm.myanimation.MyTypeEvaluator;
import com.example.hm.myanimation.states.State;

/**
 * Created by Ferenc on 2016.06.07..
 */
public class StateReAnimate implements State, ValueAnimator.AnimatorUpdateListener {

    private static final String TAG = "StateReAnimate";
    private ValueAnimator pointAnimator;
    private CustomBackground customBackground;
    private boolean isAnimationRunning = true;

    public StateReAnimate(CustomBackground _customBackground){
        customBackground = _customBackground;
    }

    @Override
    public void doFocus() {
        setAnimatorForPoint();

    }

    @Override
    public void setEvent(MotionEvent event) {

    }

    @Override
    public void stopAnimation() {

    }

    public void setAnimatorForPoint(){
        final Point mEndPoint = customBackground.endPoint;
        pointAnimator = ObjectAnimator.ofObject(this, "optimalPoint", new MyTypeEvaluator(),
                customBackground.setStartPoint(),
                mEndPoint);
        pointAnimator.setDuration(500);
        pointAnimator.setInterpolator(new LinearInterpolator());
        pointAnimator.addUpdateListener(this);
        pointAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimationRunning = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "onAnimationEnd: " + isAnimationRunning);
                isAnimationRunning = false;
                customBackground.setState(customBackground.three);
                customBackground.state.doFocus();
            }
        });
        pointAnimator.start();
    }
    public void setOptimalPoint(Point point) {
        customBackground.optimalPoint = point;

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        Log.d("optimal",""+ customBackground.optimalPoint.x);
        if(customBackground.touchedCircle == 1){
            initCoordinatesForSmallCircles(customBackground.findAngelForSmallCircle(customBackground.optimalPoint),
                    customBackground.angleForSecondCircle,
                    customBackground.angleForThirdCircle);
        }

        if(customBackground.touchedCircle == 2){
            initCoordinatesForSmallCircles(customBackground.angleForFirstCircle,
                    customBackground.findAngelForSmallCircle(customBackground.optimalPoint),
                    customBackground.angleForThirdCircle);
        }

        if(customBackground.touchedCircle == 3){
            initCoordinatesForSmallCircles(customBackground.angleForFirstCircle,
                    customBackground.angleForSecondCircle,
                    customBackground.findAngelForSmallCircle(customBackground.optimalPoint));
        }
    customBackground.invalidate();

    }

    @Override
    public void initCoordinatesForSmallCircles(int _angleForFirstCircle, int _angleForSecondCircle, int _angleForThirdCircle) {
        customBackground.X1 = customBackground.findOrigoXForSmallCircle(_angleForFirstCircle);
        customBackground.Y1 = customBackground.findOrigoYForSmallCircle(_angleForFirstCircle);

        ///// X and Y for second small circle
        customBackground.X2 = customBackground.findOrigoXForSmallCircle(_angleForSecondCircle);
        customBackground.Y2 = customBackground.findOrigoYForSmallCircle(_angleForSecondCircle);

        ///// X and Y for third small circle
        customBackground.X3 = customBackground.findOrigoXForSmallCircle(_angleForThirdCircle);
        customBackground.Y3 = customBackground.findOrigoYForSmallCircle(_angleForThirdCircle);
    }

    @Override
    public boolean animationIsRunning() {
        return isAnimationRunning;
    }
}
