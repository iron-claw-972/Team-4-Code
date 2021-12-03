/*
 * 
 * (mini) DRONES (Distance Reading Object Navigation Electronic System)
 * by collin w
 * updated 12/2/2021
 * 
 * note: everything in inches :(
 * for second robotics team (972)
 * 
 */

const int leftPing = 9;
const int leftEcho = 8;
const int rightPing = 11;
const int rightEcho = 10;
const int leftLED = 12;
const int rightLED = 13;
const int buzzer = 7;

long interval = 1000;
long prev = 0;

boolean buzzerState = false;

int distances[5] = {24, 12, 6, 3, 1};
int intervals[5] = {1000, 600, 400, 200, 100};

float leftDuration, rightDuration, leftInch, rightInch;

float distFromTable = 4;

int delayMS = 50;

//Setup
void setup() {
  Serial.begin(9600);
  
  pinMode(leftPing, OUTPUT);
  pinMode(leftEcho, INPUT);
  pinMode(rightPing, OUTPUT);
  pinMode(rightEcho, INPUT);

  pinMode(leftLED, OUTPUT);
  pinMode(rightLED, OUTPUT);
  pinMode(buzzer, OUTPUT);
  
  digitalWrite(leftPing, LOW);
  digitalWrite(rightPing, LOW);
  digitalWrite(leftLED, LOW);
  digitalWrite(rightLED, LOW);
  digitalWrite(buzzer, LOW);
  delay(10);

  digitalWrite(leftLED, HIGH);
  digitalWrite(rightLED, HIGH);
  digitalWrite(buzzer, HIGH);
  delay(100);
  digitalWrite(leftLED, LOW);
  digitalWrite(rightLED, LOW);
  digitalWrite(buzzer, LOW);
  delay(100);
  digitalWrite(leftLED, HIGH);
  digitalWrite(rightLED, HIGH);
  digitalWrite(buzzer, HIGH);
  delay(100);
  digitalWrite(leftLED, LOW);
  digitalWrite(rightLED, LOW);
  digitalWrite(buzzer, LOW);
  delay(100);

  Serial.println("(mini) DRONES has activated. Prepare yourselves.");
}

//Loop
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

  if(rightInch >= distances[0]) {
    digitalWrite(buzzer, LOW);
    buzzerState = false;
    
  } else if(rightInch >= distances[1]) {
    interval = intervals[0];
    
  } else if(rightInch >= distances[2]) {
    interval = intervals[1];
    
  } else if(rightInch >= distances[3]) {
    interval = intervals[2];
    
  } else if(rightInch >= distances[4]) {
    interval = intervals[3];
    
  } else {
    digitalWrite(buzzer, HIGH);
    buzzerState = true;
    
  }

  if(rightInch <= distances[0]) {
    if(leftInch >= rightInch) {
      digitalWrite(leftLED, HIGH);
      digitalWrite(rightLED, LOW);
      
    } else if(leftInch <= rightInch) {
      digitalWrite(leftLED, LOW);
      digitalWrite(rightLED, HIGH);
    }
  } else {
    digitalWrite(leftLED, LOW);
    digitalWrite(rightLED, LOW);
  }

  if(millis() - prev > interval) {
    buzzerState = !buzzerState;

    if(buzzerState)
      digitalWrite(buzzer, HIGH);
    else
      digitalWrite(buzzer, LOW);
  }
  
  delay(delayMS);
}
