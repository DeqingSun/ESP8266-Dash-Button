package org.thinkcreate.esp_reverse;

import android.os.Looper;

/**
 * Created by sundeqing on 4/16/15.
 */
public class rev_Class_h {
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
        long l = System.currentTimeMillis();
        int i = 0;
        if ((this.b) || (i >= Long.MAX_VALUE))
        {
            label22:
            i = 0;
            label24:
            if ((!this.b) && (i < 20L)) {
          //      break label121;
            }
            i = 0;
            label42:
            if ((!this.b) && (i < 20L)) {
         //       break label149;
            }
            i = 0;
        }
        for (;;)
        {
            if ((this.b) || (i >= Long.MAX_VALUE)) {}
            label121:
            label149:
            do
            {//todo
                return this.a;
         /*     //  this.c.a(paramg.a(), "255.255.255.255", 7001, 10L);
                if (System.currentTimeMillis() - l > 2000L) {
              //      break label22;
                }
                i += 1;
                break;
                this.c.a(paramg.b(), "255.255.255.255", 7001, 10L);
                i += 1;
              //  break label24;
                this.c.a(paramg.c(), "255.255.255.255", 7001, 10L);
                i += 1;
             //   break label42;
                this.c.a(paramg.d(), "255.255.255.255", 7001, 10L);*/
            } while (System.currentTimeMillis() - l > 6000L);
           // i += 1;
        }
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
                int i = (byte)(ssidStr.length() + passwordStr.length());    //i:v2 paramh //recheck after fix h TODO

                while(true) {
                    int j = d.a();  //j:v3  get a data //recheck after fix h TODO
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
        boolean bool1 = false;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("Don't call the esptouch Task at Main(UI) thread directly.");
        }
        rev_Class_c_ssidpwd_encoder localc = new rev_Class_c_ssidpwd_encoder(this.ssidStr, this.passwordStr);
        d();
        int i = 0;
        for (;;)
        {
            if (i >= 7) {}
            boolean bool2;
            do
            {
                return bool1;
                //bool2 = a(localc);//todo
                //bool1 = bool2;
            } while (bool2);
            //i += 1;
            //bool1 = bool2;
        }
    }
}
