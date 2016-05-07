package com.example.base;

import android.util.SparseArray;
import android.view.View;

/**
 * 万能的ViewHolder 值得注意的是SparseArray这个知识点，优化过的存储integer和object键值对的hashmap
 * 扑火つ飞蛾 分享了一个github上的开源项目小工具，更cool更简洁！ 核心同样是使用SparseArray存储id+view
 * 但更巧妙的是使用Builder模式进一步封装了常用的控件方法，进一步简化了代码~
 * 地址是  https://github.com/JoanZapata/base-adapter-helper 
 * 大家可以去下载代码看看，更cool了，感谢扑火つ飞蛾的分享~
 * 用列：
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
