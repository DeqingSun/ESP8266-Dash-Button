package org.thinkcreate.esp_reverse;

/**
 * Created by sundeqing on 4/16/15.
 */
import java.util.zip.Checksum;

public class rev_Class_m
        implements Checksum
{
    private static final short[] b = new short[256];
    private final short a = 0;
    private short c = 0;

    static
    {
        //todo
        /*for (int i=0;i<256;i++){


        }

        int i = 0;
        int k;
        int j;
        for (;;)
        {
            if (i >= 256) {
                return;
            }
            k = 0;
            j = i;
            if (k < 8) {
                break;
            }
            b[i] = ((short)j);
            i += 1;
        }
        if ((j & 0x1) != 0) {
            j = j >>> 1 ^ 0x8C;
        }
        for (;;)
        {
            k += 1;
            break;
            j >>>= 1;
        }*/
    }

    public void a(byte[] paramArrayOfByte)
    {
        update(paramArrayOfByte, 0, paramArrayOfByte.length);
    }

    public long getValue()
    {
        return this.c & 0xFF;
    }

    public void reset()
    {
        this.c = this.a;
    }

    public void update(int paramInt)
    {
        update(new byte[] { (byte)paramInt }, 0, 1);
    }

    public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
        int i = 0;
        for (;;)
        {
            if (i >= paramInt2) {
                return;
            }
            int j = paramArrayOfByte[(paramInt1 + i)];
            int k = this.c;
            this.c = ((short)(b[((j ^ k) & 0xFF)] ^ this.c << 8));
            i += 1;
        }
    }
}
