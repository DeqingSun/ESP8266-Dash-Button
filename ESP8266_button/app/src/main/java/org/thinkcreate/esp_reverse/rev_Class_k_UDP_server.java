package org.thinkcreate.esp_reverse;

import android.util.Log;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by sundeqing on 4/16/15.
 */
public class rev_Class_k_UDP_server {
    private DatagramSocket b;
    private final byte[] c = new byte[1];
    private DatagramPacket a = new DatagramPacket(this.c, 1);

    public rev_Class_k_UDP_server(int portNumber, int timeout)
    {
        try
        {
            this.b = new DatagramSocket(portNumber);
            this.b.setSoTimeout(timeout);
            Log.d("UDPSocketServer", "mServerSocket is created, socket read timeout: " + timeout + ", port: " + portNumber);
            return;
        }
        catch (IOException localIOException)
        {
            Log.e("UDPSocketServer", "IOException");
            localIOException.printStackTrace();
        }
    }

    public byte a()
    {
        Log.d("UDPSocketServer", "receiveOneByte() entrance");
        try
        {
            this.b.receive(this.a);
            Log.d("UDPSocketServer", "receive: " + (this.a.getData()[0] + 0));
            byte b1 = this.a.getData()[0];
            return b1;
        }
        catch (IOException localIOException)
        {
            localIOException.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return Byte.MIN_VALUE;
    }

    public boolean a(int paramInt)
    {
        try
        {
            this.b.setSoTimeout(paramInt);
            return true;
        }
        catch (SocketException localSocketException)
        {
            localSocketException.printStackTrace();
        }
        return false;
    }

    public void interruptSocketServer()
    {
        Log.i("UDPSocketServer", "USPSocketServer is interrupt");
        closeSocket();
    }

    public void closeSocket()
    {
        try {
            this.b.close();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    protected void finalize() throws Throwable {
        closeSocket();
        super.finalize();
    }
}
