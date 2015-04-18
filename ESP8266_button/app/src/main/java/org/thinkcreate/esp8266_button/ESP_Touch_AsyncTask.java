package org.thinkcreate.esp8266_button;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.thinkcreate.esp_reverse.rev_Class_h_ESP_TOUCH_Main;

/**
 * Created by sundeqing on 4/16/15.
 */
public class ESP_Touch_AsyncTask extends AsyncTask<Void,Void,Boolean>
    {
        private Activity b;
        private ProgressDialog c;
        private final String ssidStr;
        private final String passwordStr;
        private boolean g;
        private static final String TAG = "ESP_Touch_AsyncTask";
        private rev_Class_h_ESP_TOUCH_Main ESPSender;

        public ESP_Touch_AsyncTask(MainActivity paramEspTouchActivity, Activity paramActivity, String ssid, String password)
        {
            this.b = paramActivity;
            this.g = false;
            this.ssidStr = ssid;
            this.passwordStr = password;
            this.ESPSender=new rev_Class_h_ESP_TOUCH_Main(ssid, password);
          //  this.d = new activity_C_class_maybe_handle(this.ssidStr, this.passwordStr);
        }

    protected Boolean a(String... paramVarArgs)
    {
        //return Boolean.valueOf(this.d.b());
        return true;
    }

    protected void a(Boolean paramBoolean)
    {
      //  this.c.dismiss();
        int i;
      //  if (paramBoolean.booleanValue()) {
     //       i = 2131427563;
     //   }
        /*for (;;)
        {
          //  Toast.makeText(this.b, i, 1).show();
            return;
            if (this.g) {
                i = 2131427566;
            } else if (MainActivity.b(this.a).equals(this.e)) {
                i = 2131427564;
            } else {
                i = 2131427565;
            }
        }*/
    }

    public void onCancelled(Boolean result)
    {
      /*  if (this.d != null)
        {
            this.g = true;
            this.d.a();
        }*/
        Log.d(TAG, "Cancelled");
        ESPSender.a();
    }


        @Override
        protected Boolean doInBackground(Void... voids) {
            Log.d(TAG, "Background");
            return ESPSender.b();
        }

        protected void onPreExecute()
    {
        /*this.c = new ProgressDialog(this.b);
        this.c.setMessage(this.a.getString(2131427562, new Object[] { this.e }));
        this.c.setCanceledOnTouchOutside(false);
        this.c.setOnCancelListener(this);
        this.c.show();*/
        Log.d(TAG, "PRE EXE");
    }

        @Override
        protected void onPostExecute(Boolean result) {
            Log.d(TAG, "POST");
        }


    }
