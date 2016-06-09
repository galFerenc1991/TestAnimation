package com.example.hm.myanimation.states.two;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

import com.example.hm.myanimation.CustomBackground;
import com.example.hm.myanimation.states.State;

/**
 * Created by Ferenc on 2016.06.07..
 */
public class StateTwo implements State {

    private ValueAnimator animator;
    private CustomBackground customBackground;
    private boolean isAnimationRunning = true;

    public StateTwo(CustomBackground _customBackground){
        customBackground = _customBackground;
    }

    @Override
    public void doFocus() {
        setAnimator();
    }

    @Override
    public void setEvent(MotionEvent event) {
        customBackground.initCenterReAnimatedSmallCircles(event);
        if (event.getAction() == MotionEvent.ACTION_UP){

            if(customBackground.initTapTheCircle()){
                if(customBackground.tapUpInCenterCircle(event)){
//                    customBackground.setState(customBackground.stateAnimateTwoCircles);
//                    customBackground.stateAnimateTwoCircles.doFocus();

                } else{
                    customBackground.setState(customBackground.stateTwoReanimate);
                    customBackground.stateTwoReanimate.doFocus();
                }
            }
            if(customBackground.tapUpInCenterCircle(event)){
                customBackground.initTouchedPlatform(event);
                if(customBackground.touchedPlatform == 1){
                    Log.d("platform", "" + customBackground.touchedPlatform);
                    customBackground.setState(customBackground.stateGoToStateThree);
                    customBackground.stateGoToStateThree.doFocus();
                }
            }
        }
    }

    @Override
    public void stopAnimation() {
        Log.d("stop","" + animator.getAnimatedValue());

        animator.cancel();
    }

    public void setAnimator() {
        Log.d("AngleFirst", "setAnimator: " + customBackground.angleForFirstCircleTest);
        animator = ValueAnimator.ofInt(customBackground.angleForFirstCircleTest, customBackground.angleForFirstCircleTest + 360 );
        animator.setDuration(10000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(this);
        animator.start();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        Log.d("values", ""+animator.getAnimatedValue());
        if(customBackground.radiusForSmallCircle_1 == 10){

            customBackground.angleForFirstCircle = (int) animator.getAnimatedValue();

            customBackground.angleForSecondCircle = ((int) animator.getAnimatedValue() + 90) % 360;
            customBackground.angleForThirdCircle = ((int) animator.getAnimatedValue() + 270) % 360;
            initCoordinatesForSmallCircles(customBackground.angleForFirstCircle,
                    customBackground.angleForSecondCircle,
                    customBackground.angleForThirdCircle);
        }
        if(customBackground.radiusForSmallCircle_2 == 10){
            customBackground.angleForSecondCircle = ((int) animator.getAnimatedValue() + 120) % 360;

            customBackground.angleForFirstCircle = ((int) animator.getAnimatedValue()+30) % 360;
            customBackground.angleForThirdCircle = ((int) animator.getAnimatedValue() + 210) % 360;
            initCoordinatesForSmallCircles(customBackground.angleForFirstCircle,
                    customBackground.angleForSecondCircle,
                    customBackground.angleForThirdCircle);

        }
        if(customBackground.radiusForSmallCircle_3 == 10){
            customBackground.angleForThirdCircle = ((int) animator.getAnimatedValue() + 240) % 360;
            customBackground.angleForFirstCircle = ((int) animator.getAnimatedValue() + 330) % 360;
            customBackground.angleForSecondCircle = ((int) animator.getAnimatedValue() + 150) % 360;
            initCoordinatesForSmallCircles(customBackground.angleForFirstCircle,
                    customBackground.angleForSecondCircle,
                    customBackground.angleForThirdCircle);
        }
        //customBackground.angleForFirstCircleTest = (int)animator.getAnimatedValue() % 360;

        customBackground.angleForFirstCircleTest = customBackground.angleForFirstCircle;
        customBackground.angleForSecondCircleTest = customBackground.angleForSecondCircle;
        customBackground.angleForThirdCircleTest = customBackground.angleForThirdCircle;
        ///
        customBackground.invalidate();
        customBackground.setEndPointForStateGoToStateThree();
    }

    @Override
    public void initCoordinatesForSmallCircles(int _angleForFirstCircle, int _angleForSecondCircle, int _angleForThirdCircle) {
        if(customBackground.radiusForSmallCircle_1 == 10){

            customBackground.X2 = customBackground.findOrigoXForSmallCircle(_angleForSecondCircle);
            customBackground.Y2 = customBackground.findOrigoYForSmallCircle(_angleForSecondCircle);

            ///// X and Y for third small circle
            customBackground.X3 = customBackground.findOrigoXForSmallCircle(_angleForThirdCircle);
            customBackground.Y3 = customBackground.findOrigoYForSmallCircle(_angleForThirdCircle);
        }
        if(customBackground.radiusForSmallCircle_2 == 10){
            customBackground.X1 = customBackground.findOrigoXForSmallCircle(_angleForFirstCircle);
            customBackground.Y1 = customBackground.findOrigoYForSmallCircle(_angleForFirstCircle);

            customBackground.X3 = customBackground.findOrigoXForSmallCircle(_angleForThirdCircle);
            customBackground.Y3 = customBackground.findOrigoYForSmallCircle(_angleForThirdCircle);
        }
        if(customBackground.radiusForSmallCircle_3 == 10){
            customBackground.X1 = customBackground.findOrigoXForSmallCircle(_angleForFirstCircle);
            customBackground.Y1 = customBackground.findOrigoYForSmallCircle(_angleForFirstCircle);

            ///// X and Y for second small circle
            customBackground.X2 = customBackground.findOrigoXForSmallCircle(_angleForSecondCircle);
            customBackground.Y2 = customBackground.findOrigoYForSmallCircle(_angleForSecondCircle);
        }
        customBackground.invalidate();
    }

    @Override
    public boolean animationIsRunning() {
        return false;
    }
}
