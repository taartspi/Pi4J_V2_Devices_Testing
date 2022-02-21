# Pi4J_V2-TemperatureSensor

Implement Java Interface and provider for Temperature Sensor using a BMP280

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