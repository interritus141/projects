/*
 sketch.ino

 Stephen Hailes, Oct 2020

 This is a template sketch for the testboard simduino
  
 */

#include <stdio.h>
#include <string.h>
#include <math.h>

// These define which pins are connected to what device on the virtual bioreactor
//
const byte lightgatePin  = 2;
const byte stirrerPin    = 5;
const byte heaterPin     = 6;
const byte thermistorPin = A0;
const byte pHPin         = A1;

// The PCA9685 is connected to the default I2C connections. There is no need
// to set these explicitly.

double R2, logR2, Tk, Tc, Tf, Vo; //R2 is the thermistor resistance, Tk is the temperature in Kelvin
double R1 = 10000; //Vo is the series resistance
double Vs = 1023.0; //Vs is the voltage divider output voltage
float c1 = 0.00180018234165587, c2 = 0.000125303859529376, c3 =5.10399949700750e-07;


void setup() {
  Serial.begin(9600);
  Serial.println("Hello World");

  pinMode(lightgatePin,  INPUT);
  pinMode(stirrerPin,    OUTPUT);
  pinMode(heaterPin,     OUTPUT);
  pinMode(thermistorPin, INPUT);
  pinMode(pHPin,         INPUT);

  // More setup...
  
}