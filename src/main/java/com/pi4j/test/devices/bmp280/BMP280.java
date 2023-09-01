package com.pi4j.test.devices.bmp280;

import com.pi4j.Pi4J;
import com.pi4j.devices.bmp280.BMP280Device;
import com.pi4j.devices.bmp280.BMP280DeviceI2C;
import com.pi4j.plugin.linuxfs.provider.i2c.LinuxFsI2CProvider;
import com.pi4j.util.Console;

public class BMP280 {

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
                LinuxFsI2CProvider.newInstance()).build();


        // print installed providers
        System.out.println("----------------------------------------------------------");
        System.out.println("PI4J PROVIDERS");
        System.out.println("----------------------------------------------------------");
        pi4j.providers().describe().print(System.out);
        System.out.println("----------------------------------------------------------");

        final Console console = new Console();
        console.print("==============================================================");
        console.print("startup  BMP280   ");
        console.print("==============================================================");



        var bmpDev = new BMP280DeviceI2C(pi4j, console, 1, 0x77, "info");
        bmpDev.initSensor();
        console.println("  Dev I2C detail    " + bmpDev.i2cDetail());
        console.println("  Setup ----------------------------------------------------------");


        console.println("  I2C detail : " + bmpDev.i2cDetail());

        double reading1 = bmpDev.temperatureC();
        console.println(" Temperatue C = " + reading1);

        double reading2 = bmpDev.temperatureF();
        console.println(" Temperatue F = " + reading2);

        double press1 = bmpDev.pressurePa();
        console.println(" Pressure Pa = " + press1);

        double press2 = bmpDev.pressureIn();
        console.println(" Pressure InHg = " + press2);

        double press3 = bmpDev.pressureMb();
        console.println(" Pressure mb = " + press3);


        // Shutdown Pi4J
        pi4j.shutdown();
    }

}
