package org.thinkcreate.esp_reverse;

/**
 * Created by sundeqing on 4/16/15.
 */

class rev_Class_i
        extends Thread
{
    private rev_Class_h a;

    rev_Class_i(rev_Class_h paramh) {
        a=paramh;
    }

    public void run()
    {
        long l = System.currentTimeMillis();    //l:v0
        int i = (byte)(rev_Class_h.a(this.a).length() + rev_Class_h.b(this.a).length());    //i:v2 paramh //recheck after fix h TODO

        while(true) {

            int j = rev_Class_h.c(this.a).a();  //j:v3 //recheck after fix h TODO
            if (j == i) {
                i = (int) (46000L - (System.currentTimeMillis() - l));
                if (i >= 0) {
                    rev_Class_h.c(this.a).a(i);
                    rev_Class_h.a(this.a, true);
                    break;
                }else{
                    break;
                }
            } else {
                if (j != -128) {
                    continue;
                }else{
                    break;

                }

            }


        }


        this.a.a();
        rev_Class_h.c(this.a).c();


    }
}
