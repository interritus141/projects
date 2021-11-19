#include <math.h>
#include <string.h>

const byte lightgatePin  = 2;
const byte stirrerPin    = 5;
const byte heaterPin     = 6;
const byte thermistorPin = A0;
const byte pHPin         = A1;

const int phS = 7; //ph of standard solution
const float Es = 512.0;  //Electrical potential at reference or standard electrode
const float F = 9.6485309*10000; //Faraday constant
const float R = 8.314510; //universal gas constant
int phv;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  Serial.println("Hello World");

  pinMode(lightgatePin,  INPUT);
  pinMode(stirrerPin,    OUTPUT);
  pinMode(heaterPin,     OUTPUT);
  pinMode(thermistorPin, INPUT);
  pinMode(pHPin,         INPUT);
}

double getTemp(int i)
{
  i=i-200;
  double temp;
  temp = log(((10240000/i)-10000));
  temp = 1/(0.001129148 + (0.000234125+(0.0000000876741 * temp * temp)) * temp);
  return temp;
}

double getpH()
{
  phv = analogRead(A1);
  float Ex = phv*5000.0/1023;
  int T = getTemp(analogRead(A0)); //This will get information from Temperature Team
  float ln = log(10)/log(2.71828);
  float phX = phS + ((Es-Ex)*0.001*F)/(R*T*ln);
  return phX;
}

void loop() {
  // put your main code here, to run repeatedly:
  float pH;
  pH = getpH();
  Serial.print("pH is ");
  Serial.print(pH);
  Serial.println();
  delay(1000);
}
