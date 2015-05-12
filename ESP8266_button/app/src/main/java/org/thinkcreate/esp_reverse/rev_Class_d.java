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
        StringBuilder localStringBuilder = new StringBuilder(); //v1
        byte[] arrayOfByte = a();   //v2
        for (int i = 0;i<4;i++)    //v0
        {
            String str = rev_Class_l_maybe_util.convertByte2HexString(arrayOfByte[i]);  //v3
            localStringBuilder.append("0x");
            if (str.length() == 1) {
                localStringBuilder.append("0");
            }
            localStringBuilder.append(str).append(" ");
        }
        return localStringBuilder.toString();
    }
}
