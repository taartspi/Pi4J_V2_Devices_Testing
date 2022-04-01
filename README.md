# Pi4J_V2-TemperatureSensor

Implement Java Interface and provider for Temperature Sensor using a BMP280

!!!  NOTE !!!
This requires an update to the pi4j-core. At this time the update has not been reviewed or merged. So, if you
want an implementation that doesn't require core updates, see: https://github.com/Pi4J/pi4j-device-tca9548
The package com.pi4j.devices.bmp280 only requires the released <pi4j.version>2.2.0-SNAPSHOT</pi4j.version>. 
Also, this package does not use any other code within my repo.

The example program has the assumption the BMP280 is connected to Pi bus 1, and the device is configured 
to operate as device address 0x76.  If these assumptions are not possible, modify the BMP280I2cExample program
to use your specific bus and device address.



1. mvn clean package
2. cd target/distribution
3. sudo ./runBMP280.sh


Will create the BMP280 provider and call setup to create a BMP280Device instance
Call the various temperature and pressure methods defined in the Sensor interface.

No parameters are required.  However, if 'any' parameter value is supplied, the example uses the create pattern 
for device instantiation, otherwise provider setup is used.


The file BMP280.pdf documents a method of connecting an Adafruit BMP280 to a Pi.
