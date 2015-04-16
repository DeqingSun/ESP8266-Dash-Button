package org.thinkcreate.esp_reverse;

/**
 * Created by sundeqing on 4/16/15.
 */
import java.util.Random;

public class rev_Class_l_maybe_util
{
    private static byte a()
    {
        return (byte)(127 - new Random().nextInt(256));
    }   //get random number

    public static byte a(byte highByte, byte LowByte)  //merge 2 byte as HHHHLLLL
    {
        if ((highByte < 0) || (highByte > 15) || (LowByte < 0) || (LowByte > 15)) {
            throw new RuntimeException("Out of Boundary");
        }
        return (byte)(highByte << 4 | LowByte);
    }

    public static byte a(char paramChar)    //convert char to byte
    {
        if (paramChar > 255) {
            throw new RuntimeException("Out of Boundary");
        }
        return (byte)paramChar;
    }

    public static char a(byte paramByte)
    {
        return (char)(paramByte & 0xFF);
    }

    public static char b(byte paramByte1, byte paramByte2)
    {
        return (char)(a(paramByte1) << '\b' | a(paramByte2));
    }

    public static String b(byte paramByte)
    {
        return Integer.toHexString(a(paramByte));
    }

    public static byte[] b(char paramChar)
    {
        if ((paramChar < 0) || (paramChar > 255)) {
            throw new RuntimeException("Out of Boundary");
        }
        String str = Integer.toHexString(paramChar);
        int i;
        int j;
        if (str.length() > 1)
        {
            i = (byte)Integer.parseInt(str.substring(0, 1), 16);
            j = (byte)Integer.parseInt(str.substring(1, 2), 16);
        }
        for (;;)
        {
            //return new byte[] { i, j };//todo
            j = (byte)Integer.parseInt(str.substring(0, 1), 16);
            i = 0;
        }
    }

    public static byte[] c(byte paramByte)
    {
        return c(a(paramByte));
    }

    public static byte[] c(char paramChar)
    {
        byte[] arrayOfByte = new byte[paramChar];
        char c = '\000';
        for (;;)
        {
            if (c >= paramChar) {
                return arrayOfByte;
            }
            arrayOfByte[c] = a();
            c += '\001';
        }
    }
}
