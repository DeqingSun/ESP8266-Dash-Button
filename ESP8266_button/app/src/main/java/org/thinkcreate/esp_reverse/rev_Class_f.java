package org.thinkcreate.esp_reverse;

/**
 * Created by sundeqing on 4/17/15.
 */
public class rev_Class_f
{
    private final byte a;
    private final byte b;
    private final byte c;
    private final byte d;

    public rev_Class_f(char paramChar)
    {
        byte[] localByteArray = rev_Class_l_maybe_util.splitUint8To2bytes(paramChar);    //v0
        this.a = localByteArray[0];
        this.b = localByteArray[1];
        rev_Class_m localObjectClassM = new rev_Class_m();  //v0
        localObjectClassM.update(paramChar);
        byte[] localByteArray2 = rev_Class_l_maybe_util.splitUint8To2bytes((char) ((int) localObjectClassM.getValue()));  //v0
        this.c = localByteArray2[0];
        this.d = localByteArray2[1];
    }

    public byte[] a()
    {
        return new byte[] { 0, rev_Class_l_maybe_util.combine2bytesToOne((byte) 4, this.a), 0, rev_Class_l_maybe_util.combine2bytesToOne((byte) 5, this.b), 0, rev_Class_l_maybe_util.combine2bytesToOne((byte) 6, this.c), 0, rev_Class_l_maybe_util.combine2bytesToOne((byte) 7, this.d) };
    }

    public char[] b()
    {
        byte[] arrayOfByte = a();   //v1
        int j = arrayOfByte.length / 2; //v2
        char[] arrayOfChar = new char[j];   //v3
        for (int i = 0;i<j;i++) //v0
        {
            arrayOfChar[i] = rev_Class_l_maybe_util.combine2bytesToU8(arrayOfByte[(i * 2)], arrayOfByte[(i * 2 + 1)]);
        }
        return arrayOfChar;
    }

    public String toString()
    {
        StringBuilder localStringBuilder = new StringBuilder();
        byte[] arrayOfByte = a();

        for (int i = 0;i<8;i++)
        {
            String str = rev_Class_l_maybe_util.convertByte2HexString(arrayOfByte[i]);
            localStringBuilder.append("0x");
            if (str.length() == 1) {
                localStringBuilder.append("0");
            }
        }
        return localStringBuilder.toString();
    }
}

