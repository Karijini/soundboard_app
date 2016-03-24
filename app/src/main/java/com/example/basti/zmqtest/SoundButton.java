package com.example.basti.zmqtest;
import android.content.Context;
import android.graphics.*;
import android.os.Bundle;
import android.view.View;
import android.support.v4.content.ContextCompat;
import android.text.StaticLayout;
import android.text.Layout;
import android.text.TextPaint;
/**
 * Created by basti on 24.01.2016.
 */
public class SoundButton extends View {
    private Paint m_paint_inner_arc;
    private Paint m_paint_arc;
    private Paint m_paint_bg_arc;
    private TextPaint m_paint_text;
    private RectF m_arc_rect;
    private String m_sound;
    StaticLayout m_sl;

//    private Paint[] mPaints;
//    private Paint mFramePaint;
//    private boolean[] mUseCenters;
//    private RectF[] mOvals;
//    private RectF mBigOval;
    private float mStart;
    private float mSweep;
//    private int mBigIndex;
    private static final float SWEEP_INC = 2;
    private static final float START_INC = 15;
    public SoundButton(Context context) {
        super(context);

        m_sound = "";

        m_paint_inner_arc = new Paint();
        m_paint_inner_arc.setStyle(Paint.Style.STROKE);
        m_paint_inner_arc.setAntiAlias(true);
        m_paint_inner_arc.setStrokeWidth(3);
        m_paint_inner_arc.setColor(ContextCompat.getColor(context, R.color.opaque_yellow));

        m_paint_arc = new Paint();
        m_paint_arc.setStyle(Paint.Style.STROKE);
        m_paint_arc.setAntiAlias(true);
        m_paint_arc.setStrokeWidth(8);
        m_paint_arc.setColor(ContextCompat.getColor(context, R.color.opaque_orange));

        m_paint_bg_arc = new Paint();
        m_paint_bg_arc.setStyle(Paint.Style.STROKE);
        m_paint_bg_arc.setAntiAlias(true);
        m_paint_bg_arc.setStrokeWidth(12);
        m_paint_bg_arc.setColor(ContextCompat.getColor(context, R.color.grey));

        m_paint_text = new TextPaint();
        m_paint_text.setTextSize(30);
        m_paint_text.setTextAlign(Paint.Align.CENTER);
        m_paint_text.setColor(ContextCompat.getColor(context, R.color.default_text));

        m_sl = new StaticLayout(m_sound, m_paint_text, 300, Layout.Alignment.ALIGN_CENTER, 1, 1, false);

        m_arc_rect = new RectF(10, 10, 280, 280);

//        mPaints = new Paint[4];
//        mUseCenters = new boolean[4];
//        mOvals = new RectF[4];
//        mPaints[0] = new Paint();
//        mPaints[0].setAntiAlias(true);
//        mPaints[0].setStyle(Paint.Style.FILL);
//        mPaints[0].setColor(0x88FF0000);
//        mUseCenters[0] = false;
//        mPaints[1] = new Paint(mPaints[0]);
//        mPaints[1].setColor(0x8800FF00);
//        mUseCenters[1] = true;
//        mPaints[2] = new Paint(mPaints[0]);
//        mPaints[2].setStyle(Paint.Style.STROKE);
//        mPaints[2].setStrokeWidth(4);
//        mPaints[2].setColor(0x880000FF);
//        mUseCenters[2] = false;
//        mPaints[3] = new Paint(mPaints[2]);
//        mPaints[3].setColor(0x88888888);
//        mUseCenters[3] = true;
//        mBigOval = new RectF(40, 10, 280, 250);
//        mOvals[0] = new RectF( 10, 270,  70, 330);
//        mOvals[1] = new RectF( 90, 270, 150, 330);
//        mOvals[2] = new RectF(170, 270, 230, 330);
//        mOvals[3] = new RectF(250, 270, 310, 330);
//        mFramePaint = new Paint();
//        mFramePaint.setAntiAlias(true);
//        mFramePaint.setStyle(Paint.Style.STROKE);
//        mFramePaint.setStrokeWidth(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minwidth = 100;
        // Try for a width based on our minimum
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
        int minh = MeasureSpec.getSize(w) - minwidth + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(MeasureSpec.getSize(w) - minwidth, heightMeasureSpec, 0);

        setMeasuredDimension(300, 300);
    }
    void setPercent(int p)
    {
        mSweep = p;
        if (p!=0) {
            m_paint_text.setColor(ContextCompat.getColor(this.getContext(), R.color.highlighted_text));
        }else{
            m_paint_text.setColor(ContextCompat.getColor(this.getContext(), R.color.default_text));
        }
        invalidate();
    }

    void setSound(String sound)
    {
        m_sound = sound;
        m_sl = new StaticLayout(sound, m_paint_text, 200, Layout.Alignment.ALIGN_NORMAL, 1, 1, false);
        invalidate();
    }
    public String getSound()
    {
        return m_sound;
    }
    @Override protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        //canvas.drawText("testtestsetsetsetset",0,120,mPaints[0]);
        canvas.drawArc(m_arc_rect, 0, 360, false, m_paint_bg_arc);
        canvas.drawArc(m_arc_rect, mStart, mSweep, false, m_paint_arc);
        canvas.drawArc(m_arc_rect, mStart, mSweep, false,m_paint_inner_arc);
        //canvas.drawText(m_sound,0,150,m_paint_text);
        canvas.save();

        canvas.translate(140, 150 - m_sl.getHeight()/2);
        m_sl.draw(canvas);
        canvas.restore();
        //canvas.drawText();

//        for (int i = 0; i < 4; i++) {
//            drawArcs(canvas, mOvals[i], mUseCenters[i], mPaints[i]);
//        }
//        mSweep += SWEEP_INC;
//        if (mSweep > 360) {
//            mSweep -= 360;
//            mStart += START_INC;
//            if (mStart >= 360) {
//                mStart -= 360;
//            }
//            //mBigIndex = (mBigIndex + 1) % mOvals.length;
//        }
        //invalidate();
    }
}
