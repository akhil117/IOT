#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>  
#include "DHT.h"
#include <IRsend.h>
#define IR_LED D1
IRsend irsend(IR_LED);
#define FIREBASE_HOST "iotfirebaseproject-8f0c3.firebaseio.com"
#define FIREBASE_AUTH "nim2GI1La5UqBXeUbUTX4MM8c7jtg42FOHzOKDqc"
#define WIFI_SSID "Sarath Chandra"
#define WIFI_PASSWORD "qwerty12"

String email="saisarathganti@gmail.com";
String password="1234567890";

// Pin Definitions
#define DHTPIN D2 
#define DHTTYPE DHT11
int sensorValue;
int isSmokepin = D4;
int isFlamePin = D3;
int isFlame = HIGH;
int isSmoke = HIGH;
DHT dht(DHTPIN, DHTTYPE);
bool smoke1 = 0;
bool smoke2 = 0;
bool flame1 = 0;
bool remote=LOW;
int leng=email.length()-10;

String tru=email.substring(0,leng);
String fin=tru+password;
uint16_t Samsung_power_toggle[71] = {
    38000, 1,  1,  170, 170, 20, 63, 20, 63, 20, 63,  20, 20, 20, 20,
    20,    20, 20, 20,  20,  20, 20, 63, 20, 63, 20,  63, 20, 20, 20,
    20,    20, 20, 20,  20,  20, 20, 20, 20, 20, 63,  20, 20, 20, 20,
    20,    20, 20, 20,  20,  20, 20, 20, 20, 63, 20,  20, 20, 63, 20,
    63,    20, 63, 20,  63,  20, 63, 20, 63, 20, 1798};
void setup() 
{
    Serial.begin(9600);
    while (!Serial) ;
    Serial.println("start");
    WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
    Serial.print("connecting");
    while (WiFi.status() != WL_CONNECTED) {
      Serial.print(".");
      delay(500);
    }
    Serial.println();
    Serial.print("connected: ");
    Serial.println(WiFi.localIP());
    pinMode(isSmokepin, INPUT);
    pinMode(isFlamePin, INPUT);
    irsend.begin();
    dht.begin();
    Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
    
}

void loop() 
{
  smokeRead();
  smokeDetect();
  flameDetect();
  if (flame1 == 1 || smoke1 == 1 || smoke2 == 1) {
    Serial.println("Warnig, Warning, Warning");
    delay(5000);
  }
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  float f = dht.readTemperature(true);
  if (isnan(h) || isnan(t) || isnan(f)) {
    Serial.println("Failed to read from DHT sensor!");
  }
  float hif = dht.computeHeatIndex(f, h);
  float hic = dht.computeHeatIndex(t, h, false);
  int light=Firebase.getInt("users/"+fin+"/BedRoom/light");
  if(light==0){
    Serial.write(Dx,HIGH);
  }
  else{
    Serial.write(Dx,LOW);
  }
  Firebase.setInt("users/"+fin+"/temperature",t);
  Firebase.setInt("users/"+fin+"/humidity",h);
  Serial.print("Humidity: ");
  Serial.print(h);
  Serial.print(" %\t");
  Serial.print("Temperature: ");
  Serial.print(t);
  Serial.print(" *C ");
  Serial.print(f);
  Serial.print(" *F\t");
  Serial.print("Heat index: ");
  Serial.print(hic);
  Serial.print(" *C ");
  Serial.print(hif);
  Serial.println(" *F");
  
  if(remote==HIGH){
      Serial.println("Toggling power");
      #if SEND_GLOBALCACHE
        irsend.sendGC(Samsung_power_toggle, 71);
      #else   // SEND_GLOBALCACHE
        Serial.println("Can't send because SEND_GLOBALCACHE has been disabled.");
      #endif  // SEND_GLOBALCACHE
        delay(10000);
  }
  
  delay(2000);
      
}

//////////////////////////////////  

void smokeDetect() {
  isSmoke = digitalRead(isSmokepin);
  if (isSmoke == LOW) {
    smoke1 = 1;
    Serial.println("SMOKE, SMOKE, SMOKE");
    Firebase.setInt("users/"+fin+"/DigitalSmoke",1);
  } else {
    smoke1 = 0;
    Firebase.setInt("users/"+fin+"/DigitalSmoke",0);
    Serial.println("NO SMOKE");
  }
}

void flameDetect() {
  isFlame = digitalRead(isFlamePin);
  if (isFlame == LOW) {
    flame1 = 1;
    Serial.println("FLAME, FLAME, FLAME");
  } else {
    flame1 = 0;
    Serial.println("no flame");
  }
}

void smokeRead() {
  sensorValue = analogRead(A0);
  Serial.println(sensorValue);
  if (sensorValue > 80) {
    smoke2 = 1;
    Serial.println("SMOKE, SMOKE, SMOKE");
    Firebase.setInt("users/"+fin+"/AnalogSmoke",1);
  } else {
    smoke2 = 0;
    Firebase.setInt("users/"+fin+"/AnalogSmoke",0);
    Serial.println("NO SMOKE");
  }
}
