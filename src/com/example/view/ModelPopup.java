package com.example.view;

import com.example.blog.R;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

/**
 * @Description:��QQ�ӵײ�������PopupWindow���Դ��������϶���
 * @author http://blog.csdn.net/finddreams
 */ 
public class ModelPopup extends PopupWindow implements android.view.View.OnClickListener{

	private OnDialogListener listener;
	private View mPopView;
	public ModelPopup(Context context, OnDialogListener listener,boolean isShowMd) {  
        super(context);  
        this.listener=listener;
        LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mPopView= inflater.inflate(R.layout.popu_get_photo, null);
        this.setContentView(mPopView);
        this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
        Button btn_choose_photo=(Button)mPopView.findViewById(R.id.btn_choose_photo);
        Button btn_take_photo=(Button)mPopView.findViewById(R.id.btn_take_photo);
        Button btn_model=(Button)mPopView.findViewById(R.id.btn_model);
        Button btn_cancel=(Button)mPopView.findViewById(R.id.btn_cancel);
        if(!isShowMd) {
        	btn_model.setVisibility(View.GONE);
        }
        btn_choose_photo.setOnClickListener(this);
        btn_take_photo.setOnClickListener(this);
        btn_model.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        // ����SelectPicPopupWindow��������ɵ��
        this.setFocusable(true);
        // �������Ŀؼ�Ҳ����ʹ��PopUpWindow dimiss
        this.setOutsideTouchable(true);
        // ����SelectPicPopupWindow�������嶯��Ч��
        this.setAnimationStyle(R.style.PopupAnimation);
        // ʵ����һ��ColorDrawable��ɫΪ��͸��
        ColorDrawable dw = new ColorDrawable(0xb0000000);//0xb0000000
        // ����SelectPicPopupWindow��������ı���
        this.setBackgroundDrawable(dw);//��͸����ɫ
    } 
	/**
	 * Dialog��ť�ص��ӿ�
	 *
	 */
	public interface OnDialogListener {

		public void onChoosePhoto();//ѡ�񱾵���Ƭ
		
		public void onTakePhoto();//����
		
		public void onModel();//����

		public void onCancel();//ȡ��

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.btn_choose_photo:
			listener.onChoosePhoto();
			break;
		case R.id.btn_take_photo:
			listener.onTakePhoto();
			break;
		case R.id.btn_model:
			listener.onModel();
			break;
		case R.id.btn_cancel:
			listener.onCancel();
			break;
		}
		dismiss();
	}
}
