package com.tbuonomo.randomlines;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tommy on 21/10/16.
 */
public class RandomLinesView extends View {
    private static final float LINE_SPACING = 50;
    private static final float LINE_WIDTH = 0.2f;

    private float lineSpacing;
    private float lineWidth;
    private int translationMax;

    // Variables
    private int screenHeight;

    private int screenWidth;

    private float density;

    private Paint paint;

    private List<RandomLine> randomLines;

    public RandomLinesView(Context context) {
        super(context);
        init(null);
    }

    public RandomLinesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public RandomLinesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void init(AttributeSet attrs) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenHeight = metrics.heightPixels;
        screenWidth = metrics.widthPixels;
        density = metrics.density;

        setBackgroundColor(Color.TRANSPARENT);

        int lineColor;
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RandomLinesView);
            lineColor = a.getColor(R.styleable.RandomLinesView_lineColor, Color.WHITE);
            lineWidth = a.getDimension(R.styleable.RandomLinesView_lineWidth, dpToPixels(LINE_WIDTH));
            lineSpacing = a.getDimension(R.styleable.RandomLinesView_lineSpacing, dpToPixels(LINE_SPACING));
            a.recycle();
        } else {
            lineColor = Color.WHITE;
            lineWidth = dpToPixels(LINE_WIDTH);
            lineSpacing = dpToPixels(LINE_SPACING);
        }

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(lineColor);
        paint.setStrokeWidth(lineWidth);

        initLines();
    }

    private void initLines() {
        translationMax = (int) dpToPixels(lineSpacing * 3 / 4);
        randomLines = new ArrayList<>();
        int heightSegments = (int) (screenHeight * 3 / lineSpacing);

        List<ValueAnimator> animators = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < heightSegments; i++) {
            final RandomLine lf = new RandomLine(-lineWidth, -screenHeight + i * lineSpacing, screenWidth + lineWidth, i * lineSpacing);
            final RandomLine lr = new RandomLine(screenWidth + lineWidth, -screenHeight + i * lineSpacing, -lineWidth, i * lineSpacing);
            randomLines.add(lf);
            randomLines.add(lr);

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1, 0);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.setDuration(rand.nextInt(10000) + 10000);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float offset = (float) animation.getAnimatedValue();
                    lf.yS = lf.initialYS + offset * translationMax * lf.translateFactorS;
                    lf.yE = lf.initialYE + offset * translationMax * lf.translateFactorE;
                    lr.yS = lr.initialYS + offset * translationMax * lr.translateFactorS;
                    lr.yE = lr.initialYE + offset * translationMax * lr.translateFactorE;
                    invalidate();
                }
            });

            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationRepeat(Animator animation) {
                    super.onAnimationRepeat(animation);
                    lf.refreshTranslateFactors();
                    lr.refreshTranslateFactors();
                }
            });

            animators.add(valueAnimator);
            valueAnimator.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (RandomLine l : randomLines) {
            canvas.drawLine(l.xS, l.yS, l.xE, l.yE, paint);
        }
    }

    private float dpToPixels(float dp) {
        return dp * density;
    }

    private float pixelsToDp(float px) {
        return px / density;
    }

    private class RandomLine {
        private Random random;
        private float translateFactorS;
        private float translateFactorE;
        private float xS, yS, xE, yE;
        private float initialYS, initialYE;

        private RandomLine(float xS, float yS, float xE, float yE) {
            random = new Random();
            this.xS = xS;
            this.yS = yS + random.nextInt(translationMax) - translationMax / 2;
            this.xE = xE;
            this.yE = yE + random.nextInt(translationMax) - translationMax / 2;
            this.initialYE = yE;
            this.initialYS = yS;
            translateFactorS = random.nextFloat() * 2 - 1.0f;
            translateFactorE = random.nextFloat() * 2 - 1.0f;
        }

        private void refreshTranslateFactors() {
            translateFactorS = random.nextFloat() * 2 - 1.0f;
            translateFactorE = random.nextFloat() * 2 - 1.0f;
        }
    }

    /*----------------------------------------------*/
    /*-----------------USERS METHODS----------------*/
    /*----------------------------------------------*/

    public void hide(boolean animate) {
        if (animate) {
            animate().alpha(0).setDuration(500).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    setVisibility(GONE);
                }
            }).start();
        } else {
            setVisibility(GONE);
        }
    }

    public void show(boolean animate) {
        if (animate) {
            animate().alpha(1).setDuration(500).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationEnd(animation);
                    setVisibility(VISIBLE);
                }
            }).start();
        } else {
            setVisibility(VISIBLE);
        }
    }
}
