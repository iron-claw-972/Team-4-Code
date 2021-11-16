#include <Wire.h>

/*
 * 
 * DRONES (Distance Reading Object Navigation Evasion System)
 * by collin w
 * updated 11/12/2021
 * 
 * note: everything in inches :(
 * for second robotics team (972)
 * 
 */

const int leftPing = 9;
const int leftEcho = 8;
const int rightPing = 11;
const int rightEcho = 10;

boolean aligning = false;
float leftDuration, rightDuration, leftInch, rightInch;

float distBetween = 10;
float wheelBase = 26;

float distFromTable = 2;
float rotPerSec = 2;

void setup() {
  Serial.begin(9600);

  Wire.begin(4);
  Wire.onReceive(receiveEvent);
  
  pinMode(leftPing, OUTPUT);
  pinMode(leftEcho, INPUT);
  pinMode(rightPing, OUTPUT);
  pinMode(rightEcho, INPUT);
  
  digitalWrite(leftPing, LOW);
  digitalWrite(rightPing, LOW);
  delay(10);
}

void loop() {
  leftDuration = 0;
  rightDuration = 0;
  
  for(int i = 0; i < 5; i++) {
    delayMicroseconds(2);
    digitalWrite(leftPing, HIGH);
    delayMicroseconds(10);
    digitalWrite(leftPing, LOW);
    
    leftDuration += pulseIn(leftEcho, HIGH);
    
    delayMicroseconds(2);
    digitalWrite(rightPing, HIGH);
    delayMicroseconds(10);
    digitalWrite(rightPing, LOW);
    
    rightDuration += pulseIn(rightEcho, HIGH);
  }

  leftInch = (leftDuration / 5) / 74 / 2;
  rightInch = (rightDuration / 5) / 74 / 2;

  if(getSerial("align")) { //MAKE SURE SERIAL IS SET TO NO LINE ENDING!
    aligning = true;
    Serial.println("------------------------------ Beginning Aligning Procedure ------------------------------");
  }

  if(aligning == true) {
    if(leftInch < distFromTable && rightInch < distFromTable && (abs(calcDegrees()) < 1)) {
      Serial.println("------------------------------ Aligning Procedure Completed ------------------------------");
      aligning = false;
      
    } else {
      Serial.println("Left: " + String(leftInch - distFromTable) + ", Right: " + String(rightInch - distFromTable));
      sendEvent("L:" + String(leftInch - distFromTable));
      sendEvent("R:" + String(rightInch - distFromTable));
    }
  }
  
  delay(200);
}

float calcDegrees() {
  float diff = rightInch - leftInch;
  float angle = 100 * tan(diff / distBetween);

  return angle;
}

float calcRotation() {
  float diff = rightInch - leftInch;
  float angle = 100 * tan(diff / distBetween);
  float circumference = (wheelBase * 2) * PI;
  float angleDiff = (angle / 360);

  return circumference * angleDiff;
}

boolean getSerial(String text2) {
  String text;
  
  while(Serial.available() > 0) {
    text = Serial.readString();
  }

  if(text == text2) {
    return true;
  } else {
    return false;
  }
}

void receiveEvent(int packet) {
  String data = "";

  while (Wire.available() > 0) {
    char n=(char)Wire.read();
    if((int) n > (int)(' '))
      data += n; 
  }
  
  if (data == "align") {
    aligning = true;
    Serial.println("------------------------------ Beginning Aligning Procedure ------------------------------");
  }
}

void sendEvent(String data) {
  Wire.beginTransmission(4);
  Wire.write(data);
  Wire.endTransmission();
}
