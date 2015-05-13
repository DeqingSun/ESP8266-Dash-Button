package org.thinkcreate.esp8266_button;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.thinkcreate.esp_reverse.rev_Class_h_ESP_TOUCH_Main;

/**
 * Created by sundeqing on 4/16/15.
 */
public class ESP_Touch_AsyncTask extends AsyncTask<Void,Void,Boolean>
    {
        private Activity parentActivity;
        private ProgressDialog mProgressDialog;
        private final String ssidStr;
        private final String passwordStr;
        private boolean g;
        private static final String TAG = "ESP_Touch_AsyncTask";
        private rev_Class_h_ESP_TOUCH_Main ESPSender;
        private TextView mTextView;

        public ESP_Touch_AsyncTask(MainActivity paramEspTouchActivity, Activity paramActivity, String ssid, String password)
        {
            this.parentActivity = paramActivity;
            this.g = false;
            this.ssidStr = ssid;
            this.passwordStr = password;
            this.ESPSender=new rev_Class_h_ESP_TOUCH_Main(ssid, password);
          //  this.d = new activity_C_class_maybe_handle(this.ssidStr, this.passwordStr);
            mTextView = (TextView) parentActivity.findViewById(R.id.textView1);
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
        parentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText("Cancelled");
            }
        });
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

        mProgressDialog = new ProgressDialog(parentActivity);
        mProgressDialog
                .setMessage("Esptouch is configuring, please wait for a moment...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.i(TAG, "progress dialog is canceled");
                //if (mEsptouchTask != null) {
                //    mEsptouchTask.interrupt();
                //}
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


        Log.d(TAG, "PRE EXE");
        parentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText("SETTING");
            }
        });
    }

        @Override
        protected void onPostExecute(Boolean result) {
            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                    .setEnabled(true);
            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(
                    "Confirm");
            // it is unnecessary at the moment, add here just to show how to use isCancelled()
            /*if (!result.isCancelled()) {
                if (result.isSuc()) {
                    mProgressDialog.setMessage("Esptouch success, bssid = "
                            + result.getBssid());
                } else {
                    mProgressDialog.setMessage("Esptouch fail");
                }
            }*/


            Log.d(TAG, "POST");
            Log.d(TAG, result?"SET OK":"SET Failed");
            final boolean result2=result;
            parentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTextView.setText(result2?"SET OK":"SET Failed");
                }
            });
        }



    }
