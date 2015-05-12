package org.thinkcreate.esp_reverse;

/**
 * Created by sundeqing on 4/16/15.
 */

public class rev_Class_a
{
    private final byte a;
    private final byte b;
    private final byte c;
    private final byte d;
    private final byte e;

    public rev_Class_a(char paramChar, int paramInt)
    {
        if (paramInt > 63) {
            throw new RuntimeException("index > INDEX_MAX");
        }
        byte[] localByteArray = rev_Class_l_maybe_util.splitUint8To2bytes(paramChar);//v0
        this.b = localByteArray[0];
        this.c = localByteArray[1];
        rev_Class_m localObjectClassM = new rev_Class_m();   //v0
        localObjectClassM.update(rev_Class_l_maybe_util.convertUint8toByte(paramChar));
        localObjectClassM.update(paramInt);
        byte[] localByteArray2 = rev_Class_l_maybe_util.splitUint8To2bytes((char) (int) localObjectClassM.getValue());//v0
        this.d = localByteArray2[0];
        this.e = localByteArray2[1];
        if ((this.c == 0) && (this.e == 0)) {
            this.a = ((byte)(paramInt | 0x1940));
        }else {
            this.a = ((byte) (paramInt | 0x1900));
        }
    }

    public byte[] a()
    {
        return new byte[] { 0, rev_Class_l_maybe_util.combine2bytesToOne(this.d, this.b), 1, this.a, 0, rev_Class_l_maybe_util.combine2bytesToOne(this.e, this.c) };
    }

    public String toString()
    {
        StringBuilder localStringBuilder = new StringBuilder(); //v1
        byte[] arrayOfByte = a();   //v2
        for (int i=0;i<6;i++){
            String str = rev_Class_l_maybe_util.convertByte2HexString(arrayOfByte[i]);
            localStringBuilder.append("0x");
            if (str.length() == 1) {
                localStringBuilder.append("0");
            }
            localStringBuilder.append(str).append(" ");
        }
        return localStringBuilder.toString();
    }
}
