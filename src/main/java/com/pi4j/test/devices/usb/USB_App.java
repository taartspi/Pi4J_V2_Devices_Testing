package com.pi4j.test.devices.usb;



import com.pi4j.Pi4J;
import com.pi4j.context.Context;


import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.io.serial.*;
import com.pi4j.test.devices.usb.SerialReader;
import com.pi4j.util.Console;

public class USB_App {


    public static void main(String[] args) throws InterruptedException {

        System.out.println("Starting serial...");

        Context pi4j = Pi4J.newAutoContext();


        //Object StopBits;
        Serial serial = pi4j.create(Serial.newConfigBuilder(pi4j)
                .baud(Baud._4800)
                .dataBits_8()
                .parity(Parity.NONE)
                .stopBits(StopBits._1)
                .flowControl(FlowControl.NONE)
                .id("gps port")
                .device("/dev/ttyAMA0") //USB0")
                .provider("pigpio-serial") //. --  //Note I commended this out as my serial is on USB port!  Different val needed?
                .build());

        System.out.println("serial " + serial);
        serial.open();

        System.out.println("about to create runnable");


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Waiting till serial port is open");
                while (!serial.isOpen()) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("retry...");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                //opened now!
                System.out.println("serial port is open!");
                var console = new Console();

                // Start a thread to handle the incoming data from the serial port
                SerialReader serialReader = new SerialReader(console,serial);
                Thread serialReaderThread = new Thread(serialReader, "SerialReader");
                serialReaderThread.setDaemon(true);
                serialReaderThread.start();

            }
        };
        System.out.println("about to start runnable");
        runnable.run();
    }

}
