package org.thinkcreate.esp8266_button;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by sundeqing on 4/22/15.
 */


public class Set_URL_AsyncTask extends AsyncTask<Void,Void,Boolean> {
    private Activity parentActivity;
    private final String URL;
    private boolean g;
    private static final String TAG = "Set_URL_AsyncTask";
    private TextView mTextView;
    public Set_URL_AsyncTask(MainActivity paramEspTouchActivity, Activity paramActivity, String _URL)
    {
        this.parentActivity = paramActivity;
        this.g = false;
        this.URL = _URL;
       // mTextView = (TextView) parentActivity.findViewById(R.id.textView1);
    }

    public void onCancelled(Boolean result)
    {
        Log.d(TAG, "Cancelled");
    }


    @Override
    protected Boolean doInBackground(Void... voids) {
        Log.d(TAG, "Background");
        return true;
    }

    protected void onPreExecute()
    {
        Log.d(TAG, "PRE EXE");
    }

    @Override
    protected void onPostExecute(Boolean result) {
        Log.d(TAG, "POST");
    }




}