package com.example.timemanager2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.Random;

public class draw extends View{
    Paint paint;
    int length;
    int[] points={0,1,2,3,4,5,10,20,100};
    int luckPosition=-1;
    int start=0,luckNumber=3;
    int Points=0;
    int down=0;
    String[] ts=new String[8];
    public draw(Context context) {
        this(context,null);
    }
    public draw(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }
    public draw(Context context, AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        Typeface fy=Typeface.createFromAsset(getContext().getAssets(),"font/font1.ttf");
        paint.setTypeface(fy);
    }


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w=getWidth();
        int h=getHeight();
        int center=0;
        if(w<h){
            length=w/3;
            center=h/2;
        }
        else{
            length=h/3;
            center=w/2;
        }
        int a=3*length;
        int b=a/2;
        down=center-b;
        int blue=Color.argb(255,68,138,255);
        int green=Color.argb(255,251,251,8);
        int red=Color.argb(255,211,59,111);
        if(h>w) {
            paint.setColor(blue);
            if(luckPosition==0){
                paint.setColor(red);
                invalidate();
            }
            canvas.drawRect(0, down, length, length + down, paint);
            paint.setColor(green);
            if(luckPosition==1){
                paint.setColor(red);
                invalidate();
            }
            canvas.drawRect(length, down, length * 2, length + down, paint);
            paint.setColor(blue);
            if(luckPosition==2){
                paint.setColor(red);
                invalidate();
            }
            canvas.drawRect(length * 2, down, length * 3, length+down, paint);
            paint.setColor(green);
            if(luckPosition==7){
                paint.setColor(red);
                invalidate();
            }
            canvas.drawRect(0, length+down, length, length * 2+down, paint);
            paint.setColor(Color.WHITE);
                canvas.drawRect(length, length+down, length * 2, length * 2+down, paint);
            paint.setColor(green);
            if(luckPosition==3){
                paint.setColor(red);
                invalidate();
            }
            canvas.drawRect(length * 2, length+down, length * 3, length * 2+down, paint);
            paint.setColor(blue);
            if(luckPosition==6){
                paint.setColor(red);
                invalidate();
            }
            canvas.drawRect(0, length * 2+down, length, length * 3+down, paint);
            paint.setColor(green);
            if(luckPosition==5){
                paint.setColor(red);
                invalidate();
            }
            canvas.drawRect(length, length * 2+down, length * 2, length * 3+down, paint);
            paint.setColor(blue);
            if(luckPosition==4){
                paint.setColor(red);
                invalidate();
            }
            canvas.drawRect(length * 2, length * 2+down, length * 3, length * 3+down, paint);
            paint.setColor(Color.BLACK);
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize(40);
            int i=0;
            while(i<8){
                ts[i]=points[i]+" points";
                i++;
            }
            float wBegin=(float)length/4;
            float hBegin=(float)(length +2* down)/2;
            canvas.drawText(ts[0],wBegin,hBegin,paint);
            canvas.drawText(ts[1],wBegin+length,hBegin,paint);
            canvas.drawText(ts[2],wBegin+length+length,hBegin,paint);
            canvas.drawText(ts[3],wBegin,hBegin+length,paint);
            canvas.drawText("Luck Draw",wBegin+length-30,hBegin+length,paint );
            canvas.drawText(ts[4],wBegin+length+length,hBegin+length,paint);
            canvas.drawText(ts[5],wBegin,hBegin+length+length,paint);
            canvas.drawText(ts[6],wBegin+length,hBegin+length+length,paint);
            canvas.drawText(ts[7],wBegin+length+length,hBegin+length+length,paint);
        }
    }

    public int getLuckNumber(){
        if(luckNumber==0){
            Points=0;
        }
        if(luckNumber==1){
            Points=1;
        }
        else if(luckNumber==2){
            Points=2;
        }
        else if(luckNumber==3){
            Points=4;
        }
        else if(luckNumber==4){
            Points=20;
        }
        else if(luckNumber==5){
            Points=10;
        }
        else if(luckNumber==6){
            Points=5;
        }
        else if(luckNumber==7){
            Points=3;
        }
        return Points;
    }

//    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if(event.getAction()==MotionEvent.ACTION_UP){
//            RectF r=new RectF(length, length+down, length * 2, length * 2+down);
//            if(r.contains(event.getX(),event.getY())){
//                startAnim();
//            }
//            return super.onTouchEvent(event);
//        }
//
//
//
//        return super.onTouchEvent(event);
////    }
//    public RectF getCenterRect(){
//        RectF r=new RectF(length, length+down, length * 2, length * 2+down);
//        return r;
//    }

    public void startAnim(){
        Random r=new Random();
        luckNumber=r.nextInt(8);
        start=0;
        ValueAnimator valueAnimator=ValueAnimator.ofInt(start,32+luckNumber).setDuration(8000);
        invalidate();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int position=(int)animation.getAnimatedValue();
                luckPosition=position%8;
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                start =luckNumber;
                int p=getLuckNumber();
                String say="You get "+p+" points";
                Toast.makeText(getContext(), say, Toast.LENGTH_LONG).show();
            }
        });
        valueAnimator.start();
    }



}
