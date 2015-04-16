package org.thinkcreate.esp_reverse;

/**
 * Created by sundeqing on 4/16/15.
 */
public class rev_Class_d {
    public byte[] a()
    {
        return new byte[] { 1, 2, 3, 4 };
    }

    public String toString()
    {
        StringBuilder localStringBuilder = new StringBuilder();
        byte[] arrayOfByte = a();
        int i = 0;
        for (;;)
        {
            if (i >= 4) {
                return localStringBuilder.toString();
            }
            String str = rev_Class_l_maybe_util.b(arrayOfByte[i]);
            localStringBuilder.append("0x");
            if (str.length() == 1) {
                localStringBuilder.append("0");
            }
            localStringBuilder.append(str).append(" ");
            i += 1;
        }
    }
}
