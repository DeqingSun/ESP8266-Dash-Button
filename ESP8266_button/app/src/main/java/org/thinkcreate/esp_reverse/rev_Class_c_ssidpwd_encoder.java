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
        char c1 = (char)(ssid.length() + password.length());
        char c2 = (char)password.length();
        byte[] localObject = new rev_Class_d().a();
        this.a = new byte[localObject.length][];
        int i = 0;
        if (i >= this.a.length) //todo
        {
  /*          localObject = new rev_Class_e(c1, ssid).b();
            this.b = new byte[localObject.length][];
            i = 0;
            label87:
            if (i < this.b.length) {
                break label195;
            }
            localObject = new f(c2).b();
            this.c = new byte[localObject.length][];
            i = 0;
            label124:
            if (i < this.c.length) {
                break label219;
            }
            ssid = new b(ssid, password).b();
            this.d = new byte[ssid.length][];
            i = j;*/
        }
        for (;;)
        {
     /*       if (i >= this.d.length)
            {
                return;
                this.a[i] = rev_Class_l_maybe_util.c(localObject[i]);
                i += 1;
                break;
                label195:
                this.b[i] = rev_Class_l_maybe_util.c(localObject[i]);
                i += 1;
                break label87;
                label219:
                this.c[i] = rev_Class_l_maybe_util.c(localObject[i]);
                i += 1;
                break label124;
            }
            this.d[i] = rev_Class_l_maybe_util.c(ssid[i]);
            i += 1;*/
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
