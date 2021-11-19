#include <stdio.h>
#include <string.h>
#include <math.h>

const byte lightgatePin  = 2;
const byte stirrerPin    = 5;
const byte heaterPin     = 6;
const byte thermistorPin = A0;
const byte pHPin         = A1;

double count;
unsigned long timeold;
unsigned int rpm;
int data;
void counts();

void setup() {
  Serial.begin(9600);
  Serial.println("Hello World");

  pinMode(lightgatePin,  INPUT);
  pinMode(stirrerPin,    OUTPUT);
  pinMode(heaterPin,     OUTPUT);
  pinMode(thermistorPin, INPUT);
  pinMode(pHPin,         INPUT);

  attachInterrupt(2, counts, FALLING);
  rpm = 0;
  timeold = 0;
  count = 0;
  data = 0;
}

unsigned int getRPM() {
  if (millis()-timeold == 1000){
    detachInterrupt(2);
    rpm = count*60;
    count = 0;
    timeold = millis();
    attachInterrupt(2, counts, FALLING);
    return rpm;
  }
}

void counts() {
  count = count + 0.5; //use +0.5 as there are 2 changes per revolution 
  //Serial.println(count);
}

void loop() {
  rpm = getRPM();
  Serial.print("RPM is ");
  Serial.print(rpm);
  Serial.println();
  delay(1000);
}
