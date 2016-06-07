package com.example.hm.myanimation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ferenc on 2016.05.30..
 */
public class CustomBackground extends View implements ValueAnimator.AnimatorUpdateListener{
    private List<Animator> animatorsGroup = new ArrayList<>();
    private  boolean tapUpInCenterCircle;
    private Paint backGroundPaint;
    private Paint paintForWhiteLines;
    private Paint paintForSmallCircles;
    private static float origoX;
    private static float origoY;
    private int angleForFirstCircle = 0;
    private int angleForSecondCircle = 120;
    private int angleForThirdCircle = 240;
    //////Test values/////
    private int angleForFirstCircleTest;
    private int angleForSecondCircleTest;
    private int angleForThirdCircleTest;
    /////////////////////
    private int X1;
    private int Y1;
    private int X2;
    private int Y2;
    private int X3;
    private int Y3;
    private Point optimalPoint;
    private Point startPoint;
    private Point endPoint;
    private float radiusForSmallCircles;
    private float radiusForSmallCircle_1;
    private float radiusForSmallCircle_2;
    private float radiusForSmallCircle_3;
    private double radiusTouch;
    private double radiusTouch1;
    private double radiusTouch2;
    private double radiusTouch3;
    private ValueAnimator animator;
    private ValueAnimator animator1;
    private ValueAnimator radiusAnimator;
    private ValueAnimator pointAnimator;
    private AnimatorSet set;
    private AnimatorSet set1;
    private GestureDetector mGestureDetector;
    private int touchedCircle;
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
    }

    public void initPoint(){
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
        setAnimator();
    }

    private ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {

        }
    };

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        Log.d("Update","Update");
        if(animation == animator1){

            if(touchedCircle == 1){
                radiusForSmallCircle_1 = (float)radiusAnimator.getAnimatedValue();
                angleForSecondCircle = angleForSecondCircleTest - (int)animator1.getAnimatedValue()/2;
                angleForThirdCircle = angleForThirdCircleTest + (int) animator1.getAnimatedValue()/2;
                initCoordinatesForSmallCircles(angleForFirstCircle, angleForSecondCircle, angleForThirdCircle);
            }
            if(touchedCircle == 2){
                radiusForSmallCircle_2 = (float)radiusAnimator.getAnimatedValue();
                angleForFirstCircle = angleForFirstCircleTest + (int)animator1.getAnimatedValue()/2;
                angleForThirdCircle = angleForThirdCircleTest - (int) animator1.getAnimatedValue()/2;
                initCoordinatesForSmallCircles(angleForFirstCircle, angleForSecondCircle, angleForThirdCircle);

            }
            if(touchedCircle == 3){
                radiusForSmallCircle_3 = (float)radiusAnimator.getAnimatedValue();
                angleForFirstCircle = angleForFirstCircleTest - (int)animator1.getAnimatedValue()/2;
                angleForSecondCircle = angleForSecondCircleTest + (int) animator1.getAnimatedValue()/2;
                initCoordinatesForSmallCircles(angleForFirstCircle, angleForSecondCircle, angleForThirdCircle);
            }

        }if(animation == animator){

            if(touchedCircle == 1){

                angleForSecondCircle = ((int) animator.getAnimatedValue() + 90) % 360;
                angleForThirdCircle = ((int) animator.getAnimatedValue() + 270) % 360;
                initCoordinatesForSmallCircles(angleForFirstCircle, angleForSecondCircle, angleForThirdCircle);
            }
            if(touchedCircle == 2){
                angleForFirstCircle = (int) animator.getAnimatedValue()+30;
                angleForThirdCircle = ((int) animator.getAnimatedValue() + 210) % 360;
                initCoordinatesForSmallCircles(angleForFirstCircle, angleForSecondCircle, angleForThirdCircle);

            }
            if(touchedCircle == 3){
                angleForFirstCircle = (int) animator.getAnimatedValue() + 330;
                angleForSecondCircle = ((int) animator.getAnimatedValue() + 150) % 360;
                initCoordinatesForSmallCircles(angleForFirstCircle, angleForSecondCircle, angleForThirdCircle);
            }
            if(touchedCircle == 0){
                angleForFirstCircle = (int) animator.getAnimatedValue();
                angleForSecondCircle = ((int) animator.getAnimatedValue() + 120) % 360;
                angleForThirdCircle = ((int) animator.getAnimatedValue() + 240) % 360;
                angleForFirstCircleTest = angleForFirstCircle;
                angleForSecondCircleTest = angleForSecondCircle;
                angleForThirdCircleTest = angleForThirdCircle;
                initCoordinatesForSmallCircles(angleForFirstCircle, angleForSecondCircle, angleForThirdCircle);
            }
        }if(animation == pointAnimator){
            if(touchedCircle == 1){

                initCoordinatesForSmallCircles(findAngelForSmallCircle(optimalPoint), angleForSecondCircle, angleForThirdCircle);
            }
            if(touchedCircle == 2){

                initCoordinatesForSmallCircles(angleForFirstCircle, findAngelForSmallCircle(optimalPoint), angleForThirdCircle);
            }
            if(touchedCircle == 3){

                initCoordinatesForSmallCircles(angleForFirstCircle, angleForSecondCircle, findAngelForSmallCircle(optimalPoint));
            }
        }

        invalidate();
    }

    public void setAnimators(){

        setAnimatorForTwoCirclePositive();
        setAnimator();

        set = new AnimatorSet();
        set.playTogether(animatorsGroup);
        set.play(animatorsGroup.get(animatorsGroup.size()-1)).before(animator);
        set.start();
    }

    public void setAnimators2(){

        setAnimatorForPoint();
        setAnimator();

        set1 = new AnimatorSet();
        set1.play(pointAnimator).before(animator);
        set1.start();
    }

    public void setAnimator() {
        animator = ValueAnimator.ofInt( angleForFirstCircleTest, angleForFirstCircleTest + 360 );
        animator.setDuration(10000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(this);
        animator.start();
    }

    public void setAnimatorForTwoCirclePositive(){

        radiusAnimator = ValueAnimator.ofFloat(radiusForSmallCircles,0);
        radiusAnimator.setDuration(500);
        radiusAnimator.setInterpolator(new LinearInterpolator());
        radiusAnimator.addUpdateListener(this);
        animatorsGroup.add(radiusAnimator);

        animator1 = ValueAnimator.ofInt(0,60);
        animator1.setDuration(500);
        animator1.setInterpolator(new LinearInterpolator());
        animator1.addUpdateListener(this);
        animatorsGroup.add(animator1);
    }

    public void setAnimatorForPoint(){
        pointAnimator = ObjectAnimator.ofObject(this, "optimalPoint", new MyTypeEvaluator(), setStartPoint(), endPoint);
        pointAnimator.setDuration(500);
        pointAnimator.setInterpolator(new LinearInterpolator());
        pointAnimator.addUpdateListener(this);
        //pointAnimator.start();
    }
    public void setOptimalPoint(Point point) {
        this.optimalPoint = point;
    }

    public Point setStartPoint(){
        if(touchedCircle ==1){
            startPoint.set(X1,Y1);
        }
        if(touchedCircle ==2){
            startPoint.set(X2,Y2);
        }
        if(touchedCircle ==3){
            startPoint.set(X3,Y3);
        }
        return startPoint;
    }

    public void setEndPoint(){
        if(touchedCircle ==1){
            endPoint.set(findOrigoXForSmallCircle(angleForFirstCircle),findOrigoYForSmallCircle(angleForFirstCircle));
        }
        if(touchedCircle ==2){
            endPoint.set(findOrigoXForSmallCircle(angleForSecondCircle),findOrigoYForSmallCircle(angleForSecondCircle));
        }
        if(touchedCircle ==3){
            endPoint.set(findOrigoXForSmallCircle(angleForThirdCircle),findOrigoYForSmallCircle(angleForThirdCircle));
        }
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if(initTapTheCircle()){
                if(tapUpInCenterCircle(event)){
                    setAnimators();
                }else{
                    setAnimators2();
                }
            }
        }
        return true;
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

    /**--------GestureListener--------------------------------------------------------------------------------------------------------------------- */

    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("scroll","scroll");

            if(touchedCircle == 1){
                animator.cancel();
                X1 = X1 - (int)distanceX;
                Y1 = Y1 - (int)distanceY;
                invalidate();
            }
            if(touchedCircle == 2){
                animator.cancel();
                X2 = X2 - (int)distanceX;
                Y2 = Y2 - (int)distanceY;
                invalidate();
            }
            if(touchedCircle == 3){
                animator.cancel();
                X3 = X3 - (int)distanceX;
                Y3 = Y3 - (int)distanceY;
                invalidate();
            }
           return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            initRadiusTouchForSmallCircle(e);
            touchedCircle = findTouchedCircle(radiusTouch1, radiusTouch2, radiusTouch3);
            if(initTapTheCircle()){
                animator.removeAllUpdateListeners();
                animator.cancel();
                setEndPoint();
            }
            return false;
        }


    }
}
