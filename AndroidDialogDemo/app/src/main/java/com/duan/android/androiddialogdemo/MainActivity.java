package com.duan.android.androiddialogdemo;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
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
import com.duan.android.androiddialogdemo.widget.CountDownTextView;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    @BindView(R.id.linear1) LinearLayout linearPrice;      //
    @BindView(R.id.tv1) TextView tvPrice;                  //
    @BindView(R.id.iv_enter1) ImageView ivEnterPrice;      //

    private SlimDialog mDoubleBtDialog;      // 双按钮弹窗
    private SlimDialog mSingleBtDialog;      // 单按钮弹窗
    private View contentView;        // dialog content view
    private CountDownTextView btCode; // 获取验证码 按钮
    private EditText telEt;           // 手机号
    private EditText verificationEt;  // 验证码

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

        initDialogContentView(); // 初始化Dialog
    }

    @OnClick(R.id.linear1)
    public void onLinear1Clicked(){
       showDoubleBtDialog();
    }

    @OnClick(R.id.linear2)
    public void onLinear2Clicked(){
       showSingleBtDialog();
    }

    /**
     * 双按钮 Dialog
     *
     */
    private void showDoubleBtDialog(){
        if (mDoubleBtDialog == null){
            Log.d(TAG, "mDialog == null");
            mDoubleBtDialog = new SlimDialog
                    .Builder(this)
                    .setTitle("Dialog Test 1")
                    .setTitleColor(getResources().getColor(R.color.themeColor))
                    .setInsideContentView(getDialogContentView1())
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
     * 适用于 展示既定信息
     */
    private void showSingleBtDialog(){
        if (mSingleBtDialog == null){
            Log.d(TAG, "mDialog == null");
            mSingleBtDialog = new SlimDialog
                    .Builder(this)
                    .setTitle("Dialog Test 1")
                    .setTitleColor(getResources().getColor(R.color.themeColor))
                    .setInsideContentView(getDialogContentView1())
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

    public View initDialogContentView() {
        if (contentView == null){
            contentView = View.inflate(this, R.layout.dialog_verification, null);
        }
        btCode = contentView.findViewById(R.id.tv_send_verify_code);
        telEt = contentView.findViewById(R.id.edit_telephone);
        verificationEt = contentView.findViewById(R.id.edit_code);

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
        return contentView;
    }

    /**
     * Dialog content view
     * @param dialogType
     * @return
     */
    public View getDialogContentView1(int dialogType) {
        switch (dialogType){
        }
        if (contentView == null){
            contentView = View.inflate(this, R.layout.dialog_verification, null);
            telEt = contentView.findViewById(R.id.edit_telephone);
            verificationEt = contentView.findViewById(R.id.edit_code);
        }
        telEt.setText("13524114059");
        telEt.setEnabled(false);
        telEt.setHint("");
        return contentView;
    }


    public void showPromptMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
