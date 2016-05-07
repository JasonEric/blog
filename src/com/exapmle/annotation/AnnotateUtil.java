/*
 * Copyright (c) 2014,KJFrameForAndroid Open Source Project,张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.exapmle.annotation;

import java.lang.reflect.Field;

import net.tsz.afinal.FinalActivity.Method;
import net.tsz.afinal.annotation.view.EventListener;
import net.tsz.afinal.annotation.view.Select;
import net.tsz.afinal.annotation.view.ViewInject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;

/**
 * 
 * @description 注解工具类
 */
public class AnnotateUtil {
    /**
     * @param currentClass
     *            当前类，一般为Activity或Fragment
     * @param sourceView
     *            待绑定控件的直接或间接父控件
     */
//    public static void initBindView(Object currentClass, View sourceView) {
//        Field[] fields = currentClass.getClass().getDeclaredFields();
//        if (fields != null && fields.length > 0) {
//            for (Field field : fields) {
//                BindView bindView = field.getAnnotation(BindView.class);
//                if (bindView != null) {
//                    int viewId = bindView.id();
//                    boolean clickLis = bindView.click();
//                    try {
//                        field.setAccessible(true);
//                        if (clickLis) {
//                            sourceView.findViewById(viewId).setOnClickListener(
//                                    (OnClickListener) currentClass);
//                        }
//                        field.set(currentClass, sourceView.findViewById(viewId));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//    /**
//     * 必须在setContentView之后调用
//     * 
//     * @param aty
//     */
//    public static void initBindView(Activity aty) {
//        initBindView(aty, aty.getWindow().getDecorView());
//    }
//
//    /**
//     * 必须在setContentView之后调用
//     * 
//     * @param view
//     *            侵入式的view，例如使用inflater载入的view
//     */
//    public static void initBindView(View view) {
//        Context cxt = view.getContext();
//        if (cxt instanceof Activity) {
//            initBindView((Activity) cxt);
//        } else {
//            throw new RuntimeException("view must into Activity");
//        }
//    }
//
//    /**
//     * 必须在setContentView之后调用
//     * 
//     * @param frag
//     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//	public static void initBindView(Fragment frag) {
//        initBindView(frag, frag.getActivity().getWindow().getDecorView());
//    }
    public static void initInjectedView(Activity activity){
		initInjectedView(activity, activity.getWindow().getDecorView());
	}
	
	
	public static void initInjectedView(Object injectedSource,View sourceView){
		Field[] fields = injectedSource.getClass().getDeclaredFields();
		if(fields!=null && fields.length>0){
			for(Field field : fields){
				try {
					field.setAccessible(true);
					
					if(field.get(injectedSource)!= null )
						continue;
				
					BindView bindView = field.getAnnotation(BindView.class);
					if(bindView!=null){
						int viewId = bindView.id();
					    field.set(injectedSource,sourceView.findViewById(viewId));
					    setListener(injectedSource,field,bindView.click(),Method.Click);
						setListener(injectedSource,field,bindView.longClick(),Method.LongClick);
						setListener(injectedSource,field,bindView.itemClick(),Method.ItemClick);
						setListener(injectedSource,field,bindView.itemLongClick(),Method.itemLongClick);
						Select select = bindView.select();
						if(!TextUtils.isEmpty(select.selected())){
							setViewSelectListener(injectedSource,field,select.selected(),select.noSelected());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	private static void setViewSelectListener(Object injectedSource,Field field,String select,String noSelect)throws Exception{
		Object obj = field.get(injectedSource);
		if(obj instanceof View){
			((AbsListView)obj).setOnItemSelectedListener(new EventListener(injectedSource).select(select).noSelect(noSelect));
		}
	}
	
	
	private static void setListener(Object injectedSource,Field field,String methodName,Method method)throws Exception{
		if(methodName == null || methodName.trim().length() == 0)
			return;
		
		Object obj = field.get(injectedSource);
		
		switch (method) {
			case Click:
				if(obj instanceof View){
					((View)obj).setOnClickListener(new EventListener(injectedSource).click(methodName));
				}
				break;
			case ItemClick:
				if(obj instanceof AbsListView){
					((AbsListView)obj).setOnItemClickListener(new EventListener(injectedSource).itemClick(methodName));
				}
				break;
			case LongClick:
				if(obj instanceof View){
					((View)obj).setOnLongClickListener(new EventListener(injectedSource).longClick(methodName));
				}
				break;
			case itemLongClick:
				if(obj instanceof AbsListView){
					((AbsListView)obj).setOnItemLongClickListener(new EventListener(injectedSource).itemLongClick(methodName));
				}
				break;
			default:
				break;
		}
	}
	
	public enum Method{
		Click,LongClick,ItemClick,itemLongClick
	}

}
