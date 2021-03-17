package kr.co.triphos.scarecrow.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import kr.co.triphos.scarecrow.R;

/**
 *
 */
public class ToastUtil {

    /**
     * Toast
     * 기본 Tag는 i 입니다.
     * Info(i), Error(e), Sucess(s)
     * @param tag
     * @param activity
     * @param strMessage
     */
    public static void show(String tag, Activity activity, String strMessage) {
        int ic_info = R.drawable.popup_icon_03;
        if(tag.equalsIgnoreCase("e")) {
            ic_info = R.drawable.popup_icon_01;
        }
        else if(tag.equalsIgnoreCase("s")) {
            ic_info = R.drawable.popup_icon_02;
        }

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.layout_toast, null);
        ImageView toastIcon = (ImageView) layout.findViewById(R.id.iv_toast_icon);
        TextView toastMsg = (TextView) layout.findViewById(R.id.tv_toast_msg);
        toastIcon.setImageResource(ic_info);
        toastMsg.setText(strMessage);

        Toast mToast = new Toast(activity.getApplicationContext());
        mToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, activity.getResources().getDimensionPixelSize(R.dimen.toast_bottom_margin2));
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setView(layout);
        mToast.show();
    }
}