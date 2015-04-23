package org.thinkcreate.esp8266_button;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.thinkcreate.org.thinkcreate.esp_sendUDPsettings.Setting_UDP_handler;

/**
 * Created by sundeqing on 4/22/15.
 */


public class Set_URL_AsyncTask extends AsyncTask<Void,Void,Boolean> {
    private Activity parentActivity;
    private final String URL;
    private boolean g;
    private static final String TAG = "Set_URL_AsyncTask";
    private TextView mTextView;
    private Setting_UDP_handler mSettingUDPHandler;

    public Set_URL_AsyncTask(MainActivity paramEspTouchActivity, Activity paramActivity, String _URL)
    {
        this.parentActivity = paramActivity;
        this.g = false;
        this.URL = _URL;
        this.mSettingUDPHandler=new Setting_UDP_handler(this.URL);
        mTextView = (TextView) parentActivity.findViewById(R.id.textView1);
    }

    public void onCancelled(Boolean result)
    {
        Log.d(TAG, "Cancelled");
        mSettingUDPHandler.a();
        parentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText("URL Cancelled");
            }
        });
    }


    @Override
    protected Boolean doInBackground(Void... voids) {
        Log.d(TAG, "Background");
        return mSettingUDPHandler.b();
    }

    protected void onPreExecute()
    {
        Log.d(TAG, "PRE EXE");
        parentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText("URL SETTING");
            }
        });
    }

    @Override
    protected void onPostExecute(Boolean result) {
        Log.d(TAG, "POST");
        final boolean result2=result;
        Log.d(TAG, result2?"UDP SETTING OK":"UDP SETTING FAIL");
        parentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText(result2?"URL SET OK":"URL SET Failed");
            }
        });
    }




}