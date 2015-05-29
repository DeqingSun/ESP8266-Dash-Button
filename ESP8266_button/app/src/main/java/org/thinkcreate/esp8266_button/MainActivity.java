package org.thinkcreate.esp8266_button;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.espressif.iot.esptouch.EsptouchTask;
import com.espressif.iot.esptouch.IEsptouchResult;
import com.espressif.iot.esptouch.IEsptouchTask;
import com.espressif.iot.esptouch.task.__IEsptouchTask;


public class MainActivity extends Activity implements View.OnClickListener{

    private Button mButtonESPTouch,mButtonSETUrl;
    private TextView mTextView;
    private EditText mEditSSID,mEditPassword,mEditURL;
    private static final String TAG = "ESP8266_Button";
    private Set_URL_AsyncTask activeSet_URL_AsyncTask = null;
    private Wifi_SSID_access mWifiSSIDAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonESPTouch =(Button)findViewById(R.id.button_ESPTouch);
        mButtonESPTouch.setOnClickListener(this);
        mButtonSETUrl =(Button)findViewById(R.id.button_SetURL);
        mButtonSETUrl.setOnClickListener(this);

        mEditSSID=(EditText)findViewById(R.id.editText_SSID);
        mEditPassword=(EditText)findViewById(R.id.editText_Password);
        mEditURL=(EditText)findViewById(R.id.editText_URL);

        mTextView = (TextView) findViewById(R.id.textView1);

        mWifiSSIDAccess = new Wifi_SSID_access(this);
    }

    @Override
    protected void onResume() {
        super.onResume();


        mEditURL.setText(getResources().getString(R.string.default_URL));

        // display the connected ap's ssid
        String apSsid = mWifiSSIDAccess.getWifiConnectedSsid();

        if (apSsid != null) {
            mEditSSID.setText(apSsid);
            if (getResources().getString(R.string.default_SSID).equals(apSsid)){
                mEditPassword.setText(getResources().getString(R.string.default_password));
            }else{
                mEditPassword.setText("");
            }
        } else {
            mTextView.setText("WiFi not connected");
            mEditSSID.setText(getResources().getString(R.string.default_SSID));
            mEditPassword.setText(getResources().getString(R.string.default_password));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClick(View v) {
        if (mButtonESPTouch == v){
            Log.d(TAG, "START ESPTouch");

            String apSsid = mEditSSID.getText().toString();
            String apPassword = mEditPassword.getText().toString();
            String apBssid = mWifiSSIDAccess.getWifiConnectedBssid();
            Boolean isSsidHidden = false;
            String isSsidHiddenStr = "NO";
            if (isSsidHidden)
            {
                isSsidHiddenStr = "YES";
            }
            if (__IEsptouchTask.DEBUG) {
                Log.d(TAG, "mBtnConfirm is clicked, mEdtApSsid = " + apSsid
                        + ", " + " mEdtApPassword = " + apPassword);
            }

            if (__IEsptouchTask.DEBUG) {
                Log.d(TAG, "mBtnConfirm is clicked, mEdtApSsid = " + apSsid
                        + ", " + " mEdtApPassword = " + apPassword);
            }
            new EsptouchAsyncTask2().execute(apSsid, apBssid, apPassword, isSsidHiddenStr);
        }else if(mButtonSETUrl == v){
            Log.d(TAG, "START Setting URL");

            String URL = mEditURL.getText().toString();

            if (activeSet_URL_AsyncTask!=null){
                activeSet_URL_AsyncTask.cancel(true);
            }
            if (activeSet_URL_AsyncTask!=null && activeSet_URL_AsyncTask.getStatus() == AsyncTask.Status.RUNNING){
                Log.d(TAG, "Not cancelled yet");
                Toast.makeText(getApplicationContext(), "Still killing old task\nplease try again", Toast.LENGTH_SHORT).show();
            }else {
                activeSet_URL_AsyncTask = new Set_URL_AsyncTask(this, this, URL);
                activeSet_URL_AsyncTask.execute();
            }


        }



    }

    /**
     * EsptouchAsyncTask2是主要的配置任务
     * */
    private class EsptouchAsyncTask2 extends AsyncTask<String, Void, IEsptouchResult> {

        private ProgressDialog mProgressDialog;

        private IEsptouchTask mEsptouchTask;

        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog
                    .setMessage("Esptouch is configuring, please wait for a moment...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (__IEsptouchTask.DEBUG) {
                        Log.i(TAG, "progress dialog is canceled");
                    }
                    if (mEsptouchTask != null) {
                        mEsptouchTask.interrupt();
                    }
                }
            });
            mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                    "Waiting...", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            mProgressDialog.show();
            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                    .setEnabled(false);
        }

        @Override
        protected IEsptouchResult doInBackground(String... params) {
            String apSsid = params[0];
            String apBssid = params[1];
            String apPassword = params[2];
            String isSsidHiddenStr = params[3];
            boolean isSsidHidden = false;
            if(isSsidHiddenStr.equals("YES"))
            {
                isSsidHidden = true;
            }
            mEsptouchTask = new EsptouchTask(apSsid, apBssid, apPassword, isSsidHidden, MainActivity.this);
            IEsptouchResult result = mEsptouchTask.executeForResult();
            return result;
        }

        @Override
        protected void onPostExecute(IEsptouchResult result) {
            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                    .setEnabled(true);
            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(
                    "Confirm");
            // it is unnecessary at the moment, add here just to show how to use isCancelled()
            if (!result.isCancelled()) {
                if (result.isSuc()) {
                    mProgressDialog.setMessage("Esptouch success, bssid = "
                            + result.getBssid());
                } else {
                    mProgressDialog.setMessage("Esptouch fail");
                }
            }
        }
    }

}
