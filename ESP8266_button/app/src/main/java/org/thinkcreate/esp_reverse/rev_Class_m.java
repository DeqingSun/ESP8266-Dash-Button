package org.thinkcreate.esp_reverse;

/**
 * Created by sundeqing on 4/16/15.
 */
import java.util.zip.Checksum;

public class rev_Class_m    //OK
        implements Checksum
{
    private static final short[] crcTable = new short[256];
    private final short init;
    private short value;
    private static final short CRC_POLYNOM = 0x8c;
    private static final short CRC_INITIAL = 0x00;

    static
    {
        //fixed generate LUT
        for (int dividend=0;dividend<256;dividend++){    //smali: i:v3 256:v5
            int remainder=dividend;    //j:v0
            for (int k=0;k<8;k++) {
                if ((remainder & 0x1) != 0) {
                    remainder = (remainder >> 1) ^ CRC_POLYNOM;
                }else{
                    remainder = remainder>>1;
                }
            }
            crcTable[dividend] = ((short)remainder);
        }
    }

    public rev_Class_m() {
        this.value = this.init = CRC_INITIAL;
    }

    public void update(byte[] buffer)
    {
        update(buffer, 0, buffer.length);
    }

    public long getValue()
    {
        return this.value & 0xFF;
    }

    public void reset()
    {
        this.value = this.init;
    }

    public void update(int paramInt)
    {
        update(new byte[] { (byte)paramInt }, 0, 1);
    }

    public void update(byte[] buffer, int offset, int len)
    {

        for (int i = 0;i<len;i++)
        {
            int j = buffer[(offset + i)];
            int k = this.value;
            this.value = ((short)(crcTable[((j ^ k) & 0xFF)] ^ this.value << 8));
        }
    }
}
