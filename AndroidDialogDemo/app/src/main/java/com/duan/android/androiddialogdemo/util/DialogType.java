package com.duan.android.androiddialogdemo.util;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 * author : Duan
 * time : 2019/04/08
 * desc :
 * version: 1.0
 * </pre>
 */
public class DialogType {
    /**
     * normal
     * single_button
     *
     */
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_SINGLE_BUTTON = 1;

    public final int mType;
    //Retention -元注解(系统提供的，用于定义注解的“注解”)
    @Retention(RetentionPolicy.SOURCE)
    //这里指定int的取值只能是以下范围
    @IntDef({TYPE_NORMAL, TYPE_SINGLE_BUTTON})
    @interface DialogTypeDef {
    }

    /**
     * 在Android系统中使用枚举的开销是使用常量的2倍
     * 推荐使用注解+
     * 当用户输入了我们定义之外的数值，编辑器可以给我们提示
     * @param type
     */
    public DialogType(@DialogTypeDef int type) {
        this.mType = type;
    }
}
