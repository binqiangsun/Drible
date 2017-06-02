package com.lukou.service.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.lukou.service.LibApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public final class Environment {

	private static boolean debugable = true;
	private static String imei;
	private static String screenInfo;
	private static PackageInfo packageInfo;
	private static ApplicationInfo appInfo;
	private static String versionName;

	private Environment() {
	}

	private static String escapeSource(String src) {
		StringBuilder sb = new StringBuilder();
		for (char c : src.toCharArray()) {
			if (c >= 'a' && c <= 'z') {
				sb.append(c);
			} else if (c >= 'A' && c <= 'Z') {
				sb.append(c);
			} else if (c >= '0' && c <= '9') {
				sb.append(c);
			} else if (c == '.' || c == '_' || c == '-' || c == '/') {
				sb.append(c);
			} else if (c == ' ') {
				sb.append('_');
			}
		}
		return sb.toString();
	}

	private static PackageInfo pkgInfo() {
		if (packageInfo == null) {
			try {
				Context c = LibApplication.instance();
				packageInfo = c.getPackageManager().getPackageInfo(
						c.getPackageName(), 0);
			} catch (NameNotFoundException e) {
			}
		}

		return packageInfo;
	}

	private static ApplicationInfo appInfo(){

		if (appInfo == null){
			Context context = LibApplication.instance();
			try {
				appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		}

		return appInfo;
	}

	/**
	 * 手机的IMEI设备序列号
	 * <p>
	 * 第一次启动时会保存该序列号，可以频繁调用
	 * 
	 * @return IMEI or "00000000000000" if error
	 */
	public static String imei() {
		if (imei == null) {
			// update cached imei when identity changed. including brand, model,
			// radio and system version
			String deviceIdentity = Build.VERSION.RELEASE + ";" + Build.MODEL
					+ ";" + Build.BRAND;
			if (deviceIdentity.length() > 64) {
				deviceIdentity = deviceIdentity.substring(0, 64);
			}
			if (deviceIdentity.indexOf('\n') >= 0) {
				deviceIdentity = deviceIdentity.replace('\n', ' ');
			}

			String cachedIdentity = null;
			String cachedImei = null;
			try {
				// do not use file storage, use cached instead
				File path = new File(LibApplication.instance().getCacheDir(),
						"cached_imei");
				FileInputStream fis = new FileInputStream(path);
				byte[] buf = new byte[1024];
				int l = fis.read(buf);
				fis.close();
				String str = new String(buf, 0, l, "UTF-8");
				int a = str.indexOf('\n');
				cachedIdentity = str.substring(0, a);
				int b = str.indexOf('\n', a + 1);
				cachedImei = str.substring(a + 1, b);
			} catch (Exception e) {
			}

			if (deviceIdentity.equals(cachedIdentity)) {
				imei = cachedImei;
			} else {
				imei = null;
			}

			// cache fail, read from telephony manager
			if (imei == null) {
				try {
					TelephonyManager tel = (TelephonyManager) LibApplication
							.instance().getSystemService(
									Context.TELEPHONY_SERVICE);
					imei = tel.getDeviceId();
					if (imei != null) {
						if (imei.length() < 8) {
							imei = null;
						} else {
							char c0 = imei.charAt(0);
							boolean allSame = true;
							for (int i = 0, n = imei.length(); i < n; i++) {
								if (c0 != imei.charAt(i)) {
									allSame = false;
									break;
								}
							}
							if (allSame)
								imei = null;
						}
					}
				} catch (Exception e) {
				}
				if (imei != null) {
					try {
						File path = new File(LibApplication.instance()
								.getCacheDir(), "cached_imei");
						FileOutputStream fos = new FileOutputStream(path);
						String str = deviceIdentity + "\n" + imei + "\n";
						fos.write(str.getBytes("UTF-8"));
						fos.close();
					} catch (Exception e) {
					}
				} else {
					File path = new File(LibApplication.instance()
							.getCacheDir(), "cached_imei");
					path.delete();
				}
			}

			if (imei == null) {
				imei = "00000000000000";
			}
		}
		return imei;
	}

	public static String screenInfo() {
		if (TextUtils.isEmpty(screenInfo)) {
			Context c = LibApplication.instance();
			DisplayMetrics dm = c.getResources().getDisplayMetrics();
			screenInfo = "screenwidth=" + dm.widthPixels + "&screenheight="
					+ dm.heightPixels + "&screendensity=" + dm.density;
		}

		return screenInfo;
	}

	public static String version() {
		return pkgInfo().versionName;
	}

	public static String versionCodeStr() {
		if(TextUtils.isEmpty(versionName)){
			versionName = String.valueOf(pkgInfo().versionCode);
		}
		return versionName;
	}

	public static int versionCode() {
		return pkgInfo().versionCode;
	}
    private static String sDeviceId;

    public static String deviceID() {
        if(TextUtils.isEmpty(sDeviceId)) {
            TelephonyManager telephonyManager;
            telephonyManager = (TelephonyManager) LibApplication.instance().getSystemService( Context.TELEPHONY_SERVICE );
            if (telephonyManager != null) {
                sDeviceId = telephonyManager.getDeviceId();
            }
        }
        return sDeviceId;
    }

    private static String sSource = "";
    private static String sWeiboSource = "";

    public static String source() {
        return ChannelManager.instance().getChannel();
    }

    public static String weiboSource() {
        if (TextUtils.isEmpty(sWeiboSource)) {
			sWeiboSource = appInfo().metaData.getString("WEIBO_SOURCE","");
        }
        return sWeiboSource;
    }

    public static boolean isDebugMode() {
        return debugable;
    }

	public static void setDebugable(boolean isDebug) {
		debugable = isDebug;
	}
}
