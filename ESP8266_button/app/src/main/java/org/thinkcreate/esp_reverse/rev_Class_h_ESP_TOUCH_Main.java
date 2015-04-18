package org.thinkcreate.esp_reverse;

import android.os.Looper;

/**
 * Created by sundeqing on 4/16/15.
 */
public class rev_Class_h_ESP_TOUCH_Main {  //fixed
    private volatile boolean validRespReceived = false;
    private volatile boolean senderInterrupted = false;
    private final rev_Class_j_socketDataSender c_socketSender;
    private final rev_Class_k_UDP_server d_socketReceiver;
    private final String ssidStr;
    private final String passwordStr;

    public rev_Class_h_ESP_TOUCH_Main(String ssid, String password)
    {
        this.ssidStr = ssid;
        this.passwordStr = password;
        this.c_socketSender = new rev_Class_j_socketDataSender();
        this.d_socketReceiver = new rev_Class_k_UDP_server(10000, 46000);   //port,timeout,  k is a UDP server to receive data
    }

    private boolean SendEncodedSSIDPWD(rev_Class_g_encoder_interface paramg)
    {
        long l = System.currentTimeMillis();    //v7 low v8 high

        for(int i=0; (!this.senderInterrupted) && (i < Long.MAX_VALUE) ;i++){    //i?
            this.c_socketSender.a(paramg.a(), "255.255.255.255", 7001, 10L);
            if (System.currentTimeMillis() - l > 2000L){
                break;
            }
        }

        for(int i=0; (!this.senderInterrupted) && (i < 20L) ;i++){    //i?
            this.c_socketSender.a(paramg.b(), "255.255.255.255", 7001, 10L);
        }

        for(int i=0; (!this.senderInterrupted) && (i < 20L) ;i++){    //i?
            this.c_socketSender.a(paramg.c(), "255.255.255.255", 7001, 10L);
        }

        for(int i=0; (!this.senderInterrupted) && (i < Long.MAX_VALUE) ;i++){
            this.c_socketSender.a(paramg.d(), "255.255.255.255", 7001, 10L);
            if (System.currentTimeMillis() - l > 6000L){
                break;
            }
        }
        return this.validRespReceived;
    }

    private void interruptSockets()
    {
        this.senderInterrupted = true;
        this.c_socketSender.function_a();
        this.d_socketReceiver.interruptSocketServer();
    }

    private void d(){
        Thread thread = new Thread(){   //class i
            public void run()
            {
                long l = System.currentTimeMillis();    //l:v0
                int i = (byte)(ssidStr.length() + passwordStr.length());    //i:v2 paramh

                while(true) {
                    int j = d_socketReceiver.a();  //j:v3  get SendEncodedSSIDPWD data
                    if (j == i) {
                        int timeLeft = (int) (46000L - (System.currentTimeMillis() - l));
                        if (timeLeft >= 0) {
                            d_socketReceiver.a(timeLeft); //set timeout
                            validRespReceived =true;
                        }
                        break;
                    } else if (j == -128) {
                        break;
                    }
                }
                a();
                d_socketReceiver.closeSocket();  //close socket
            }

        };
        thread.start();
    }

    public void a()
    {
        interruptSockets();
    }

    public boolean b()
    {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("Don't call the esptouch Task at Main(UI) thread directly.");
        }
        rev_Class_c_ssidpwd_encoder encodedData = new rev_Class_c_ssidpwd_encoder(this.ssidStr, this.passwordStr);
        d();
        for (int i=0;i<7;i++)    //i:v7
        {
            if (SendEncodedSSIDPWD(encodedData)) return true;
        }
        return false;
    }
}
