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
        byte[] localObject0 = rev_Class_l_maybe_util.b(paramChar);
        this.b = localObject0[0];
        this.c = localObject0[1];
        rev_Class_m localObject1 = new rev_Class_m();
        ((rev_Class_m)localObject1).update(rev_Class_l_maybe_util.a(paramChar));
        ((rev_Class_m)localObject1).update(paramInt);
        byte[] localObject2 = rev_Class_l_maybe_util.b((char)(int)((rev_Class_m)localObject1).getValue());
        this.d = localObject2[0];
        this.e = localObject2[1];
        if ((this.c == 0) && (this.e == 0))
        {
            this.a = ((byte)(paramInt | 0x1940));
            return;
        }
        this.a = ((byte)(paramInt | 0x1900));
    }

    public byte[] a()
    {
        return new byte[] { 0, rev_Class_l_maybe_util.a(this.d, this.b), 1, this.a, 0, rev_Class_l_maybe_util.a(this.e, this.c) };
    }

    public String toString()
    {
        StringBuilder localStringBuilder = new StringBuilder();
        byte[] arrayOfByte = a();
        int i = 0;
        for (;;)
        {
            if (i >= 6) {
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
