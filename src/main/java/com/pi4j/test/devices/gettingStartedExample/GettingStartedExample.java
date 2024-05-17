package com.pi4j.test.devices.gettingStartedExample;


import com.pi4j.Pi4J;
import com.pi4j.io.gpio.digital.*;

public class GettingStartedExample {

    public static void main(String[] args) throws Exception {

        var pi4j = Pi4J.newAutoContext();
        DigitalOutput output = null;
        var config = DigitalOutput.newConfigBuilder(pi4j)
                .address(21)
                .shutdown(DigitalState.HIGH)
                .provider("gpiod-digital-output");
        try {
            output = pi4j.create(config);
        } catch (Exception e) {
            System.out.println("create output failed");
        }
        output.high();
        Thread.sleep(6000);
    }
}
