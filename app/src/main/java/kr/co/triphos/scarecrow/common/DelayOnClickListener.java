package kr.co.triphos.aos.dyboiler.common;

import android.os.SystemClock;
import android.view.View;

public abstract class DelayOnClickListener implements View.OnClickListener {

    private static final long MIN_CLICK_INTERVAL = 600; // 중복 클릭 방지 시간 설정
    private long mLastClickTime; // 마지막 클릭 시간

    public abstract void onClickDelay(View v);

    @Override
    public final void onClick(View v) {
        long currentClickTime = SystemClock.uptimeMillis();
        long elapsedTime = currentClickTime - mLastClickTime;
        mLastClickTime = currentClickTime;

        // 중복 클릭인 경우
        if(elapsedTime <= MIN_CLICK_INTERVAL) {
            return;
        }

        // 중복 클릭이 아니라면 추상함수 호출
        onClickDelay(v);
    }
}