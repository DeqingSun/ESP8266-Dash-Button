package org.thinkcreate.esp_reverse;

/**
 * Created by sundeqing on 4/16/15.
 */

public class rev_Class_c_ssidpwd_encoder
        implements rev_Class_g_encoder_interface
{
    private final byte[][] a;
    private final byte[][] b;
    private final byte[][] c;
    private final byte[][] d;

    public rev_Class_c_ssidpwd_encoder(String ssid, String password)
    {
        char c1 = (char)(ssid.length() + password.length());    //v2
        char c2 = (char)password.length();  //v3
        byte[] localObject = new rev_Class_d().a(); //get 1,2,3,4,  //v4
        this.a = new byte[localObject.length][];

        for (int i=0;i<this.a.length;i++){  //i:v0
            this.a[i] = rev_Class_l_maybe_util.c(localObject[i]);//v5=this.a
        }
        char[] arrayOfChar = new rev_Class_e(c1, ssid).b(); //v2
        this.b = new byte[arrayOfChar.length][];

        for (int i=0;i<this.b.length;i++){
            this.b[i] = rev_Class_l_maybe_util.c(arrayOfChar[i]);//v4=this.b
        }
        char[] arrayOfChar3=new rev_Class_f(c2).b();//v2
        this.c = new byte[arrayOfChar3.length][];

        for (int i=0;i<this.c.length;i++){
            this.c[i] = rev_Class_l_maybe_util.c(arrayOfChar3[i]);//v3=this.c
        }
        char[] arrayOfChar4 = new rev_Class_b(ssid, password).b();//v0
        this.d = new byte[arrayOfChar4.length][];

        for (int i=0;i<this.d.length;i++){  //i:v1
            this.d[i] = rev_Class_l_maybe_util.c(arrayOfChar4[i]);//v2=this.d
        }
    }

    public byte[][] a()
    {
        return this.a;
    }

    public byte[][] b()
    {
        return this.b;
    }

    public byte[][] c()
    {
        return this.c;
    }

    public byte[][] d()
    {
        return this.d;
    }
}
