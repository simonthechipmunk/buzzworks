# BuzzWorks - Software for controlling Arduino based RGB I²C Game Buzzers
![BuzzWorks](/Screenshot.png?raw=true "Main Window")

This Java Application is meant to run on a touchscreen to give the "Gamemaster" easy control over the participating teams.
It controls the connected Hardware Buzzers and manages Teams (Their Points, Assigned Name, Teamcolor).

This program is free software; you can redistribute it and/or modify it
under the terms of the GNU General Public License as published by the Free
Software Foundation; either version 2 of the License, or (at your option)
any later version.
 
 
 
## Features
* Manage Teams (Name, Points, Color) through GUI
* Manage Buzzers (Animation Mode, Lock, Colors) through GUI





## Setup
The basic setup:

JavaBuzzWorks <-> Serial RS232 <-> Arduino(Atmega328) <-> I²C Bus <-> Buzzers(ATTiny85)

The Arduino is the connection point between I²C and RS232, handles buzzers and distributes messages between them and the PC.

Arduino Sketches for Buzzers and the Message-server can be found in the /src/arduino folder.
A short description of the message structure is written down in Commands.md

The Buzzers are fairly easy to make and quite universal as they feature 15 addressable WS2812b LEDs and 2 digital output pins
for data transmission. Using those for software based I²C means the system becomes highly flexible (number of buzzers, remotely assigning teamcolors, setting animations, locking specific teams, ...).
A detailed instruction Guide on how to make them will follow as soon as I get the time to do it ;) (link will be included here)


This Application makes use of third party libraries to make this work. Thanks to authors and companies behind those:

**Arduino**

Adafruit NeoPixel (WS2812b) [https://github.com/adafruit/Adafruit_NeoPixel]

TinyWire I²C for ATTiny [https://github.com/rambo/TinyWire/tree/master/TinyWireS]

ATTiny Hardware Support for Arduino IDE [http://highlowtech.org/?p=1695]


**Java**

RxTx (BuiltIn)



## Missing features
* tab with list of teams and points, external window with points list to show via projector
* actual games to play





## Incomplete features





## Known issues
*(bug) Terminating the Application leaves a stale lock file on the serial port.








## Change log
**Version 1 (14-11-2016)**
* first public version


