package org.thinkcreate.org.thinkcreate.esp_sendUDPsettings;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by sundeqing on 4/22/15.
 */
public class Setting_UDP_sender {
    private DatagramSocket mDatagramSocket;
    private volatile boolean exceptionHappened;

    public Setting_UDP_sender()
    {
        try
        {
            this.mDatagramSocket = new DatagramSocket();
            this.exceptionHappened = false;
            return;
        }
        catch (SocketException localSocketException)
        {
            localSocketException.printStackTrace();
        }
    }

    public void function_a()
    {
        this.exceptionHappened = false;
    }

    public boolean sendArray(byte[] paramArrayOfByte, String paramString, int portNum, long paramLong)
    {
        if ((paramArrayOfByte != null) && (paramArrayOfByte.length > 0)) {
            if ((!this.exceptionHappened)) {
                try
                {
                    DatagramPacket localDatagramPacket = new DatagramPacket(paramArrayOfByte, paramArrayOfByte.length, InetAddress.getByName(paramString), portNum);
                    this.mDatagramSocket.send(localDatagramPacket);
                    try
                    {
                        Thread.sleep(paramLong);
                    }catch (InterruptedException paramArrayOfByte_exp){
                        paramArrayOfByte_exp.printStackTrace();
                        this.exceptionHappened = true;
                    }
                }
                catch (UnknownHostException paramArrayOfByte_exp1)
                {
                    paramArrayOfByte_exp1.printStackTrace();
                    this.exceptionHappened = true;
                }
                catch (IOException paramArrayOfByte_exp2)
                {
                    paramArrayOfByte_exp2.printStackTrace();
                    this.exceptionHappened = true;
                }
            }
            if (this.exceptionHappened) {
                closeSocket();
            }
        }
        return this.exceptionHappened;
    }

    public void closeSocket()
    {
        this.mDatagramSocket.close();
    }

    protected void finalize() throws Throwable {
        closeSocket();
        super.finalize();
    }
}
