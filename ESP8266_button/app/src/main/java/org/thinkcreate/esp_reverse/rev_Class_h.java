package org.thinkcreate.esp_reverse;

import android.os.Looper;

/**
 * Created by sundeqing on 4/16/15.
 */
public class rev_Class_h {  //fixed
    private volatile boolean a = false;
    private volatile boolean b = false;
    private final rev_Class_j c;
    private final rev_Class_k_UDP_server d;
    private final String ssidStr;
    private final String passwordStr;

    public rev_Class_h(String ssid, String password)
    {
        this.ssidStr = ssid;
        this.passwordStr = password;
        this.c = new rev_Class_j();
        this.d = new rev_Class_k_UDP_server(10000, 46000);   //port,timeout,  k is a UDP server to receive data
    }

    private boolean a(rev_Class_g_encoder_interface paramg)
    {
        long l = System.currentTimeMillis();    //v7 low v8 high

        for(int i=0; (!this.b) && (i < Long.MAX_VALUE) ;i++){    //i?
            this.c.a(paramg.a(), "255.255.255.255", 7001, 10L);
            if (System.currentTimeMillis() - l > 2000L){
                break;
            }
        }

        for(int i=0; (!this.b) && (i < 20L) ;i++){    //i?
            this.c.a(paramg.b(), "255.255.255.255", 7001, 10L);
        }

        for(int i=0; (!this.b) && (i < 20L) ;i++){    //i?
            this.c.a(paramg.c(), "255.255.255.255", 7001, 10L);
        }

        for(int i=0; (!this.b) && (i < Long.MAX_VALUE) ;i++){
            this.c.a(paramg.d(), "255.255.255.255", 7001, 10L);
            if (System.currentTimeMillis() - l > 6000L){
                break;
            }
        }
        return this.a;
    }

    private void c()
    {
        this.b = true;
        this.c.a();
        this.d.b();
    }

    private void d(){
        Thread thread = new Thread(){   //class i
            public void run()
            {
                long l = System.currentTimeMillis();    //l:v0
                int i = (byte)(ssidStr.length() + passwordStr.length());    //i:v2 paramh

                while(true) {
                    int j = d.a();  //j:v3  get a data
                    if (j == i) {
                        int timeLeft = (int) (46000L - (System.currentTimeMillis() - l));
                        if (timeLeft >= 0) {
                            d.a(timeLeft); //set timeout
                            a=true;
                        }
                        break;
                    } else if (j == -128) {
                        break;
                    }
                }
                a();
                d.c();  //close socket
            }

        };
        thread.start();
    }

    public void a()
    {
        c();
    }

    public boolean b()
    {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("Don't call the esptouch Task at Main(UI) thread directly.");
        }
        rev_Class_c_ssidpwd_encoder localc = new rev_Class_c_ssidpwd_encoder(this.ssidStr, this.passwordStr);
        d();
        for (int i=0;i<7;i++)    //i:v7
        {
            if (a(localc)) return true;
        }
        return false;
    }
}
