package org.thinkcreate.org.thinkcreate.esp_sendUDPsettings;

import android.os.Looper;

import java.util.Arrays;

/**
 * Created by sundeqing on 4/22/15.
 */
public class Setting_UDP_handler {  //fixed
    private volatile boolean validRespReceived = false;
    private volatile boolean senderInterrupted = false;
    private final Setting_UDP_sender c_socketSender;
    private final Setting_UDP_server d_socketReceiver;
    private final String URL;

    public Setting_UDP_handler(String _URL)
    {
        this.URL = _URL;
        this.c_socketSender = new Setting_UDP_sender();
        this.d_socketReceiver = new Setting_UDP_server(10000, 10000);   //port,timeout,  k is a UDP server to receive data
    }

    private boolean sendByteArray(byte[] paramg)
    {
        long l = System.currentTimeMillis();    //v7 low v8 high

        boolean sendInterruptedSleep=false;

        for(int i=0; (!this.senderInterrupted) && (i < Long.MAX_VALUE) ;i++){    //i?
            sendInterruptedSleep=this.c_socketSender.sendArray(paramg, "255.255.255.255", 7001, 100L);
            if (sendInterruptedSleep) return this.validRespReceived;
            if (System.currentTimeMillis() - l > 1000L){
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

    private void runServer(){
        Thread thread = new Thread(){   //class i
            public void run()
            {
                long l = System.currentTimeMillis();    //l:v0
                int i = (byte)(URL.length());    //i:v2 paramh

                while(!senderInterrupted) {
                    int j = d_socketReceiver.a();  //j:v3  get sendByteArray data
                    if (j == i) {
                        int timeLeft = (int) (10000L - (System.currentTimeMillis() - l));
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
        byte[] URLBytes = URL.getBytes();
        byte[] URLBytesWithCheckSum=new byte[URLBytes.length+3];
        int checksum=0;
        for (int i=0;i<URLBytes.length;i++){
            URLBytesWithCheckSum[i]=URLBytes[i];
            checksum+=(URLBytes[i]&0xFF);
            checksum&=0xFFFF;
        }
        checksum=0xFFFF-checksum;
        URLBytesWithCheckSum[URLBytes.length+1]=(byte)(checksum&0xFF);
        URLBytesWithCheckSum[URLBytes.length+2]=(byte)(checksum>>8);
        System.out.println(Arrays.toString(URLBytesWithCheckSum));
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("Don't call the esptouch Task at Main(UI) thread directly.");
        }
        runServer();
        for (int i=0;i<10;i++)    //i:v7
        {
            if (sendByteArray(URLBytesWithCheckSum)) return true;
        }
        return false;
    }
}