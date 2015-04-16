package org.thinkcreate.esp_reverse;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by sundeqing on 4/16/15.
 */
public class rev_Class_j {
    private DatagramSocket a;
    private volatile boolean b;

    public rev_Class_j()
    {
        try
        {
            this.a = new DatagramSocket();
            this.b = false;
            return;
        }
        catch (SocketException localSocketException)
        {
            localSocketException.printStackTrace();
        }
    }

    public void a()
    {
        this.b = false;
    }

    public void a(byte[][] paramArrayOfByte, String paramString, int paramInt, long paramLong)
    {
        if ((paramArrayOfByte == null) || (paramArrayOfByte.length <= 0)) {}
        label133:
        for (int i=0;;)
        {
            return;
            //int i = 0;
            //if ((this.b) || (i >= paramArrayOfByte.length)) {}//todo
            /*for (;;)
            {
                if (!this.b) {
                    break label133;
                }
                b();
                return;
                if (paramArrayOfByte[i].length == 0)
                {
                    i += 1;
                    break;
                }
                try
                {
                    DatagramPacket localDatagramPacket = new DatagramPacket(paramArrayOfByte[i], paramArrayOfByte[i].length, InetAddress.getByName(paramString), paramInt);
                    this.a.send(localDatagramPacket);
                    try
                    {
                        Thread.sleep(paramLong);
                    }
                    catch (InterruptedException paramArrayOfByte_exp)
                    {
                        paramArrayOfByte_exp.printStackTrace();
                        this.b = true;
                    }
                }
                catch (UnknownHostException paramArrayOfByte_exp1)
                {
                    paramArrayOfByte_exp1.printStackTrace();
                    this.b = true;
                }
                catch (IOException paramArrayOfByte_exp2)
                {
                    paramArrayOfByte_exp2.printStackTrace();
                    this.b = true;
                }
            }*/
        }
    }

    public void b()
    {
        this.a.close();
    }

    protected void finalize() throws Throwable {
        b();
        super.finalize();
    }
}
