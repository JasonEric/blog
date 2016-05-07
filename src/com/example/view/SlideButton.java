package com.example.view;

import com.example.blog.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;


/**
 * @Description: �߷�iphone��������
 * Examples: ��ʹ��ʾ����
 * slipButton.setCheck(true);
 * slipButton.SetOnChangedListener(new OnChangedListener()
 * {
 *     @Override
 *     public void OnChanged(boolean CheckState)
 *     {
 *         //����SlipButton�����¼�
 *     }
 * });
 * @author http://blog.csdn.net/finddreams

**/

public class SlideButton extends View implements OnTouchListener {
	/**
	 * ��¼��ǰ��ť�Ƿ��,trueΪ��,flaseΪ�ر�
	 */
    private boolean NowChoose = false;
    private boolean isChecked;
    /**
	 *��¼�û��Ƿ��ڻ����ı���
	 */
    private boolean OnSlip = false;
    /**
   	 * ����ʱ��x,��ǰ��x
   	 */
    private float DownX, NowX;
    /**
   	 * �򿪺͹ر�״̬��,�α��Rect
   	 */
    private Rect Btn_On, Btn_Off;
    private boolean isChgLsnOn = false;
    private OnChangedListener ChgLsn;
    private Bitmap bg_on, bg_off, slip_btn;
    public SlideButton(Context context)
    {
        super(context);
        init();
    }
    public SlideButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }
    public SlideButton(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }
    private void init()
    {// ��ʼ��
        bg_on = BitmapFactory.decodeResource(getResources(), R.drawable.slip_left);
        bg_off = BitmapFactory.decodeResource(getResources(), R.drawable.slip_right);
        slip_btn = BitmapFactory.decodeResource(getResources(), R.drawable.slip_ball);
        Btn_On = new Rect(0, 0, slip_btn.getWidth(), slip_btn.getHeight());
        Btn_Off = new Rect(bg_off.getWidth() - slip_btn.getWidth(), 0, bg_off.getWidth(),
                slip_btn.getHeight());
        setOnTouchListener(this);// ���ü�����,Ҳ����ֱ�Ӹ�дOnTouchEvent
    }
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas)
    {// ��ͼ����
        super.onDraw(canvas);
        Matrix matrix = new Matrix();
        Paint paint = new Paint();
        float x;
        if (NowX < (bg_on.getWidth() / 2))// ������ǰ�������εı�����ͬ,�ڴ����ж�
        {
            x = NowX - slip_btn.getWidth() / 2;
            canvas.drawBitmap(bg_off, matrix, paint);// �����ر�ʱ�ı���
        }
        else
        {
            x = bg_on.getWidth() - slip_btn.getWidth() / 2;
            canvas.drawBitmap(bg_on, matrix, paint);// ������ʱ�ı���
        }
        if (OnSlip)// �Ƿ����ڻ���״̬,
        {
            if (NowX >= bg_on.getWidth())// �Ƿ񻮳�ָ����Χ,�������α��ܵ���ͷ,����������ж�
                x = bg_on.getWidth() - slip_btn.getWidth() / 2;// ��ȥ�α�1/2�ĳ���...
            else if (NowX < 0)
            {
                x = 0;
            }
            else
            {
                x = NowX - slip_btn.getWidth() / 2;
            }
        }
        else
        {// �ǻ���״̬
            if (NowChoose)// �������ڵĿ���״̬���û��α��λ��
            {
                x = Btn_Off.left;
                canvas.drawBitmap(bg_on, matrix, paint);// ��ʼ״̬ΪtrueʱӦ�û�����״̬ͼƬ
            }
            else
                x = Btn_On.left;
        }
        if (isChecked)
        {
            canvas.drawBitmap(bg_on, matrix, paint);
            x = Btn_Off.left;
            isChecked = !isChecked;
        }
        if (x < 0)// ���α�λ�ý����쳣�ж�...
            x = 0;
        else if (x > bg_on.getWidth() - slip_btn.getWidth())
            x = bg_on.getWidth() - slip_btn.getWidth();
        canvas.drawBitmap(slip_btn, x, 0, paint);// �����α�.
    }
    
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        switch (event.getAction())
        // ���ݶ�����ִ�д���
        {
            case MotionEvent.ACTION_MOVE:// ����
                NowX = event.getX();
                break;
            case MotionEvent.ACTION_DOWN:// ����
                if (event.getX() > bg_on.getWidth() || event.getY() > bg_on.getHeight())
                    return false;
                OnSlip = true;
                DownX = event.getX();
                NowX = DownX;
                break;
            case MotionEvent.ACTION_CANCEL: // �Ƶ��ؼ��ⲿ
                OnSlip = false;
                boolean choose = NowChoose;
                if (NowX >= (bg_on.getWidth() / 2))
                {
                    NowX = bg_on.getWidth() - slip_btn.getWidth() / 2;
                    NowChoose = true;
                }
                else
                {
                    NowX = NowX - slip_btn.getWidth() / 2;
                    NowChoose = false;
                }
                if (isChgLsnOn && (choose != NowChoose)) // ��������˼�����,�͵����䷽��..
                    ChgLsn.OnChanged(NowChoose);
                break;
            case MotionEvent.ACTION_UP:// �ɿ�
                OnSlip = false;
                boolean LastChoose = NowChoose;
                if (event.getX() >= (bg_on.getWidth() / 2))
                {
                    NowX = bg_on.getWidth() - slip_btn.getWidth() / 2;
                    NowChoose = true;
                }
                else
                {
                    NowX = NowX - slip_btn.getWidth() / 2;
                    NowChoose = false;
                }
                if (isChgLsnOn && (LastChoose != NowChoose)) // ��������˼�����,�͵����䷽��..
                    ChgLsn.OnChanged(NowChoose);
                break;
            default:
        }
        invalidate();// �ػ��ؼ�
        return true;
    }
    
    public void SetOnChangedListener(OnChangedListener l)
    {
        // ���ü�����,��״̬�޸ĵ�ʱ��
        isChgLsnOn = true;
        ChgLsn = l;
    }
    
    public interface OnChangedListener
    {
        abstract void OnChanged(boolean CheckState);
    }
    
    public void setCheck(boolean isChecked)
    {
        this.isChecked = isChecked;
        NowChoose = isChecked;
    }
}


