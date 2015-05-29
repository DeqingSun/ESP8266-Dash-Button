package org.thinkcreate.esp8266_button;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by sundeqing on 5/12/15.
 */
public class Wifi_SSID_access {

    private static final String TAG = "Wifi_SSID_access";

    private final Context mContext;

    public Wifi_SSID_access(Context context) {
        mContext = context;
    }

    public String getWifiConnectedSsid() {
        try {
            WifiInfo mWifiInfo = getConnectionInfo();
            String ssid = null;
            if (mWifiInfo != null && isWifiConnected()) {
                int len = mWifiInfo.getSSID().length();
                if (mWifiInfo.getSSID().startsWith("\"")
                        && mWifiInfo.getSSID().endsWith("\"")) {
                    ssid = mWifiInfo.getSSID().substring(1, len - 1);
                } else {
                    ssid = mWifiInfo.getSSID();
                }

            }
            return ssid;
        }catch (Exception e){
            Log.e(TAG, "STACKTRACE");
            Log.e(TAG, Log.getStackTraceString(e));
            return null;
        }
    }

    public String getWifiConnectedBssid() {
        WifiInfo mWifiInfo = getConnectionInfo();
        String bssid = null;
        if (mWifiInfo != null && isWifiConnected()) {
            bssid = mWifiInfo.getBSSID();
        }
        return bssid;
    }

    // get the wifi info which is "connected" in wifi-setting
    private WifiInfo getConnectionInfo() {
        WifiManager mWifiManager = (WifiManager) mContext
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        return wifiInfo;
    }

    private boolean isWifiConnected() {
        NetworkInfo mWiFiNetworkInfo = getWifiNetworkInfo();
        boolean isWifiConnected = false;
        if (mWiFiNetworkInfo != null) {
            isWifiConnected = mWiFiNetworkInfo.isConnected();
        }
        return isWifiConnected;
    }

    private NetworkInfo getWifiNetworkInfo() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWiFiNetworkInfo;
    }
}
