package com.pi4j.plugin.bmp280.i2c.test;


import com.pi4j.Pi4J;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.sensor.Sensor;
import com.pi4j.plugin.linuxfs.provider.i2c.LinuxFsI2CProvider;
import com.pi4j.plugin.temperaturesensor.bmp280.BMP280Device;
import com.pi4j.plugin.temperaturesensor.bmp280.BMP280Provider;


public class BMP280I2cExample {

    private static final int BMP280_I2C_ADDRESS = BMP280Device.DEFAULT_ADDRESS; // 0x20
    private static final int BMP280_I2C_BUS = BMP280Device.DEFAULT_BUS;

    /**
     * Sample application using MCP23017 GPIO expansion chip.
     *
     * @param args an array of {@link java.lang.String} objects.
     * @throws java.lang.Exception if any.
     */
    public static void main(String[] args) throws Exception {


        // ------------------------------------------------------------
        // Initialize the Pi4J Runtime Context
        // ------------------------------------------------------------
        // Before you can use Pi4J you must initialize a new runtime
        // context.
        //
        // The 'Pi4J' static class includes a few helper context
        // creators for the most common use cases.  The 'newAutoContext()'
        // method will automatically load all available Pi4J
        // extensions found in the application's classpath which
        // may include 'Platforms' and 'I/O Providers'

        var pi4j = Pi4J.newContextBuilder().add(
                BMP280Provider.newInstance(),
                LinuxFsI2CProvider.newInstance()).build();


        // print installed providers
        System.out.println("----------------------------------------------------------");
        System.out.println("PI4J PROVIDERS");
        System.out.println("----------------------------------------------------------");
        pi4j.providers().describe().print(System.out);
        System.out.println("----------------------------------------------------------");

        // create I2C instance for communication with BMP180

        I2C bmp180_i2c = null; //pi4j.i2c().create(BMP180_I2C_BUS, BMP180_I2C_ADDRESS);

        // if we don't have an immediate reference to the actual provider,
        // we can obtain it from the Pi4J context using it's ID string
        BMP280Provider provider = pi4j.provider(BMP280Provider.ID);



        var bmpDev =  provider.setup(pi4j,provider,BMP280_I2C_BUS, BMP280_I2C_ADDRESS);


       bmpDev.initSensor();
        System.out.println("----------------------------------------------------------");

        double reading1 = bmpDev.temperatureC();
        System.out.println(" Temperatue C = " + reading1);

       double reading2 = bmpDev.temperatureF();
        System.out.println(" Temperatue F = " + reading2);

        double press1 = bmpDev.pressurePa();
        System.out.println(" Pressure Pa = " + press1);

        double press2 = bmpDev.pressureIn();
        System.out.println(" Pressure InHg = " + press2);

        double press3 = bmpDev.pressureMb();
        System.out.println(" Pressure mb = " + press3);


        var BMP280I2cConfig = Sensor.newConfigBuilder(pi4j)
                .id("Sensor")
                .bus(BMP280_I2C_BUS)
                .address(BMP280_I2C_ADDRESS)
                .name("BMP280Sensor")
                .provider(BMP280Provider.ID)
                .build();

        var bmpSensor = provider.create(BMP280I2cConfig);





        // Shutdown Pi4J
        pi4j.shutdown();
    }

}