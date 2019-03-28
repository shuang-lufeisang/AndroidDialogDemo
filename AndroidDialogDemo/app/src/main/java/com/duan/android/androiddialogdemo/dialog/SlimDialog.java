package com.duan.android.androiddialogdemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * <pre>
 * author : Duan
 * time : 2019/03/28
 * desc : 自定义Dialog
 * version: 1.0
 * </pre>
 */
public class SlimDialog extends Dialog {
    public SlimDialog(@NonNull Context context) {
        super(context);
    }

    public SlimDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SlimDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
