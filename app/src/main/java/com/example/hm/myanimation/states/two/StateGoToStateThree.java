package com.example.hm.myanimation.states.two;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

import com.example.hm.myanimation.CustomBackground;
import com.example.hm.myanimation.MyTypeEvaluator;
import com.example.hm.myanimation.states.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ferenc on 2016.06.08..
 */
public class StateGoToStateThree implements State {
    List<Animator> animatorsGroup = new ArrayList<>();
    private ValueAnimator pointAnimator;
    private ValueAnimator inverseRadiusAnimator;
    private ValueAnimator inverseAnimatorForTwoCircle;
    private CustomBackground customBackground;
    private boolean isAnimationRunning = true;

    public StateGoToStateThree(CustomBackground _customBackground){
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
        if(customBackground.titles.get(0).getTouchedCircle() == 1){
            customBackground.radiusForSmallCircle_1 = (float)inverseRadiusAnimator.getAnimatedValue();
            customBackground.angleForSecondCircle =  customBackground.angleForSecondCircleTest + (int)inverseAnimatorForTwoCircle.getAnimatedValue()/2;
            customBackground.angleForThirdCircle =  customBackground.angleForThirdCircleTest - (int) inverseAnimatorForTwoCircle.getAnimatedValue()/2;
            initCoordinatesForSmallCircles( customBackground.findAngelForSmallCircle(customBackground.optimalPoint),
                    customBackground.angleForSecondCircle,
                    customBackground.angleForThirdCircle);
        }
        if( customBackground.titles.get(0).getTouchedCircle() == 2){
            customBackground.radiusForSmallCircle_2 = (float)inverseRadiusAnimator.getAnimatedValue();
            customBackground.angleForFirstCircle =  customBackground.angleForFirstCircleTest - (int)inverseAnimatorForTwoCircle.getAnimatedValue()/2;
            customBackground.angleForThirdCircle =  customBackground.angleForThirdCircleTest + (int) inverseAnimatorForTwoCircle.getAnimatedValue()/2;
            initCoordinatesForSmallCircles( customBackground.angleForFirstCircle,
                    customBackground.findAngelForSmallCircle(customBackground.optimalPoint),
                    customBackground.angleForThirdCircle);
        }
        if( customBackground.titles.get(0).getTouchedCircle() == 3){
            customBackground.radiusForSmallCircle_3 = (float)inverseRadiusAnimator.getAnimatedValue();
            customBackground.angleForFirstCircle =  customBackground.angleForFirstCircleTest + (int)inverseAnimatorForTwoCircle.getAnimatedValue()/2;
            customBackground.angleForSecondCircle =  customBackground.angleForSecondCircleTest - (int) inverseAnimatorForTwoCircle.getAnimatedValue()/2;
            initCoordinatesForSmallCircles( customBackground.angleForFirstCircle,
                    customBackground.angleForSecondCircle,
                    customBackground.findAngelForSmallCircle(customBackground.optimalPoint));
        }

        customBackground.invalidate();
    }

    @Override
    public void initCoordinatesForSmallCircles(int _angleForFirstCircle, int _angleForSecondCircle, int _angleForThirdCircle) {
       // customBackground.waveListener.setWaveProgress();

        customBackground.X1 = customBackground.findOrigoXForSmallCircle(_angleForFirstCircle);
        customBackground.Y1 = customBackground.findOrigoYForSmallCircle(_angleForFirstCircle);

        ///// X and Y for second small circle
        customBackground.X2 = customBackground.findOrigoXForSmallCircle(_angleForSecondCircle);
        customBackground.Y2 = customBackground.findOrigoYForSmallCircle(_angleForSecondCircle);

        ///// X and Y for third small circle
        customBackground.X3 = customBackground.findOrigoXForSmallCircle(_angleForThirdCircle);
        customBackground.Y3 = customBackground.findOrigoYForSmallCircle(_angleForThirdCircle);

    }

    public void setAnimatorsGroup(){
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorsGroup);
        set.play(animatorsGroup.get(animatorsGroup.size()-1));
        set.start();
    }

    public void setAnimatorForTwoCircle(){

        pointAnimator = ObjectAnimator.ofObject(this, "optimalPoint", new MyTypeEvaluator(),
                customBackground.startForStateGoToStateThree,
                customBackground.endPointForStateGoToStateThree);
        pointAnimator.setDuration(500);
        pointAnimator.setInterpolator(new LinearInterpolator());
        pointAnimator.addUpdateListener(this);
        animatorsGroup.add(pointAnimator);

        inverseRadiusAnimator = ValueAnimator.ofFloat(0,customBackground.radiusForSmallCircles);
        inverseRadiusAnimator.setDuration(500);
        inverseRadiusAnimator.setInterpolator(new LinearInterpolator());
        inverseRadiusAnimator.addUpdateListener(this);
        animatorsGroup.add(inverseRadiusAnimator);

        inverseAnimatorForTwoCircle = ValueAnimator.ofInt(0,60);
        inverseAnimatorForTwoCircle.setDuration(500);
        inverseAnimatorForTwoCircle.setInterpolator(new LinearInterpolator());
        inverseAnimatorForTwoCircle.addUpdateListener(this);
        animatorsGroup.add(inverseAnimatorForTwoCircle);
        inverseAnimatorForTwoCircle.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimationRunning = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                customBackground.angleForFirstCircleTest = customBackground.angleForFirstCircle;
                customBackground.angleForSecondCircleTest = customBackground.angleForSecondCircle;
                customBackground.angleForThirdCircleTest = customBackground.angleForThirdCircle;
                customBackground.titles.clear();
                customBackground.waveListener.setWaveProgress();

                isAnimationRunning = false;
                customBackground.setState(customBackground.three);
                customBackground.state.doFocus();
            }
        });

    }
    public void setOptimalPoint(Point point) {
        customBackground.optimalPoint = point;

    }

    @Override
    public boolean animationIsRunning() {
        return isAnimationRunning;
    }
}
