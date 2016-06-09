package com.example.hm.myanimation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.hm.myanimation.states.State;
import com.example.hm.myanimation.states.three.StateReAnimate;
import com.example.hm.myanimation.states.two.StateGoToStateThree;
import com.example.hm.myanimation.states.two.StateTwo;
import com.example.hm.myanimation.states.three.StateAnimateTwoCircles;
import com.example.hm.myanimation.states.three.StateThree;
import com.example.hm.myanimation.states.two.StateTwoReanimate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ferenc on 2016.05.30..
 */
public class CustomBackground extends View implements ValueAnimator.AnimatorUpdateListener{
    public WaveListener waveListener;
    public List<Title> titles = new ArrayList<>();
    public int touchedPlatform;
    public State state;
    public StateThree three;
    public StateReAnimate stateReAnimate;
    public StateAnimateTwoCircles stateAnimateTwoCircles;
    public StateTwo two;
    public StateTwoReanimate stateTwoReanimate;
    public StateGoToStateThree stateGoToStateThree;
    private  boolean tapUpInCenterCircle;
    private Paint backGroundPaint;
    private Paint paintForWhiteLines;
    private Paint paintForSmallCircles;
    private static float origoX;
    private static float origoY;
    public int angleForFirstCircle = 0;
    public int angleForSecondCircle = 120;
    public int angleForThirdCircle = 240;
    //////Test values/////
    public int angleForFirstCircleTest;
    public int angleForSecondCircleTest;
    public int angleForThirdCircleTest;
    /////////////////////
    public int X1;
    public int Y1;
    public int X2;
    public int Y2;
    public int X3;
    public int Y3;
    public Point optimalPoint;
    private Point startPoint;
    public Point endPoint;
    public Point startForStateGoToStateThree;
    public Point endPointForStateGoToStateThree;
    public float radiusForSmallCircles;
    public float radiusForSmallCircle_1;
    public float radiusForSmallCircle_2;
    public float radiusForSmallCircle_3;
    private double radiusTouch;
    private double radiusTouch1;
    private double radiusTouch2;
    private double radiusTouch3;
    public GestureDetector mGestureDetector;
    public int touchedCircle;
    private int[] colors = {Color.argb(0, 0, 0, 0)
            ,Color.argb(50, 38, 64, 125)
            ,Color.argb(255, 38, 64, 125)
            ,Color.argb(255, 31, 51, 112)
            ,Color.argb(255, 4, 32, 85)};
    private float[] steps = {0.0f, 0.5f, 0.5000000001f, 0.65f, 1.0f};

    public CustomBackground(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomBackground(Context context) {
        this(context, null);
    }

    public CustomBackground(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();
        initPoint();
        mGestureDetector = new GestureDetector(context, new GestureListener());
        mGestureDetector.setIsLongpressEnabled(false);
        initStates();
    }

    public void initStates(){
        three = new StateThree(this);
        stateReAnimate = new StateReAnimate(this);
        stateAnimateTwoCircles = new StateAnimateTwoCircles(this);
        two = new StateTwo(this);
        stateTwoReanimate = new StateTwoReanimate(this);
        stateGoToStateThree = new StateGoToStateThree(this);
        state = three;
    }

    public void initPoint(){
        endPointForStateGoToStateThree = new Point();
        startForStateGoToStateThree = new Point();
        optimalPoint = new Point();
        startPoint = new Point();
        endPoint = new Point();
    }

    public void initPaint(){

        paintForWhiteLines = new Paint();
        DashPathEffect dashPath = new DashPathEffect(new float[] {5,5}, 1);
        paintForWhiteLines.setPathEffect(dashPath);
        paintForWhiteLines.setStyle(Paint.Style.STROKE);
        paintForWhiteLines.setColor(Color.WHITE);
    }

    public void initPaintForBackground(){
        backGroundPaint = new Paint();
        backGroundPaint.setShader(new RadialGradient(origoX,
                origoY,
                (float)getWidth()/2,
                colors,
                steps,
                Shader.TileMode.MIRROR));
    }

    public void initPaintForSmallCircles(){
        paintForSmallCircles = new Paint();
        paintForSmallCircles.setColor(getResources().getColor(R.color.smallCircleColor));
    }

    public void initOrigoCenterCircle(){
        origoY = (getHeight()/2f);
        origoX = (getWidth()/2f);
    }

    public void initRadiusForSmallCircle(){
        radiusForSmallCircles = getWidth()/8f * 0.9f;
        radiusForSmallCircle_1 =  getWidth()/8f * 0.9f;
        radiusForSmallCircle_2 =  getWidth()/8f * 0.9f;
        radiusForSmallCircle_3 =  getWidth()/8f * 0.9f;
    }

    public void drawBackgroundCircles(Canvas _canvas){
        _canvas.drawCircle(origoX, origoY, getHeight()*10, backGroundPaint);
    }
    public void drawWhiteLines(Canvas _canvas){
        _canvas.drawCircle(origoX,origoY,getWidth()*3/8f, paintForWhiteLines);
        _canvas.drawCircle(origoX,origoY,getWidth()*4/8f, paintForWhiteLines);
        _canvas.drawCircle(origoX,origoY,getWidth()*5/8f, paintForWhiteLines);
        _canvas.drawCircle(origoX,origoY,getWidth()*6/8f, paintForWhiteLines);
    }

    public void initCoordinatesForSmallCircles(int _angleForFirstCircle,
                                               int _angleForSecondCircle,
                                               int _angleForThirdCircle){

            if(touchedCircle == 1){
                X2 = findOrigoXForSmallCircle(_angleForSecondCircle);
                Y2 = findOrigoYForSmallCircle(_angleForSecondCircle);

                ///// X and Y for third small circle
                X3 = findOrigoXForSmallCircle(_angleForThirdCircle);
                Y3 = findOrigoYForSmallCircle(_angleForThirdCircle);
            }
            if(touchedCircle == 2){
                X1 = findOrigoXForSmallCircle(_angleForFirstCircle);
                Y1 = findOrigoYForSmallCircle(_angleForFirstCircle);

                X3 = findOrigoXForSmallCircle(_angleForThirdCircle);
                Y3 = findOrigoYForSmallCircle(_angleForThirdCircle);
            }
            if(touchedCircle ==3){
                X1 = findOrigoXForSmallCircle(_angleForFirstCircle);
                Y1 = findOrigoYForSmallCircle(_angleForFirstCircle);

                ///// X and Y for second small circle
                X2 = findOrigoXForSmallCircle(_angleForSecondCircle);
                Y2 = findOrigoYForSmallCircle(_angleForSecondCircle);
            }
            if(touchedCircle == 0 || tapUpInCenterCircle == false){
                ///// X and Y for first small circle
                X1 = findOrigoXForSmallCircle(_angleForFirstCircle);
                Y1 = findOrigoYForSmallCircle(_angleForFirstCircle);

                ///// X and Y for second small circle
                X2 = findOrigoXForSmallCircle(_angleForSecondCircle);
                Y2 = findOrigoYForSmallCircle(_angleForSecondCircle);

                ///// X and Y for third small circle
                X3 = findOrigoXForSmallCircle(_angleForThirdCircle);
                Y3 = findOrigoYForSmallCircle(_angleForThirdCircle);
            }
    }

    public void drawSmallCircles1(Canvas _canvas){
        _canvas.drawCircle(X1,Y1, radiusForSmallCircle_1, paintForSmallCircles);
    }

    public void drawSmallCircles2(Canvas _canvas){
        _canvas.drawCircle(X2,Y2, radiusForSmallCircle_2, paintForSmallCircles);
    }

    public void drawSmallCircles3(Canvas _canvas){
        _canvas.drawCircle(X3,Y3, radiusForSmallCircle_3, paintForSmallCircles);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initOrigoCenterCircle();
        initPaintForBackground();
        initPaintForSmallCircles();
        drawBackgroundCircles(canvas);
        drawWhiteLines(canvas);
        drawSmallCircles1(canvas);
        drawSmallCircles2(canvas);
        drawSmallCircles3(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        initRadiusForSmallCircle();
        state.doFocus();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
//        if(animation == animator1){
//
//            if(touchedCircle == 1){
//                radiusForSmallCircle_1 = (float)radiusAnimator.getAnimatedValue();
//                angleForSecondCircle = angleForSecondCircleTest - (int)animator1.getAnimatedValue()/2;
//                angleForThirdCircle = angleForThirdCircleTest + (int) animator1.getAnimatedValue()/2;
//                initCoordinatesForSmallCircles(angleForFirstCircle, angleForSecondCircle, angleForThirdCircle);
//            }
//            if(touchedCircle == 2){
//                radiusForSmallCircle_2 = (float)radiusAnimator.getAnimatedValue();
//                angleForFirstCircle = angleForFirstCircleTest + (int)animator1.getAnimatedValue()/2;
//                angleForThirdCircle = angleForThirdCircleTest - (int) animator1.getAnimatedValue()/2;
//                initCoordinatesForSmallCircles(angleForFirstCircle, angleForSecondCircle, angleForThirdCircle);
//
//            }
//            if(touchedCircle == 3){
//                radiusForSmallCircle_3 = (float)radiusAnimator.getAnimatedValue();
//                angleForFirstCircle = angleForFirstCircleTest - (int)animator1.getAnimatedValue()/2;
//                angleForSecondCircle = angleForSecondCircleTest + (int) animator1.getAnimatedValue()/2;
//                initCoordinatesForSmallCircles(angleForFirstCircle, angleForSecondCircle, angleForThirdCircle);
//            }
//        }
        invalidate();
    }

    public void setState(State _state){
        state = _state;
    }

    public void initCenterReAnimatedSmallCircles(MotionEvent _e){
        if(radiusForSmallCircle_1 == 10){
            X1 = (int)_e.getX();
            Y1 = (int)_e.getY();
            startForStateGoToStateThree.set(X1,Y1);
        }
        if(radiusForSmallCircle_2 == 10){
            X2 = (int)_e.getX();
            Y2 = (int)_e.getY();
            startForStateGoToStateThree.set(X2,Y2);
        }
        if(radiusForSmallCircle_3 == 10){
            X3 = (int)_e.getX();
            Y3 = (int)_e.getY();
            startForStateGoToStateThree.set(X3,Y3);
        }
        invalidate();
    }

    public Point setStartPoint(){
        if(touchedCircle == 1){
            startPoint.set(X1,Y1);
        }
        if(touchedCircle == 2){
            startPoint.set(X2,Y2);
        }
        if(touchedCircle == 3){
            startPoint.set(X3,Y3);
        }
        return startPoint;
    }

    public void setEndPointForStateGoToStateThree(){
        if(titles.get(0).getTouchedCircle() == 1){
            endPointForStateGoToStateThree.set(findOrigoXForSmallCircle(angleForFirstCircle),findOrigoYForSmallCircle(angleForFirstCircle));
        }
        if(titles.get(0).getTouchedCircle() ==  2){
            endPointForStateGoToStateThree.set(findOrigoXForSmallCircle(angleForSecondCircle),findOrigoYForSmallCircle(angleForSecondCircle));
        }
        if(titles.get(0).getTouchedCircle() ==  3){
            endPointForStateGoToStateThree.set(findOrigoXForSmallCircle(angleForThirdCircle),findOrigoYForSmallCircle(angleForThirdCircle));
        }

    }
    public void setEndPoint(){
        if(touchedCircle == 1){
            endPoint.set(findOrigoXForSmallCircle(angleForFirstCircle),findOrigoYForSmallCircle(angleForFirstCircle));
        }
        if(touchedCircle == 2){
            endPoint.set(findOrigoXForSmallCircle(angleForSecondCircle),findOrigoYForSmallCircle(angleForSecondCircle));
        }
        if(touchedCircle == 3){
            endPoint.set(findOrigoXForSmallCircle(angleForThirdCircle),findOrigoYForSmallCircle(angleForThirdCircle));
        }
    }

    public void initTouchedPlatform(MotionEvent _event){
        //float X = _event.getX();
        float Y = _event.getY();
        float bottomCenterCircle = getHeight()/2f + getWidth()/4f;
        float sectorHeight = getWidth()/2f/3f;
        if(bottomCenterCircle > Y && Y > bottomCenterCircle - sectorHeight ){
            touchedPlatform = 1;
        }
        if(bottomCenterCircle - sectorHeight > Y && Y > bottomCenterCircle - sectorHeight * 2){
            touchedPlatform = 2;
        }
        if(bottomCenterCircle - sectorHeight * 2 > Y && Y > bottomCenterCircle - sectorHeight * 3){
            touchedPlatform = 3;
        }
    }

    public void addNewPlatform(int _touchedCircle){
        Title title = new Title();
        title.setPlatform(_touchedCircle);
        titles.add(title);
    }

    public int findAngelForSmallCircle(Point _optimalPoint){
        int angel =(int) (Math.atan((double)( (_optimalPoint.y - getHeight()/2f)/(_optimalPoint.x - getWidth()/2))));
        return angel;
    }

    public int findOrigoXForSmallCircle(int _angel){
        int X;
        if(_angel != angleForFirstCircle && _angel != angleForSecondCircle && _angel != angleForThirdCircle){
            X = optimalPoint.x;
            return X;
        } else {
            X = (int) (Math.cos(Math.toRadians((double) _angel))*getWidth()*3/8f + getWidth()/2f);
            return X;
        }
    }

    public int findOrigoYForSmallCircle(int _angel){
        int Y;
        if(_angel != angleForFirstCircle && _angel != angleForSecondCircle && _angel != angleForThirdCircle){
            Y = optimalPoint.y;
            return Y;
        } else {
            Y = (int) (Math.sin(Math.toRadians((double) _angel))*getWidth()*3/8f + getHeight()/2);
            return Y;
        }
    }

    public void initRadiusTouchForSmallCircle(MotionEvent _event){
        float X = _event.getX();
        float Y = _event.getY();
        float num1 = ((X - X1) * (X - X1))
                + ((Y - Y1) * (Y - Y1));
        radiusTouch1 = Math.abs(Math.sqrt(num1));

        float num2 = ((X - X2) * (X - X2))
                + ((Y - Y2) * (Y - Y2));
        radiusTouch2 = Math.abs(Math.sqrt(num2));

        float num3 = ((X - X3) * (X - X3))
                + ((Y - Y3) * (Y - Y3));
        radiusTouch3 = Math.abs(Math.sqrt(num3));
    }

    public int findTouchedCircle(double _radiusTouch1, double _radiusTouch2, double _radiusTouch3){
        if(_radiusTouch1 < radiusForSmallCircle_1){
            return 1;

        } if(_radiusTouch2 < radiusForSmallCircle_2){
            return 2;

        } if(_radiusTouch3 < radiusForSmallCircle_3){
            return 3;
        }
        return 0;
    }

    public boolean initTapTheCircle(){
        boolean tapTheCircle = false;
        if(touchedCircle == 1 || touchedCircle == 2 || touchedCircle == 3){
            tapTheCircle = true;
        }
        return tapTheCircle;
    }

    public boolean tapUpInCenterCircle(MotionEvent _event){
        tapUpInCenterCircle = false;
        float X = _event.getX();
        float Y = _event.getY();
        float num1 = ((X - getWidth()/2f) * (X - getWidth()/2f))
                + ((Y - getHeight()/2f) * (Y - getHeight()/2f));
        radiusTouch = Math.abs(Math.sqrt(num1));
        if(radiusTouch < getWidth()/4f){
            tapUpInCenterCircle = true;
        }
        return tapUpInCenterCircle;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (state.animationIsRunning()){
            return false;
        }else{
            mGestureDetector.onTouchEvent(event);
            state.setEvent(event);
        }
        return true;
    }

    /**--------GestureListener--------------------------------------------------------------------------------------------------------------------- */

    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (state.animationIsRunning()){
                return false;
            } else {
                if(touchedCircle == 1){
                    X1 = X1 - (int)distanceX;
                    Y1 = Y1 - (int)distanceY;
                    invalidate();
                }
                if(touchedCircle == 2){
                    X2 = X2 - (int)distanceX;
                    Y2 = Y2 - (int)distanceY;
                    invalidate();
                }
                if(touchedCircle == 3){
                    X3 = X3 - (int)distanceX;
                    Y3 = Y3 - (int)distanceY;
                    invalidate();
                }
            }

           return true;
        }

//        @Override
//        public void onLongPress(MotionEvent e) {
//            super.onLongPress(e);
//        }

        @Override
        public boolean onDown(MotionEvent e) {
            if (state.animationIsRunning()){
                return false;
            } else {
                initRadiusTouchForSmallCircle(e);
                touchedCircle = findTouchedCircle(radiusTouch1, radiusTouch2, radiusTouch3);
                if(initTapTheCircle() || tapUpInCenterCircle(e)){
                    state.stopAnimation();
                    setEndPoint();
                }
            }
            return false;
        }
    }

    public void setListener(WaveListener _waveListener){
        waveListener = _waveListener;
    }
}
