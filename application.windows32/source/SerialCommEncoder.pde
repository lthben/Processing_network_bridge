import processing.serial.*;
import processing.net.*;

Server myServer;

Serial myPort;

void setup() {
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

void draw() {
    
    background(backgroundColour);
    
    backgroundColour++;
    
    backgroundColour = backgroundColour%255;
}

void serialEvent(Serial p) 
{  
    val = myPort.readStringUntil('\n');         // read it and store it in val

        if (val != null) {
        //println(val); //print it out in the console
        myServer.write(val);
    }
}

void endTransmission() {
    myServer.write(eol);
}

void keyPressed() {
    if (key == ESC) {
        background(255, 0, 0);
        endTransmission();
        myServer.stop();
        myServer = null;
    }
}
