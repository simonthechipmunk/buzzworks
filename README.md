# BuzzWorks - Software for controlling Arduino based RGB I²C Quiz Game Buzzers
![BuzzWorks](/Screenshot.png?raw=true "Main Window")

This Java Application is meant to run on a touchscreen to give the "Gamemaster" easy control over the participating teams.
It controls the connected Hardware Buzzers and manages Teams (Their Points, Assigned Name, Teamcolor).


## License
This program is free software; you can redistribute it and/or modify it
under the terms of the GNU General Public License as published by the Free
Software Foundation; either version 2 of the License, or (at your option)
any later version.

Icons from the GNOME Project are licenced under the Creative Commons Attribution-Share Alike 3.0
United States License. To view a copy of this licence, visit
http://creativecommons.org/licenses/by-sa/3.0/
When attributing the artwork, using "GNOME Project" is enough. 
Please link to http://www.gnome.org where available.

## Features
* Manage Teams (Name, Points, Color) through GUI
* Manage Buzzers (Animation Mode, Lock, Colors) through GUI
* Additional Window "Team Chart" to display teams and points on additional screen
* Two Games implemented ("Reverse Music", "Who is it?")
* Test Mode for running the Application without Hardware Buzzers

## System Requirements / Dependencies
**Minimum**
* Java 8 (openjdk-8-jre)
* javaFX (openjfx, libopenjfx-java)
* Screen (1024x768)

**Recommended**
* Java 8 (openjdk-8-jre)
* javaFX (openjfx, libopenjfx-java)
* TouchScreen (1024x768) for Gamemaster
* Additional Screens/Projectors for GameWindow and TeamChart



## Setup
**The basic setup**

JavaBuzzWorks <-> Serial RS232 <-> Arduino(Atmega328) <-> I²C Bus <-> Buzzers(ATTiny85)

The Arduino is the connection point between I²C and RS232, handles buzzers and distributes messages between them and the PC.

Arduino Sketches for Buzzers and the Message-server can be found in the /src/arduino folder.
A short description of the message structure is written down in Commands.md

The Buzzers are fairly easy to make and quite universal as they feature 15 addressable WS2812b LEDs and 2 digital output pins
for data transmission. Using those for software based I²C means the system becomes highly flexible (number of buzzers, remotely assigning teamcolors, setting animations, locking specific teams, ...).
A detailed instruction Guide on how to make them will follow as soon as I get the time to do it ;) (link will be included here)

**How to start the program and add music/pictures to the games**

You can use the prepacked BuzzWorks.zip. Simply unpack it and run the shell script "BuzzWorks" inside the folder (it
essentially just runs "java -jar BuzzWorks.jar" but makes sure our current directory is set correctly).

To run the program without hardware Buzzers simply check "Test Mode" on the upcoming connection window. This creates 4 teams
and an additional window with 4 buttons to emulate the buzzers. (Reaction time is quite slow on these as they use
pipedIOstreams to emulate the serial Buzzers. Actual hardware Buzzers are responding infinitely faster than this)

To use the integrated games, you need to add your own files to the corresponding folders. Games will automatically store which images/songs have already been used when the application is closed. To discard these configs tick the appropriate checkbox on startup:

"Reverse Music" (Try guessing songs when they are played in reverse):

Add two audio files (.wav) for each track you want to play to the "ReverseMusic" folder. One that plays it forward and one that
plays in reverse and use the following namescheme:

	TITLE_ARTIST_(+).wav
	TITLE_ARTIST_(-).wav
	#Example: Hot n Cold_Katy Perry_(+).wav
	
"Who is it?" (Guess names of people/objects/animals... displayed on the pictures):

Add one image file (.png, .jpg) for each image you want to display to the "WhoIsIt" folder and use the following namescheme:

	NAME.png
	#Example: Abraham Lincoln.png




This Application makes use of third party libraries to make this work. Thanks to authors and companies behind those:

**Arduino**

Adafruit NeoPixel (WS2812b) [https://github.com/adafruit/Adafruit_NeoPixel]

TinyWire I²C for ATTiny [https://github.com/rambo/TinyWire/tree/master/TinyWireS]

ATTiny Hardware Support for Arduino IDE [http://highlowtech.org/?p=1695]


**Java**

RxTx (BuiltIn)


**Icons**

Icons are taken from the GNOME Project [http://www.gnome.org]
(to be specific: the symbolic-icons [https://github.com/GNOME/gnome-icon-theme-symbolic])

## Missing features





## Incomplete features





## Known issues
*(bug) Terminating the Application leaves a stale lock file on the serial port.

*(info) This is in no way professional. Expect the unexpected. Resizing Windows may cause Eye-Cancer. I'm a java-swing noob.








## Change log
**Version 1.0 (14-11-2016)**
* first public version

**Version 1.1 (20-11-2016)**
* added TeamChart
* added TestMode
* added Game "Reverse Music"
* added Game "Who is it?"
* make this program fancy again (added icons from the gnome project)
* minor bugfixes

**Version 1.2 (29-11-2016)**
* added persistent config files for Games
* fixed textscaling for long songtitles/names
* fixed various graphical issues

