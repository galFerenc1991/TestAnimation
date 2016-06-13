package com.example.hm.myanimation.states.zero;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

import com.example.hm.myanimation.CustomBackground;
import com.example.hm.myanimation.states.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ferenc on 2016.06.12..
 */
public class StateZero implements State {
    private CustomBackground customBackground;
    private List<Animator> animatorsGroup = new ArrayList<>();
    AnimatorSet set;
    private ValueAnimator animator1;
    private ValueAnimator animator2;
    private ValueAnimator animator3;


    public StateZero(CustomBackground _customBackground){
        customBackground = _customBackground;
    }

    @Override
    public void doFocus() {
        setAnimator();
        setAnimatorsGroup();
    }

    public void stopAnimation(){
        set.cancel();
    }

    @Override
    public void setEvent(MotionEvent event) {
        customBackground.initCenterReAnimatedSmallCircles(event);

        if (event.getAction() == MotionEvent.ACTION_UP){
//
//            if(customBackground.initTapTheCircle()){
//                if(customBackground.tapUpInCenterCircle(event)){
//                    customBackground.setState(customBackground.stateGoToStateZero);
//                    customBackground.stateGoToStateZero.doFocus();
//
//                } else{
//                    customBackground.setState(customBackground.stateOneReAnimate);
//                    customBackground.stateOneReAnimate.doFocus();
//                }
//            }
            if(customBackground.tapUpInCenterCircle(event) && customBackground.touchedCircle == 0){
//                customBackground.initTouchedPlatform(event);
//                if(customBackground.touchedPlatform == 1){
//                    Log.d("platform", "" + customBackground.touchedPlatform);
                customBackground.setState(customBackground.stateComeBackToStateOne);
                customBackground.stateComeBackToStateOne.doFocus();
                //}
            }
        }
    }

    public void setAnimatorsGroup(){
        set = new AnimatorSet();
        set.playTogether(animatorsGroup);
        set.play(animatorsGroup.get(animatorsGroup.size()-1));
        set.start();
    }

    public void setAnimator() {
        animator1 = ValueAnimator.ofInt(customBackground.angleForFirstCircle, customBackground.angleForFirstCircle + 360 );
        animator1.setDuration(10000);
        animator1.setRepeatCount(ValueAnimator.INFINITE);
        animator1.setInterpolator(new LinearInterpolator());
        animator1.addUpdateListener(this);
        animatorsGroup.add(animator1);

        animator2 = ValueAnimator.ofInt(customBackground.angleForSecondCircle, customBackground.angleForSecondCircle + 360 );
        animator2.setDuration(10000);
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator2.setInterpolator(new LinearInterpolator());
        animator2.addUpdateListener(this);
        animatorsGroup.add(animator2);


        animator3 = ValueAnimator.ofInt(customBackground.angleForThirdCircle, customBackground.angleForThirdCircle + 360 );
        animator3.setDuration(10000);
        animator3.setRepeatCount(ValueAnimator.INFINITE);
        animator3.setInterpolator(new LinearInterpolator());
        animator3.addUpdateListener(this);
        animatorsGroup.add(animator3);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        customBackground.angleForFirstCircle = (int) animator1.getAnimatedValue() % 360;
        customBackground.angleForSecondCircle = (int) animator2.getAnimatedValue() % 360;
        customBackground.angleForThirdCircle = (int) animator3.getAnimatedValue() % 360;

        initCoordinatesForSmallCircles(customBackground.angleForFirstCircle,
                customBackground.angleForSecondCircle,
                customBackground.angleForThirdCircle);

        customBackground.setEndPointForStateGoToStateOne();
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
        return false;
    }
}
