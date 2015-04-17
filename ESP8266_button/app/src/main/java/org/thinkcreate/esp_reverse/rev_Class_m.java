package org.thinkcreate.esp_reverse;

/**
 * Created by sundeqing on 4/16/15.
 */
import java.util.zip.Checksum;

public class rev_Class_m    //OK
        implements Checksum
{
    private static final short[] b = new short[256];
    private final short a = 0;
    private short c = 0;

    static
    {
        //fixed generate LUT
        for (int i=0;i<256;i++){    //smali: i:v3 256:v5
            int j=i;    //j:v0
            for (int k=0;k<8;k++) {
                if ((j & 0x1) != 0) {
                    j = (j >> 1) ^ 0x8C;
                }else{
                    j = j>>1;
                }
            }
            b[i] = ((short)j);
        }
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

        for (int i = 0;i<paramInt2;i++)
        {
            int j = paramArrayOfByte[(paramInt1 + i)];
            int k = this.c;
            this.c = ((short)(b[((j ^ k) & 0xFF)] ^ this.c << 8));
        }
    }
}
