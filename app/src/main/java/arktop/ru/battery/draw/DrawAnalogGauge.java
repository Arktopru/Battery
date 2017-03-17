package arktop.ru.battery.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 */
public class DrawAnalogGauge extends View {

    private Paint mPaint;
    /**
     * min 215 = 0%
     * max 325 = 100%
     */
    public static final double MIN_ANGLE = 215;
    public static final double MAX_ANGLE = 325;
    private double angle = MIN_ANGLE;
    private float left = 0;
    private float top = 0;
    private float width = 220;
    private float height = 150;
    private String gaugeText = "";

    public DrawAnalogGauge(Context context) {
        super(context);
    }

    public DrawAnalogGauge(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawAnalogGauge(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(mPaint);

        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.BLACK);

        canvas.drawLine(
                left + width / 2,
                (float) (top + height - (height / 4.5)),
                (float) ((left + width / 2) + (width / 2) * Math.cos(Math.toRadians(angle))),
                (float) ((top + height - (height / 4.5)) + (width / 2) * Math.sin(Math.toRadians(angle))),
                mPaint
        );

        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.DKGRAY);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(left, top, left + width, top + height, mPaint);
        canvas.drawRect(left + 5, top + 5, left + width - 5, top + height - 5, mPaint);

        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(left + 6, (float) (top + height - (height / 2.5)), left + width - 6, top + height - 6, mPaint);

        mPaint.setColor(Color.DKGRAY);
        canvas.drawCircle(left + width / 2, (float) (top + height - (height / 4.5)), width / 12, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(left + 5, (float) (top + height - (height / 2.5)), left + width - 5, (float) (top + height - (height / 2.5)), mPaint);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPaint.setColor(Color.LTGRAY);
            mPaint.setStrokeWidth(35);
            canvas.drawArc(left + 25, top + 30, left + width - 25, top + height - 20, 180, 180, false, mPaint);
        }

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(24);
        mPaint.setStrokeWidth(1);
        canvas.drawText(gaugeText, left + 5, top + height + 30, mPaint);
        canvas.restore();
    }

    public void setGaugeText(String gaugeText) {
        this.gaugeText = gaugeText;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
