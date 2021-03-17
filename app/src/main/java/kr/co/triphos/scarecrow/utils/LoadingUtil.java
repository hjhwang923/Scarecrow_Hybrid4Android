package kr.co.triphos.scarecrow.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import kr.co.triphos.scarecrow.R;


public class LoadingUtil {
    public static Dialog mLoadingDialog;

    /**
     * 로딩창 시작
     * @param activity
     * @param strMessage
     */
    public static void showLodingDialog(Activity activity, String strMessage) {
        View loding_view = activity.getLayoutInflater().inflate(R.layout.layout_loding, null);
        TextView lodingMsg = (TextView) loding_view.findViewById(R.id.tv_loding_message);
        ImageView lodingImg = (ImageView) loding_view.findViewById(R.id.iv_save_loding);

        lodingMsg.setText(strMessage);
        AnimationDrawable animLoding = (AnimationDrawable) lodingImg.getBackground();
        animLoding.start();

        if(mLoadingDialog == null) {
            mLoadingDialog = new Dialog(activity, R.style.materialDialogSheet);
            mLoadingDialog.setContentView(loding_view);
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.getWindow().setGravity(Gravity.CENTER);
            WindowManager.LayoutParams layoutParams = mLoadingDialog.getWindow().getAttributes();
            layoutParams.y = 30;
            mLoadingDialog.getWindow().setAttributes(layoutParams);
            mLoadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mLoadingDialog.show();
        } else {
            // 로딩창 텍스트만 변경하기 위함.
            mLoadingDialog.setContentView(loding_view);
        }
    }

    /**
     * 로딩창 종료
     */
    public static void dismissLodingDialog() {
        if(mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }
}