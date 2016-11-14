## Buzzer Basic Command Structure:

	action.value		//no line-ending

##Commands

I²C_RequestFrom			//returns one Byte of data 0=notbuzzed, 1=buzzed

mode.0 				//set mode to 0 (test mode)
mode.1				//set mode to 1 (mode 1-5 available)

lock.1				//enable lock (buzzer can't be pressed)
lock.0				//disable lock

active.1			//activate buzzer
active.0			//deactivate buzzer

tcolor.<uint32_t>		//set team color (main team color used to identify teams)
acolor.<uint32_t>		//set activation color (color that indicates buzzed/activated buzzer)
lcolor.<uint32_t>		//set lock color (color to indicate that this buzzer is currently locked)
icolor.<uint32_t>		//set idle color (color for team independent effects or when buzzer is idle)







## RS232<->I²C Server Basic Command Structure:

	address:action.value\n	//newline('\n') for line-ending

##Commands

all:buzz.reset			//resets all buzzers (deactivate and reset "buzzed" property
all:get.online			//prints all buzzers found on I²C in the form <address>:is.online 
				//Sends "<numberofDevices>:devices.online" to terminate
all:<action>.<value>		//sends action.value to all connected buzzers
<address>:<action>.<value>	//sends action.value to the specified address

<address>:is.buzzed		//this is sent via serial when the corresponding buzzer has been pressed
