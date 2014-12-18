import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.serial.*; 
import processing.net.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SerialCommEncoder extends PApplet {




Server myServer;

Serial myPort;

public void setup() {
    size(300,300);
    
    background(0);

    myServer = new Server(this, 5331);

    //println(myServer.ip());

    println(Serial.list());

    String portName = Serial.list()[0];

    myPort = new Serial(this, portName, 9600);
}

String val;
byte eol = 0;
int backgroundColour;

public void draw() {
    
    background(backgroundColour);
    
    backgroundColour++;
    
    backgroundColour = backgroundColour%255;
}

public void serialEvent(Serial p) 
{  
    val = myPort.readStringUntil('\n');         // read it and store it in val

        if (val != null) {
        //println(val); //print it out in the console
        myServer.write(val);
    }
}

public void endTransmission() {
    myServer.write(eol);
}

public void keyPressed() {
    if (key == ESC) {
        background(255, 0, 0);
        endTransmission();
        myServer.stop();
        myServer = null;
    }
}
    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[] { "SerialCommEncoder" };
        if (passedArgs != null) {
          PApplet.main(concat(appletArgs, passedArgs));
        } else {
          PApplet.main(appletArgs);
        }
    }
}
