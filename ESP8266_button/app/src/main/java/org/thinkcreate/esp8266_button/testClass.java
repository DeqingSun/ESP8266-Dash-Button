package org.thinkcreate.esp8266_button;

import org.thinkcreate.esp_reverse.rev_Class_h;
import org.thinkcreate.esp_reverse.rev_Class_m;

/**
 * Created by sundeqing on 4/16/15.
 */
public class testClass {

    byte[] a=new byte[4];

    public testClass(){
        //rev_Class_m temp = new rev_Class_m();
        //temp.update(a,1,1);
        new rev_Class_h("SSID123", "Password456").b();

    }
}
