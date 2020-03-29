package com.dzzchao.fwanandroid.test.annotationlib;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 用反射来实现
 * 性能低
 */
public class Binding {

    public static void bind(Activity activity) {
        for (Field field : activity.getClass().getDeclaredFields()) {
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView != null) {
                try {
                    field.setAccessible(true);
                    field.set(activity, activity.findViewById(bindView.value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        for (Method method : activity.getClass().getDeclaredMethods()) {
            BindClick bindClick = method.getAnnotation(BindClick.class);
            if (bindClick != null) {
                activity.findViewById(bindClick.value()).setOnClickListener(v -> {
                    try {
                        method.setAccessible(true);
                        method.invoke(activity);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}
