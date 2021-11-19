#include <stdio.h>
#include <string.h>
#include <math.h>
#include <Wire.h>


// These define which pins are connected to what device on the virtual bioreactor
//
const byte lightgatePin  = 2;
const byte stirrerPin    = 5;
const byte heaterPin     = 6;
const byte thermistorPin = A0;
const byte pHPin         = A1;

// The PCA9685 is connected to the default I2C connections. There is no need
// to set these explicitly.

//temp constants
double Tk, Tc, Vo; 

//stirrer constants
volatile int count;
volatile int timeold;
volatile int rpm;
volatile int newtime;
void counts();

//ph constants 
double phv;

//char buf[50];
//sprintf(buf, "%d\n", 666);
//Serial.write(buf);

void setup() {
  Serial.begin(9600);
  Serial.println("Hello World");

  pinMode(lightgatePin,  INPUT);
  pinMode(stirrerPin,    OUTPUT);
  pinMode(heaterPin,     OUTPUT);
  pinMode(thermistorPin, INPUT);
  pinMode(pHPin,         INPUT);

  Wire.beginTransmission(0x40);
  Wire.write(0x00);
  Wire.write(0x21);
  Wire.endTransmission(); 

  analogWrite(heaterPin, 255);

  //gui setup
  char buf[50];
  sprintf(buf, "%d\n", 666);
  Serial.write(buf);

  //stirrer setup
  analogWrite(stirrerPin, 100);
  attachInterrupt(0, counts, FALLING);
  rpm = 0;
  timeold = 0;
  count = 0;
}

double getTemperature() {
  Vo = analogRead(A0);
  Tk = (-0.1225) * (Vo - 2950);
  Tc = Tk - 273.15;
  return Tk;
}

void startHeating() {
  analogWrite(heaterPin, 255);
}

void stopHeating() {
  analogWrite(heaterPin, 0);
}

void checkHeating() {
  float temp = getTemperature();
  if (temp > 306.0) stopHeating();
  if (temp < 301.0) startHeating();
}

unsigned int getRPM() {
  detachInterrupt(0);
  newtime = millis() - timeold;
  rpm = ((30*1000 / newtime) * count);
  timeold = millis();
  count = 0;
  return rpm;
}

void counts() {
  count++; 
}

void startStirring() {
  analogWrite(stirrerPin, 130);
}

void stopStirring() {
  analogWrite(stirrerPin, 0);
}

void checkStirring() {
  unsigned int rpm = getRPM();
  if (rpm < 900.0) startStirring();
  if (rpm > 1100.0) stopStirring();
}

double getpH() {
  phv = analogRead(A1);
  double phX = (4.2)*(log(phv)-4.8);
  return phX;
}

void addAcid() {
  Wire.beginTransmission(0x40);
  Wire.write(0x06); // write register value
  Wire.write(0x01); // write data
  Wire.write(0x00);
  Wire.write(0x00);
  Wire.write(0x04);
  Wire.endTransmission(); 
}

void addBase() {
  Wire.beginTransmission(0x40);
  Wire.write(0x0A); // write register value
  Wire.write(0x01); // write data
  Wire.write(0x00);
  Wire.write(0x00);
  Wire.write(0x04);
  Wire.endTransmission(); 
}

void checkpH() {
  float pH = getpH();
  if (pH > 5.10) addAcid();
  if (pH < 4.80) addBase();
}

void loop() {
  double T = getTemperature();
  Tc = T - 273.15;
  checkHeating();
  double pH = getpH();
  checkpH();
  unsigned int rpm = getRPM();
  attachInterrupt(0, counts, FALLING);
  //checkStirring(); only use this to set the rpm for stirring 
  // for now its set at 1200 rpm
  Serial.print("Temperature: ");
  Serial.print(Tc); Serial.print(" deg. C ");
  Serial.print(" RPM: ");
  Serial.print(rpm);
  Serial.print(" pH: ");
  Serial.print(pH);
  Serial.println();
  delay(1000);
}
