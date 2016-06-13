package com.example.hm.myanimation.states.two;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

import com.example.hm.myanimation.CustomBackground;
import com.example.hm.myanimation.states.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ferenc on 2016.06.10..
 */
public class StateGoToStateOne implements State {
    private List<Animator> animatorsGroup1 = new ArrayList<>();
    private List<Animator> animatorsGroup2 = new ArrayList<>();
    private List<Animator> animatorsGroup3 = new ArrayList<>();
    private ValueAnimator radiusAnimator1;
    private ValueAnimator radiusAnimator2;
    private ValueAnimator radiusAnimator3;

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


    public StateGoToStateOne(CustomBackground _customBackground){
        customBackground = _customBackground;
    }

    @Override
    public void doFocus() {
        if(customBackground.touchedCircle == 1){
            setAnimatorForTwoCircle1();
            setAnimatorsGroup1();
        }
        if(customBackground.touchedCircle == 2){
            setAnimatorForTwoCircle2();
            setAnimatorsGroup2();
        }
        if(customBackground.touchedCircle == 3){
            setAnimatorForTwoCircle3();
            setAnimatorsGroup3();
        }
    }

    @Override
    public void setEvent(MotionEvent event) {

    }

    @Override
    public void stopAnimation() {

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {

        if(customBackground.touchedCircle == 1){
            customBackground.radiusForSmallCircle_1 = (float) radiusAnimator1.getAnimatedValue();

//            customBackground.angleForSecondCircle = (int)animatorForTwoCircle12.getAnimatedValue();
//            customBackground.angleForThirdCircle = (int) animatorForTwoCircle13.getAnimatedValue();
//
            initCoordinatesForSmallCircles( customBackground.angleForFirstCircle,
                    customBackground.angleForSecondCircle,
                    customBackground.angleForThirdCircle);
        }

        if( customBackground.touchedCircle == 2){
            customBackground.radiusForSmallCircle_2 = (float) radiusAnimator2.getAnimatedValue();

//            customBackground.angleForFirstCircle = (int)animatorForTwoCircle21.getAnimatedValue();
//            customBackground.angleForThirdCircle = (int) animatorForTwoCircle23.getAnimatedValue();

            initCoordinatesForSmallCircles( customBackground.angleForFirstCircle,
                    customBackground.angleForSecondCircle,
                    customBackground.angleForThirdCircle);
        }
        if( customBackground.touchedCircle == 3){
            customBackground.radiusForSmallCircle_3 = (float) radiusAnimator3.getAnimatedValue();

//            customBackground.angleForFirstCircle = (int)animatorForTwoCircle31.getAnimatedValue();
//            customBackground.angleForSecondCircle = (int) animatorForTwoCircle32.getAnimatedValue();

            initCoordinatesForSmallCircles( customBackground.angleForFirstCircle % 360,
                    customBackground.angleForSecondCircle % 360,
                    customBackground.angleForThirdCircle % 360);
        }

        customBackground.invalidate();
    }

    @Override
    public void initCoordinatesForSmallCircles(int _angleForFirstCircle, int _angleForSecondCircle, int _angleForThirdCircle) {
        customBackground.waveListener.setWaveProgress();

//        if(customBackground.touchedCircle == 1){
//            customBackground.X2 = customBackground.findOrigoXForSmallCircle(_angleForSecondCircle);
//            customBackground.Y2 = customBackground.findOrigoYForSmallCircle(_angleForSecondCircle);
//
//            ///// X and Y for third small circle
//            customBackground.X3 = customBackground.findOrigoXForSmallCircle(_angleForThirdCircle);
//            customBackground.Y3 = customBackground.findOrigoYForSmallCircle(_angleForThirdCircle);
//        }
//        if(customBackground.touchedCircle == 2){
//            customBackground.X1 = customBackground.findOrigoXForSmallCircle(_angleForFirstCircle);
//            customBackground.Y1 = customBackground.findOrigoYForSmallCircle(_angleForFirstCircle);
//
//            customBackground.X3 = customBackground.findOrigoXForSmallCircle(_angleForThirdCircle);
//            customBackground.Y3 = customBackground.findOrigoYForSmallCircle(_angleForThirdCircle);
//        }
//        if(customBackground.touchedCircle ==3){
//            customBackground.X1 = customBackground.findOrigoXForSmallCircle(_angleForFirstCircle);
//            customBackground.Y1 = customBackground.findOrigoYForSmallCircle(_angleForFirstCircle);
//
//            ///// X and Y for second small circle
//            customBackground.X2 = customBackground.findOrigoXForSmallCircle(_angleForSecondCircle);
//            customBackground.Y2 = customBackground.findOrigoYForSmallCircle(_angleForSecondCircle);
//        }
    }

    public void setAnimatorsGroup1(){
        AnimatorSet set1 = new AnimatorSet();
        set1.playTogether(animatorsGroup1);
        set1.start();
    }

    public void setAnimatorsGroup2(){
        AnimatorSet set2= new AnimatorSet();
        set2.playTogether(animatorsGroup2);
        set2.start();
    }

    public void setAnimatorsGroup3(){
        AnimatorSet set3 = new AnimatorSet();
        set3.playTogether(animatorsGroup3);
        set3.start();
    }

    public void setAnimatorForTwoCircle1(){
        customBackground.addNewPlatform(customBackground.touchedCircle);

//        animatorForTwoCircle11 = ValueAnimator.ofInt(customBackground.angleForFirstCircle, customBackground.angleForFirstCircle + 360);
//        animatorForTwoCircle11.setDuration(500);
//        animatorForTwoCircle11.setInterpolator(new LinearInterpolator());
//        animatorForTwoCircle11.addUpdateListener(this);
//        animatorsGroup1.add(animatorForTwoCircle11);

//        animatorForTwoCircle12 = ValueAnimator.ofInt(customBackground.angleForSecondCircle, customBackground.angleForSecondCircle - 30);
//        animatorForTwoCircle12.setDuration(500);
//        animatorForTwoCircle12.setInterpolator(new LinearInterpolator());
//        animatorForTwoCircle12.addUpdateListener(this);
//        animatorsGroup1.add(animatorForTwoCircle12);
//
//
//        animatorForTwoCircle13 = ValueAnimator.ofInt(customBackground.angleForThirdCircle, customBackground.angleForThirdCircle + 30);
//        animatorForTwoCircle13.setDuration(500);
//        animatorForTwoCircle13.setInterpolator(new LinearInterpolator());
//        animatorForTwoCircle13.addUpdateListener(this);
//        animatorsGroup1.add(animatorForTwoCircle13);

        radiusAnimator1 = ValueAnimator.ofFloat(customBackground.radiusForSmallCircles,10);
        radiusAnimator1.setDuration(500);
        radiusAnimator1.setInterpolator(new LinearInterpolator());
        radiusAnimator1.addUpdateListener(this);
        animatorsGroup1.add(radiusAnimator1);
        radiusAnimator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimationRunning = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorsGroup1.clear();
                isAnimationRunning = false;
                customBackground.setState(customBackground.one);
                customBackground.state.doFocus();
            }
        });

    }

    public void setAnimatorForTwoCircle2(){
        customBackground.addNewPlatform(customBackground.touchedCircle);

//        animatorForTwoCircle21 = ValueAnimator.ofInt(customBackground.angleForFirstCircle, customBackground.angleForFirstCircle + 30);
//        animatorForTwoCircle21.setDuration(500);
//        animatorForTwoCircle21.setInterpolator(new LinearInterpolator());
//        animatorForTwoCircle21.addUpdateListener(this);
//        animatorsGroup2.add(animatorForTwoCircle21);

//        animatorForTwoCircle22 = ValueAnimator.ofInt(customBackground.angleForSecondCircle, customBackground.angleForSecondCircle + 360);
//        animatorForTwoCircle22.setDuration(500);
//        animatorForTwoCircle22.setInterpolator(new LinearInterpolator());
//        animatorForTwoCircle22.addUpdateListener(this);
//        animatorsGroup2.add(animatorForTwoCircle22);

//        animatorForTwoCircle23 = ValueAnimator.ofInt(customBackground.angleForThirdCircle, customBackground.angleForThirdCircle - 30);
//        animatorForTwoCircle23.setDuration(500);
//        animatorForTwoCircle23.setInterpolator(new LinearInterpolator());
//        animatorForTwoCircle23.addUpdateListener(this);
//        animatorsGroup2.add(animatorForTwoCircle23);

        radiusAnimator2 = ValueAnimator.ofFloat(customBackground.radiusForSmallCircles,10);
        radiusAnimator2.setDuration(500);
        radiusAnimator2.setInterpolator(new LinearInterpolator());
        radiusAnimator2.addUpdateListener(this);
        animatorsGroup2.add(radiusAnimator2);
        radiusAnimator2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimationRunning = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorsGroup2.clear();
                isAnimationRunning = false;
                customBackground.setState(customBackground.one);
                customBackground.state.doFocus();
            }
        });

    }

    public void setAnimatorForTwoCircle3(){
        customBackground.addNewPlatform(customBackground.touchedCircle);

//        animatorForTwoCircle31 = ValueAnimator.ofInt(customBackground.angleForFirstCircle, customBackground.angleForFirstCircle - 30);
//        animatorForTwoCircle31.setDuration(500);
//        animatorForTwoCircle31.setInterpolator(new LinearInterpolator());
//        animatorForTwoCircle31.addUpdateListener(this);
//        animatorsGroup3.add(animatorForTwoCircle31);
//
//        animatorForTwoCircle32 = ValueAnimator.ofInt(customBackground.angleForSecondCircle, customBackground.angleForSecondCircle + 30);
//        animatorForTwoCircle32.setDuration(500);
//        animatorForTwoCircle32.setInterpolator(new LinearInterpolator());
//        animatorForTwoCircle32.addUpdateListener(this);
//        animatorsGroup3.add(animatorForTwoCircle32);

//        animatorForTwoCircle33 = ValueAnimator.ofInt(customBackground.angleForSecondCircle, customBackground.angleForSecondCircle + 360);
//        animatorForTwoCircle33.setDuration(500);
//        animatorForTwoCircle33.setInterpolator(new LinearInterpolator());
//        animatorForTwoCircle33.addUpdateListener(this);
//        animatorsGroup3.add(animatorForTwoCircle33);

        radiusAnimator3 = ValueAnimator.ofFloat(customBackground.radiusForSmallCircles,10);
        radiusAnimator3.setDuration(500);
        radiusAnimator3.setInterpolator(new LinearInterpolator());
        radiusAnimator3.addUpdateListener(this);
        animatorsGroup3.add(radiusAnimator3);
        radiusAnimator3.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimationRunning = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animatorsGroup3.clear();
                isAnimationRunning = false;
                customBackground.setState(customBackground.one);
                customBackground.state.doFocus();
            }
        });
    }

    @Override
    public boolean animationIsRunning() {
        return isAnimationRunning;
    }
}

