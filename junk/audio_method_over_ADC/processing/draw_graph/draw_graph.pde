
import processing.serial.*;

Serial myPort;                       // The serial port
boolean gotData=false;

int[] sampleValue=new int[1024];

void setup() {
  size(1024, 300);  // Stage size
  frameRate(10);
  noStroke();      // No border on the next thing drawn


  String[] serialList=Serial.list();
  String portName=serialList[0];
  for (int i=0; i<serialList.length; i++) {
    if (serialList[i].indexOf("tty.usb")>=0) {
      portName=serialList[i];
    }
  }
  println(serialList);
  println(portName);
  myPort = new Serial(this, portName, 230400);
  myPort.bufferUntil('\n');
}

void draw() {
  background(0);
  draw_dataset(sampleValue, 0, 1024, 1, -height/1024.0, #FF0000, 0, height-1);
  if (gotData) {
    noStroke();
    fill(0, 255, 0);
    gotData=false;
    rect(0, 0, 10, 10);
  }
}

void serialEvent(Serial p) {
  String inString = p.readString();
  if ( false && inString.indexOf("Samples:")==0) {
    String dataStr=inString.substring(8, 8+1024*5-1);
    for (int i=0; i<1024; i++) {
      String valueStr=inString.substring(8+i*5, 8+i*5+4);
      try {
        sampleValue[i]=unhex(valueStr);
      } 
      catch (Exception e) {
        println(i+" \""+valueStr+"\" ERROR");
      }
    }
    gotData=true;
  } else {
    print(inString);
  }
}


void draw_dataset(int[] data, int offset, int len, float scaleX, float scaleY, color _color, float _x, float _y) {
  //center line
  stroke(150);
  line(_x, _y, _x+len*scaleX, _y);
  //vertical tickers
  for (int i=1000* ( (offset+999)/1000); i<offset+len; i+=1000) {
    line(_x+(i-offset)*scaleX, _y-32000*scaleY, _x+(i-offset)*scaleX, _y+32000*scaleY);
    text((i/1000)+"K", _x+(i-offset)*scaleX+2, _y+32000*scaleY);
  }
  //data
  stroke(_color);
  noFill();
  beginShape();
  vertex(_x, _y+data[offset]*scaleY);
  for (int i=1; i<len; i++) {
    if ((i+offset)>=data.length) break;
    vertex(_x+i*scaleX, _y+data[offset+i]*scaleY);
  }
  endShape();
}

