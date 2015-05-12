package org.thinkcreate.esp_reverse;

/**
 * Created by sundeqing on 4/17/15.
 */
public class rev_Class_b
{
    private final rev_Class_a[] a;

    public rev_Class_b(String paramString1, String paramString2)
    {
        String passwordSsid = paramString2 + paramString1;  //v2
        int k = passwordSsid.length();  //v3
        this.a = new rev_Class_a[k];
        char[] localCharArray = new char[k];    //v4

        for (int i=0;i < localCharArray.length;i++) //i:v0
        {
            localCharArray[i] = passwordSsid.charAt(i);
        }

        for (int i=0;i < k;i++) //i:v1
        {
            this.a[i] = new rev_Class_a(localCharArray[i], i);    //this.a:v0
        }
    }

    public byte[] a()
    {
        byte[] arrayOfByte = new byte[this.a.length * 6]; //v2
        for (int i = 0;i < this.a.length;i++) //i:v0
        {
            System.arraycopy(this.a[i].a(), 0, arrayOfByte, i * 6, 6);
        }
        return arrayOfByte;
    }

    public char[] b()
    {
        byte[] arrayOfByte = a();   //v1
        int j = arrayOfByte.length / 2; //v2
        char[] arrayOfChar = new char[j];   //v3

        for (int i = 0;i<j;i++)
        {
            arrayOfChar[i] = rev_Class_l_maybe_util.combine2bytesToU8(arrayOfByte[(i * 2)], arrayOfByte[(i * 2 + 1)]);
        }
        return arrayOfChar;
    }

    public String toString()
    {
        StringBuilder localStringBuilder = new StringBuilder();
        byte[] arrayOfByte = a();
        for (int i = 0;i < arrayOfByte.length;i++)
        {
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
