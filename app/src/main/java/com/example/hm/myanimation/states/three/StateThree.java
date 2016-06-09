package com.example.hm.myanimation.states.three;

import android.animation.ValueAnimator;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

import com.example.hm.myanimation.CustomBackground;
import com.example.hm.myanimation.states.State;

/**
 * Created by Ferenc on 2016.06.07..
 */
public class StateThree implements State {
    private CustomBackground customBackground;
    private ValueAnimator animator;
    private GestureDetector detector;


    public StateThree(CustomBackground _customBackground){
        customBackground = _customBackground;
        detector = customBackground.mGestureDetector;

    }

    @Override
    public void doFocus() {
        setAnimator();
    }

    public void stopAnimation(){
        animator.cancel();
    }

    @Override
    public void setEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP){

            if(customBackground.initTapTheCircle()){
                if(customBackground.tapUpInCenterCircle(event)){
                    customBackground.setState(customBackground.stateAnimateTwoCircles);
                    customBackground.stateAnimateTwoCircles.doFocus();

                } else{
                    customBackground.setState(customBackground.stateReAnimate);
                    customBackground.stateReAnimate.doFocus();
                }
            }
        }
    }

    public void setAnimator() {
        animator = ValueAnimator.ofInt(customBackground.angleForFirstCircleTest, customBackground.angleForFirstCircleTest + 360 );
        animator.setDuration(10000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(this);
        animator.start();
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
    public void onAnimationUpdate(ValueAnimator animation) {
        customBackground.angleForFirstCircle = (int) animator.getAnimatedValue();
        customBackground.angleForSecondCircle = ((int) animator.getAnimatedValue() + 120) % 360;
        customBackground.angleForThirdCircle = ((int) animator.getAnimatedValue() + 240) % 360;
        customBackground.angleForFirstCircleTest = customBackground.angleForFirstCircle;
        customBackground.angleForSecondCircleTest = customBackground.angleForSecondCircle;
        customBackground.angleForThirdCircleTest = customBackground.angleForThirdCircle;
        initCoordinatesForSmallCircles(customBackground.angleForFirstCircle,
                customBackground.angleForSecondCircle,
                customBackground.angleForThirdCircle);
        customBackground.invalidate();
    }

    @Override
    public boolean animationIsRunning() {
        return false;
    }
}
