package org.thinkcreate.esp8266_button;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener{

    private Button mButton1;
    private static final String TAG = "ESP8266_Button";
    private ESP_Touch_AsyncTask activeESP_Touch_AsyncTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton1=(Button)findViewById(R.id.button_1);
        mButton1.setOnClickListener(this);

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
        Log.d(TAG, "YES");
        String ssid="Buffalo-G-5E60",password="a";
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
    }

}
