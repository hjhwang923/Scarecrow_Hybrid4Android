package kr.co.triphos.scarecrow.common;

import android.os.Environment;

public class Common {
	public static boolean AlarmFlag = false;
	public static String deviceToken 		= "";				// Device Token
	public static final String serverIp 	= "localhost";	// WebServer IP	운영서버:14.0.89.197 (황대근:118, 황현정:171, 서유정:174) 172.1.0.174
	public static final String serverPort	= "9092";				// WebServer Port
	public static final int imagePort		= 3917;				// phos imageServer Port
	public static final String startURL		= String.format("http://%s:%s/", serverIp, serverPort);
	public static final String assetsPath 	= "file:///android_asset/";
	public static final String sdcardPath	= String.format("%s/Android/data", Environment.getExternalStorageDirectory().getPath());
	public static final String downFileUrl	= String.format("%s/Download/", Environment.getExternalStorageDirectory().getPath());
	public static final String errorPage	= assetsPath + "error.html?errorCode=%s&errorDesc=%s";

}
