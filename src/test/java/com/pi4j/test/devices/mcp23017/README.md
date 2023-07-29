
Using Pi 2.3.0-SNAPSHOT
openjdk version "11.0.18" 2023-01-17


                    1       28
                    2       27
                    3       26  
                    4       25
                    5       24
                    6       23
                    7       22   GPA1  -----   make/break c connection to 3.3
                    8       21   GPA0  ++++LED--/\/\/\/\/\/----  GRD Pi
Pi 3.3          Vdd 9       20                       330 ohm
Pi GRD          Vss 10      19   _____                      
                    11      18   RESET   3.3 Pi
Pi SCL          SCK 12      17   A2      Grd Pi
Pi SDA          SDA 13      16   A1      Grd Pi
                    14      15   A0      Grd Pi




CQRobot MCP23017

Pi SCL                      Blue
Pi SDA                      Green
Pi Gnd                      Black
Pi 3.3                      Red

PA1  Lead to connect/disconnect from 3.3v
PA0  ++++LED--/\/\/\/\/\/----  Gnd pin

Use  i2cdetect -y 1  shows I2C device address to use in the program.


Test case executes and LED blinks on/off as expected.
Strapping PA1 used to read low/high input state 