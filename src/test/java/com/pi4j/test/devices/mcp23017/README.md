
Using Pi 2.3.0-SNAPSHOT
openjdk version "11.0.18" 2023-01-17


                    1       28
                    2       27
                    3       26  
                    4       25
                    5       24
                    6       23
                    7       22                     330 ohm
                    8       21   GPA0  ++++LED--/\/\/\/\/\/----  GRD Pi
Pi 3.3          Vdd 9       20
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

PA0  ++++LED--/\/\/\/\/\/----  Gnd pin

I did no Ax jumpers  i2cdetect -y 1  shows I2C device address 0x27

Per CQRobot spec
/*the param can be 0 to 7,the default param is 7.means the dafault device address 0x27.

Test case executes and LED blinks on/off as expected.