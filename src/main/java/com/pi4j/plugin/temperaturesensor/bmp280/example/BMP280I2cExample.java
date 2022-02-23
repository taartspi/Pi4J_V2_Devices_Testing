package com.pi4j.plugin.temperaturesensor.bmp280.example;




/*
 *
 *
 *  #%L
 *  **********************************************************************
 *  ORGANIZATION  :  Pi4J
 *  PROJECT       :  Pi4J ::  Providers
 *  FILENAME      :  BMP280I2cExample.java
 *
 *  This file is part of the Pi4J project. More information about
 *  this project can be found here:  https://pi4j.com/
 *  **********************************************************************
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Lesser Public License for more details.
 *
 *  You should have received a copy of the GNU General Lesser Public
 *  License along with this program.  If not, see
 *  <http://www.gnu.org/licenses/lgpl-3.0.html>.
 *  #L%
 *
 */


import com.pi4j.Pi4J;
import com.pi4j.exception.LifecycleException;
import com.pi4j.io.sensor.Sensor;
import com.pi4j.plugin.linuxfs.provider.i2c.LinuxFsI2CProvider;
import com.pi4j.plugin.temperaturesensor.bmp280.BMP280Device;
import com.pi4j.plugin.temperaturesensor.bmp280.BMP280Provider;
import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * Class to test and demonstrate the BMP280 device.
 */
public class BMP280I2cExample {

    private static final int BMP280_I2C_ADDRESS = BMP280Device.DEFAULT_ADDRESS;
    private static final int BMP280_I2C_BUS = BMP280Device.DEFAULT_BUS;

    /**
     * Sample application using MCP23017 GPIO expansion chip.
     *
     * @param args an array of {@link java.lang.String} objects.
     *             Parms are not required. if 'any' parameter value is supplied,
     *             the example uses the create pattern for device instantiation,
     *             otherwise provider setup is used
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
        // Prior to running methods, set up control-c handler
        Signal.handle(new Signal("INT"), new SignalHandler() {
            public void handle(Signal sig) {
                System.out.println("Performing ctl-C shutdown");
                try {
                    pi4j.shutdown();
                } catch (LifecycleException e) {
                    e.printStackTrace();
                }
                Thread.dumpStack();
                System.exit(2);
            }
        });

        // if we don't have an immediate reference to the actual provider,
        // we can obtain it from the Pi4J context using it's ID string
        BMP280Provider provider = pi4j.provider(BMP280Provider.ID);


        if (args.length == 0) {
            var bmpDev = provider.setup(pi4j, provider, BMP280_I2C_BUS, BMP280_I2C_ADDRESS);
            bmpDev.initSensor();
            System.out.println("  Setup ----------------------------------------------------------");


            System.out.println("  I2C detail : " + bmpDev.i2cDetail());

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

        } else {

            var BMP280I2cConfig = Sensor.newConfigBuilder(pi4j)
                    .id("Sensor")
                    .bus(BMP280_I2C_BUS)
                    .address(BMP280_I2C_ADDRESS)
                    .name("BMP280Sensor")
                    .provider(BMP280Provider.ID)
                    .build();

            var bmpSensor = provider.create(BMP280I2cConfig);
            bmpSensor.initSensor();

            System.out.println("   Create    ----------------------------------------------------------");


            double reading1 = bmpSensor.temperatureC();
            System.out.println(" Temperatue C = " + reading1);

            double reading2 = bmpSensor.temperatureF();
            System.out.println(" Temperatue F = " + reading2);

            double press1 = bmpSensor.pressurePa();
            System.out.println(" Pressure Pa = " + press1);

            double press2 = bmpSensor.pressureIn();
            System.out.println(" Pressure InHg = " + press2);

            double press3 = bmpSensor.pressureMb();
            System.out.println(" Pressure mb = " + press3);


        }


        // Shutdown Pi4J
        pi4j.shutdown();
    }

}