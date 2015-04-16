package org.thinkcreate.esp_reverse;

/**
 * Created by sundeqing on 4/16/15.
 */
public class rev_Class_e {
    /*private final byte a;
    private final byte b;
    private final byte c;
    private final byte d;*/

    private final byte a=0;//TEST
    private final byte b=0;
    private final byte c=0;
    private final byte d=0;

    public rev_Class_e(char paramChar, String paramString)
    {
/*        char c1 = paramChar;  //todo
        if (paramChar < '\020') {
            c1 = (char)(paramChar + 128);
        }
        byte[] localObject = rev_Class_l_maybe_util.b(c1);
        this.a = localObject[0];
        this.b = localObject[1];
        localObject = new rev_Class_m();
        ((rev_Class_m)localObject).a(paramString.getBytes());
        paramString = rev_Class_l_maybe_util.b((char)(int)((rev_Class_m)localObject).getValue());
        this.c = paramString[0];
        this.d = paramString[1];*/
    }

    public byte[] a()
    {
        return new byte[] { 0, rev_Class_l_maybe_util.a((byte)0, this.a), 0, rev_Class_l_maybe_util.a((byte)1, this.b), 0, rev_Class_l_maybe_util.a((byte)2, this.c), 0, rev_Class_l_maybe_util.a((byte)3, this.d) };
    }

    public char[] b()
    {
        byte[] arrayOfByte = a();
        int j = arrayOfByte.length / 2;
        char[] arrayOfChar = new char[j];
        int i = 0;
        for (;;)
        {
            if (i >= j) {
                return arrayOfChar;
            }
            arrayOfChar[i] = rev_Class_l_maybe_util.b(arrayOfByte[(i * 2)], arrayOfByte[(i * 2 + 1)]);
            i += 1;
        }
    }

    public String toString()
    {
        StringBuilder localStringBuilder = new StringBuilder();
        byte[] arrayOfByte = a();
        int i = 0;
        for (;;)
        {
            if (i >= 8) {
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
