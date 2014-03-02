
//jeffhe
//Hacknc1!
// Pin 13 has an LED connected on most Arduino boards.
// give it a name:

int photo = A1;
int red = 9;
int green = 10;
int tempPin = A0;

// the setup routine runs once when you press reset:
void setup() {                
  // initialize the digital pin as an output.
    
    
  pinMode(photo, INPUT);
  pinMode(red, OUTPUT);  
  pinMode(green, OUTPUT);  
  Serial.begin(9600);
}

// the loop routine runs over and over again forever:
void loop() {

  /*
  *Section for temp sensing
  */
  float temperature = getVoltage(tempPin);
   temperature = (temperature - .5) * 100;          
   float F = (temperature*9/5) +32;
   Serial.print("The temperature in Farienheit is: ");
   Serial.println(F); //F is the final usable data
                                                  
  
  
    int sensorValue = analogRead(photo);
    //print out the value you read:
   Serial.println(sensorValue);
  if (sensorValue < 400)
  { 
   digitalWrite(red, HIGH); //Wire has been tripped
   //Insert email
   digitalWrite(green, LOW);
  }

  else
  {
    digitalWrite(red, LOW);
    digitalWrite(green, HIGH);
  }  
}


/*
 * getVoltage() - returns the voltage on the analog input defined by
 * @param pin
 */
float getVoltage(int pin){
 return (analogRead(pin) * .004882814); //converting from a 0 to 1024 digital range
                                        // to 0 to 5 volts (each 1 reading equals ~ 5 millivolts
}
