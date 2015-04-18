package org.thinkcreate.esp_reverse;

/**
 * Created by sundeqing on 4/16/15.
 */
import java.util.Random;

public class rev_Class_l_maybe_util //OK
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
    } //convert byte to char(unicode?)

    public static char b(byte paramByte1, byte paramByte2)
    {
        return (char)(a(paramByte1) << 8 | a(paramByte2));
    } //convert 2bytes to char(unicode?)

    public static String b(byte paramByte)
    {
        return Integer.toHexString(a(paramByte));
    }//toString?

    public static byte[] b(char paramChar)  //split one char's HL 4 bits into array of bytes
    {
        if ((paramChar < 0) || (paramChar > 255)) {
            throw new RuntimeException("Out of Boundary");
        }
        String str = Integer.toHexString(paramChar);    //str:v2
        byte i;
        byte j;
        if (str.length() > 1)
        {
            i = (byte)Integer.parseInt(str.substring(0, 1), 16);//v0
            j = (byte)Integer.parseInt(str.substring(1, 2), 16);//v2
        }else{
            //
            j = (byte)Integer.parseInt(str.substring(0, 1), 16);
            i = 0;
        }
        return new byte[] { i, j };
    }

    public static byte[] c(byte paramByte)
    {
        return c(a(paramByte));
    }

    public static byte[] c(char length) //generate a random array
    {
        byte[] arrayOfByte = new byte[length];

        for (char i = 0;i<length;i++){
            arrayOfByte[i] = a();
        }
        return arrayOfByte;
    }
}
