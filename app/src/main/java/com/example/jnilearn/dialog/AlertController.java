package com.example.jnilearn.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.security.spec.MGF1ParameterSpec;

/**
 * Created by Administrator on 2018/10/25.
 */

public class AlertController {
    private MyDialog mDialog;
    private Window mWindow;

    private DialogViewHelper mViewHelper;

    public AlertController(MyDialog dialog, Window window) {

        this.mDialog=dialog;
        this.mWindow=window;
    }

    public MyDialog getDialog() {
        return mDialog;
    }

    public Window getWindow() {
        return mWindow;
    }

    public void setViewHelper(DialogViewHelper mViewHelper) {
        this.mViewHelper = mViewHelper;
    }

    public  void setText(int i, CharSequence charSequence) {
        mViewHelper.setText(i,charSequence);
    }

    public void setOnclickListener(int i, View.OnClickListener onClickListener) {
        mViewHelper.setOnclickListener(i, onClickListener);
    }

    public static class AlertParams{
        public Context mContext;
        public int mThemeResId;
        // 点击空白是否能够取消  默认点击阴影可以取消
        public boolean mCancelable=true;
        // dialog Cancel监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        // dialog Dismiss监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        // dialog Key监听
        public DialogInterface.OnKeyListener mOnKeyListener;

        public int mViewLayoutResId;

        public View mView;
        // 存放点击事件
        public SparseArray<View.OnClickListener> mClickArray=new SparseArray<>();

        // 存放字体的修改
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();

        // 宽度
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 动画
        public int mAnimations = 0;
        // 位置
        public int mGravity = Gravity.CENTER;
        // 高度
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;


        public AlertParams(Context context,int themeResId){
            this.mContext=context;
            this.mThemeResId=themeResId;
        }

        public void apply(AlertController mAlert) {

            DialogViewHelper viewHelper=null;

            if(mViewLayoutResId!=0){
                viewHelper=new DialogViewHelper(mContext,mViewLayoutResId);
            }

            if(mView!=null){
                viewHelper=new DialogViewHelper();
                viewHelper.setContentView(mView);
            }

            if (viewHelper == null) {
                throw new IllegalArgumentException("请设置布局setContentView()");
            }


            mAlert.getDialog().setContentView(viewHelper.getContentView());

            mAlert.setViewHelper(viewHelper);


            // 2.设置文本
            int textArraySize = mTextArray.size();
            for (int i = 0; i < textArraySize; i++) {
                mAlert.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }

            // 3.设置点击
            for(int j=0;j<mClickArray.size();j++){
                mAlert.setOnclickListener(mClickArray.keyAt(j), mClickArray.valueAt(j));
            }


            // 4.配置自定义的效果  全屏  从底部弹出    默认动画
            Window window=mAlert.getWindow();

            // 设置位置
            window.setGravity(mGravity);

            // 设置动画
            if(mAnimations!=0){
                window.setWindowAnimations(mAnimations);
            }

            // 设置宽高
            WindowManager.LayoutParams params=window.getAttributes();
            params.width=mWidth;
            params.height=mHeight;
            window.setAttributes(params);

        }
    }



}
