package org.thinkcreate.esp8266_button;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener{

    private Button mButtonESPTouch,mButtonSETUrl;
    private EditText mEditSSID,mEditPassword,mEditURL;
    private static final String TAG = "ESP8266_Button";
    private ESP_Touch_AsyncTask activeESP_Touch_AsyncTask = null;
    private Set_URL_AsyncTask activeSet_URL_AsyncTask = null;

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

        mEditSSID.setText(getResources().getString(R.string.default_SSID));
        mEditPassword.setText(getResources().getString(R.string.default_password));
        mEditURL.setText(getResources().getString(R.string.default_URL));
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

            String ssid = mEditSSID.getText().toString();
            String password = mEditPassword.getText().toString();

            //new activity_B_class(this, this, ssid, password).execute(new String[0]);
            //testClass test = new testClass();
            if (activeESP_Touch_AsyncTask!=null){
                activeESP_Touch_AsyncTask.cancel(true);
            }
            if (activeESP_Touch_AsyncTask!=null && activeESP_Touch_AsyncTask.getStatus() == AsyncTask.Status.RUNNING){
                Log.d(TAG, "Not cancelled yet");
                Toast.makeText(getApplicationContext(), "Still killing old task\nplease try again", Toast.LENGTH_SHORT).show();
            }else {
                activeESP_Touch_AsyncTask = new ESP_Touch_AsyncTask(this, this, ssid, password);
                activeESP_Touch_AsyncTask.execute();
            }
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

}
