package kr.co.triphos.scarecrow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import com.google.firebase.BuildConfig;

import kr.co.triphos.scarecrow.common.Common;
import kr.co.triphos.scarecrow.databinding.ActivityMainBinding;
import kr.co.triphos.scarecrow.utils.AosUtil;
import kr.co.triphos.scarecrow.utils.LoadingUtil;

public class MainActivity extends AppCompatActivity {
	private ActivityMainBinding mBinding;
	@SuppressLint("StaticFieldLeak")
	public static Context mContext;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

		mContext = this;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = this.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_000000));
			window.setNavigationBarColor(ContextCompat.getColor(this, R.color.color_000000));

			CookieSyncManager.createInstance(this);
		}

		webViewSetting();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			CookieSyncManager.getInstance().startSync();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
			CookieSyncManager.getInstance().stopSync();
		}
	}

	/**
	 * WebView 설정
	 */
	private void webViewSetting() {
		WebSettings webSet = mBinding.wvMain.getSettings();
		webSet.setDefaultTextEncodingName("utf-8");			// Encoding
		webSet.setCacheMode(WebSettings.LOAD_NO_CACHE);		// Cache를 사용하지 않는다.(속도 높이기 위함)

		webSet.setJavaScriptEnabled(true);					// 자바스크립트 허용
		webSet.setAllowUniversalAccessFromFileURLs(true);	// Ajax 사용
		webSet.setLoadsImagesAutomatically(true);			// 웹뷰가 앱에 등록되어 있는 이미지 리소스를 자동으로 로드하도록 설정
		webSet.setLoadWithOverviewMode(true);				// 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
		webSet.setUseWideViewPort(true);					// html 컨텐츠가 웹뷰에 맞게 나타나도록 합니다.
		webSet.setDomStorageEnabled(true);

		webSet.setJavaScriptCanOpenWindowsAutomatically(false); // window.open() 사용여부
		webSet.setSupportZoom(false);							// 확대, 축소 기능 사용여부
		webSet.setBuiltInZoomControls(false);					// 안드로이드에서 제공하는 줌 아이콘 사용여부
		webSet.setBlockNetworkImage(false);						// 네트워크의 이미지 리소스를 로드하지않음
		webSet.setSupportMultipleWindows(false);				// 여러개의 윈도우를 사용할 수 있도록 설정
		webSet.setDisplayZoomControls(false);					// 줌 컨트롤러 설정

		mBinding.wvMain.clearCache(true);						// 캐시 삭제
		mBinding.wvMain.clearHistory();											// 이력 삭제
		mBinding.wvMain.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);	// 스크롤 노출타입
		mBinding.wvMain.setHorizontalScrollBarEnabled(true);					// 가로 스크롤
		mBinding.wvMain.setVerticalScrollBarEnabled(true);						// 세로 스크롤
		mBinding.wvMain.setLongClickable(false);								// 롱클릭을 활성화 한다.
		mBinding.wvMain.setWebChromeClient(new WebChromeClient() {				// 웹뷰에 크롬 사용 허용, 이 부분이 없으면 크롬에서 alert가 뜨지 않음
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
//				Log.e(TAG, title);
				if (title.contains("404") || title.contains("500") || title.contains("사용할 수 없음") || title.contains("Error")) {
					String errCode = "";
					String errDesc = "";
					if (title.contains("404")) {
						errCode = "404";
						errDesc = "페이지를 찾을 수 없습니다.";
					}
					else if (title.contains("500")) {
						errCode = "500";
						errDesc = "페이지 로드시 오류가 발생했습니다.";
					}
					else if (title.contains("Error")) {
						errCode = "ERROR";
						errDesc = "예기치 않은 오류가 발생했습니다.";
					}
					else if (title.contains("사용할 수 없음")) {
						errCode = "-2";
						errDesc = title;
					}
					else {
					}
					view.loadUrl(String.format(Common.errorPage, errCode, errDesc));
				}
			}
		});
		mBinding.wvMain.setWebViewClient(new WebViewClient() {
		});

		// mBinding.menuWebview.loadUrl(Common.assetsPath + Common.startURL);	// Assest내에 파일을 불러올 경우
		mBinding.wvMain.addJavascriptInterface(new JavascriptInterface(), "conn");
		mBinding.wvMain.loadUrl(Common.startURL);	//	웹뷰 실행
	}

	/**
	 * 자바스크립트 인터페이스
	 */
	private class JavascriptInterface {
		private Activity mActivity = MainActivity.this;

		/**
		 * TODO 로딩창 닫기
		 */
		@android.webkit.JavascriptInterface
		public void hideLoading() {
			LoadingUtil.dismissLodingDialog();
			mBinding.wvMain.post(() -> mBinding.wvMain.loadUrl("javascript:__HideLoading_Callback();"));
		}

		/**
		 * TODO APP 버전정보
		 */
		@android.webkit.JavascriptInterface
		public void getAppVersion() {
			mBinding.wvMain.post(() -> mBinding.wvMain.loadUrl("javascript:__GetAppVersion_Callback('A', '" + BuildConfig.VERSION_NAME + "');"));
		}

		/**
		 * TODO 로딩창 팝업
		 * @param msg
		 */
		@android.webkit.JavascriptInterface
		public void showLoading(String msg) {
			LoadingUtil.showLodingDialog(mActivity, msg);
		}

		/**
		 * TODO APP종료
		 */
		@android.webkit.JavascriptInterface
		public void callFinish() {
			AosUtil.applicationKillProcess(mActivity);
		}


	}

	/**
	 * TODO 카메라/사진 추가 시 임시로 저장되는 폴더 확인
	 *//*
	private void checkMakeDir() {
		File dir = new File(Common.sdcardPath + "/" + AosUtil.getPackageName(mContext) + "/page");
		if (dir.exists() == false) {
			dir.mkdirs();
		}

		// 갤러리 폴더 안보이게 하기
		File nomediaFile = new File(Common.sdcardPath + "/" + AosUtil.getPackageName(mContext) + "" + "/.nomedia");
		if (nomediaFile.exists() == false) {
			nomediaFile.mkdir();
		}
	}*/
}