package com.pi4j.test.devices.dht22;

import com.pi4j.context.Context;
import com.pi4j.Pi4J;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfig;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.spi.Spi;
import com.pi4j.io.spi.SpiConfig;
import com.pi4j.util.Console;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Context pi4j = Pi4J.newAutoContext();
        var console = new Console();
        System.out.println("----------------------------------------------------------");
        System.out.println("PI4J PROVIDERS");
        System.out.println("----------------------------------------------------------");
        pi4j.providers().describe().print(System.out);
        System.out.println("----------------------------------------------------------");

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


        SpiConfig spiConfig = Spi.newConfigBuilder(pi4j)
                .id("SPI0")
                .name("SPI Communication")
                .address(0)  // Use SPI channel 0
                .mode(3)
                .baud(30000000)  // 30 MHz
                .build();
       var spi = pi4j.create(spiConfig);




        pi4j.shutdown();
    }
}
