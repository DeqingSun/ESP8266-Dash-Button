package org.thinkcreate.esp8266_button;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by sundeqing on 4/16/15.
 */
public class activity_B_class  extends AsyncTask
    {
        private Activity b;
        private ProgressDialog c;
        private activity_C_class_maybe_handle d;
        private final String ssidStr;
        private final String passwordStr;
        private boolean g;

        public activity_B_class(MainActivity paramEspTouchActivity, Activity paramActivity, String ssid, String password)
        {
            this.b = paramActivity;
            this.g = false;
            this.ssidStr = ssid;
            this.passwordStr = password;
            this.d = new activity_C_class_maybe_handle(this.ssidStr, this.passwordStr);
        }

    protected Boolean a(String... paramVarArgs)
    {
        //return Boolean.valueOf(this.d.b());
        return true;
    }

    protected void a(Boolean paramBoolean)
    {
        this.c.dismiss();
        int i;
        if (paramBoolean.booleanValue()) {
            i = 2131427563;
        }
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

    public void onCancel(DialogInterface paramDialogInterface)
    {
        if (this.d != null)
        {
            this.g = true;
            this.d.a();
        }
    }

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }

        protected void onPreExecute()
    {
        /*this.c = new ProgressDialog(this.b);
        this.c.setMessage(this.a.getString(2131427562, new Object[] { this.e }));
        this.c.setCanceledOnTouchOutside(false);
        this.c.setOnCancelListener(this);
        this.c.show();*/
    }


}
