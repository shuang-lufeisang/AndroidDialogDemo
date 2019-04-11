package com.duan.android.androiddialogdemo;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.duan.android.androiddialogdemo.dialog.SlimDialog;
import com.duan.android.androiddialogdemo.util.DialogType;
import com.duan.android.androiddialogdemo.widget.CountDownTextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    @BindView(R.id.linear1) LinearLayout linearPrice;      //
    @BindView(R.id.tv1) TextView tvPrice;                  //
    @BindView(R.id.iv_enter1) ImageView ivEnterPrice;      //

    private SlimDialog mDoubleBtDialog;// 双按钮弹窗
    private SlimDialog mSingleBtDialog;// 单按钮弹窗

    private View mDoubleBtContentView;// 双按钮弹窗 content view
    private CountDownTextView btCode; // 获取验证码 按钮
    private EditText telEt;           // 手机号
    private EditText verificationEt;  // 验证码

    private View mSingleBtContentView;// dialog content view
    private TextView tvMessage;       // dialog message

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 当前手机版本为Android 5.0及以上  状态栏透明处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);   // 状态栏透明处理
        }


        // 解决 7.0 以后的状态栏 蒙灰问题（DecorView的源码，7.0 是单独类 有新属性 mSemiTransparentStatusBarColor。反射修改之）
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(getWindow().getDecorView(), Color.TRANSPARENT);  // 改为透明
            } catch (Exception e) {}
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    // 初始化 view
    private void initView() {

        initDoubleBtDialogContentView(); // 初始化双按钮Dialog
        initSingleBtDialogContentView(); // 初始化单按钮Dialog
    }

    @OnClick(R.id.linear1)
    public void onLinear1Clicked(){
       showDoubleBtDialog();
    }

    @OnClick(R.id.linear2)
    public void onLinear2Clicked(){
       showMessageDialog();
    }

    @OnClick(R.id.linear3)
    public void onLinear3Clicked(){
       showSpecifiedMessageDialog();
    }

    /**
     * 双按钮 Dialog
     */
    private void showDoubleBtDialog(){
        if (mDoubleBtDialog == null){
            Log.d(TAG, "mDialog == null");
            mDoubleBtDialog = new SlimDialog
                    .Builder(this)
                    .setTitle("Dialog Test 1")
                    .setTitleColor(getResources().getColor(R.color.themeColor))
                    .setInsideContentView(getDialogContentView(TYPE_NORMAL))
                    .setEnableOneButton(true)
                    .setEnableAutoDismiss(false)
                    .setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showPromptMessage("positive button clicked!");
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showPromptMessage("negative button clicked!");
                        }
                    })
                    .create();
        }
        mDoubleBtDialog.show();
    }

    /**
     * 单按钮 Dialog
     * 适用于 展示普通信息
     */
    private void showMessageDialog(){
        if (mSingleBtDialog == null){
            Log.d(TAG, "mDialog == null");
            mSingleBtDialog = new SlimDialog
                    .Builder(this)
                    .setTitle("Dialog Title")
                    .setTitleColor(getResources().getColor(R.color.themeColor))
                    //.setInsideContentView(getDialogContentView(DialogType.TYPE_SINGLE_BUTTON))
                    .setMessage("dialog message for user\n like : one piece message")
                    .setEnableOneButton(true)
                    .setEnableAutoDismiss(false)
                    .setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showPromptMessage("positive button clicked!");
                        }
                    })
                    .create();
        }
        mSingleBtDialog.show();
    }

    /**
     * 单按钮 Dialog
     * 适用于 展示特定页面信息
     */
    private void showSpecifiedMessageDialog(){
        if (mSingleBtDialog == null){
            Log.d(TAG, "mDialog == null");
            mSingleBtDialog = new SlimDialog
                    .Builder(this)
                    .setTitle("Dialog Title")
                    .setTitleColor(getResources().getColor(R.color.themeColor))
                    .setInsideContentView(getDialogContentView(TYPE_SINGLE_BUTTON))
                    //.setInsideContentView(getDialogContentView(DialogType.TYPE_SINGLE_BUTTON))
                    .setEnableOneButton(true)
                    .setEnableAutoDismiss(false)
                    .setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showPromptMessage("positive button clicked!");
                        }
                    })
                    .create();
        }
        mSingleBtDialog.show();
    }

    /**
     * 非自动 dismiss 的 Dialog 在指定条件下 调用本方法来 dismiss
     */
    public void hideDialog() {
        Log.d(TAG, "hideDialog");
        if (mSingleBtDialog != null && mSingleBtDialog.isShowing()){
            mSingleBtDialog.dismiss();
        }
        if (mDoubleBtDialog != null && mDoubleBtDialog.isShowing()){
            mDoubleBtDialog.dismiss();
        }
    }

    public View initDoubleBtDialogContentView() {
        if (mDoubleBtContentView == null){
            mDoubleBtContentView = View.inflate(this, R.layout.dialog_verification, null);
        }
        btCode = mDoubleBtContentView.findViewById(R.id.tv_send_verify_code);
        telEt = mDoubleBtContentView.findViewById(R.id.edit_telephone);
        verificationEt = mDoubleBtContentView.findViewById(R.id.edit_code);

        // 点击获取验证码 清空输入
        btCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/4/1 调接口发送验证码
                // mPresenter.sendCheckCode();
                showPromptMessage("调接口发送验证码");
                verificationEt.setText("");
            }
        });
        return mDoubleBtContentView;
    }
    public View initSingleBtDialogContentView() {
        if (mSingleBtContentView == null){
            mSingleBtContentView = View.inflate(this, R.layout.dialog_msg, null);
        }
        tvMessage = mSingleBtContentView.findViewById(R.id.tv_message);
        tvMessage.setPadding(16,10,16,20);
        tvMessage.setText("user name: Monkey D Luffy \n position: captain" +
                "\n favorite food: various of meat \n father: Monkey D Dragon " +
                "\n grandpa: Monkey D KaPu \n brother: A & S");
        return mSingleBtContentView;
    }

    /**
     *
     * Dialog content view
     * @param dialogType todo  Def 注解代替枚举类型
     * @return
     */
    public View getDoubleBtDialogContentView(int dialogType) {
        switch (dialogType){
            case DialogType.TYPE_NORMAL:
                break;
            case DialogType.TYPE_SINGLE_BUTTON:
                break;
            default:
                break;
        }
        if (mDoubleBtContentView == null){
            mDoubleBtContentView = View.inflate(this, R.layout.dialog_verification, null);
            telEt = mDoubleBtContentView.findViewById(R.id.edit_telephone);
            verificationEt = mDoubleBtContentView.findViewById(R.id.edit_code);
        }
        telEt.setText("13524114059");
        telEt.setEnabled(false);
        telEt.setHint("");
        return mDoubleBtContentView;
    }

    public View getSingleBtDialogContentView(int dialogType) {
        switch (dialogType){
        }
        if (mSingleBtContentView == null){
            mSingleBtContentView = View.inflate(this, R.layout.dialog_verification, null);
            telEt = mSingleBtContentView.findViewById(R.id.edit_telephone);
            verificationEt = mSingleBtContentView.findViewById(R.id.edit_code);
        }
        telEt.setText("13524114059");
        telEt.setEnabled(false);
        telEt.setHint("");
        return mSingleBtContentView;
    }

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_SINGLE_BUTTON = 1;
    //Retention -元注解(系统提供的，用于定义注解的“注解”)
    @Retention(RetentionPolicy.SOURCE)
    //这里指定int的取值只能是以下范围
    @IntDef({TYPE_NORMAL, TYPE_SINGLE_BUTTON})
    @interface DialogTypeDef {
    }

    public View getDialogContentView(@DialogTypeDef int dialogType) {
        switch (dialogType){
            case DialogType.TYPE_NORMAL:
                if (mDoubleBtContentView == null){
                    mDoubleBtContentView = View.inflate(this, R.layout.dialog_verification, null);
                    telEt = mDoubleBtContentView.findViewById(R.id.edit_telephone);
                    verificationEt = mDoubleBtContentView.findViewById(R.id.edit_code);
                }
                telEt.setText("13524114059");
                telEt.setEnabled(false);
                telEt.setHint("");
                return mDoubleBtContentView;

            case DialogType.TYPE_SINGLE_BUTTON:   // 单按钮Dialog 展示信息使用
                if (mSingleBtContentView == null){
                    mSingleBtContentView = View.inflate(this, R.layout.dialog_msg, null);
                    tvMessage = mSingleBtContentView.findViewById(R.id.tv_message);
                }
                tvMessage.setText("user name: Monkey D Luffy\n position: captain" +
                        "\n favorite food: various of meat \n father: Monkey D Dragon " +
                        "\n grandpa: Monkey D KaPu \n brother: A & S");
                return mSingleBtContentView;
            default:
                if (mDoubleBtContentView == null){
                    mDoubleBtContentView = View.inflate(this, R.layout.dialog_verification, null);
                    telEt = mDoubleBtContentView.findViewById(R.id.edit_telephone);
                    verificationEt = mDoubleBtContentView.findViewById(R.id.edit_code);
                }
                telEt.setText("13524114059");
                telEt.setEnabled(false);
                telEt.setHint("");
                return mDoubleBtContentView;
        }
    }

    public void showPromptMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
