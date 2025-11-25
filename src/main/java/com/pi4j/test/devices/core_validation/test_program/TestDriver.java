package com.pi4j.test.devices.core_validation.test_program;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.plugin.ffm.providers.gpio.DigitalOutputFFMProviderImpl;
import com.pi4j.plugin.ffm.providers.i2c.I2CFFMProviderImpl;
import com.pi4j.plugin.ffm.providers.pwm.PwmFFMProviderImpl;
import com.pi4j.plugin.ffm.providers.serial.SerialFFMProviderImpl;
import com.pi4j.plugin.ffm.providers.spi.SpiFFMProviderImpl;
import com.pi4j.util.Console;

public class TestDriver {
    private static Context pi4j;
    static final Console console = new Console();

    public static void main(String[] args) throws Exception {


        pi4j = com.pi4j.Pi4J.newContextBuilder()
                .add(new DigitalOutputFFMProviderImpl())
                .add(new I2CFFMProviderImpl())
                .add(new SpiFFMProviderImpl())
                .add(new PwmFFMProviderImpl())
                .add(new SerialFFMProviderImpl())
                .build();


    // print installed providers
        System.out.println("----------------------------------------------------------");
        System.out.println("PI4J PROVIDERS");
        System.out.println("----------------------------------------------------------");
        pi4j.providers().describe().print(System.out);
        System.out.println("----------------------------------------------------------");

        console.print("==============================================================");
        console.print("startup  TestDriver   ");
        console.print("=============================================================");




}
