package com.pi4j.test.devices.dht22;

import com.pi4j.context.Context;
import com.pi4j.Pi4J;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.gpio.digital.DigitalState;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Context pi4j = Pi4J.newAutoContext();

        DigitalOutputConfig config = DigitalOutput.newConfigBuilder(pi4j)
                .id("LED")
                .name("Simple LED Control")
                .address(27)
                .shutdown(DigitalState.LOW)
                .initial(DigitalState.LOW)
                .build();

        Thread.sleep(1000);

        var relay = pi4j.create(config);
        System.out.println(relay.state());

        relay.high();
        relay.low();
        relay.state(DigitalState.LOW);

        pi4j.shutdown();
    }
}
