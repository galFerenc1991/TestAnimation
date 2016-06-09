package com.example.hm.myanimation.states.three;

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
public class StateAnimateTwoCircles implements State {
    List<Animator> animatorsGroup = new ArrayList<>();
    private ValueAnimator radiusAnimator;
    private ValueAnimator animatorForTwoCircle;
    private CustomBackground customBackground;
    private boolean isAnimationRunning = true;


    public StateAnimateTwoCircles(CustomBackground _customBackground){
        customBackground = _customBackground;
    }

    @Override
    public void doFocus() {
        setAnimatorForTwoCircle();
        setAnimatorsGroup();
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
                customBackground.radiusForSmallCircle_1 = (float)radiusAnimator.getAnimatedValue();
            customBackground.angleForSecondCircle =  customBackground.angleForSecondCircleTest - (int)animatorForTwoCircle.getAnimatedValue()/2;
            customBackground.angleForThirdCircle =  customBackground.angleForThirdCircleTest + (int) animatorForTwoCircle.getAnimatedValue()/2;
                initCoordinatesForSmallCircles( customBackground.angleForFirstCircle,
                        customBackground.angleForSecondCircle,
                        customBackground.angleForThirdCircle);
            }
            if( customBackground.touchedCircle == 2){
                customBackground.radiusForSmallCircle_2 = (float)radiusAnimator.getAnimatedValue();
                customBackground.angleForFirstCircle =  customBackground.angleForFirstCircleTest + (int)animatorForTwoCircle.getAnimatedValue()/2;
                customBackground.angleForThirdCircle =  customBackground.angleForThirdCircleTest - (int) animatorForTwoCircle.getAnimatedValue()/2;
                initCoordinatesForSmallCircles( customBackground.angleForFirstCircle,
                        customBackground.angleForSecondCircle,
                        customBackground.angleForThirdCircle);
            }
            if( customBackground.touchedCircle == 3){
                customBackground.radiusForSmallCircle_3 = (float)radiusAnimator.getAnimatedValue();
                customBackground.angleForFirstCircle =  customBackground.angleForFirstCircleTest - (int)animatorForTwoCircle.getAnimatedValue()/2;
                customBackground.angleForSecondCircle =  customBackground.angleForSecondCircleTest + (int) animatorForTwoCircle.getAnimatedValue()/2;
                initCoordinatesForSmallCircles( customBackground.angleForFirstCircle,
                        customBackground.angleForSecondCircle,
                        customBackground.angleForThirdCircle);
            }

        customBackground.invalidate();
    }

    @Override
        public void initCoordinatesForSmallCircles(int _angleForFirstCircle, int _angleForSecondCircle, int _angleForThirdCircle) {
        customBackground.waveListener.setWaveProgress();

        if(customBackground.touchedCircle == 1){
            customBackground.X2 = customBackground.findOrigoXForSmallCircle(_angleForSecondCircle);
            customBackground.Y2 = customBackground.findOrigoYForSmallCircle(_angleForSecondCircle);

            ///// X and Y for third small circle
            customBackground.X3 = customBackground.findOrigoXForSmallCircle(_angleForThirdCircle);
            customBackground.Y3 = customBackground.findOrigoYForSmallCircle(_angleForThirdCircle);
        }
        if(customBackground.touchedCircle == 2){
            customBackground.X1 = customBackground.findOrigoXForSmallCircle(_angleForFirstCircle);
            customBackground.Y1 = customBackground.findOrigoYForSmallCircle(_angleForFirstCircle);

            customBackground.X3 = customBackground.findOrigoXForSmallCircle(_angleForThirdCircle);
            customBackground.Y3 = customBackground.findOrigoYForSmallCircle(_angleForThirdCircle);
        }
        if(customBackground.touchedCircle ==3){
            customBackground.X1 = customBackground.findOrigoXForSmallCircle(_angleForFirstCircle);
            customBackground.Y1 = customBackground.findOrigoYForSmallCircle(_angleForFirstCircle);

            ///// X and Y for second small circle
            customBackground.X2 = customBackground.findOrigoXForSmallCircle(_angleForSecondCircle);
            customBackground.Y2 = customBackground.findOrigoYForSmallCircle(_angleForSecondCircle);
        }
    }

    public void setAnimatorsGroup(){
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorsGroup);
        set.play(animatorsGroup.get(animatorsGroup.size()-1));
        set.start();
    }

    public void setAnimatorForTwoCircle(){
        customBackground.addNewPlatform(customBackground.touchedCircle);
        Log.d("mz","" + customBackground.titles.size());

        radiusAnimator = ValueAnimator.ofFloat(customBackground.radiusForSmallCircles,10);
        radiusAnimator.setDuration(500);
        radiusAnimator.setInterpolator(new LinearInterpolator());
        radiusAnimator.addUpdateListener(this);
        animatorsGroup.add(radiusAnimator);

        animatorForTwoCircle = ValueAnimator.ofInt(0,60);
        animatorForTwoCircle.setDuration(500);
        animatorForTwoCircle.setInterpolator(new LinearInterpolator());
        animatorForTwoCircle.addUpdateListener(this);
        animatorsGroup.add(animatorForTwoCircle);
        animatorForTwoCircle.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimationRunning = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //////
//                customBackground.angleForFirstCircleTest = customBackground.angleForFirstCircle;
//                customBackground.angleForSecondCircleTest = customBackground.angleForSecondCircle;
//                customBackground.angleForThirdCircleTest = customBackground.angleForThirdCircle;
                //////
                isAnimationRunning = false;
                customBackground.setState(customBackground.two);
                customBackground.state.doFocus();
            }
        });

    }

    @Override
    public boolean animationIsRunning() {
        return isAnimationRunning;
    }
}
