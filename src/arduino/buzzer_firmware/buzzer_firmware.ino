/*
  Firmware for I²C Game-Buzzer with RGB WS2812 (14+1)
 
 
 * SETUP:
 * ATtiny Pin 1 = (RESET) N/U                      ATtiny Pin 2 = (D3) Data LED WS2812
 * ATtiny Pin 3 = (D4) Switch Loop                 ATtiny Pin 4 = GND
 * ATtiny Pin 5 = I2C SDA on DS1621  & GPIO        ATtiny Pin 6 = (D1) N/U
 * ATtiny Pin 7 = I2C SCK on DS1621  & GPIO        ATtiny Pin 8 = VCC (2.7-5.5V)
 
 Created 5. Nov. 2016
 by Simon Junga (simonthechipmunk)
 */

#include "TinyWireS.h"                  //wrapper class for I2C slave routines
#include <Adafruit_NeoPixel.h>          //WS2812 library
#include "WS2812_Definitions.h"

#define LED_COUNT 15
#define I2C_SLAVE_ADDR  31            //I²C slave address (Buzzers start at 31)
const int data = 3; //led data out
const int button = 4; //switch loop input

//Create an instance of the Adafruit_NeoPixel class called "leds".
Adafruit_NeoPixel leds = Adafruit_NeoPixel(LED_COUNT, data, NEO_GRB + NEO_KHZ800);

//variables
boolean test_mode0 = false;

volatile boolean buzzed = false;
volatile boolean locked = false;
volatile boolean activated = false;
volatile byte mode = 0;
volatile uint32_t teamcolor = BLUE;
volatile uint32_t activatecolor = GREEN;
volatile uint32_t lockcolor = RED;
volatile uint32_t idlecolor = 0x444444; //dim white


//timers
const byte timers_length = 6;
enum timers{m0, m1, m2, m3, m4, m5};
unsigned long timers[timers_length] = {0};
unsigned long currentsysTime = 0;

//counters
const byte counters_length = 6;
enum counters{mode0_a, mode0_b, mode1_a, mode3_a, mode3_b, mode5_a};
byte counters[counters_length] = {0};




//setup
void setup() {      

  //initialize inputs/outputs
  pinMode(data, OUTPUT);
  pinMode(button, INPUT);
  digitalWrite(button, HIGH);  

  //init I2C Slave mode
  TinyWireS.begin(I2C_SLAVE_ADDR);
  TinyWireS.onRequest(wireRequest);   //register event for master request
  TinyWireS.onReceive(wireReceive);   //register event for receiving data

  //init WS2812 LEDs
  leds.begin();
  clearLEDs();
  leds.show();
}




//loop
void loop() {
  
  //update system run-time
  currentsysTime = millis();

  //I²C
  //wireReceive();
  TinyWireS_stop_check();
  
  //global settings
  if(digitalRead(button) == HIGH){
    
    test_mode0 = true;
    if(!locked && mode != 0) buzzed = true;
  }
  
  if(locked){
    buzzed = false;
    activated = false;
  }
  
  if(activated){
    buzzed = false;
  }
    
    
  //mode specific settings 
  if(mode == 0){ //show individual pixels WHITE, RED, GREEN, BLUE when buzzer is pressed (TEST MODE)
    
    activated = false;
    locked = false;
    
    if(test_mode0 && counters[mode0_a] == 0 && timers[m0] + 80 < currentsysTime){
      
      clearLEDs();      
      leds.setPixelColor(counters[mode0_b], WHITE);
      counters[mode0_b]++;
      timers[m0] = currentsysTime;
      
      if(counters[mode0_b] == LED_COUNT){
        counters[mode0_a]++;
        counters[mode0_b] = 0;
        test_mode0 = false;
      }
    }
    else if(test_mode0 && counters[mode0_a] == 1 && timers[m0] + 80 < currentsysTime){
      
      clearLEDs();      
      leds.setPixelColor(counters[mode0_b], RED);
      counters[mode0_b]++;
      timers[m0] = currentsysTime;
      
      if(counters[mode0_b] == LED_COUNT){
        counters[mode0_a]++;
        counters[mode0_b] = 0;
        test_mode0 = false;
      }
    }
    else if(test_mode0 && counters[mode0_a] == 2 && timers[m0] + 80 < currentsysTime){

      clearLEDs();      
      leds.setPixelColor(counters[mode0_b], GREEN);
      counters[mode0_b]++;
      timers[m0] = currentsysTime;
      
      if(counters[mode0_b] == LED_COUNT){
        counters[mode0_a]++;
        counters[mode0_b] = 0;
        test_mode0 = false;
      }
    }
    else if(test_mode0 && counters[mode0_a] == 3 && timers[m0] + 80 < currentsysTime){

      clearLEDs();      
      leds.setPixelColor(counters[mode0_b], BLUE);
      counters[mode0_b]++;
      timers[m0] = currentsysTime;
      
      if(counters[mode0_b] == LED_COUNT){
        counters[mode0_a] = 0;
        counters[mode0_b] = 0;
        test_mode0 = false;
      }
    }
    
    leds.show();
   
  }
  else if(mode == 1){ //TOP-LED is Lock indicator (lockcolor). Buzzer lights fully (teamcolor). Running animation on single Pixel (activatecolor) when activated
   
    clearLEDs();
   
    for (int i=0; i<LED_COUNT-1; i++)
    {
      leds.setPixelColor(i, teamcolor);      
    }
    
    if(activated){
      leds.setPixelColor(counters[mode1_a], activatecolor);
      leds.setPixelColor((counters[mode1_a]+1)%(LED_COUNT-1), activatecolor);
      leds.setPixelColor((counters[mode1_a]+2)%(LED_COUNT-1), activatecolor);

    }
   
    if(activated && timers[m1] + 50 < currentsysTime){      
      counters[mode1_a]++;
     
      if(counters[mode1_a] == LED_COUNT-2){
        counters[mode1_a] = 0;
      }
     
      timers[m1] = currentsysTime;
    }
   
    if(locked){
      leds.setPixelColor(LED_COUNT-1, lockcolor);
    }
   
    leds.show();
   
  }
  else if(mode == 2){//LOCK: only top led lights in teamcolor. TEAMCOLOR: every second led. ACTIVATED: every second+1 blinking 
    
    clearLEDs();
    
    for (int i=1; i<LED_COUNT-1; i+=2)
    {
      leds.setPixelColor(i, teamcolor);      
    }
    
    if(activated && timers[m2] > currentsysTime ){
      for (int i=0; i<LED_COUNT; i+=2)
      {
        leds.setPixelColor(i, activatecolor);      
      }

    }
    if(timers[m2] + 800 < currentsysTime){
      timers[m2] = currentsysTime + 400;
    }
    
    if(locked){
      clearLEDs();
      leds.setPixelColor(LED_COUNT-1, teamcolor);
    }
    
    leds.show();
    
  }
  else if(mode == 3){//LOCK: only top led lights in teamcolor. TEAMCOLOR: top led. ACTIVATED: ring pulses teamcolor. IDLE: every second led lights
    
    if(activated){
      
      if(timers[m3] + 5 < currentsysTime){
        
        //leds.setBrightness(counters[mode3_a]);
        
        byte red = (teamcolor & 0xFF0000) >> 16;
        byte green = (teamcolor & 0x00FF00) >> 8;
        byte blue = (teamcolor & 0x0000FF);
        
        red = (red * counters[mode3_a]) >> 8;
        green = (green * counters[mode3_a]) >> 8;
        blue = (blue * counters[mode3_a]) >> 8;      
        
        for (int i=0; i<LED_COUNT-1; i++)
        {
          leds.setPixelColor(i, red, green, blue);     
        }
        
        if(counters[mode3_a] == 254) counters[mode3_b] = 0;
        else if(counters[mode3_a] == 0) counters[mode3_b] = 1;
        
        
        if(counters[mode3_b]) counters[mode3_a]+=2;
        else counters[mode3_a]-=2;
        
        timers[m3] = currentsysTime;               
      }   
      
    }
    else{
      
      clearLEDs();
      for (int i=0; i<LED_COUNT-1; i+=2)
      {
        leds.setPixelColor(i, idlecolor);      
      }
      
    }
      

         
    if(locked){
      clearLEDs();
    }
    
    leds.setPixelColor(LED_COUNT-1, teamcolor);
    leds.show();
    
  }
  else if(mode == 4){//LOCK: only top led lights in teamcolor. TEAMCOLOR: every second led. ACTIVATED: ring blinking teamcolor<->activatecolor
    
    clearLEDs();
    
    for (int i=1; i<LED_COUNT-1; i+=2)
    {
      leds.setPixelColor(i, teamcolor);      
    }
    
    if(activated && timers[m4] > currentsysTime ){
      for (int i=0; i<LED_COUNT-1; i++)
      {
        leds.setPixelColor(i, activatecolor);      
      }

    }
    else if(activated){
      for (int i=0; i<LED_COUNT-1; i++)
      {
        leds.setPixelColor(i, teamcolor);      
      }
    }
    if(timers[m4] + 600 < currentsysTime){
      timers[m4] = currentsysTime + 400;
    }
    
    if(locked){
      clearLEDs();
      leds.setPixelColor(LED_COUNT-1, teamcolor);
    }
    
    leds.show();
    
  }
  else if(mode == 5){//LOCK: all off. TEAMCOLOR: top led + 3 led in ring. ACTIVATED: running animation of 3 led
  
    if(activated && timers[m5] + 60 < currentsysTime){
      
      clearLEDs();      
      leds.setPixelColor(counters[mode5_a], teamcolor);
      leds.setPixelColor((counters[mode5_a]+1)%(LED_COUNT-1), teamcolor);
      leds.setPixelColor((counters[mode5_a]+2)%(LED_COUNT-1), teamcolor);
      
      counters[mode5_a]++;
      timers[m5] = currentsysTime;
      
      if(counters[mode5_a] == LED_COUNT-1){
        counters[mode5_a] = 0;
      }
    }
    else if(!activated){
      clearLEDs();
      for (int i=0; i<LED_COUNT-1; i+=5)
      {
        leds.setPixelColor(i, teamcolor);      
      }
    }
    
    
    leds.setPixelColor(LED_COUNT-1, teamcolor);
    
    if(locked){
      clearLEDs();
    }
    
    leds.show();
  }
  
 

}






//functions
void clearLEDs() //Sets all LEDs to off, but DOES NOT update the display;
{
  for (int i=0; i<LED_COUNT; i++)
  {
    leds.setPixelColor(i, 0);
  }
}

//leds.setPixelColor(i, color);
//leds.setPixelColor(i, red, green, blue);








//I²C
void wireReceive(uint8_t received_bytes){ //wire receive event


  String wire_action = "";
  String wire_value = "";

  while(TinyWireS.available()){

    char c = char(TinyWireS.receive()); //read one char from byte-stream

    if (c == '.'){ //stop if separator is found (output request)
      while(TinyWireS.available()){
        wire_value += char(TinyWireS.receive());
      }
      break;
    }

    wire_action += c; //concat string
  }
  
  
  //process received data    
  if(wire_action == "mode"){
    mode = (byte)wire_value.toInt();
    clearLEDs();
  }
  else if(wire_action == "lock"){
    locked = boolean(wire_value.toInt());
  }
  else if(wire_action == "active"){
    activated = boolean(wire_value.toInt());
  }
  else if(wire_action == "tcolor"){
    teamcolor = uint32_t(wire_value.toInt());
  }
  else if(wire_action == "acolor"){
    activatecolor = uint32_t(wire_value.toInt());
  }
  else if(wire_action == "lcolor"){
    lockcolor = uint32_t(wire_value.toInt());
  }
  else if(wire_action == "icolor"){
    idlecolor = uint32_t(wire_value.toInt());
  }
  
}
  
  

void wireRequest(){ //master request event. return 'buzzed' status variable
  
  TinyWireS.send((uint8_t)buzzed);
  buzzed = false;
}




