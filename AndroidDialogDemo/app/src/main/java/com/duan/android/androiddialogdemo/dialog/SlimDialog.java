package com.duan.android.androiddialogdemo.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duan.android.androiddialogdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <pre>
 * author : Duan
 * time : 2019/03/28
 * desc : 自定义Dialog
 * version: 1.0
 * </pre>
 */
public class SlimDialog extends Dialog {

    @BindView(R.id.title_text) TextView titleText;
    @BindView(R.id.message_text) TextView messageText;

    @BindView(R.id.positive) TextView positiveButton;
    @BindView(R.id.negative) TextView negativeButton;

    @BindView(R.id.dialog_root) LinearLayout dialogRoot;

    @BindView(R.id.content_group) ViewGroup contentParentView;

    private Context mContext;
    private int mTheme;
    private static String TAG = "SlimDialog";


    public SlimDialog(@NonNull Context context) {
        //super(context);
        this(context, R.style.AppDialog_Light);
    }

    public SlimDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mTheme = themeResId;
        this.getWindow().setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.bg_round_white)); // 弹窗 圆角

        setContentView(R.layout.app_dialog);
        mContext = context;
    }

//    protected SlimDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
//        super(context, cancelable, cancelListener);
//    }
    private void applyTheme() {
    //R.style.HwAppDialog_Light
    TypedArray a = mContext.getTheme().obtainStyledAttributes(mTheme, R.styleable.AppDialog);

    TypedValue value = new TypedValue();
    a.getValue(R.styleable.AppDialog_dialogBackground, value);
    if(value.resourceId >= 0x01000000) {
        try {
            XmlResourceParser parser = mContext.getResources().getXml(value.resourceId);
            Drawable d = Drawable.createFromXml(mContext.getResources(), parser);
            getWindow().setBackgroundDrawable(d);
        }catch (Exception e)
        {
            getWindow().setBackgroundDrawable(new ColorDrawable(value.data));
        }
    } else{
        getWindow().setBackgroundDrawable(new ColorDrawable(value.data));
    }

    //color = a.getColor(R.styleable.HwAppDialog_titleColor, 0);
    value = new TypedValue();
    a.getValue(R.styleable.AppDialog_titleColor, value);
    if(value.resourceId >= 0x01000000) {
        //TODO: not finished but not necessary now
        titleText.setTextColor(value.data);
    }
    else{
        titleText.setTextColor(value.data);
    }

    value = new TypedValue();
    a.getValue(R.styleable.AppDialog_messageColor, value);
    if(value.resourceId >= 0x01000000) {
        //TODO: not finished but not necessary now
        messageText.setTextColor(value.data);
    }
    else{
        messageText.setTextColor(value.data);
    }

    value = new TypedValue();
    a.getValue(R.styleable.AppDialog_buttonBackground, value);
    if(value.resourceId >= 0x01000000) {
        negativeButton.setBackgroundResource(value.resourceId);
//            neutralButton.setBackgroundResource(value.resourceId);
    }
    else{
        negativeButton.setBackgroundResource(value.data);
//            neutralButton.setBackgroundResource(value.data);
    }

    value = new TypedValue();
    a.getValue(R.styleable.AppDialog_buttonTextColor, value);
    if(value.resourceId >= 0x01000000) {
        try {
            ColorStateList colors = mContext.getResources().getColorStateList(value.resourceId);
            negativeButton.setTextColor(colors);
//                neutralButton.setTextColor(colors);
        }
        catch (Exception e)
        {
            negativeButton.setTextColor(value.data);
//                neutralButton.setTextColor(value.data);
        }
    }
    else{
        negativeButton.setTextColor(value.data);
//            neutralButton.setTextColor(value.data);
    }

    value = new TypedValue();
    if(parameters.mEnableDefaultButton){
        a.getValue(R.styleable.AppDialog_vividButtonBackground, value);
    }else{
        a.getValue(R.styleable.AppDialog_buttonBackground, value);
    }
    if(value.resourceId >= 0x01000000) {
        positiveButton.setBackgroundResource(value.resourceId);
    }
    else{
        positiveButton.setBackgroundResource(value.data);
    }

    value = new TypedValue();
    if(parameters.mEnableDefaultButton){
        a.getValue(R.styleable.AppDialog_vividButtonTextColor, value);
    }else{
        a.getValue(R.styleable.AppDialog_buttonTextColor, value);
    }
    if(value.resourceId >= 0x01000000) {
        try {
            ColorStateList colors = mContext.getResources().getColorStateList(value.resourceId);
            positiveButton.setTextColor(colors);
        }
        catch (Exception e)
        {
            positiveButton.setTextColor(value.data);
        }
    }
    else{
        positiveButton.setTextColor(value.data);
    }

    value = new TypedValue();
    a.getValue(R.styleable.AppDialog_dialogBorder, value);
    if(value.resourceId >= 0x01000000) {
        try {
            int dimension = (int)mContext.getResources().getDimension(value.resourceId);
            dialogRoot.setPadding(dimension, dimension, dimension, dimension);
        }
        catch (Exception e)
        {
            dialogRoot.setPadding(value.data, value.data, value.data, value.data);
        }
    }
    else{
        dialogRoot.setPadding(value.data, value.data, value.data, value.data);
    }

    a.recycle();
}

    /**
     * dialog inside content
     * @param view
     */
    public void setInsideContentView(View view) {

        if (contentParentView == null) {
            Log.e(TAG, "contentParentView == NULL");
        } else {
            Log.e(TAG, "contentParentView != NULL");
        }
        if (titleText == null) {
            Log.e(TAG, "titleText == NULL");
        } else {
            Log.e(TAG, "titleText != NULL");
        }
        if (positiveButton == null) {
            Log.e(TAG, "positiveButton == NULL");
        } else {
            Log.e(TAG, "positiveButton != NULL");
        }
        parameters.mIsInsideContentExist = true;
        contentParentView.addView(view);
    }

    public void setButton(final int whichButton, CharSequence text, final DialogInterface.OnClickListener listener) {
        switch (whichButton) {
            case DialogInterface.BUTTON_POSITIVE:
                positiveButton.setText(text);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onClick(SlimDialog.this, whichButton);
                        }
                        dismiss();
                    }
                });
                positiveButton.setVisibility(View.VISIBLE);
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                negativeButton.setText(text);
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onClick(SlimDialog.this, whichButton);
                        }
                        dismiss();
                    }
                });
                negativeButton.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

    }

    /** 弹窗标题 */
    @Override
    public void setTitle(@Nullable CharSequence title) {
        // super.setTitle(title);
        titleText.setText(title);
        titleText.setVisibility(View.VISIBLE);
        Log.d(TAG, "setTitle: " + title);
    }
    /** 弹窗标题 颜色 */
    public void setTitleColor(int titleColor) {
        titleText.setTextColor(titleColor);
        Log.d(TAG, "titleColor: " + titleColor);
    }
    
    /** 弹窗内容 */
    public void setMessage(CharSequence message) {
        messageText.setText(message);
        messageText.setVisibility(View.VISIBLE);
        Log.d(TAG, "setMessage: " + message);
    }

    @Override
    public void show() {
        Log.d(TAG, "dialog Override show()");
        if(getContext() instanceof Activity) {
            Activity activity = (Activity)getContext();
            if(activity.isFinishing()){
                //DO NOT CREATE DIALOG WHEN ACTIVITY IS FINISHED!!!
                return;
            }
        }

        super.show();
        ButterKnife.bind(this);

        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        Log.d(TAG, "metrics.widthPixels: "+ width +" ||  metrics.heightPixels: "+ height);
        Log.d(TAG, "width: "+ (6 * width) / 7 +" ||  height: "+ ViewGroup.LayoutParams.WRAP_CONTENT);

        this.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);

        onShow(this);
        //applyTheme();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public void onShow(SlimDialog dialog) {
        Log.d(TAG, "dialog onShow() invoked in Override show()");
        /* 标题 */
        if(parameters.mTitle != null && !parameters.mTitle.isEmpty()) {
            dialog.setTitle(parameters.mTitle);
            dialog.setTitleColor(parameters.mTitleColor);
            Log.d(TAG, "dialog setTitle(): "+ parameters.mTitle);
        }
        /* 弹窗内容 */
        if(parameters.mMessage != null && !parameters.mMessage.isEmpty()) {
            dialog.setMessage(parameters.mMessage);
            Log.d(TAG, "dialog setMessage(): "+ parameters.mMessage);
        }
        /* positive button */
        if(parameters.mPositiveButtonText!= null && !parameters.mPositiveButtonText.isEmpty()) {
            dialog.setButton(DialogInterface.BUTTON_POSITIVE,
                    parameters.mPositiveButtonText,
                    parameters.mPositiveButtonClickListener); // set positive button
            Log.d(TAG, "dialog mPositiveButtonText()");
        }else {
            positiveButton.setVisibility(View.GONE);
        }
        /* negative button */
        if(parameters.mNegativeButtonText!= null && !parameters.mNegativeButtonText.isEmpty()) {
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                    parameters.mNegativeButtonText,
                    parameters.mNegativeButtonClickListener); // set negative button
            Log.d(TAG, "dialog mNegativeButtonText()");
        }else {
            negativeButton.setVisibility(View.GONE);
        }
        if(parameters.mOnDismissListener!= null) {
            dialog.setOnDismissListener(parameters.mOnDismissListener);// Set a listener to be invoked when the dialog is dismissed.
            Log.d(TAG, "dialog mOnDismissListener()");
        }
        if(parameters.mInsideContentView != null) {

            Log.d(TAG, "dialog mInsideContentView() parameters.mIsInsideContentExist: " + parameters.mIsInsideContentExist);
            if (parameters.mIsInsideContentExist){
                contentParentView.removeView(parameters.mInsideContentView);
            }
            dialog.setInsideContentView(parameters.mInsideContentView);
        }
        dialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK){
                    return true;
                }else {
                    return false;
                }
            }
        });
    }



    /** Slim Dialog Parameters */
    SlimDialogParameters parameters;

    protected static class SlimDialogParameters {
        Context mContext;
        String mTitle;
        int mTitleColor;
        String mMessage;
        Boolean mEnableOneButton;    // only one button
        Boolean mEnableAutoDismiss;  // 触发 positive 按钮后自动 dismiss
        Boolean mEnableDefaultButton;
        String mPositiveButtonText;  // Positive 按钮名称
        OnClickListener mPositiveButtonClickListener; // Positive 按钮点击事件监听
        String mNegativeButtonText;  // Negative 按钮名称
        OnClickListener mNegativeButtonClickListener; // Negative 按钮点击事件监听
        String mNeutralButtonText;   // Neutral 按钮名称
        OnClickListener mNeutralButtonClickListener;  // Neutral  按钮点击事件监听
        View mInsideContentView;     // Dialog 内容框
        Boolean mIsInsideContentExist; // InsideContent是否已存在
        OnDismissListener mOnDismissListener;         // Set a listener to be invoked when the dialog is dismissed.
        SlimDialogParameters() {
            Log.e(TAG, " =========== SlimDialogParameters Construct =========== ");
            mEnableOneButton = false;
            mEnableDefaultButton = false;
            mEnableAutoDismiss = true;     // 默认 触发positive button 后自动 dismiss
            mIsInsideContentExist = false;
        }
    }
    
    static int resolveDialogTheme(Context context, int resid) {
        if (resid >= 0x01000000) {   // start of real resource IDs.
            return resid;
        } else {
            TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.AppDialogStyle, outValue, true);
            return outValue.resourceId;
        }
    }

    /**
     * Builder 设计模式
     */
    public static class Builder {

        protected SlimDialogParameters parameters;
        protected int mTheme;

        public Builder(Context context) {
            this(context, resolveDialogTheme(context, 0));
        }

        public Builder(Context context, int theme) {
            parameters = new SlimDialogParameters();
            parameters.mContext = context;
            mTheme = theme;
            Log.d(TAG, "dialog Builder()");
        }

        private Context getContext()
        {
            return parameters.mContext;
        }

        public Builder setTitle(String title) {
            parameters.mTitle = title;
            return this;
        }
        public Builder setTitleColor(int color) {
            parameters.mTitleColor = color;
            return this;
        }

        public Builder setTitle(int titleID) {
            String title = getContext().getString(titleID);
            return setTitle(title);
        }

        public Builder setMessage(String message) {
            parameters.mMessage = message;
            return this;
        }

        public Builder setMessage(int messageID) {
            String message = getContext().getString(messageID);
            return setMessage(message);
        }

        public Builder setPositiveButton(String buttonText, OnClickListener onClickListener) {
            parameters.mPositiveButtonText = buttonText;
            parameters.mPositiveButtonClickListener = onClickListener;
            return this;
        }

        public Builder setPositiveButton(int buttonTextID, OnClickListener onClickListener) {
            String buttonText = getContext().getString(buttonTextID);
            return setPositiveButton(buttonText, onClickListener);
        }

        /* 触发positive按钮后自动dismiss */
        public Builder setEnableAutoDismiss(boolean enableAutoDismiss) {
            parameters.mEnableAutoDismiss = enableAutoDismiss;
            return this;
        }
        /* Enable one button（like: submit） */
        public Builder setEnableOneButton(boolean enableOneButton) {
            parameters.mEnableOneButton = enableOneButton;
            return this;
        }

        public Builder setEnableDefaultButton(boolean enableDefaultButton) {
            parameters.mEnableDefaultButton = enableDefaultButton;
            return this;
        }

        public Builder setNegativeButton(String buttonText, OnClickListener onClickListener) {
            parameters.mNegativeButtonText = buttonText;
            parameters.mNegativeButtonClickListener = onClickListener;
            return this;
        }

        public Builder setNegativeButton(int buttonTextID, OnClickListener onClickListener) {
            String buttonText = getContext().getString(buttonTextID);
            return setNegativeButton(buttonText, onClickListener);
        }

        public Builder setNeutralButton(String buttonText, OnClickListener onClickListener) {
            parameters.mNeutralButtonText = buttonText;
            parameters.mNeutralButtonClickListener = onClickListener;
            return this;
        }

        public Builder setNeutralButton(int buttonTextID, OnClickListener onClickListener) {
            String buttonText = getContext().getString(buttonTextID);
            return setNeutralButton(buttonText, onClickListener);
        }

        public Builder setOnDismissListener(OnDismissListener listener) {
            parameters.mOnDismissListener = listener;
            return this;
        }

        public Builder setInsideContentView(View view) {
            Log.d(TAG, "========== setInsideContentView ==========");
            parameters.mInsideContentView = view;
            return this;
        }

        public SlimDialog create() {
            Log.d(TAG, "dialog create()");
            final SlimDialog dialog = new SlimDialog(parameters.mContext, mTheme);
            dialog.parameters = parameters;
            /*
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            */
            return dialog;
        }
    }

}