package com.example.hm.myanimation.states.two;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

import com.example.hm.myanimation.CustomBackground;
import com.example.hm.myanimation.states.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ferenc on 2016.06.07..
 */
public class StateTwo implements State {

    private List<Animator> animatorsGroup1 = new ArrayList<>();
    private List<Animator> animatorsGroup2 = new ArrayList<>();
    private List<Animator> animatorsGroup3 = new ArrayList<>();

    AnimatorSet set1;
    AnimatorSet set2;
    AnimatorSet set3;

    private ValueAnimator animatorForTwoCircle11;
    private ValueAnimator animatorForTwoCircle12;
    private ValueAnimator animatorForTwoCircle13;

    private ValueAnimator animatorForTwoCircle21;
    private ValueAnimator animatorForTwoCircle22;
    private ValueAnimator animatorForTwoCircle23;

    private ValueAnimator animatorForTwoCircle31;
    private ValueAnimator animatorForTwoCircle32;
    private ValueAnimator animatorForTwoCircle33;

    private CustomBackground customBackground;
    private boolean isAnimationRunning = true;

    public StateTwo(CustomBackground _customBackground){
        customBackground = _customBackground;
    }

    @Override
    public void doFocus() {
        if(customBackground.radiusForSmallCircle_1 == 10){
            setAnimatorForTwoCircle1();
            setAnimatorsGroup1();
        }
        if(customBackground.radiusForSmallCircle_2 == 10){
            setAnimatorForTwoCircle2();
            setAnimatorsGroup2();
        }
        if(customBackground.radiusForSmallCircle_3 == 10){
            setAnimatorForTwoCircle3();
            setAnimatorsGroup3();
        }
    }

    @Override
    public void setEvent(MotionEvent event) {
        customBackground.initCenterReAnimatedSmallCircles(event);
        if (event.getAction() == MotionEvent.ACTION_UP){

            if(customBackground.initTapTheCircle()){
                if(customBackground.tapUpInCenterCircle(event)){
                    customBackground.setState(customBackground.stateGoToStateOne);
                    customBackground.stateGoToStateOne.doFocus();

                } else{
                    customBackground.setState(customBackground.stateTwoReanimate);
                    customBackground.stateTwoReanimate.doFocus();
                }
            }
            if(customBackground.tapUpInCenterCircle(event) && customBackground.touchedCircle == 0){
//                customBackground.initTouchedPlatform(event);
//                if(customBackground.touchedPlatform == 1){
//                    Log.d("platform", "" + customBackground.touchedPlatform);
                    customBackground.setState(customBackground.stateGoToStateThree);
                    customBackground.stateGoToStateThree.doFocus();
                //}
            }
        }
    }

    @Override
    public void stopAnimation() {
        if(customBackground.radiusForSmallCircle_1 == 10){
            set1.cancel();
        }
        if(customBackground.radiusForSmallCircle_2 == 10){
            set2.cancel();
        }
        if(customBackground.radiusForSmallCircle_3 == 10){
            set3.cancel();
        }

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {

        if(customBackground.radiusForSmallCircle_1 == 10){

            customBackground.angleForFirstCircle = (int) animatorForTwoCircle11.getAnimatedValue() % 360;

            customBackground.angleForSecondCircle = (int) animatorForTwoCircle12.getAnimatedValue() % 360;
            customBackground.angleForThirdCircle = (int) animatorForTwoCircle13.getAnimatedValue() % 360;

            initCoordinatesForSmallCircles(customBackground.angleForFirstCircle,
                                           customBackground.angleForSecondCircle,
                                           customBackground.angleForThirdCircle);
        }
        if(customBackground.radiusForSmallCircle_2 == 10){
            customBackground.angleForSecondCircle = (int) animatorForTwoCircle22.getAnimatedValue() % 360;

            customBackground.angleForFirstCircle = (int) animatorForTwoCircle21.getAnimatedValue() % 360;
            customBackground.angleForThirdCircle = (int) animatorForTwoCircle23.getAnimatedValue() % 360;

            initCoordinatesForSmallCircles(customBackground.angleForFirstCircle,
                                           customBackground.angleForSecondCircle,
                                           customBackground.angleForThirdCircle);

        }
        if(customBackground.radiusForSmallCircle_3 == 10){
            customBackground.angleForThirdCircle = (int) animatorForTwoCircle33.getAnimatedValue() % 360;

            customBackground.angleForFirstCircle = (int) animatorForTwoCircle31.getAnimatedValue() % 360;
            customBackground.angleForSecondCircle = (int) animatorForTwoCircle32.getAnimatedValue() % 360;

            initCoordinatesForSmallCircles(customBackground.angleForFirstCircle,
                                           customBackground.angleForSecondCircle,
                                           customBackground.angleForThirdCircle);
        }

        customBackground.invalidate();
        customBackground.setEndPointForStateGoToStateThree();
        Log.d("Endpoint", "" + customBackground.endPointForStateGoToStateThree);
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

    public void setAnimatorsGroup1(){
        set1 = new AnimatorSet();
        set1.playTogether(animatorsGroup1);
        set1.start();
    }

    public void setAnimatorsGroup2(){
        set2= new AnimatorSet();
        set2.playTogether(animatorsGroup2);
        set2.start();
    }

    public void setAnimatorsGroup3(){
        set3 = new AnimatorSet();
        set3.playTogether(animatorsGroup3);
        set3.start();
    }

    public void setAnimatorForTwoCircle1(){

        animatorForTwoCircle11 = ValueAnimator.ofInt(customBackground.angleForFirstCircle, customBackground.angleForFirstCircle + 360);
        animatorForTwoCircle11.setDuration(10000);
        animatorForTwoCircle11.setRepeatCount(ValueAnimator.INFINITE);
        animatorForTwoCircle11.setInterpolator(new LinearInterpolator());
        animatorForTwoCircle11.addUpdateListener(this);
        animatorsGroup1.add(animatorForTwoCircle11);

        animatorForTwoCircle12 = ValueAnimator.ofInt(customBackground.angleForSecondCircle, customBackground.angleForSecondCircle + 360);
        animatorForTwoCircle12.setDuration(10000);
        animatorForTwoCircle12.setRepeatCount(ValueAnimator.INFINITE);
        animatorForTwoCircle12.setInterpolator(new LinearInterpolator());
        animatorForTwoCircle12.addUpdateListener(this);
        animatorsGroup1.add(animatorForTwoCircle12);


        animatorForTwoCircle13 = ValueAnimator.ofInt(customBackground.angleForThirdCircle, customBackground.angleForThirdCircle + 360);
        animatorForTwoCircle13.setDuration(10000);
        animatorForTwoCircle13.setRepeatCount(ValueAnimator.INFINITE);
        animatorForTwoCircle13.setInterpolator(new LinearInterpolator());
        animatorForTwoCircle13.addUpdateListener(this);
        animatorsGroup1.add(animatorForTwoCircle13);

    }

    public void setAnimatorForTwoCircle2(){

        animatorForTwoCircle21 = ValueAnimator.ofInt(customBackground.angleForFirstCircle, customBackground.angleForFirstCircle + 360);
        animatorForTwoCircle21.setDuration(10000);
        animatorForTwoCircle21.setRepeatCount(ValueAnimator.INFINITE);
        animatorForTwoCircle21.setInterpolator(new LinearInterpolator());
        animatorForTwoCircle21.addUpdateListener(this);
        animatorsGroup2.add(animatorForTwoCircle21);

        animatorForTwoCircle22 = ValueAnimator.ofInt(customBackground.angleForSecondCircle, customBackground.angleForSecondCircle + 360);
        animatorForTwoCircle22.setDuration(10000);
        animatorForTwoCircle22.setRepeatCount(ValueAnimator.INFINITE);
        animatorForTwoCircle22.setInterpolator(new LinearInterpolator());
        animatorForTwoCircle22.addUpdateListener(this);
        animatorsGroup2.add(animatorForTwoCircle22);

        animatorForTwoCircle23 = ValueAnimator.ofInt(customBackground.angleForThirdCircle, customBackground.angleForThirdCircle + 360);
        animatorForTwoCircle23.setDuration(10000);
        animatorForTwoCircle23.setRepeatCount(ValueAnimator.INFINITE);
        animatorForTwoCircle23.setInterpolator(new LinearInterpolator());
        animatorForTwoCircle23.addUpdateListener(this);
        animatorsGroup2.add(animatorForTwoCircle23);
    }

    public void setAnimatorForTwoCircle3(){

        animatorForTwoCircle31 = ValueAnimator.ofInt(customBackground.angleForFirstCircle, customBackground.angleForFirstCircle + 360);
        animatorForTwoCircle31.setDuration(10000);
        animatorForTwoCircle31.setRepeatCount(ValueAnimator.INFINITE);
        animatorForTwoCircle31.setInterpolator(new LinearInterpolator());
        animatorForTwoCircle31.addUpdateListener(this);
        animatorsGroup3.add(animatorForTwoCircle31);

        animatorForTwoCircle32 = ValueAnimator.ofInt(customBackground.angleForSecondCircle, customBackground.angleForSecondCircle + 360);
        animatorForTwoCircle32.setDuration(10000);
        animatorForTwoCircle32.setRepeatCount(ValueAnimator.INFINITE);
        animatorForTwoCircle32.setInterpolator(new LinearInterpolator());
        animatorForTwoCircle32.addUpdateListener(this);
        animatorsGroup3.add(animatorForTwoCircle32);

        animatorForTwoCircle33 = ValueAnimator.ofInt(customBackground.angleForThirdCircle, customBackground.angleForThirdCircle + 360);
        animatorForTwoCircle33.setDuration(10000);
        animatorForTwoCircle33.setRepeatCount(ValueAnimator.INFINITE);
        animatorForTwoCircle33.setInterpolator(new LinearInterpolator());
        animatorForTwoCircle33.addUpdateListener(this);
        animatorsGroup3.add(animatorForTwoCircle33);
    }

    @Override
    public boolean animationIsRunning() {
        return false;
    }
}
