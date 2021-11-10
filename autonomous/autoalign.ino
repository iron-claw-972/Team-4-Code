//note: everything in cm

const int leftPing = 9;
const int leftEcho = 8;
const int rightPing = 11;
const int rightEcho = 10;

boolean change = false;

float leftDuration, rightDuration;
float leftCM, rightCM;

void setup() {
  Serial.begin(9600);
  
  pinMode(leftPing, OUTPUT);
  pinMode(leftEcho, INPUT);
  pinMode(rightPing, OUTPUT);
  pinMode(rightEcho, INPUT);
  
  digitalWrite(leftPing, LOW);
  digitalWrite(rightPing, LOW);
  delayMicroseconds(2);
}

void loop() {
  delayMicroseconds(2);
  digitalWrite(leftPing, HIGH);
  digitalWrite(rightPing, HIGH);
  delayMicroseconds(10);
  digitalWrite(leftPing, LOW);
  digitalWrite(rightPing, LOW);
  
  if(change) {
    leftDuration = pulseIn(leftEcho, HIGH);
    leftCM = (leftDuration / 29) / 2;
  } else {
    rightDuration = pulseIn(rightEcho, HIGH);
    rightCM = (rightDuration / 29) / 2;
  }
   
  //Serial.println("Left: " + String(leftCM) + ", Right: " + String(rightCM));
  Serial.println(calcDegrees());
  //Serial.println(calcRotation()) + calcRotation() > 0 ? " left" : " right";
  change = !change;
  delay(50);
}

float calcDegrees() {
  float diff = rightCM - leftCM;
  float distBetween = 66; //cm

  float angle = 100 * tan(diff / distBetween);

  return angle;
}

float calcRotation() {
  float diff = rightCM - leftCM;
  float distBetween = 10; //cm
  float wheelBase = 66; //cm

  float angle = 100 * tan(diff / distBetween);

  float circumference = (wheelBase * 2) * PI;
  float angleDiff = (angle / 360);

  return circumference * angleDiff;
}
