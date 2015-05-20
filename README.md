# ESP8266 Dash Button

![full](https://raw.githubusercontent.com/DeqingSun/ESP8266-Dash-Button/master/circuit/push_opti.gif)

This is a small box with only one button. Every time you push the button. It will connect to WiFi and access an URL.

WiFi SSID and Password can be configured through ESP-TOUCH protocol and the URL can be changed by UDP packet. Two LEDs on it's case shows status of module and circuit.

## Internal Structure

![](https://raw.githubusercontent.com/DeqingSun/ESP8266-Dash-Button/master/circuit/render_clipped.jpg)

This button consists of following parts: Two AA batteries and battery holder, ESP8266 module and circuit, a big button for better experience, case, button cover. 

## How it works

When button is pressed, the circuit powers ESP8266 module and the module will connect to WiFi with SSID and password in flash memory. Once Wifi connection is established, this button will request a certain URL in flash memory and turn itself off to save power.

## How to config WiFi network and URL

There is an minimal Android app in this repo that can do configuration wirelessly.

![](https://raw.githubusercontent.com/DeqingSun/ESP8266-Dash-Button/master/circuit/Screenshot_app.png)

The app is a barely working one and it lacks advanced features for now. However it does work. You can hold button on ESP8266 board for 5 seconds and it will enter ESP-TOUCH mode. The Android code is a bit messy because they are decompiled from a obfuscated APK file. 

You can connect your phone to target WiFi network, fill textbox with SSID and password and press "ESPTouch", then the app will encode SSID and password in length of UDP packets and broadcast packets to all devices. If ESP8266 decodes WiFi information successfully, it will broadcast a UDP packet back to your phone to end pairing process and listen UDP packets for URL. You can enter URL into third textbox and press "SET URL" to finish setting process.

If you want to change the URL only, press button once when it is in ESP-Touch mode.

## How to make one

You can use the gerber files in this repo or you can order PCB with [OSH Park](https://oshpark.com/shared_projects/X8mGIjUE) directly.

![](https://raw.githubusercontent.com/DeqingSun/ESP8266-Dash-Button/master/circuit/Assembly.jpg)

After you solder all parts and ESP-12 module, you can either compile the source code on your own or use the pre-compiled bin files. 

## How to do more things with IFTTT

Currently IFTTT can not be triggered with a HTTP request. So I created a App Engine app to send a Email to IFTTT. The app can print all email it received in log file so you can create account with App Engine Email Address.

Here is an example of add Google Calendar entry with button.

![](https://raw.githubusercontent.com/DeqingSun/ESP8266-Dash-Button/master/circuit/press_button.png)
