package com.example.base;

import android.util.SparseArray;
import android.view.View;

/**
 * ���ܵ�ViewHolder ֵ��ע�����SparseArray���֪ʶ�㣬�Ż����Ĵ洢integer��object��ֵ�Ե�hashmap
 * �˻�ķɶ� ������һ��github�ϵĿ�Դ��ĿС���ߣ���cool����࣡ ����ͬ����ʹ��SparseArray�洢id+view
 * �����������ʹ��Builderģʽ��һ����װ�˳��õĿؼ���������һ�����˴���~
 * ��ַ��  https://github.com/JoanZapata/base-adapter-helper 
 * ��ҿ���ȥ���ش��뿴������cool�ˣ���л�˻�ķɶ�ķ���~
 * ���У�
 * public View getView(int position, View convertView, ViewGroup parent) {

    if (convertView == null) {
        convertView = LayoutInflater.from(context)
          .inflate(R.layout.banana_phone, parent, false);
    }

    ImageView bananaView = BaseViewHolder.get(convertView, R.id.banana);
    TextView phoneView = BaseViewHolder.get(convertView, R.id.phone);
    return convertView;
	}
 * @author shengzh
 * @date 2015-1-7
 *
 */
public class BaseViewHolder {
	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}

}
