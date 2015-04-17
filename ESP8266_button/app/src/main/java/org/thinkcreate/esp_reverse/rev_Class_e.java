package org.thinkcreate.esp_reverse;

/**
 * Created by sundeqing on 4/16/15.
 */
public class rev_Class_e {
    /*private final byte a;
    private final byte b;
    private final byte c;
    private final byte d;*/

    private byte a;
    private byte b;
    private byte c;
    private byte d;

    public rev_Class_e(char paramChar, String paramString)
    {
        char c1 = paramChar;  //todo
        if (paramChar < 16) {
            c1 = (char)(paramChar + 128);
        }
        byte[] localByteArray = rev_Class_l_maybe_util.b(c1);
        this.a = localByteArray[0];
        this.b = localByteArray[1];
        rev_Class_m localObject = new rev_Class_m();
        localObject.a(paramString.getBytes());
        byte[] localByteArray2 = rev_Class_l_maybe_util.b((char)((int)localObject.getValue()));
        this.c = localByteArray2[0];
        this.d = localByteArray2[1];
    }

    public byte[] a()
    {
        return new byte[] { 0, rev_Class_l_maybe_util.a((byte)0, this.a), 0, rev_Class_l_maybe_util.a((byte)1, this.b), 0, rev_Class_l_maybe_util.a((byte)2, this.c), 0, rev_Class_l_maybe_util.a((byte)3, this.d) };
    }

    public char[] b()
    {
        byte[] arrayOfByte = a();   //v1
        int halfArrayLen = arrayOfByte.length / 2; //v2
        char[] arrayOfChar = new char[halfArrayLen];    //v3
        for (int i=0;i < halfArrayLen;i += 1)   //i:v0
        {
            arrayOfChar[i] = rev_Class_l_maybe_util.b(arrayOfByte[(i * 2)], arrayOfByte[(i * 2 + 1)]);
        }
        return arrayOfChar;
    }

    public String toString()
    {
        StringBuilder localStringBuilder = new StringBuilder(); //v1
        byte[] arrayOfByte = a();   //v2

        for (int i = 0;i<8;i++)//v0
        {
            String str = rev_Class_l_maybe_util.b(arrayOfByte[i]);  //v3
            localStringBuilder.append("0x");
            if (str.length() == 1) {
                localStringBuilder.append("0");
            }
            localStringBuilder.append(str).append(" ");
        }
        return localStringBuilder.toString();
    }
}
