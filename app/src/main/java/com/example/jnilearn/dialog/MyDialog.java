package com.example.jnilearn.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.example.jnilearn.R;

/**
 * Created by Administrator on 2018/10/25.
 */

public class MyDialog extends Dialog{

    private AlertController mAlert;

    protected MyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mAlert=new AlertController(this,getWindow());
    }

    public  static class Builder{

        private final AlertController.AlertParams P;

        public Builder(Context context){

            this(context, R.style.dialog);

        }

        public Builder(Context context,int themeResId){
            P=new AlertController.AlertParams(context, themeResId);
        }



        public Builder setContentView(View view){
            P.mView=view;
            P.mViewLayoutResId=0;
            return this;
        }
        // 设置布局内容的layoutId
        public Builder setContentView(int layoutId) {
            P.mView = null;
            P.mViewLayoutResId = layoutId;
            return this;
        }

        // 设置点击事件
        public Builder setOnClickListener(int view , View.OnClickListener listener){
            P.mClickArray.put(view,listener);
            return this;
        }

        /**
         * 从底部弹出
         * @param isAnimation 是否有动画
         * @return
         */
        public Builder formBottom(boolean isAnimation){
            if(isAnimation){
                P.mAnimations = R.style.dialog_from_bottom_anim;
            }
            P.mGravity = Gravity.BOTTOM;
            return this;
        }
        public Builder fullWidth(){
            P.mWidth= WindowManager.LayoutParams.MATCH_PARENT;
            return  this;
        }

        public MyDialog create() {
            // Context has already been wrapped with the appropriate theme.
            final MyDialog dialog = new MyDialog(P.mContext, P.mThemeResId);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }


        public MyDialog show(){
            final MyDialog dialog=create();
            dialog.show();
            return  dialog;
        }
    }
}
