package org.thinkcreate.org.thinkcreate.esp_sendUDPsettings;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by sundeqing on 4/22/15.
 */
public class Setting_UDP_server {
    private DatagramSocket mReceiveSocket;
    private final byte[] mPacketBuffer = new byte[1];
    private DatagramPacket mPacketStorage = new DatagramPacket(this.mPacketBuffer, 1);

    public Setting_UDP_server(int portNumber, int timeout)
    {
        try
        {
            this.mReceiveSocket = new DatagramSocket(portNumber);
            this.mReceiveSocket.setSoTimeout(timeout);
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
            this.mReceiveSocket.receive(this.mPacketStorage);
            Log.d("UDPSocketServer", "receive: " + (this.mPacketStorage.getData()[0] + 0));
            byte b1 = this.mPacketStorage.getData()[0];
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
            this.mReceiveSocket.setSoTimeout(paramInt);
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
            this.mReceiveSocket.close();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    protected void finalize() throws Throwable {
        closeSocket();
        super.finalize();
    }
}

