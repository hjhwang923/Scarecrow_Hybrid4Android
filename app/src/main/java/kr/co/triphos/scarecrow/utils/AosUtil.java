package kr.co.triphos.scarecrow.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;

/**
 * Android 제어용 Utils
 */
public class AosUtil {
	// 하드웨어 빽키 연속 누르는 시간
	private static long backKeyPressedTime = 0;

	/**
	 * APP 버전정보 가져오기
	 */
	public static String getAppVersion(Context ctx) {
		PackageInfo pInfo = null;
		try {
			pInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return pInfo.versionName;
	}

	/**
	 * TODO package 명 리턴
	 * @param aContext
	 * @return 패키지명
	 */
	public static String getPackageName(Context aContext) {
		return aContext.getApplicationContext().getPackageName();
	}

	/**
	 * dp to pixel
	 * @param dp Integer
	 * @return Integer
	 */
	public static int dpToPx(int dp) {
		return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
	}

	/**
	 * pixel to dp
	 * @param px Integer
	 * @return Integer
	 */
	public static int pxToDp(int px) {
		return (int) (px / Resources.getSystem().getDisplayMetrics().density);
	}

	/**
	 * NavigationBar 유무 체크
	 * @param context Context
	 * @return Boolean
	 */
	public static boolean checkForNavigationBar(Context context) {
		boolean useSoftNavigation;
		int id = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");

		if (id > 0) {
			useSoftNavigation = context.getResources().getBoolean(id);
		}
		else {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
				boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
				useSoftNavigation = (!(hasBackKey && hasHomeKey));
			}
			else {
				useSoftNavigation = ViewConfiguration.get(context).hasPermanentMenuKey();
			}
		}

		return useSoftNavigation;
	}

	/**
	 * TODO 네트워크 체크
	 * @param context Context
	 * @return Boolean
	 */
	public static boolean checkNetwokState(Context context) {
		try {
			ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetwork = conManager.getActiveNetworkInfo();
			String strActiveNetworkType = "";

			if (activeNetwork != null) {
				if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
					strActiveNetworkType = "TYPE_WIFI";
				}
				else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
					strActiveNetworkType = "TYPE_MOBILE";
				}
				else if (activeNetwork.getType() == ConnectivityManager.TYPE_WIMAX) {
					strActiveNetworkType = "TYPE_WIMAX_LTE";
				}
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception ex) {
			return false;
		}
	}

	/**
	 * TODO App 종료
	 * @param activity Activity
	 */
	public static void applicationKillProcess(Activity activity) {
		if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
			backKeyPressedTime = System.currentTimeMillis();
			ToastUtil.show("i", activity, "한번 더 누르면 종료됩니다.");
		}
		else {
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}

	/**
	 * TODO 키보드 숨기기
	 * @param activity Activity
	 */
	public static void configKeyboard(Activity activity) {
		if (activity == null) return;

		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

		if (imm.isAcceptingText()) {
			imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
		}
	}
/*
	*//**
	 * TODO Camera, Photos 호출
	 * @param aContext Context
	 * @param callPageType 호출후 리턴받을 페이지 구분명
	 * @param fileNM 저장할 파일명(지정하지 않으면 yyyyMMdd_HHmmss.[jpg|gif]로 저장됨)
	 *//*
	public static void showCameraActivity(Context aContext, String isAlbum, String callPageType, String fileNM) {
		Intent intent = new Intent(aContext, CameraActivity.class);
		intent.putExtra("isAlbum", isAlbum);
		intent.putExtra("callPageType", callPageType);
		intent.putExtra("fileNM", fileNM);
		aContext.startActivity(intent);
	}*/
}
