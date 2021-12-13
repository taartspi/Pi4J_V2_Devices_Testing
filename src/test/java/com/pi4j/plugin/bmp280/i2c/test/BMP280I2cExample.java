package com.pi4j.plugin.bmp280.i2c.test;


import com.pi4j.Pi4J;
import com.pi4j.io.i2c.I2C;
import com.pi4j.plugin.linuxfs.provider.i2c.LinuxFsI2CProvider;
import com.pi4j.plugin.temperaturesensor.bmp280.BMP280;
import com.pi4j.plugin.temperaturesensor.bmp280.BMP280Provider;


public class BMP280I2cExample {

    private static final int BMP280_I2C_ADDRESS = BMP280.DEFAULT_ADDRESS; // 0x20
    private static final int BMP280_I2C_BUS = BMP280.DEFAULT_BUS;

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



        provider.setup(pi4j,BMP280_I2C_BUS, BMP280_I2C_ADDRESS);


        float temp = provider.temperatureC();

        var BMP280I2cConfig = I2C.newConfigBuilder(pi4j)
                .id("Sensor")
                .name("BMP280Sensor")
                .device(BMP280_I2C_ADDRESS)
                .bus(BMP280_I2C_BUS)
                .provider(BMP280Provider.ID)
                .build();

        var bmpSensor = provider.create(BMP280I2cConfig);


        var bus = BMP280I2cConfig.bus();


        // Shutdown Pi4J
        pi4j.shutdown();
    }

}