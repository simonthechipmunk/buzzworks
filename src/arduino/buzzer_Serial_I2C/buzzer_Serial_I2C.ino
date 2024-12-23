/*
  Connection Service for I²C Buzzer System. I²C <-> RS232
  
  Basic Command Structure:  
    address:action.value
 
 Created 5. Nov. 2016
 by Simon Junga (simonthechipmunk)
 */

#include <avr/wdt.h>
#include <Wire.h>


//variables
String serial_inputString = "";
String wire_action = "";
String wire_address = "";
String wire_value = "";
boolean readComplete = false;

const byte i2cDelay = 2;

const int dev_size = 11;
const byte start_addr = 30; // start_addr + 10 are scanned for buzzers
byte devices[dev_size] = {0};

boolean buzzed = false;
int count = 0;


//timers
const byte timers_length = 1;
enum timers{scan};
unsigned long timers[timers_length] = {0};
unsigned long currentsysTime = 0;

//counters
const byte counters_length = 1;
enum counters{};
byte counters[counters_length] = {0};





//setup
void setup()
{
  //init serial port at 9600 baud
  Serial.begin(9600);
  
  //init I²C
  Wire.begin(80);                //join i2c bus with address #80
  Wire.setWireTimeout(30000, false);   // set I2C timeout to 30ms
  //Wire.onRequest(wireRequest);   //register event for master request
  //Wire.onReceive(wireReceive);   //register event for receiving data

  // enable watchdog timer
  wdt_enable(WDTO_250MS);
}





//loop
void loop()
{
    
  //update system run-time
  currentsysTime = millis();
  
  //I²C scanner
  if(timers[scan] + 5000 < currentsysTime){
    
    byte error, address;
    int nDevices;
 
    nDevices = 0;
    for(address = start_addr; address < start_addr+dev_size-1; address++ )
    {
      // The i2c_scanner uses the return value of
      // the Write.endTransmisstion to see if
      // a device did acknowledge to the address.
      Wire.beginTransmission(address);
      error = Wire.endTransmission();
 
      if (error == 0)
      {
       //I2C device found at address
        devices[nDevices%10] = address;
 
        nDevices++;
      }
      else if (error==4)
      {
       //Unknown error at address
      }   
    }
    
    //fill with 0
    for(int i = nDevices; i<dev_size-1; i++){
      devices[i] = 0;
    }
    
    timers[scan] = currentsysTime;
 
  }
  
  
  //poll buzzers
  if(devices[count] > 0){ //arraysize is 11. last item is always 0
    
    byte data = 0;
    i2c_request(data, devices[count]);
    if(data == 1 && !buzzed){
      
      buzzed = true;
      
      String s = "";
      s += devices[count];
      s += ":is.buzzed";
      Serial.println(s);
      
      transmit("active", "1", devices[count]);
    }
    
    count++;
  }
  else{
    count = 0;
  }
    
  //receive Serial input  
  while (Serial.available() > 0 && readComplete == false) {
    
    
    // read the bytes incoming from the client:
    char inChar = (char)Serial.read();
      
    if(inChar == 10)
        //stop reading if NewLine is sent (ASCII 10, '\n' or ctrl-J)
    { 
       int index1 = serial_inputString.indexOf(':');                     // get index of separator char
       int index2 = serial_inputString.indexOf('.');                     // get index of separator char
       wire_address = serial_inputString.substring(0,index1);            // store string before ":" to string
       wire_action = serial_inputString.substring(index1+1,index2);      // store string between ":" and "." to string
       wire_value = serial_inputString.substring(index2+1);              // store string after "." to string
         
       readComplete = true; // set the command conversion to completed
      

    }
    
    serial_inputString += inChar; // add the next inChar to the string (concat)
  }

    
    
  //process received data
  if(readComplete == true){
        
    //send command to buzzer(s)
    if((wire_address == "all" && wire_action == "buzz" && wire_value == "reset")
        || (wire_action == "mode" && wire_value == "0")){
      buzzed = false;
      
      int n = 0;
      while(devices[n] > 0){ //arraysize is 11. last item is always 0
    
        transmit("active", "0", devices[n]);    
        n++;
      }
    }
    if(wire_address == "all" && wire_action == "get" && wire_value == "online"){
      buzzed = false;
      
      int n = 0;
      while(devices[n] > 0){ //arraysize is 11. last item is always 0    
        
        String s = "";
        s += devices[n];
        s += ":is.online";
        Serial.println(s);
        n++;
      }
      
      String s = "";
      s += n;
      s += ":devices.online";
      Serial.println(s);
    }
    else if(wire_address == "all"){
      int n = 0;
      while(devices[n] > 0){ //arraysize is 11. last item is always 0
    
        transmit(wire_action, wire_value, devices[n]);    
        n++;
      }
    }
    else{         
      byte address = wire_address.toInt();
      transmit(wire_action, wire_value, address);
    }
    
    
    //reset
    serial_inputString = "";
    readComplete = false;
  }
  
}





//functions

//transmission to buzzer
void transmit(String command, String value, const byte address){
  
  if(value.substring(0,2) == "0x"){ //detect HEX 
    long l = strtol(value.substring(2).c_str(), NULL, 16); // remove '0x' from HEX String and convert to long
    value = "";
    value += l;
  }
    
  i2c_transmit(command + "." + value, address);
}





//I²C requests/transmits
void i2c_request(byte &data, const int &address){
  
  Wire.requestFrom(address, 1);    // request 1 byte from slave device
  if(Wire.available() == 1){       // only accept appropriate amount of data
    
    //return received byte
    data = (byte)Wire.read();
  }
  
}

void i2c_transmit(const String keyword, const int &address){
  
  delay(i2cDelay);
  Wire.beginTransmission(address);  // transmit to device
  Wire.print(keyword);              //send keyword
  Wire.endTransmission();           // stop transmitting
  delay(i2cDelay);
  
}
